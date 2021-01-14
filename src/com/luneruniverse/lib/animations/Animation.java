package com.luneruniverse.lib.animations;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.luneruniverse.lib.animations.visuals.Visual;

public class Animation {
	
	private final List<Visual> objects;
	private final int width;
	private final int height;
	private final int length;
	private Color bg;
	
	private final List<Map.Entry<File, Integer>> sounds;
	
	public Animation(int width, int height, int length) {
		this.objects = new ArrayList<>();
		this.width = width;
		this.height = height;
		this.length = length;
		this.bg = Color.BLACK;
		
		this.sounds = new ArrayList<>();
	}
	
	public void addObject(Visual visual) {
		this.objects.add(visual);
	}
	List<Visual> getObjects() {
		return new ArrayList<>(objects);
	}
	
	public void setBackground(Color bg) {
		this.bg = bg;
	}
	public Color getBackground() {
		return bg;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getLength() {
		return length;
	}
	
	public void addSound(File file, int pos) {
		sounds.add(new Map.Entry<File, Integer>() {
			@Override
			public File getKey() {
				return file;
			}
			@Override
			public Integer getValue() {
				return pos;
			}
			@Override
			public Integer setValue(Integer value) {
				throw new IllegalStateException("Not Supported");
			}
		});
	}
	List<Map.Entry<File, Integer>> getSounds() {
		return new ArrayList<>(sounds);
	}
	
}
