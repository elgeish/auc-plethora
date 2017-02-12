/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * AppearancePropertyEditor.java
 * Created on December 01, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.beans.PropertyEditorSupport;
import edu.aucegypt.plethora.widgets.Appearance;
import java.awt.Component;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Property editor for properties of Appearance
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class AppearancePropertyEditor extends PropertyEditorSupport {
    
    public String getAsText() {
        Appearance ap = (Appearance) getValue();
        
        if (ap == null) {
            return "";
        }
        
        return "[" + ap.getRed() +
                ", " + ap.getGreen() +
                ", " + ap.getBlue() +
                ", " + ap.getAlpha() + "]";
    }
    
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        try {
            setValue(parseAppearance(text));
        } catch (Exception ex) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                    "Can NOT parse Appearance", NotifyDescriptor.INFORMATION_MESSAGE));
        }
    }
    
    public static Appearance parseAppearance(String s) {
        if(s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
            s = s.substring(1, s.length() - 1);
        }
        
        String[] array = s.split(",");
        
        return new Appearance(
                Float.parseFloat(array[0]),
                Float.parseFloat(array[1]),
                Float.parseFloat(array[2]),
                Float.parseFloat(array[3]));
    }
    
    
    public boolean supportsCustomEditor() {
        return true;
    }
    
    public Component getCustomEditor() {
        return new AppearanceCustomEditor(this);
    }

}