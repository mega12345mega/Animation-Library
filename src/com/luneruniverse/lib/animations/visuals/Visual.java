package com.luneruniverse.lib.animations.visuals;

import java.awt.Graphics2D;

import com.luneruniverse.lib.animations.translations.Translation;

public abstract class Visual {
	
	protected final int x;
	protected final int y;
	protected final int level;
	
	protected int visualX;
	protected int visualY;
	
	protected Translation<? extends Visual> translation;
	
	public Visual(int x, int y, int level) {
		this.level = level;
		this.x = x;
		this.y = y;
	}
	
	public int getLevel() {
		return level;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void setVisualX(int visualX) {
		this.visualX = visualX;
	}
	public int getVisualX() {
		return visualX;
	}
	
	public void setVisualY(int visualY) {
		this.visualY = visualY;
	}
	public int getVisualY() {
		return visualY;
	}
	
	public void reset() {
		this.visualX = x;
		this.visualY = y;
	}
	@SuppressWarnings("unchecked")
	public void update(int timePassed) {
		reset();
		
		if (translation != null)
			((Translation<Visual>) translation).execute(this, Math.min(timePassed, translation.getLength()));
	}
	
	public abstract void render(Graphics2D g);
	
	public void setTranslation(Translation<? extends Visual> translation) {
		this.translation = translation;
	}
	
}
