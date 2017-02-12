/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * SimpleParameter.java
 * Created on December 03, 2006
 */

package org.netbeans.modules.plethora.model;

/**
 * Decription wrapper for the simple properties of the Shape3D, Container3D and Component3D
 *
 * @see Parameter
 * @see ComplexParameter
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
public class SimpleParameter extends Parameter {
    
    private String value;
    
    public SimpleParameter(String type, String value) {
        super(type);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
   
}