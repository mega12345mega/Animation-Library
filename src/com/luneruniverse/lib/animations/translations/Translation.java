package com.luneruniverse.lib.animations.translations;

import com.luneruniverse.lib.animations.visuals.Visual;

public abstract class Translation<T extends Visual> {
	
	protected int endX;
	protected int endY;
	protected final int length;
	
	public Translation(int x, int y, int length) {
		this.endX = x;
		this.endY = y;
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	
	protected final double getProgress(int timePassed) {
		if (length <= 0)
			return 1;
		return (double) timePassed / length;
	}
	
	public final boolean isDone(int timePassed) {
		return getProgress(timePassed) >= 1;
	}
	
	public abstract void execute(T visual, int timePassed);
	
}
