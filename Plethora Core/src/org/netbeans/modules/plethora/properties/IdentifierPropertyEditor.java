/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * IdentifierPropertyEditor.java
 * Created on November 19, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.beans.PropertyEditorSupport;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Property editor for identifiers
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class IdentifierPropertyEditor extends PropertyEditorSupport {
    
    public boolean supportsCustomEditor() {
        return false;
    }
    
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        setValue(text);
    }
    
    public void setValue(Object value) {
        if (!org.openide.util.Utilities.isJavaIdentifier((String) value)) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                    Bundle.getBundleString("MSG_JavaVariableName"), NotifyDescriptor.INFORMATION_MESSAGE));
        } else {
            
            try {
                super.setValue(value);
            } catch (Exception ex) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        ex.getMessage(), NotifyDescriptor.INFORMATION_MESSAGE));
            }
            
        }
    }
    
}