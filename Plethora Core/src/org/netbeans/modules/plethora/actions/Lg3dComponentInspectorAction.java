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