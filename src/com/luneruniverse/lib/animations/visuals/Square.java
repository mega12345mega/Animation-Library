package com.luneruniverse.lib.animations.visuals;

import java.awt.Color;
import java.awt.Graphics2D;

public class Square extends Visual {
	
	protected final Color color;
	protected final int width;
	protected final int height;
	
	protected Color visualColor;
	protected int visualWidth;
	protected int visualHeight;
	
	public Square(Color color, int x, int y, int width, int height, int level) {
		super(x, y, level);
		
		this.color = color;
		this.width = width;
		this.height = height;
	}
	
	public void setVisualColor(Color visualColor) {
		this.visualColor = visualColor;
	}
	public Color getVisualColor() {
		return visualColor;
	}
	
	public void setVisualWidth(int visualWidth) {
		this.visualWidth = visualWidth;
	}
	public int getVisualWidth() {
		return visualWidth;
	}
	
	public void setVisualHeight(int visualHeight) {
		this.visualHeight = visualHeight;
	}
	public int getVisualHeight() {
		return visualHeight;
	}
	
	@Override
	public void reset() {
		super.reset();
		
		this.visualColor = color;
		this.visualWidth = width;
		this.visualHeight = height;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(visualColor);
		g.fillRect(visualX, visualY, visualWidth, visualHeight);
	}
	
}
