/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ShapeWrapper.java
 * Created on November 15, 2006
 */
package org.netbeans.modules.plethora.peers;

import edu.aucegypt.plethora.widgets.Appearance;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;

/**
 * A ShapeWrapper has to extend the shape it needs to wrap. However, it must
 * provide certain functionalities to save and restore the properties' values in
 * the Lg3dDataDocument. It also provides a common super type for the wrappers
 *
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public interface ShapeWrapper {
    Appearance DEFAULT_APPEARANCE = new Appearance(0.25f, 0.25f, 0.25f, 1);
    
    ShapePeer getShapePeer();
    void setShapePeer(ShapePeer shapePeer);
    void setProperty(String property, Class type, Object value);
    void setProperty(String property, Class type, Parameter... parameters);
    void resetProperty(String property);
    void setName(String name);
    Lg3dDataDocument getLg3dDataDocument();
}