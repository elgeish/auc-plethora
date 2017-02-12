/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ButtonPeer.java
 * Created on December 05, 2006
 */

package org.netbeans.modules.plethora.peers;

import org.netbeans.modules.plethora.model.IdPair;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for a Box
 *
 * @see ShapePeer
 * @see ButtonWrapper
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class ButtonPeer extends ShapePeer {
    
    public static final String ICON_BASE = "org/netbeans/modules/plethora/resources/peers/button.png";
    
    public ButtonPeer() {
    }
    
    public ButtonPeer(IdPair idPair, ButtonWrapper ButtonWrapper) {
        super(idPair, ButtonWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
    
    public ButtonPeer(IdPair idPair, ButtonWrapper ButtonWrapper, Component3DWrapper component3DWrapper) {
        super(idPair, ButtonWrapper, component3DWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }

}