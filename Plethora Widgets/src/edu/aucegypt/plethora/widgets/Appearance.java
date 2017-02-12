/*
 * License Notice
 *
 * This file is part of Plethora.
 *
 * Plethora is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License.
 *
 * Plethora is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Plethora; if not, write to the Free Software
 *
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