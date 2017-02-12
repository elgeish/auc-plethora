/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dDesignerFrame.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import org.netbeans.modules.plethora.model.Lg3dDataDocument;

/**
 * Frame 3D to add components in and to load the select Lg3dDataDocument
 * 
 * @see Lg3dDesigner
 * @see Lg3dDataDocument
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dDesignerFrame extends org.jdesktop.lg3d.wg.Frame3D {
    
    private static Lg3dDesignerFrame instance = null;
    private Lg3dDataDocument lg3dDataDocument = null;
    
    private Lg3dDesignerFrame() {
        super();
    }
    
    static Lg3dDesignerFrame getLg3dDesignerFrame(Lg3dDataDocument lg3dDataDocument) {
        if (instance == null) {
            instance = new Lg3dDesignerFrame();
            instance.setVisible(true);
            instance.draw(lg3dDataDocument);
        }
        
        return instance;
    }
    
    static void draw(Lg3dDataDocument lg3dDataDocument) {
        if (instance.lg3dDataDocument != lg3dDataDocument) {
            instance.lg3dDataDocument = lg3dDataDocument;
            instance.removeAllChildren();
        }
    }
    
}