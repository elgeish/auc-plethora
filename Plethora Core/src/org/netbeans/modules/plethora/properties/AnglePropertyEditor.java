/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * AnglePropertyEditor.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyEditorSupport;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;

/**
 * Property editor for angles. It supports a range from 0 to (2 * Math.PI)
 *
 * @see FloatInplaceSpinner
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class AnglePropertyEditor extends PropertyEditorSupport implements ExPropertyEditor, InplaceEditor.Factory {

    private InplaceEditor inplaceEditor;
    
    public void attachEnv(PropertyEnv env) {
        env.registerInplaceEditorFactory(this);
    }
    
    public String getAsText() {
        return super.getAsText().equals("null") ? "" : super.getAsText();
    }
    
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        setValue(Float.parseFloat(text));
    }

    public InplaceEditor getInplaceEditor() {
        if (inplaceEditor == null) {
            inplaceEditor = new FloatInplaceSpinner(0, 2 * (float) Math.PI, (float) Math.PI / 6);
        }
        
        return inplaceEditor;
    }
    
    public boolean supportsCustomEditor() {
        return true;
    }
    
    public Component getCustomEditor() {
        return new AngleCustomEditor(this);
    }
   
}