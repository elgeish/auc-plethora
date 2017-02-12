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
 * BoxWrapper.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import edu.aucegypt.plethora.widgets.Appearance;
import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.utils.shape.Box;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;

/**
 * Wrapper for a Box.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see BoxPeer
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class BoxWrapper extends Box implements ShapeWrapper {
    
    private static final int DEFAULT_DIM = 1;
    
    private Lg3dDataDocument lg3dDataDocument;
    private BoxPeer boxPeer;
    private Appearance appearance;
    
    public BoxWrapper() {
        this(DEFAULT_DIM, DEFAULT_DIM, DEFAULT_DIM, DEFAULT_APPEARANCE);
    }
    
    public BoxWrapper(float xdim, float ydim, float zdim, Appearance ap) {
        super(xdim, ydim, zdim, ap);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
        appearance = ap;
    }
    
    public ShapePeer getShapePeer() {
        return boxPeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        boxPeer = (BoxPeer) shapePeer;
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
    
    public void setParameters(float xdim, float ydim, float zdim, Appearance ap) {
        try {
            
            lg3dDataDocument.setParameters(getName(),
                    new SimpleParameter(float.class.getName(), xdim + "f"),
                    new SimpleParameter(float.class.getName(), ydim + "f"),
                    new SimpleParameter(float.class.getName(), zdim + "f"),
                    ShapePeer.createAppearanceParameter(ap));

        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setXdimension(float xdim) {
        setParameters(xdim, getYdimension(), getZdimension(), getAppearance0());
        ShapePeer.reconstruct(boxPeer, xdim, getYdimension(), getZdimension(), getAppearance0());
    }
    
    public void setYdimension(float ydim) {
        setParameters(getXdimension(), ydim, getZdimension(), getAppearance0());
        ShapePeer.reconstruct(boxPeer, getXdimension(), ydim, getZdimension(), getAppearance0());
    }
    
    public void setZdimension(float zdim) {
        setParameters(getXdimension(), getYdimension(), zdim, getAppearance0());
        ShapePeer.reconstruct(boxPeer, getXdimension(), getYdimension(), zdim, getAppearance0());
    }
    
    public void setAppearance0(Appearance ap) {
        setParameters(getXdimension(), getYdimension(), getZdimension(), ap);
        ShapePeer.reconstruct(boxPeer, getXdimension(), getYdimension(), getZdimension(), ap);
    }
    
    public Appearance getAppearance0() {
        return appearance;
    }
    
}