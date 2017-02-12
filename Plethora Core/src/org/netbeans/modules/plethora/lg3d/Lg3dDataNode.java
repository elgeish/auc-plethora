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
 * Lg3dDataNode.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lg3d;

import org.netbeans.modules.java.JavaNode;
import javax.swing.Action;
import org.openide.util.actions.SystemAction;

/**
 * Visual representation of the java file that has a brother LG3D file.
 * It provides the list of actions on that node as well as the icon of the file
 * 
 * @see Lg3dDataObject
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dDataNode extends JavaNode {
    
    /** Icon base for LG3D form data objects. */
    public static final String LG3D_ICON_BASE = "org/netbeans/modules/plethora/resources/lg3d-icon.png";
    public static final String LG3D_ICON_32 = "org/netbeans/modules/plethora/resources/lg3d-icon32.png";

    public Lg3dDataNode(Lg3dDataObject lg3dDataObject) {
        super(lg3dDataObject);
        setIconBaseWithExtension(LG3D_ICON_BASE);
    }
    
    public Lg3dDataObject getLg3dDataObject() {
        return (Lg3dDataObject) getDataObject();
    }
    
    public Action getPreferredAction() {
        // there is an issue her with id: 56351
        return new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                getLg3dDataObject().getLg3dEditorSupport().openLg3dEditor(false);
            }
        };
    }
    
    public Action[] getActions(boolean context) {
        Action[] javaActions = super.getActions(context);
        Action[] lg3dActions = new Action[javaActions.length + 2];
        
        lg3dActions[0] = javaActions[0]; // OpenAction
        lg3dActions[1] = SystemAction.get(org.openide.actions.EditAction.class);
        lg3dActions[2] = null;
        System.arraycopy(javaActions, 1, lg3dActions, 3, javaActions.length - 1);
        
        return lg3dActions;
    }
    
}