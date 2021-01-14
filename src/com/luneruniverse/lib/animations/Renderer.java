package com.luneruniverse.lib.animations;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.luneruniverse.lib.animations.visuals.Visual;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.ICloseEvent;
import com.xuggle.mediatool.event.IOpenCoderEvent;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IRational;

public final class Renderer {
	
	private final Animation animation;
	private final List<Visual> objects;
	private final int fps;
	private final int numFrames;
	
	public Renderer(Animation animation, int fps) {
		this.animation = animation;
		this.objects = animation.getObjects();
		this.objects.sort((a, b) -> a.getLevel() - b.getLevel());
		this.fps = fps;
		this.numFrames = animation.getLength() * fps / 1000;
	}
	
	private BufferedImage getFrame(int frame) {
		if (frame >= numFrames)
			throw new IllegalArgumentException("Invalid frame position: " + frame);
		
		BufferedImage img = new BufferedImage(animation.getWidth(), animation.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(animation.getBackground());
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		for (Visual object : objects) {
			object.update(frame * 1000 / fps);
			object.render(g);
		}
		
		g.dispose();
		return img;
	}
	
	public boolean render(Graphics g, long startTime) {
		return render(g, (int) Math.min(fps * (System.currentTimeMillis() - startTime) / 1000, numFrames - 1));
	}
	public boolean render(Graphics g, int frame) {
		g.drawImage(getFrame(frame), 0, 0, null);
		return frame == numFrames - 1;
	}
	
	private volatile Supplier<Long> startTime = new Supplier<>(-1L);
	
	@SuppressWarnings("serial")
	public JFrame createFrame() {
		if (startTime.get() > 0)
			throw new IllegalStateException("Only one frame can be opened at once");
		
		JFrame frame = new JFrame();
		frame.setTitle("Renderer");
		frame.getContentPane().setPreferredSize(new Dimension(animation.getWidth(), animation.getHeight()));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(new JComponent() {
			@Override
			public void paint(Graphics g) {
				while (startTime.get() < 0) {}
				
				if (render(g, startTime.get())) {
					frame.setVisible(false);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
		frame.setVisible(true);
		
		List<Thread> soundThreads = new ArrayList<>();
		for (Map.Entry<File, Integer> sounds : animation.getSounds()) {
			IMediaReader reader = ToolFactory.makeReader(sounds.getKey().getAbsolutePath());
			reader.addListener(ToolFactory.makeViewer(IMediaViewer.Mode.AUDIO_ONLY));
			
			soundThreads.add(new Thread(() -> {
				while (startTime.get() < 0) {}
				
				try {
					Thread.sleep(sounds.getValue());
				} catch (InterruptedException e) {
					return;
				}
				
				while (reader.readPacket() == null) {
					if (!frame.isVisible()) {
						reader.close();
						break;
					}
				}
			}));
			soundThreads.get(soundThreads.size() - 1).start();
		}
		
		new Thread(() -> {
			while (startTime.get() < 0) {}
			
			
			int sleep = 1000 / fps;
			
			while (frame.isVisible()) {
				frame.repaint();
				
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for (Thread soundThread : soundThreads)
				soundThread.interrupt();
			
			startTime.set(-1L);
		}).start();
		
		startTime.set(System.currentTimeMillis());
		
		return frame;
	}
	
	public void export(File file) throws IOException {
		if (!file.getAbsoluteFile().getParentFile().exists() && !file.getAbsoluteFile().getParentFile().mkdirs())
			throw new IOException("Unable to make containing folders");
		
		if (!file.exists())
			file.createNewFile();
		
		IMediaWriter writer = ToolFactory.makeWriter(file.getAbsolutePath());
		writer.addVideoStream(0, 0, IRational.make(fps, 1), animation.getWidth(), animation.getHeight());
		writer.addAudioStream(1, 0, 2, 44100);
		
		int numFrames = fps * animation.getLength() / 1000;
		for (int i = 0; i < numFrames; i++) {
			BufferedImage frame = new BufferedImage(animation.getWidth(), animation.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = frame.getGraphics();
			g.drawImage(getFrame(i), 0, 0, null);
			g.dispose();
			
			writer.encodeVideo(0, frame, i * 1000 / fps, TimeUnit.MILLISECONDS);
		}
		
		int arrayLength = 44100 * animation.getLength() / 1000 * 2;
		int scale = arrayLength / animation.getLength();
		short[] audio = new short[arrayLength];
		for (int i = 0; i < audio.length; i++)
			audio[i] = 0;
		for (Map.Entry<File, Integer> sound : animation.getSounds()) {
			List<Short> fileAudio = new ArrayList<>();
			IMediaReader reader = ToolFactory.makeReader(sound.getKey().getAbsolutePath());
			reader.addListener(new MediaToolAdapter() {
	            @Override
	            public void onOpenCoder(IOpenCoderEvent event) {
	            }
	            
	            @Override
	            public void onAudioSamples(IAudioSamplesEvent event) {
	            	IAudioSamples samples = event.getAudioSamples();
	            	ShortBuffer buffer = samples.getByteBuffer().asShortBuffer();
	            	short[] values = new short[buffer.remaining()];
	            	for (int i = 0; buffer.hasRemaining(); i++)
	            		values[i] = buffer.get();
	            	for (int i = 0; i < values.length; i++)
	            		fileAudio.add(values[i]);
	            }
	            
	            @Override
	            public void onClose(ICloseEvent event) {
	            }
	        });
			
	        while (reader.readPacket() == null) {
	        }
	        
	        
	        try {
		        for (int i = 0; i < fileAudio.size(); i++)
		        	audio[i + sound.getValue() * scale] += fileAudio.get(i);
	        } catch (ArrayIndexOutOfBoundsException e) {}
		}
		writer.encodeAudio(1, audio);
		
		writer.close();
	}
	
}
