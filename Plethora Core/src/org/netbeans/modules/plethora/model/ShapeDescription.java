/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ShapeDescription.java
 *
 */

package org.netbeans.modules.plethora.model;

import java.util.Vector;

/**
 * Description wrapper for the Shape3D
 *
 * @see ContainerDescription
 * @see ComponentDescription
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
class ShapeDescription {
    
    private String id;
    private String type;
    private Vector<Parameter> params;
    private Vector<Property> properties;
    
    public ShapeDescription(String id, String type) {
        this.id = id;
        this.type = type;
        params = new Vector<Parameter>(3);
        properties = new Vector<Property>(3);
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Vector<Parameter> getParameters(){
        return params;
    }
    
    public void setParameters(Vector<Parameter> params){
        this.params = params;
    }

    public Vector<Property> getProperties(){
        return properties;
    }
    
    public void setProperties(Vector<Property> properties){
        this.properties = properties;
    }

}