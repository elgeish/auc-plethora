/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * AbstractInplaceEditor.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import javax.swing.KeyStroke;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;

/**
 * An abstract class that implements the InplaceEditor interface.
 * It provides implementation for these common methods:
 *
 *  - connect
 *  - clear
 *  - supportsTextEntry
 *  - getKeyStrokes
 *  - getPropertyEditor
 *  - getPropertyModel
 *  - setPropertyModel
 *  - addActionListener
 *  - removeActionListener
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public abstract class AbstractInplaceEditor implements InplaceEditor {
    
    protected PropertyEditor propertyEditor;
    protected PropertyModel propertyModel;
    
    public void connect(PropertyEditor propertyEditor, PropertyEnv env) {
        this.propertyEditor = propertyEditor;
        reset();
    }
    
    public void clear() {
        propertyEditor = null;
        propertyModel = null;
    }
    
    public boolean supportsTextEntry() {
        return true;
    }
    
    public KeyStroke[] getKeyStrokes() {
        return new KeyStroke[0];
    }
    
    public PropertyEditor getPropertyEditor() {
        return propertyEditor;
    }
    
    public PropertyModel getPropertyModel() {
        return propertyModel;
    }
    
    public void setPropertyModel(PropertyModel propertyModel) {
        this.propertyModel = propertyModel;
    }
    
    public void addActionListener(ActionListener actionListener) {
    }
    
    public void removeActionListener(ActionListener actionListener) {
    }
    
}