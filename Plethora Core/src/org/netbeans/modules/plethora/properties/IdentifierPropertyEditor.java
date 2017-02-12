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