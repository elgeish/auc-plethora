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
 * ButtonWrapper.java
 * Created on December 05, 2006
 */

package org.netbeans.modules.plethora.peers;

import edu.aucegypt.plethora.widgets.Appearance;
import edu.aucegypt.plethora.widgets.Button;
import javax.xml.xpath.XPathExpressionException;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;

/**
 * Wrapper for a Button.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see ButtonPeer
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class ButtonWrapper extends Button implements ShapeWrapper {
    
    private static final String DEFAULT_STRING = "[Button]";
    
    private Lg3dDataDocument lg3dDataDocument;
    private ButtonPeer ButtonPeer;
    
    public ButtonWrapper() {
        this(DEFAULT_STRING);
    }
    
    public ButtonWrapper(String s) {
        super(s);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
    }
    
    public ShapePeer getShapePeer() {
        return ButtonPeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        ButtonPeer = (ButtonPeer) shapePeer;
    }
    
    public void setProperty(String property, Class type, Object value) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.setProperty(getName(), property, type.getName(), String.valueOf(value));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setProperty(String property, Class type, Parameter... parameters) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.setProperty(getName(), property, type.getName(), parameters);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void resetProperty(String property) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.removeProperty(getName(), property);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Lg3dDataDocument getLg3dDataDocument() {
        return lg3dDataDocument;
    }
    
    public void setParameters(String s) {
        try {
            
            lg3dDataDocument.setParameters(getName(),
                    new SimpleParameter(String.class.getName(), s));

        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setText(String s) {
        setParameters(s);
        ShapePeer.reconstruct(ButtonPeer, s);
    }

}