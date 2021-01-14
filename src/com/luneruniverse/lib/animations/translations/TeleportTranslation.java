package com.luneruniverse.lib.animations.translations;

import com.luneruniverse.lib.animations.visuals.Visual;

public class TeleportTranslation<T extends Visual> extends Translation<T> {
	
	public TeleportTranslation(int x, int y) {
		super(x, y, 0);
	}
	
	@Override
	public void execute(Visual visual, int timePassed) {
		visual.setVisualX(endX);
		visual.setVisualY(endY);
	}
	
}
