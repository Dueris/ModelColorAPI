package me.dueris.modelcolor.colortransformers;

import java.awt.Color;

public class OriginsTransformer implements ColorTransformer{

    @Override
    public Color transform(Color color, double r, double g, double b) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        Color pix = new Color(
            calculateValue(red * r),
            calculateValue(green * g),
            calculateValue(blue * b),
            calculateValue(color.getAlpha()));
        return pix;
    }

    protected int calculateValue(double fl){
        return (int) (fl < 0 ? 0 : fl > 255 ? 255 : fl);
    }

}