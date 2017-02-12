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
 * CylinderWrapper.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import edu.aucegypt.plethora.widgets.Appearance;
import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;

/**
 * Wrapper for a Cylinder.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see CylinderPeer
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class CylinderWrapper extends Cylinder implements ShapeWrapper {
    
    private static final int DEFAULT_RADIUS = 1;
    private static final int DEFAULT_HEIGHT = 2;
    private static final int DEFAULT_X_DIV = 15;
    private static final int DEFAULT_Y_DIV = 1;
    
    private Lg3dDataDocument lg3dDataDocument;
    private CylinderPeer cylinderPeer;
    private Appearance appearance;
    
    public CylinderWrapper() {
        this(DEFAULT_RADIUS, DEFAULT_HEIGHT, GENERATE_NORMALS, DEFAULT_X_DIV, DEFAULT_Y_DIV, DEFAULT_APPEARANCE);
    }
    
    public CylinderWrapper(float radius, float height, int primflags, int xdivision, int ydivision, Appearance ap) {
        super(radius, height, primflags, xdivision, ydivision, ap);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
        appearance = ap;
    }
    
    public ShapePeer getShapePeer() {
        return cylinderPeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        cylinderPeer = (CylinderPeer) shapePeer;
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
    
    public void setParameters(float radius, float height, int primflags, int xdivision, int ydivision, Appearance ap) {
        try {
            lg3dDataDocument.setParameters(getName(),
                    new SimpleParameter(float.class.getName(), radius + "f"),
                    new SimpleParameter(float.class.getName(), height + "f"),
                    new SimpleParameter(int.class.getName(), String.valueOf(primflags)),
                    new SimpleParameter(int.class.getName(), String.valueOf(xdivision)),
                    new SimpleParameter(int.class.getName(), String.valueOf(ydivision)),
                    ShapePeer.createAppearanceParameter(ap));
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setRadius(float radius) {
        setParameters(radius, getHeight(), getPrimitiveFlags(), getXdivisions(), getYdivisions(), getAppearance0());
        ShapePeer.reconstruct(cylinderPeer, radius, getHeight(), getPrimitiveFlags(), getXdivisions(), getYdivisions(), getAppearance0());
    }
    
    public void setHeight(float height) {
        setParameters(getRadius(), height, getPrimitiveFlags(), getXdivisions(), getYdivisions(), getAppearance0());
        ShapePeer.reconstruct(cylinderPeer, getRadius(), height, getPrimitiveFlags(), getXdivisions(), getYdivisions(), getAppearance0());
    }
    
    @SuppressWarnings("deprecation")
    public void setPrimitiveFlags(int primflags) {
        setParameters(getRadius(), getHeight(), primflags, getXdivisions(), getYdivisions(), getAppearance0());
        ShapePeer.reconstruct(cylinderPeer, getRadius(), getHeight(), primflags, getXdivisions(), getYdivisions(), getAppearance0());
    }
    
    public void setXdivisions(int xdiv) {
        setParameters(getRadius(), getHeight(), getPrimitiveFlags(), xdiv, getYdivisions(), getAppearance0());
        ShapePeer.reconstruct(cylinderPeer, getRadius(), getHeight(), getPrimitiveFlags(), xdiv, getYdivisions(), getAppearance0());
    }
    
    public void setYdivisions(int ydiv) {
        setParameters(getRadius(), getHeight(), getPrimitiveFlags(), getXdivisions(), ydiv, getAppearance0());
        ShapePeer.reconstruct(cylinderPeer, getRadius(), getHeight(), getPrimitiveFlags(), getXdivisions(), ydiv, getAppearance0());
    }
    
    public void setAppearance0(Appearance ap) {
        setParameters(getRadius(), getHeight(), getPrimitiveFlags(), getXdivisions(), getYdivisions(), ap);
        ShapePeer.reconstruct(cylinderPeer, getRadius(), getHeight(), getPrimitiveFlags(), getXdivisions(), getYdivisions(), ap);
    }
    
    public Appearance getAppearance0() {
        return appearance;
    }
    
}