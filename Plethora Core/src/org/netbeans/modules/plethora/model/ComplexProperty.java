/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ComplexProperty.java
 * Created on November 24, 2006
 */

package org.netbeans.modules.plethora.model;

import java.util.Vector;

/**
 * Description wrapper for the complex properties of the Shape3D, Container3D and Component3D
 *
 * @see Property
 * @see SimpleProperty
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
class ComplexProperty extends Property {
    
    private Vector<Parameter> params; // Type and value
    
    public ComplexProperty() {
        super();
        params = new Vector<Parameter>(3);
    }
    
    public ComplexProperty(String name, String type, Vector<Parameter> params) {
        super(name, type);
        this.params = params;
    }
    
    public Vector<Parameter> getParameters() {
        return params;
    }
    
    public void setParameters(Vector<Parameter> params){
        this.params = params;
    }

}