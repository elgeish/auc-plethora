/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Appearance.java
 * Created on December 04, 2006
 */

package edu.aucegypt.plethora.widgets;

import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

/**
 * Appearance
 * 
 * @see org.jdesktop.lg3d.utils.shape.SimpleAppearance
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Appearance extends SimpleAppearance {
    
    private float red;
    private float green;
    private float blue;
    private float alpha;
    
    public Appearance(float r, float g, float b, float a) {
        super(r, g, b, a);
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }
    
    public float getRed() {
        return red;
    }
    
    public float getGreen() {
        return green;
    }
    
    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

}