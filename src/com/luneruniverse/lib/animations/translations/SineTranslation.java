package com.luneruniverse.lib.animations.translations;

import com.luneruniverse.lib.animations.visuals.Visual;

public class SineTranslation<T extends Visual> extends SmoothTranslation<T> {
	public SineTranslation(int x, int y, int length) {
		super((percent) -> Math.pow(Math.sin(percent * Math.PI / 2), 2), x, y, length);
	}
}
