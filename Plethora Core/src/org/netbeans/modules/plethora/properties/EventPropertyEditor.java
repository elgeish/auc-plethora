/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * EventPropertyEditor.java
 * Created on November 21, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.awt.event.ActionEvent;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;
import javax.swing.AbstractAction;
import org.netbeans.modules.plethora.editor.Lg3dEditorSupport;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;

/**
 * Property editor for identifiers
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class EventPropertyEditor extends PropertyEditorSupport {
    
    public boolean supportsCustomEditor() {
        return false;
    }
    
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        setValue(text);
    }
    
    public void setValue(Object value) {
        if (!value.equals("") && !org.openide.util.Utilities.isJavaIdentifier((String) value)) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                    Bundle.getBundleString("MSG_JavaMethodName"), NotifyDescriptor.INFORMATION_MESSAGE));
        } else {
            
            try {
                super.setValue(value);
            } catch (Exception ex) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        ex.getMessage(), NotifyDescriptor.INFORMATION_MESSAGE));
            }
            
        }
    }
    
    static class PostSetAction extends AbstractAction {
        
        private Node.Property property;
        private Lg3dEditorSupport lg3dEditorSupport;
        
        public PostSetAction(Node.Property property) {
            this.property = property;
            lg3dEditorSupport = Lg3dComponentInspector.getInstance().getLg3dDataDocument().getLg3dDataObject().getLg3dEditorSupport();
        }
        
        public void actionPerformed(ActionEvent e) {
            try {
                lg3dEditorSupport.openAtEventHandler((String) property.getValue());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
}