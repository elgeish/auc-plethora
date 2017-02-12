/*
 * License Notice
 *
 * This file is part of Plethora.
 *
 * Plethora is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License.
 *
 * Plethora is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Plethora; if not, write to the Free Software
 *
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