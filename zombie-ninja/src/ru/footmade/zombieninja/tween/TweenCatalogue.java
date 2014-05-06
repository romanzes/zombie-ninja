package ru.footmade.zombieninja.tween;

import ru.footmade.zombieninja.screens.ui.HorizontalScrollingBackground.Layer.LayerElement;
import aurelienribon.tweenengine.Tween;

public class TweenCatalogue {
	public static void register() {
		Tween.registerAccessor(LayerElement.class, new LayerElementAccessor());
	}
}
