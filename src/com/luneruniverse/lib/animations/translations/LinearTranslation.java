package com.luneruniverse.lib.animations.translations;

import com.luneruniverse.lib.animations.visuals.Visual;

public class LinearTranslation<T extends Visual> extends SmoothTranslation<T> {
	public LinearTranslation(int x, int y, int length) {
		super((percent) -> percent, x, y, length);
	}
}
