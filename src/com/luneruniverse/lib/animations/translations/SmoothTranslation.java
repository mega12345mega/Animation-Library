package com.luneruniverse.lib.animations.translations;

import com.luneruniverse.lib.animations.visuals.Visual;

public class SmoothTranslation<T extends Visual> extends Translation<T> {
	
	private final SmoothPercent smoothPercent;
	
	public SmoothTranslation(SmoothPercent smoothPercent, int x, int y, int length) {
		super(x, y, length);
		
		this.smoothPercent = smoothPercent;
	}
	
	@Override
	public void execute(Visual visual, int timePassed) {
		double percent = smoothPercent.getPercent(getProgress(timePassed));
		visual.setVisualX((int) ((endX - visual.getVisualX()) * percent + visual.getVisualX()));
		visual.setVisualY((int) ((endY - visual.getVisualY()) * percent + visual.getVisualY()));
	}
	
	
	@FunctionalInterface
	public static interface SmoothPercent {
		public double getPercent(double linearPercent);
	}
	
}
