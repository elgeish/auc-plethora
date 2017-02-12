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
 * Color3fCustomEditor.java
 * Created on December 05, 2006
 */

package org.netbeans.modules.plethora.properties;

import javax.swing.JColorChooser;
import javax.vecmath.Color3f;

/**
 * Custom Editor for Color3f
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Color3fCustomEditor extends JColorChooser {
    
    public Color3fCustomEditor(final Color3fPropertyEditor color3fPropertyEditor) {       
        try {
            setColor(((Color3f) color3fPropertyEditor.getValue()).get());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                color3fPropertyEditor.setValue(new Color3f(getColor()));
            }
        });
    }
    
}