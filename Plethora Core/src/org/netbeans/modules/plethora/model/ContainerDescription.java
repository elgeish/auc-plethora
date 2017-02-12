/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ContainerDescription.java
 * Created on November 24, 2006
 */

package org.netbeans.modules.plethora.model;

import java.util.Vector;

/**
 * Description for the Container3D
 *
 * @see CompoenentDescription
 * @see ShapeDescription
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
class ContainerDescription {
    
    private String id;
    private String type;
    private Vector<Property> properties;
    private Vector<UserEvent> userEvents;
    
    public ContainerDescription(String id, String type) {
        this.id = id;
        this.type = type;
        properties = new Vector<Property>(3);
        userEvents = new Vector<UserEvent>(3);
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
        
    public void setProperties(Vector<Property> properties) {
        this.properties = properties;
    }
    
    public Vector<Property> getProperties() {
        return properties;
    }
    public void setUserEvents(Vector<UserEvent> userEvents) {
        this.userEvents = userEvents;
    }
    
    public Vector<UserEvent> getUserEvents() {
        return userEvents;
    }

}