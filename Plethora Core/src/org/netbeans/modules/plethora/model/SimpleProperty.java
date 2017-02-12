/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * SimpleProperty.java
 * Created on November 24, 2006
 */

package org.netbeans.modules.plethora.model;

/**
 * Decription wrapper for the simple properties of the Shape3D, Container3D and Component3D
 *
 * @see Property
 * @see ComplexProperty
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
class SimpleProperty extends Property {
    
    private String value;
    
    public SimpleProperty() {
    }
    
    public SimpleProperty(String name, String type, String value) {
        super(name, type);
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

}