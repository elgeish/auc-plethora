/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * UserEvent.java
 * Created on November 24, 2006
 */

package org.netbeans.modules.plethora.model;

/**
 * Description wrapper for the events that can be registered to a shape
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
class UserEvent {
    
    private String name;
    private String event;
    private String handler;
    
    public UserEvent(String name, String event, String handler) {
        this.name = name;
        this.event = event;
        this.handler = handler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
   
}