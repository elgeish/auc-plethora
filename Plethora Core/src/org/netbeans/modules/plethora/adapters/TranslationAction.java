/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * TranslationAction.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.adapters;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionFloat2;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.netbeans.modules.plethora.peers.Component3DWrapper;
import org.openide.awt.StatusDisplayer;

/**
 * TranslationAction
 * 
 * @see Component3DWarapper
 *
 * @author Moataz Nour
 * @version 1.00
 */
public class TranslationAction implements ActionFloat2 {
    
    public void performAction(LgEventSource lgEventSource, float x, float y) {
        Component3DWrapper sourceComponent = (Component3DWrapper) lgEventSource;
        float z = sourceComponent.getTranslation().z;
        
        sourceComponent.setTranslation(new Vector3f(x, y, z));
        StatusDisplayer.getDefault().setStatusText("[" + x + ", " + y + ", " + z + "]");
    }
}
