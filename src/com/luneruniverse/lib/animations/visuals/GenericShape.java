package com.luneruniverse.lib.animations.visuals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class GenericShape extends Visual {
	
	protected final Area shape;
	protected final Color color;
	
	protected Area visualShape;
	protected Color visualColor;
	
	public GenericShape(Shape shape, Color color, int level) {
		super(shape.getBounds().x, shape.getBounds().y, level);
		
		this.shape = new Area(shape).createTransformedArea(AffineTransform.getTranslateInstance(-x, -y));
		this.color = color;
	}
	
	public void setVisualShape(Area visualShape) {
		this.visualShape = visualShape;
	}
	public Area getVisualShape() {
		return visualShape;
	}
	
	public void setVisualColor(Color visualColor) {
		this.visualColor = visualColor;
	}
	public Color getVisualColor() {
		return visualColor;
	}
	
	@Override
	public void reset() {
		super.reset();
		
		this.visualShape = shape;
		this.visualColor = color;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(visualColor);
		g.fill(visualShape.createTransformedArea(AffineTransform.getTranslateInstance(visualX, visualY)));
	}
	
}
