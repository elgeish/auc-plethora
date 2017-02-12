/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * BoxPeer.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import org.netbeans.modules.plethora.model.IdPair;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for a Box
 *
 * @see ShapePeer
 * @see BoxWrapper
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class BoxPeer extends ShapePeer {
    
    public static final String ICON_BASE = "org/netbeans/modules/plethora/resources/peers/box.png";
    
    public BoxPeer() {
    }
    
    public BoxPeer(IdPair idPair, BoxWrapper boxWrapper) {
        super(idPair, boxWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
    
    public BoxPeer(IdPair idPair, BoxWrapper boxWrapper, Component3DWrapper component3DWrapper) {
        super(idPair, boxWrapper, component3DWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }

}