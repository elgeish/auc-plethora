/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Parameter.java
 * Created on December 03, 2006
 */

package org.netbeans.modules.plethora.model;

/**
 * Description for a parameter
 *
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public abstract class Parameter {
    private String type;
    
    protected Parameter(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
}