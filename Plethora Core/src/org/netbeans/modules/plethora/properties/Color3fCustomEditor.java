/*
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