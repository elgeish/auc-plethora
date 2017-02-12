/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * SphereWrapper.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import edu.aucegypt.plethora.widgets.Appearance;
import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;

/**
 * Wrapper for a Sphere.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see SpherePeer
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class SphereWrapper extends Sphere implements ShapeWrapper {
    
    private static final int DEFAULT_RADIUS = 1;
    private static final int DEFAULT_DIV = 15;
    
    private Lg3dDataDocument lg3dDataDocument;
    private SpherePeer spherePeer;
    private Appearance appearance;
    
    public SphereWrapper() {
        this(DEFAULT_RADIUS, GENERATE_NORMALS, DEFAULT_DIV, DEFAULT_APPEARANCE);
    }
    
    public SphereWrapper(float radius, int primflags, int divisions, Appearance ap) {
        super(radius, primflags, divisions, ap);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
        appearance = ap;
    }
    
    public ShapePeer getShapePeer() {
        return spherePeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        spherePeer = (SpherePeer) shapePeer;
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
    
    public void setParameters(float radius, int primflags, int divisions, Appearance ap) {
        try {
            lg3dDataDocument.setParameters(getName(),
                    new SimpleParameter(float.class.getName(), radius + "f"),
                    new SimpleParameter(int.class.getName(), String.valueOf(primflags)),
                    new SimpleParameter(int.class.getName(), String.valueOf(divisions)),
                    ShapePeer.createAppearanceParameter(ap));
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setRadius(float radius) {
        setParameters(radius, getPrimitiveFlags(), getDivisions(), getAppearance0());
        ShapePeer.reconstruct(spherePeer, radius, getPrimitiveFlags(), getDivisions(), getAppearance0());
    }
    
    @SuppressWarnings("deprecation")
    public void setPrimitiveFlags(int primflags) {
        setParameters(getRadius(), primflags, getDivisions(), getAppearance0());
        ShapePeer.reconstruct(spherePeer, getRadius(), primflags, getDivisions(), getAppearance0());
    }
    
    public void setDivisions(int divisions) {
        setParameters(getRadius(), getPrimitiveFlags(), divisions, getAppearance0());
        ShapePeer.reconstruct(spherePeer, getRadius(), getPrimitiveFlags(), divisions, getAppearance0());
    }
    
    public void setAppearance0(Appearance ap) {
        setParameters(getRadius(), getPrimitiveFlags(), getDivisions(), ap);
        ShapePeer.reconstruct(spherePeer, getRadius(), getPrimitiveFlags(), getDivisions(), ap);
    }
    
    public Appearance getAppearance0() {
        return appearance;
    }
    
}