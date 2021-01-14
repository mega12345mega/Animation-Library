package com.luneruniverse.lib.animations.visuals;

import java.awt.Color;
import java.awt.Graphics2D;

public class SquareOutline extends Square {
	
	protected final int thickness;
	
	protected int visualThickness;
	
	public SquareOutline(Color color, int x, int y, int width, int height, int thickness, int level) {
		super(color, x, y, width, height, level);
		
		this.thickness = thickness;
	}
	
	public void setVisualThickness(int visualThickness) {
		this.visualThickness = visualThickness;
	}
	public int getVisualThickness() {
		return visualThickness;
	}
	
	@Override
	public void reset() {
		super.reset();
		
		this.visualThickness = thickness;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(visualColor);
		g.fillRect(visualX, visualY, visualWidth, visualThickness);
		g.fillRect(visualX, visualY + visualHeight - visualThickness, visualWidth, visualThickness);
		g.fillRect(visualX, visualY + visualThickness, visualThickness, visualHeight - 2 * visualThickness);
		g.fillRect(visualX + visualWidth - visualThickness, visualY + visualThickness, visualThickness, visualHeight - 2 * visualThickness);
	}
	
}
