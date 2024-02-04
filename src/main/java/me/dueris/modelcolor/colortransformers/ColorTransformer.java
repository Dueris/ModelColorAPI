package me.dueris.modelcolor.colortransformers;

public interface ColorTransformer {
    public java.awt.Color transform(java.awt.Color color, double r, double g, double b);
}