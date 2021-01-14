package com.luneruniverse.lib.animations.translations;

import java.util.List;

import com.luneruniverse.lib.animations.visuals.Visual;

public class ComplexTranslation<T extends Visual> extends Translation<T> {
	
	protected final List<Translation<? extends T>> translations;
	protected int currentTranslation;
	
	public ComplexTranslation(List<Translation<? extends T>> translations) {
		super(0, 0, getLength(translations));
		
		this.translations = translations;
		this.currentTranslation = 0;
	}
	private static <T extends Visual> int getLength(List<Translation<? extends T>> translations) {
		int length = 0;
		for (Translation<? extends Visual> translation : translations)
			length += translation.length;
		return length;
	}
	
	@Override
	public void execute(T visual, int timePassed) {
		
		int currentTranslation = 0;
		int currentTimePassed = 0;
		while (currentTimePassed <= timePassed) {
			if (currentTranslation >= translations.size())
				break;
			
			@SuppressWarnings("unchecked")
			Translation<T> translation = (Translation<T>) translations.get(currentTranslation);
			translation.execute(visual, Math.min(timePassed - currentTimePassed, translation.length));
			
			if (translation.length > 0 && timePassed - currentTimePassed < translation.length)
				break;
			
			currentTimePassed += Math.min(timePassed - currentTimePassed, translation.length);
			currentTranslation++;
		}
		
	}
	
}
