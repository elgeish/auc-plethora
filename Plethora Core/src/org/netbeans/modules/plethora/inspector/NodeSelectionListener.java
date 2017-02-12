/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * NodeSelectionListener.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.inspector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;

/**
 * Node selection change listener
 *
 * @see Lg3dComponentInspector
 *
 * @author Sarah Nadi, May Sayed, and Mohamed El-Geish
 * @version 1.00
 */
class NodeSelectionListener implements PropertyChangeListener, ActionListener, Runnable {
    
    private javax.swing.Timer timer;
    
    NodeSelectionListener() {
        timer = new javax.swing.Timer(150, this);
        timer.setCoalesce(true);
        timer.setRepeats(false);
    }
    
    /* Invoked by Timer */
    public void actionPerformed(ActionEvent evt) {
        java.awt.EventQueue.invokeLater(this); // Replan to EventQueue thread
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        if (ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
            timer.restart(); // Restart waiting for expensive part of the update
        }
    }
    
    /**
     * Updates activated nodes and actions. It is executed via timer
     * restarted each time a new selection change appears - if they come
     * quickly e.g. due to the user is holding a cursor key, this
     * (relatively time expensive update) is done only at the end.
     */
    public void run() {
        Lg3dComponentInspector lg3dComponentInspector = Lg3dComponentInspector.getInstance();
        Node[] selectedNodes = lg3dComponentInspector.getExplorerManager().getSelectedNodes();
        
        lg3dComponentInspector.setActivatedNodes(selectedNodes);
        lg3dComponentInspector.updatePasteAction();
        timer.stop();
    }

}