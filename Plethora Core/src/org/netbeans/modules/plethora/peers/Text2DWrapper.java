/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Text2DWrapper.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import java.awt.Font;
import java.util.Vector;
import javax.vecmath.Color3f;
import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.ComplexParameter;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;

/**
 * Wrapper for Text2D.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see Text2DPeer
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Text2DWrapper extends Text2D implements ShapeWrapper {
    
    private static final String DEFAULT_TEXT = "[Text 2D]";
    private static final Color3f DEFAULT_COLOR = new Color3f(java.awt.Color.WHITE);
    private static final Font DEFAULT_FONT = new Font(Font.DIALOG, Font.PLAIN, 12);
    
    private Lg3dDataDocument lg3dDataDocument;
    private Text2DPeer text2DPeer;
    
    public Text2DWrapper() {
        this(DEFAULT_TEXT, DEFAULT_COLOR, DEFAULT_FONT);
    }
    
    public Text2DWrapper(String text, Color3f color, Font font) {
        super(text, color, font);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
    }
    
    public ShapePeer getShapePeer() {
        return text2DPeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        text2DPeer = (Text2DPeer) shapePeer;
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
    
    public void setParameters(String text, Color3f color, Font font) {
        try {
            ComplexParameter colorParameter = new ComplexParameter(Color3f.class.getName());
            ComplexParameter fontParameter = new ComplexParameter(Font.class.getName());
            Vector<Parameter> colorParams = new Vector<Parameter>(3);
            Vector<Parameter> fontParams = new Vector<Parameter>(3);
            
            colorParams.add(new SimpleParameter(float.class.getName(), color.x + "f"));
            colorParams.add(new SimpleParameter(float.class.getName(), color.y + "f"));
            colorParams.add(new SimpleParameter(float.class.getName(), color.z + "f"));
            colorParameter.setParameters(colorParams);
            
            fontParams.add(new SimpleParameter(String.class.getName(), font.getName()));
            fontParams.add(new SimpleParameter(int.class.getName(), String.valueOf(font.getStyle())));
            fontParams.add(new SimpleParameter(int.class.getName(), String.valueOf(font.getSize())));
            fontParameter.setParameters(fontParams);
            
            lg3dDataDocument.setParameters(getName(),
                    new SimpleParameter(String.class.getName(), text),
                    colorParameter,
                    fontParameter);
            
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setString(String text) {
        setParameters(text, getColor(), getFont());
        ShapePeer.reconstruct(text2DPeer, text, getColor(), getFont());
    }
    
    public void setColor(Color3f color) {
        setParameters(getString(), color, getFont());
        ShapePeer.reconstruct(text2DPeer, getString(), color, getFont());
    }
    
    public void setFont(Font font) {
        setParameters(getString(), getColor(), font);
        try {
            ShapePeer.reconstruct(text2DPeer, getString(), getColor(), font);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Font getFont() {
        return new Font(getFontName(), getFontStyle(), getFontSize());
    }
    
}