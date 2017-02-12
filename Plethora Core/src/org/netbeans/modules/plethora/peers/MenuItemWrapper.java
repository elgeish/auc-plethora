/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * MenuItemWrapper.java
 * Created on December 05, 2006
 */

package org.netbeans.modules.plethora.peers;

import edu.aucegypt.plethora.widgets.Appearance;
import edu.aucegypt.plethora.widgets.MenuItem;
import javax.xml.xpath.XPathExpressionException;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;

/**
 * Wrapper for a MenuItem.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see MenuItemPeer
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class MenuItemWrapper extends MenuItem implements ShapeWrapper {
    
    private static final String DEFAULT_STRING = "[Menu Item]";
    
    private Lg3dDataDocument lg3dDataDocument;
    private MenuItemPeer menuItemPeer;
    
    public MenuItemWrapper() {
        this(DEFAULT_STRING);
    }
    
    public MenuItemWrapper(String s) {
        super(s);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
    }
    
    public ShapePeer getShapePeer() {
        return menuItemPeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        menuItemPeer = (MenuItemPeer) shapePeer;
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
        ShapePeer.reconstruct(menuItemPeer, s);
    }

}