/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ComponentDescription.java
 * Created on November 24, 2006
 */

package org.netbeans.modules.plethora.model;

import java.util.Vector;

/**
 * Description wrapper for the Component3D
 *
 * @see ContainerDescription
 * @see ShapeDescription
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
class ComponentDescription extends ContainerDescription {
    
    private ShapeDescription shape;
    
    public ComponentDescription(String id, String type) {
        super(id, type);
    }
    
    public void setShapeDescription(ShapeDescription shape) {
        this.shape = shape;
    }
    
    public ShapeDescription getShapeDescription() {
        return shape;
    }
    
}