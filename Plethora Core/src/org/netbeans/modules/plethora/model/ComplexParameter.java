/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ComplexParameter.java
 * Created on December 3, 2006
 */

package org.netbeans.modules.plethora.model;

import java.util.Vector;

/**
 * Description for a complex parameter
 *
 * @see Lg3dDataDocument
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
public class ComplexParameter extends Parameter {
    
    private Vector<Parameter> params;
    
    public ComplexParameter(String type) {
        this(type, new Vector<Parameter>(3));
    }
    
    public ComplexParameter(String type, Vector<Parameter> params) {
        super(type);
        this.params = params;
    }

    public Vector<Parameter> getParameters() {
        return params;
    }

    public void setParameters(Vector<Parameter> params) {
        this.params = params;
    }

}