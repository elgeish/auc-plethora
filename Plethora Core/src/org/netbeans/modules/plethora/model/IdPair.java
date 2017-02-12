/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * IdPair.java
 * Created on November 24, 2006
 */

package org.netbeans.modules.plethora.model;

/**
 * Description for a pair of IDs: component and shape
 *
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class IdPair {
    
    private String componentId;
    private String shapeId;
    
    public IdPair() {
    }
    
    public IdPair(String componentId, String shapeId) {
        this.componentId = componentId;
        this.shapeId = shapeId;
    }
    
    public String getComponentId() {
        return componentId;
    }
    
    public String getShapeId() {
        return shapeId;
    }
    
    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }
    
    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }
    
}