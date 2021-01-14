package com.luneruniverse.lib.animations.translations;

import java.util.Arrays;

import com.luneruniverse.lib.animations.visuals.Visual;

public class ShowTranslation<T extends Visual> extends ComplexTranslation<T> {
	
	public ShowTranslation(T visual, int delay) {
		super(Arrays.asList(
				new HideTranslation<>(),
				new WaitTranslation<>(delay),
				new TeleportTranslation<>(visual.getX(), visual.getY())
			));
	}
	
}
