/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ModelFilePropertyEditor.java
 * Created on November 15, 2006
 */
package org.netbeans.modules.plethora.properties;

import java.awt.Component;
import java.beans.PropertyEditorSupport;



/**
 * Property editor for model files
 *
 * @author Moataz Nour and Mohamed El-Geish
 * @version 1.00
 */
public class ModelFilePropertyEditor extends PropertyEditorSupport {

    public boolean supportsCustomEditor() {
        return true;
    }
    
    public Component getCustomEditor() {
        return new ModelFileCustomEditor(this);
    }
    
}