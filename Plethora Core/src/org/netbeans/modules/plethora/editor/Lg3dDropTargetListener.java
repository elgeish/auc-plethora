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
 * Lg3dDropTargetListener.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.peers.ShapePeer;

/**
 * Listner to the drop action on the Lg3dEditorComponent and
 * addes the dropped item to the Lg3dDataDocument,
 * the Lg3dComponentInspector, and the Lg3dDesigner
 *
 * @see Lg3dEditorComponent
 * @see Lg3dDesigner
 * @see Lg3dDataDocument
 * @see Lg3dComponentInspector
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dDropTargetListener implements DropTargetListener {
    
    private Lg3dEditorSupport lg3dEditorSupport;
    private Lg3dEditorComponent lg3dEditorComponent;
    private Lg3dDataDocument lg3dDataDocument;
    private Lg3dDesigner lg3dDesigner;
    private Lg3dComponentInspector lg3dComponentInspector;
    
    public Lg3dDropTargetListener(Lg3dEditorComponent lg3dEditorComponent) {
        this.lg3dEditorComponent = lg3dEditorComponent;
        lg3dEditorSupport = lg3dEditorComponent.getLg3dDataObject().getLg3dEditorSupport();
        lg3dDesigner = lg3dEditorComponent.getLg3dDesigner();
        lg3dDataDocument = lg3dEditorComponent.getLg3dDataObject().getLg3dDataDocument();
        lg3dComponentInspector = Lg3dComponentInspector.getInstance();
    }
    
    public void drop(DropTargetDropEvent dtde) {
        try {
            ShapePeer peer = lg3dEditorComponent.getSelectedShapePeer();

            if (peer != null) {
                peer = peer.createShapePeer(lg3dDataDocument.addElement(peer.getLg3dClassName()));

                /* // FOR DEMO ONLY >> REMOVE LATER */
                /************************************/
                if (!peer.getLg3dClassName().equals("org.jdesktop.lg3d.wg.Container3D")
                && !peer.getLg3dClassName().equals("org.jdesktop.lg3d.utils.shape.Text2D")
                && !peer.getLg3dClassName().startsWith("edu.aucegypt.plethora.widgets")) {
                    peer.getComponent().setScale(0.05f);
                }
                /************************************/
                
                lg3dDesigner.add(peer.getComponent());
                lg3dComponentInspector.appendNode(peer);
                lg3dComponentInspector.selectNode(peer);
                lg3dEditorSupport.notifyModified();
            }
            
        } catch (Exception ex) {
            org.openide.ErrorManager.getDefault().notify(ex); // Shouldn't happen
        }
    }
    
    public void dragEnter(DropTargetDragEvent dtde) {
    }
    
    public void dragOver(DropTargetDragEvent dtde) {
    }
    
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }
    
    public void dragExit(DropTargetEvent dte) {
    }
    
}