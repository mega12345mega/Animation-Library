package com.luneruniverse.lib.animations.translations;

import com.luneruniverse.lib.animations.visuals.Visual;

public class WaitTranslation<T extends Visual> extends Translation<T> {
	public WaitTranslation(int length) {
		super(0, 0, length);
	}
	
	@Override
	public void execute(Visual visual, int timePassed) {}
}
