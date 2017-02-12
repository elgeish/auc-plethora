/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * DepthTranslationAction.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.adapters;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.netbeans.modules.plethora.peers.Component3DWrapper;
import org.openide.awt.StatusDisplayer;

/**
 * DepthTranslationAction
 * 
 * @see Component3DWarapper
 *
 * @author Moataz Nour
 * @version 1.00
 */
public class DepthTranslationAction implements ActionInt{
    
    public void performAction(LgEventSource lgEventSource, int i) {
        Component3DWrapper sourceComponent = (Component3DWrapper) lgEventSource;
        
        float x = sourceComponent.getTranslation().x;
        float y = sourceComponent.getTranslation().y;
        float z = sourceComponent.getTranslation().z + i * 0.01f;
        
        sourceComponent.setTranslation(new Vector3f(x, y, z));
        StatusDisplayer.getDefault().setStatusText("[" + x + ", " + y + ", " + z + "]");
    }
    
}
