package ru.footmade.zombieninja.tween;

import ru.footmade.zombieninja.screens.ui.HorizontalScrollingBackground.Layer.LayerElement;
import aurelienribon.tweenengine.TweenAccessor;

public class LayerElementAccessor implements TweenAccessor<LayerElement> {
    public static final int SIZE = 1;

    @Override
    public int getValues(LayerElement target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case SIZE:
                returnValues[0] = target.width;
                returnValues[1] = target.height;
                return 2;
            default: assert false; return -1;
        }
    }
    
    @Override
    public void setValues(LayerElement target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case SIZE:
                target.width = newValues[0];
                target.height = newValues[1];
                break;
            default: assert false; break;
        }
    }
}
