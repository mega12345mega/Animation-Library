package com.luneruniverse.lib.animations.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.luneruniverse.lib.animations.Animation;
import com.luneruniverse.lib.animations.Renderer;
import com.luneruniverse.lib.animations.translations.ComplexTranslation;
import com.luneruniverse.lib.animations.translations.LinearTranslation;
import com.luneruniverse.lib.animations.translations.ShowTranslation;
import com.luneruniverse.lib.animations.translations.SineTranslation;
import com.luneruniverse.lib.animations.translations.SmoothTranslation;
import com.luneruniverse.lib.animations.translations.TeleportTranslation;
import com.luneruniverse.lib.animations.translations.Translation;
import com.luneruniverse.lib.animations.visuals.Circle;
import com.luneruniverse.lib.animations.visuals.CircleOutline;
import com.luneruniverse.lib.animations.visuals.ComplexVisual;
import com.luneruniverse.lib.animations.visuals.Drawing;
import com.luneruniverse.lib.animations.visuals.GenericShape;
import com.luneruniverse.lib.animations.visuals.Square;
import com.luneruniverse.lib.animations.visuals.SquareOutline;
import com.luneruniverse.lib.animations.visuals.Text;
import com.luneruniverse.lib.animations.visuals.Visual;

public class AnimationTest {
	
	public static void main(String[] args) {
		new AnimationTest();
	}
	
	public AnimationTest() {
		
		Animation animation = new Animation(600, 600, 3000);
		animation.setBackground(Color.DARK_GRAY);
		
		Square square = new Square(Color.YELLOW, 100, 100, 100, 100, 0);
		square.setTranslation(new SineTranslation<>(300, 200, 2000));
		animation.addObject(square);
		
		Square square2 = new Square(Color.ORANGE, 300, 100, 100, 100, 1);
		square2.setTranslation(new SineTranslation<>(100, 100, 2000));
		animation.addObject(square2);
		
		Square square3 = new Square(new Color(100, 200, 255), 400, 400, 100, 100, -1);
		square3.setTranslation(new ComplexTranslation<>(Arrays.asList(
				new ShowTranslation<>(square3, 1000),
				new SineTranslation<>(400, 100, 1000),
				new SineTranslation<>(100, 100, 1000)
			)));
		animation.addObject(square3);
		
		Square square4 = new Square(Color.GREEN, 300, 400, 100, 100, 2);
		square4.setTranslation(new SmoothTranslation<>((percent) -> Math.sin(percent * 3 * Math.PI / 2 + Math.PI), 400, 400, 2000));
		animation.addObject(square4);
		
		SquareOutline square5 = new SquareOutline(Color.BLACK, 300, 400, 100, 100, 20, 3);
		square5.setTranslation(new SmoothTranslation<>((percent) -> Math.sin(percent * 3 * Math.PI / 2 + Math.PI), 400, 400, 2000));
		animation.addObject(square5);
		
		Circle circle = new Circle(Color.MAGENTA, 250, 250, 100, 100, 4);
		circle.setTranslation(new ComplexTranslation<>(Arrays.asList(
				new SineTranslation<>(250, -150, 500),
				new TeleportTranslation<>(250, 650),
				new SineTranslation<>(250, 250, 500),
				new SineTranslation<>(650, 250, 500),
				new TeleportTranslation<>(-150, 250),
				new SineTranslation<>(250, 250, 500)
			)));
		animation.addObject(circle);
		
		CircleOutline circle2 = new CircleOutline(Color.CYAN, 250, 250, 100, 100, 20, 5);
		circle2.setTranslation(new ComplexTranslation<>(Arrays.asList(
				new SineTranslation<>(650, 250, 500),
				new TeleportTranslation<>(-150, 250),
				new SineTranslation<>(250, 250, 500),
				new SineTranslation<>(250, -150, 500),
				new TeleportTranslation<>(250, 650),
				new SineTranslation<>(250, 250, 500)
			)));
		animation.addObject(circle2);
		
		GenericShape shape = new GenericShape(new Ellipse2D.Double(100, 400, 100, 100), Color.RED, 6);
		shape.setTranslation(new SineTranslation<>(400, 400, 3000));
		animation.addObject(shape);
		
		Square innerSquare;
		ComplexVisual visual = new ComplexVisual(Arrays.asList(
				innerSquare = new Square(Color.WHITE, 0, 0, 50, 50, 0),
				new Square(Color.WHITE, 50, 50, 50, 50, 0),
				new Square(Color.MAGENTA, 25, 25, 50, 50, 1)
			), 100, 300, 7);
		visual.setTranslation(new Translation<Visual>(0, 0, 2000) {
			private int scale = 5;
			
			@Override
			public void execute(Visual visual, int timePassed) {
				visual.setVisualX((int) (visual.getVisualX() + Math.random() * scale * 2 - scale));
				visual.setVisualY((int) (visual.getVisualY() + Math.random() * scale * 2 - scale));
			}
		});
		innerSquare.setTranslation(new LinearTranslation<>(50, 0, 2000));
		animation.addObject(visual);
		
		Text text = new Text(Color.WHITE, new Font("Arial", Font.BOLD, 20), "Animation Test", true, 450, 570, 10);
		animation.addObject(text);
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("C:\\Users\\Tim\\Desktop\\Test Files\\fire_layer_0.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Drawing drawing = new Drawing(Color.BLACK, img, -400, -400, 400, 400, -100);
		drawing.setTranslation(new SineTranslation<>(100, 100, 500));
		animation.addObject(drawing);
		
		animation.addSound(new File("C:\\Users\\Tim\\Desktop\\Test Files\\sound.mp3"), 1000);
		animation.addSound(new File("C:\\Users\\Tim\\Desktop\\Test Files\\sound.mp3"), 2000);
		animation.addSound(new File("C:\\Users\\Tim\\Desktop\\Test Files\\sound.mp3"), 2700);
		
		Renderer renderer = new Renderer(animation, 30);
		renderer.createFrame().setLocationRelativeTo(null);
		
		try {
			renderer.export(new File("C:\\Users\\Tim\\Desktop\\Test Files\\output.mp4"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
