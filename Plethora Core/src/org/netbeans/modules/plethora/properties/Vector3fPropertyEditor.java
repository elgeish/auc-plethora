/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Vector3fPropertyEditor.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.beans.PropertyEditorSupport;
import javax.vecmath.Vector3f;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Property editor for properties of type javax.vecmath.Vector3f
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Vector3fPropertyEditor extends PropertyEditorSupport {
    
    public String getAsText() {
        Vector3f vector = (Vector3f) getValue();
        
        return "[" + vector.x + ", " + vector.y + ", " + vector.z + "]";
    }
    
    public void setAsText(String s) {
        try {
            setValue(parseVector3f(s));
        } catch (Exception ex) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                    "Can NOT parse Vector3f", NotifyDescriptor.INFORMATION_MESSAGE));
        }
        
    }
    
    public static Vector3f parseVector3f(String s) {
        if(s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
            s = s.substring(1, s.length() - 1);
        }
        
        String[] array = s.split(",");
        
        return new Vector3f(
                Float.parseFloat(array[0]),
                Float.parseFloat(array[1]),
                Float.parseFloat(array[2]));
    }
    
}