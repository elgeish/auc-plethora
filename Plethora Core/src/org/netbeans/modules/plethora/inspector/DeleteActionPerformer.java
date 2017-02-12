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
 * DeleteActionPerformer.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.inspector;

import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import javax.swing.AbstractAction;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.openide.nodes.Node;
import org.openide.util.Mutex;
import org.openide.util.actions.ActionPerformer;
import org.openide.util.actions.SystemAction;

/**
 * Performer for Delete Action
 *
 * @see Lg3dComponentInspector
 *
 * @author Sarah Nadi, May Sayed, and Mohamed El-Geish
 * @version 1.00
 */
class DeleteActionPerformer extends AbstractAction implements ActionPerformer, Mutex.Action {
    
    private Node[] nodesToDestroy;
    
    public void actionPerformed(ActionEvent e) {
        performAction(null);
    }
    
    public void performAction(SystemAction action) {
        Node[] selected = Lg3dComponentInspector.getInstance().getExplorerManager().getSelectedNodes();
        
        if (selected == null || selected.length == 0) {
            return;
        }
        
        for (int i = 0 ; i < selected.length ; i++) {
            if (!selected[i].canDestroy()) {
                return;
            }
        }
        
        try { // Clear nodes selection first
            Lg3dComponentInspector.getInstance().getExplorerManager().setSelectedNodes(new Node[0]);
        } catch (PropertyVetoException e) { // Cannot be vetoed
        }
        
        nodesToDestroy = selected;
        
        if (java.awt.EventQueue.isDispatchThread()) {
            doDelete();
        } else { // Reinvoke synchronously in AWT thread
            Mutex.EVENT.readAccess(this);
        }
    }
    
    public Object run() {
        doDelete();
        return null;
    }
    
    private void doDelete() {
        Lg3dDataDocument lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
        
        if (nodesToDestroy != null) {
            for (int i = 0 ; i < nodesToDestroy.length ; i++) {
                try {
                    lg3dDataDocument.removeElement(nodesToDestroy[i].getName());
                    nodesToDestroy[i].destroy();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            nodesToDestroy = null;
        }
    }
    
}