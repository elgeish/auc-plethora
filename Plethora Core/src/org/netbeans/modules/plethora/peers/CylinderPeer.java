/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * CylinderPeer.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import org.netbeans.modules.plethora.model.IdPair;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for a Cylinder
 *
 * @see ShapePeer
 * @see CylinderWrapper
 *
 * @author Mohamed El-Geish 
 * @version 1.00
 */
public class CylinderPeer extends ShapePeer {
    
    public static final String ICON_BASE = "org/netbeans/modules/plethora/resources/peers/cylinder.png";
    
    public CylinderPeer() {
    }
    
    public CylinderPeer(IdPair idPair, CylinderWrapper cylinderWrapper) {
        super(idPair, cylinderWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
    
    public CylinderPeer(IdPair idPair, CylinderWrapper cylinderWrapper, Component3DWrapper component3DWrapper) {
        super(idPair, cylinderWrapper, component3DWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
   
}