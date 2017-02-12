/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ColorCubeWrapper.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.utils.shape.ColorCube;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;

/**
 * Wrapper for a ColorCube.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save 
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see ColorCubePeer
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish 
 * @version 1.00
 */
public class ColorCubeWrapper extends ColorCube implements ShapeWrapper {
    
    private static final int DEFAULT_SCALE = 1;
    
    private Lg3dDataDocument lg3dDataDocument;
    private ColorCubePeer colorCubePeer;
    private double scale;
    
    public ColorCubeWrapper() {
        this(DEFAULT_SCALE);
    }
    
    public ColorCubeWrapper(double scale) {
        super(scale);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
        this.scale = scale;
    }
    
    public ShapePeer getShapePeer() {
        return colorCubePeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        colorCubePeer = (ColorCubePeer) shapePeer;
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
    
    public void setParameters(double scale) {
        try {
            lg3dDataDocument.setParameters(getName(),
                new SimpleParameter(double.class.getName(), String.valueOf(scale)));
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setScale(double scale) {
        setParameters(scale);
        ShapePeer.reconstruct(colorCubePeer, scale);
    }

    public double getScale() {
        return scale;
    }
    
}