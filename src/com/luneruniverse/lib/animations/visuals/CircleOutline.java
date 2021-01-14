package com.luneruniverse.lib.animations.visuals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class CircleOutline extends Circle {
	
	protected final int thickness;
	
	protected int visualThickness;
	
	public CircleOutline(Color color, int x, int y, int width, int height, int thickness, int level) {
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
		Ellipse2D outer = new Ellipse2D.Double(visualX, visualY, visualWidth, visualHeight);
		Ellipse2D inner = new Ellipse2D.Double(visualX + visualThickness, visualY + visualThickness, visualWidth - 2 * visualThickness, visualHeight - 2 * visualThickness);
		Area area = new Area(outer);
		area.subtract(new Area(inner));
		
		g.setColor(visualColor);
		g.fill(area);
	}
	
}
