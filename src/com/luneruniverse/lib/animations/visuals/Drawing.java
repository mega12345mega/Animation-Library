package com.luneruniverse.lib.animations.visuals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

public class Drawing extends Square {
	
	protected final Image image;
	
	protected Image visualImage;
	
	public Drawing(Color background, Image image, int x, int y, int width, int height, int level) {
		super(background, x, y, width, height, level);
		
		this.image = image;
	}
	public Drawing(Color background, Image image, int x, int y, int level) {
		super(background, x, y, image.getWidth(null), image.getHeight(null), level);
		
		this.image = image;
	}
	
	public void setVisualImage(Image visualImage) {
		this.visualImage = visualImage;
	}
	public Image getVisualImage() {
		return visualImage;
	}
	
	@Override
	public void reset() {
		super.reset();
		
		this.visualImage = image;
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
		g.drawImage(visualImage, visualX, visualY, visualWidth, visualHeight, null);
	}
	
}
