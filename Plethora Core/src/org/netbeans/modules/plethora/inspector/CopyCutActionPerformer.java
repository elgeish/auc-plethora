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
 * CopyCutActionPerformer.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.inspector;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.ErrorManager;
import org.openide.nodes.Node;
import org.openide.util.actions.ActionPerformer;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.ExTransferable;

/**
 * Performer for Copy/Cut Action
 *
 * @see Lg3dComponentInspector
 *
 * @author Sarah Nadi, May Sayed, and Mohamed El-Geish
 * @version 1.00
 */
class CopyCutActionPerformer extends AbstractAction implements ActionPerformer {
    
    private boolean copy;
    
    public CopyCutActionPerformer(boolean copy) {
        this.copy = copy;
    }
    
    public void actionPerformed(ActionEvent e) {
        performAction(null);
    }
    
    public void performAction(SystemAction action) {
        Transferable transferable;
        Node[] selected = Lg3dComponentInspector.getInstance().getExplorerManager().getSelectedNodes();
        
        if (selected == null || selected.length == 0) {
            transferable = null;
        } else if (selected.length == 1) {
            transferable = getTransferableOwner(selected[0]);
        } else {
            Transferable[] transArray = new Transferable[selected.length];
            
            for (int i=0; i < selected.length; i++)
                if ((transArray[i] = getTransferableOwner(selected[i]))
                == null)
                    return;
            
            transferable = new ExTransferable.Multi(transArray);
        }
        
        if (transferable != null) {
            Lg3dComponentInspector.getInstance().getClipboard().setContents(transferable, new StringSelection(""));
        }
    }
    
    private Transferable getTransferableOwner(Node node) {
        try {
            return copy ? node.clipboardCopy() : node.clipboardCut();
        } catch (java.io.IOException e) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
            return null;
        }
    }
}
