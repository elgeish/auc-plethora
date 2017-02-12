/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * PrimitiveFlagsPropertyEditor.java
 * Created on November 19, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.beans.PropertyEditorSupport;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;

/**
 * Property editor for primitive flags
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class PrimitiveFlagsPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor, InplaceEditor.Factory {
    
    private InplaceEditor inplaceEditor;

    public void attachEnv(PropertyEnv env) {
        env.registerInplaceEditorFactory(this);
    }
    
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        setValue(Integer.parseInt(text));
    }
    
    public InplaceEditor getInplaceEditor() {
        if (inplaceEditor == null) {
            inplaceEditor = new PrimitiveFlagsInplaceComboBox();
        }
        
        return inplaceEditor;
    }

}