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