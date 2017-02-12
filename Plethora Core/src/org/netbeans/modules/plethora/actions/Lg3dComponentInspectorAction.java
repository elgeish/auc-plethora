/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dComponentInspectorAction.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.util.Utilities;

/**
 * Action that is used to launch the LG3D component inspector window
 * 
 * @see Lg3dComponentInspector
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dComponentInspectorAction extends AbstractAction {

    public Lg3dComponentInspectorAction() {
        super(Bundle.getBundleString("CTL_InspectorAction"),
                new ImageIcon(Utilities.loadImage(Lg3dComponentInspector.INSPECTOR_ICON_BASE)));
    }
    
    public void actionPerformed(ActionEvent evt) {
        Lg3dComponentInspector inspector = Lg3dComponentInspector.getInstance();
        
        inspector.open();
        inspector.requestActive();
    }
    
}