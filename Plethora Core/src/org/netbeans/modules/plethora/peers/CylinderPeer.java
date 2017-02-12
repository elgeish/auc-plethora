/*
 * License Notice
 *
 * This file is part of Plethora.
 *
 * Plethora is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License.
 *
 * Plethora is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Plethora; if not, write to the Free Software
 *
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