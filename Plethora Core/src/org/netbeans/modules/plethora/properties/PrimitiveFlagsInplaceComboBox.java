/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * PrimitiveFlagsInplaceComboBox.java
 * Created on November 27, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 * Inplace editor for primitive flags. The getComponent() method returns a JComboBox
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class PrimitiveFlagsInplaceComboBox extends AbstractInplaceEditor {
    
    private JComboBox comboBox;

    public PrimitiveFlagsInplaceComboBox() {
        comboBox = new JComboBox(new String[] {
            "GENERATE_NORMALS",
            "GENERATE_TEXTURE_COORDS",
            "GENERATE_NORMALS_INWARD",
            "GEOMETRY_NOT_SHARED",
            "ENABLE_GEOMETRY_PICKING",
            "ENABLE_APPEARANCE_MODIFY"
        });
    }
    
    public JComponent getComponent() {
        return comboBox;
    }
    
    public Object getValue() {
        return 2 >> comboBox.getSelectedIndex();
    }
    
    public void setValue(Object object) {
        comboBox.setSelectedIndex(2 << Integer.parseInt(object.toString()));
    }
    
    public void reset() {
        comboBox.setSelectedIndex(2 << Integer.parseInt(propertyEditor.getValue().toString()));
    }
    
    public boolean isKnownComponent(Component component) {
        return component == comboBox || comboBox.isAncestorOf(component);
    }
   
}