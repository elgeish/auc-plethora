/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Property.java
 * Created on November 24, 2006
 */

package org.netbeans.modules.plethora.model;

/**
 * Description wrapper for the properties of the Shape3D, Container3D and Component3D
 *
 * @see SimpleProperty
 * @see ComplexProperty
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
abstract class Property {
    
    private String name;
    private String type;
    
    public Property() {
    }
    
    public Property(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

}