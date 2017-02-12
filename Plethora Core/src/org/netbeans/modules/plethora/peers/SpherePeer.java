/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * SpherePeer.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import org.netbeans.modules.plethora.model.IdPair;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for a Sphere
 *
 * @see ShapePeer
 * @see SphereWrapper
 *
 * @author Mohamed El-Geish 
 * @version 1.00
 */
public class SpherePeer extends ShapePeer {
    
    public static final String ICON_BASE = "org/netbeans/modules/plethora/resources/peers/sphere.png";

    public SpherePeer() {
    }
    
    public SpherePeer(IdPair idPair, SphereWrapper sphereWrapper) {
        super(idPair, sphereWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
    
    public SpherePeer(IdPair idPair, SphereWrapper sphereWrapper, Component3DWrapper component3DWrapper) {
        super(idPair, sphereWrapper, component3DWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
   
}