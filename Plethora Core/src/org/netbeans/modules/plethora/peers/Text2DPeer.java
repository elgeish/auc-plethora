/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Text2DPeer.java
 * Created on December 01, 2006
 */

package org.netbeans.modules.plethora.peers;

import org.netbeans.modules.plethora.model.IdPair;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for Text2D
 *
 * @see ShapePeer
 * @see Text2DWrapper
 *
 * @author Mohamed El-Geish 
 * @version 1.00
 */
public class Text2DPeer extends ShapePeer {
    
    public static final String ICON_BASE = "org/netbeans/modules/plethora/resources/peers/text2d.png";
    
    public Text2DPeer() {
    }
    
    public Text2DPeer(IdPair idPair, Text2DWrapper text2DWrapper) {
        super(idPair, text2DWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
    
    public Text2DPeer(IdPair idPair, Text2DWrapper text2DWrapper, Component3DWrapper component3DWrapper) {
        super(idPair, text2DWrapper, component3DWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }

}