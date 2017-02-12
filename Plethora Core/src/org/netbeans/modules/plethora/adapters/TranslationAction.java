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