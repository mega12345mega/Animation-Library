package com.luneruniverse.lib.animations.visuals;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text extends Visual {
	
	protected final Color color;
	protected final Font font;
	protected final String text;
	protected final boolean centered;
	
	protected Color visualColor;
	protected Font visualFont;
	protected String visualText;
	protected boolean visualCentered;
	
	public Text(Color color, Font font, String text, boolean centered, int x, int y, int level) {
		super(x, y, level);
		
		this.color = color;
		this.font = font;
		this.text = text;
		this.centered = centered;
	}
	
	public void setVisualColor(Color visualColor) {
		this.visualColor = visualColor;
	}
	public Color getVisualColor() {
		return visualColor;
	}
	
	public void setVisualFont(Font visualFont) {
		this.visualFont = visualFont;
	}
	public Font getVisualFont() {
		return visualFont;
	}
	
	public void setVisualText(String visualText) {
		this.visualText = visualText;
	}
	public String getVisualText() {
		return visualText;
	}
	
	public void setVisualCentered(boolean visualCentered) {
		this.visualCentered = visualCentered;
	}
	public boolean getVisualCentered() {
		return visualCentered;
	}
	
	@Override
	public void reset() {
		super.reset();
		
		this.visualColor = color;
		this.visualFont = font;
		this.visualText = text;
		this.visualCentered = centered;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(visualColor);
		g.setFont(visualFont);
		g.drawString(visualText, visualX - (visualCentered ? (int) g.getFontMetrics().getStringBounds(visualText, g).getWidth() / 2 : 0), visualY - (visualCentered ? g.getFontMetrics().getHeight() / 2 : 0));
	}
	
}
