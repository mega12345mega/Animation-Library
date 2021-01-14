package com.luneruniverse.lib.animations.visuals;

import java.awt.Graphics2D;
import java.util.List;

public class ComplexVisual extends Visual {
	
	protected final List<Visual> visuals;
	
	public ComplexVisual(List<Visual> visuals, int x, int y, int level) {
		super(x, y, level);
		
		this.visuals = visuals;
		this.visuals.sort((a, b) -> a.level - b.level);
	}
	
	@Override
	public void update(int timePassed) {
		super.update(timePassed);
		
		for (Visual visual : visuals)
			visual.update(Math.min(timePassed, visual.translation == null ? 0 : visual.translation.getLength()));
	}
	
	@Override
	public void render(Graphics2D g) {
		for (Visual visual : visuals) {
			visual.setVisualX(visual.getVisualX() + visualX);
			visual.setVisualY(visual.getVisualY() + visualY);
			
			visual.render(g);
			
			visual.setVisualX(visual.getVisualX() - visualX);
			visual.setVisualY(visual.getVisualY() - visualY);
		}
	}
	
}
