/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * FloatInplaceSpinner.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Inplace editor for floats. The getComponent() method returns a JSpinner
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class FloatInplaceSpinner extends AbstractInplaceEditor {
    
    private JSpinner spinner;

    public FloatInplaceSpinner(float min, float max, float step) {
        spinner = new JSpinner(new SpinnerNumberModel(0, min, max, step));
    }
    
    public JComponent getComponent() {
        return spinner;
    }
    
    public Object getValue() {
        return Float.parseFloat(spinner.getValue().toString());
    }
    
    public void setValue(Object object) {
        spinner.setValue(object);
    }
    
    public void reset() {
        spinner.setValue(propertyEditor.getValue());
    }
    
    public boolean isKnownComponent(Component component) {
        return component == spinner || spinner.isAncestorOf(component);
    }
   
}