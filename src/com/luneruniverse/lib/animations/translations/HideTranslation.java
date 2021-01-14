package com.luneruniverse.lib.animations.translations;

import com.luneruniverse.lib.animations.visuals.Visual;

public class HideTranslation<T extends Visual> extends TeleportTranslation<T> {
	public HideTranslation() {
		super(-999999, -999999);
	}
}
