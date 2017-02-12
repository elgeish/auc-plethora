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
 * Lg3dComponentInspector.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.inspector;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import javax.swing.text.DefaultEditorKit;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.peers.Frame3DPeer;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.ErrorManager;
import org.openide.actions.PasteAction;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.ClipboardEvent;
import org.openide.util.datatransfer.ClipboardListener;
import org.openide.util.datatransfer.ExClipboard;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.datatransfer.MultiTransferObject;
import org.openide.util.datatransfer.PasteType;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Inspector of the components in the 3D Frame.
 * It describes the hierarchy of the components and the containers inside each other
 *
 * @see Lg3dComponentInspectorAction
 *
 * @author Sarah Nadi, May Sayed, and Mohamed El-Geish
 * @version 1.00
 */
@SuppressWarnings("deprecation")
public class Lg3dComponentInspector extends TopComponent implements ExplorerManager.Provider {
    
    public static final String INSPECTOR_ICON_BASE = "org/netbeans/modules/plethora/resources/inspector-icon.png";
    
    private static final String PREFERRED_ID = "Lg3dComponentInspector";
    private static Lg3dComponentInspector instance;
    
    private ExplorerManager explorerManager;
    private EmptyInspectorNode emptyInspectorNode;
    private BeanTreeView treeView;
    private Lg3dDataDocument lg3dDataDocument;
    private PasteAction pasteAction = (PasteAction) SystemAction.findObject(PasteAction.class, true);
    private CopyCutActionPerformer copyActionPerformer = new CopyCutActionPerformer(true);
    private CopyCutActionPerformer cutActionPerformer = new CopyCutActionPerformer(false);
    private DeleteActionPerformer deleteActionPerformer = new DeleteActionPerformer();
    private ClipboardListener clipboardListener;
    
    private Lg3dComponentInspector() {
        explorerManager = new ExplorerManager();
        associateLookup(ExplorerUtils.createLookup(explorerManager, setupActionMap(getActionMap())));
        emptyInspectorNode = new EmptyInspectorNode();
        explorerManager.setRootContext(emptyInspectorNode);
        explorerManager.addPropertyChangeListener(new NodeSelectionListener());
        setLayout(new java.awt.BorderLayout());
        createComponents();
        setIcon(Utilities.loadImage(INSPECTOR_ICON_BASE));
        setName(Bundle.getBundleString("CTL_InspectorCaption"));
        setToolTipText(Bundle.getBundleString("HINT_ComponentInspector"));
    }
    
    private void createComponents() {
        treeView = new BeanTreeView();
        treeView.setDragSource(true);
        treeView.setDropTarget(true);
        treeView.getAccessibleContext().setAccessibleName(Bundle.getBundleString("ACS_ComponentTree"));
        treeView.getAccessibleContext().setAccessibleDescription(Bundle.getBundleString("ACSD_ComponentTree"));
        add(java.awt.BorderLayout.CENTER, treeView);
    }
    
    /**
     * Gets default instance. Don't use directly, it reserved for '.settings' file only,
     * i.e. deserialization routines, otherwise you can get non-deserialized instance.
     */
    public static synchronized Lg3dComponentInspector getDefault() {
        if (instance == null) {
            instance = new Lg3dComponentInspector();
        }
        
        return instance;
    }
    
    /**
     * Finds default instance. Use in client code instead of {@link #getDefault()}.
     */
    public static synchronized Lg3dComponentInspector getInstance() {
        if (instance == null) {
            TopComponent tc = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
            
            if (instance == null) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, new IllegalStateException(
                        "Can not find Lg3dComponentInspector component for its ID. Returned " + tc)); // NOI18N
                instance = new Lg3dComponentInspector();
                instance.open();
            }
        }
        
        return instance;
    }
    
    public void setLg3dDataDocument(Lg3dDataDocument lg3dDataDocument) {
        if (this.lg3dDataDocument != lg3dDataDocument) {
            setLg3dDataDocument(lg3dDataDocument, 0);
        }
    }
    
    public void setLg3dDataDocument(final Lg3dDataDocument lg3dDataDocument, boolean visible) {
        if (this.lg3dDataDocument != lg3dDataDocument) {
            setLg3dDataDocument(lg3dDataDocument, visible ? 1 : -1);
        }
    }
    
    private void setLg3dDataDocument(final Lg3dDataDocument lg3dDataDocument, final int visibility) {
        if (java.awt.EventQueue.isDispatchThread()) {
            doSetLg3dDataDocument(lg3dDataDocument, visibility);
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    doSetLg3dDataDocument(lg3dDataDocument, visibility);
                }
            });
        }
    }
    
    private void doSetLg3dDataDocument(Lg3dDataDocument lg3dDataDocument, int visibility) {
        this.lg3dDataDocument = lg3dDataDocument;
        removeAll();
        createComponents();
        
        if (lg3dDataDocument == null) {
            getExplorerManager().setRootContext(emptyInspectorNode);
        } else {
            getExplorerManager().setRootContext(lg3dDataDocument.getFrame3DPeer());
        }
        
        revalidate();
        
        if (visibility > 0) {
            open();
        } else if (visibility < 0) {
            close();
        }
    }
    
    public Lg3dDataDocument getLg3dDataDocument() {
        return lg3dDataDocument;
    }
    
    public boolean appendNode(Node node) {
        return explorerManager.getRootContext().getChildren().add(new Node[] {node});
    }
    
    public void selectNode(Node node) {
        try {
            if (java.awt.EventQueue.isDispatchThread()) {
                if (!isOpened()) {
                    open();
                }
                Lg3dComponentInspector.getInstance().requestActive();
            } else {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        if (!isOpened()) {
                            open();
                        }
                        Lg3dComponentInspector.getInstance().requestActive();
                    }
                });
            }
            
            getExplorerManager().setExploredContextAndSelection(node, new Node[] {node});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setSelectedNodes(Node[] nodes, Lg3dDataDocument lg3dDataDocument) throws PropertyVetoException {
        if (this.lg3dDataDocument == lg3dDataDocument) {
            getExplorerManager().setSelectedNodes(nodes);
        }
    }
    
    public Node[] getSelectedNodes() {
        return getExplorerManager().getSelectedNodes();
    }
    
    void updatePasteAction() {
        if(java.awt.EventQueue.isDispatchThread()) {
            doUpdatePasteActionInAwtThread();
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    doUpdatePasteActionInAwtThread();
                }
            });
        }
    }
    
    private void doUpdatePasteActionInAwtThread() {
        Node[] selected = getExplorerManager().getSelectedNodes();
        
        if (selected != null && selected.length == 1) {
            // Exactly one node must be selected
            Clipboard clipboard = getClipboard();
            Transferable trans = clipboard.getContents(this); // [this??]
            
            if (trans != null) {
                Node node = selected[0];
                PasteType[] pasteTypes = node.getPasteTypes(trans);
                if (pasteTypes.length != 0) {
                    // transfer accepted by the node, we are done
                    pasteAction.setPasteTypes(pasteTypes);
                    return;
                }
                
                boolean multiFlavor = false;
                try {
                    multiFlavor = trans.isDataFlavorSupported(ExTransferable.multiFlavor);
                } catch (Exception e) {} // ignore, should not happen
                
                if (multiFlavor) {
                    // The node did not accept whole multitransfer as is - try
                    // to break it into individual transfers and paste them in
                    // sequence instead.
                    try {
                        MultiTransferObject mto = (MultiTransferObject)
                        trans.getTransferData(ExTransferable.multiFlavor);
                        
                        int n = mto.getCount(), i;
                        Transferable[] t = new Transferable[n];
                        PasteType[] p = new PasteType[n];
                        
                        for (i=0; i < n; i++) {
                            t[i] = mto.getTransferableAt(i);
                            pasteTypes = node.getPasteTypes(t[i]);
                            if (pasteTypes.length == 0)
                                break;
                            
                            p[i] = pasteTypes[0]; // ??
                        }
                        
                        if (i == n) { // all individual transfers accepted
                            pasteAction.setPasteTypes(new PasteType[] { new MultiPasteType(t, p) });
                            return;
                        }
                        
                    } catch (Exception ex) {
                        org.openide.ErrorManager.getDefault().notify(org.openide.ErrorManager.INFORMATIONAL, ex);
                    }
                }
            }
        }
        
        pasteAction.setPasteTypes(null);
    }
    
    Clipboard getClipboard() {
        Clipboard clipboard = (java.awt.datatransfer.Clipboard)
        
        Lookup.getDefault().lookup(java.awt.datatransfer.Clipboard.class);
        
        if (clipboard == null) {
            clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        }
        
        return clipboard;
    }
    
    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }
    
    public UndoRedo getUndoRedo() {
        return super.getUndoRedo();
    }
    
    public Object writeReplace() {
        return new ResolvableHelper();
    }
    
    protected void componentActivated() {
        attachActions();
    }
    
    protected void componentDeactivated() {
        detachActions();
    }
    
    /**
     * Overriden to explicitely set persistence type of ComponentInspector
     * to PERSISTENCE_ALWAYS
     */
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }
    
    protected String preferredID() {
        return PREFERRED_ID;
    }
    
    javax.swing.ActionMap setupActionMap(javax.swing.ActionMap map) {
        map.put(DefaultEditorKit.copyAction, copyActionPerformer);
        map.put(DefaultEditorKit.cutAction, cutActionPerformer);
        //map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(explorerManager));
        map.put("delete", deleteActionPerformer); // NOI18N
        return map;
    }
    
    synchronized void attachActions() {
        ExplorerUtils.activateActions(explorerManager, true);
        updatePasteAction();
        
        Clipboard clipboard = getClipboard();
        
        if (clipboard instanceof ExClipboard) {
            ExClipboard clip = (ExClipboard) clipboard;
            
            if (clipboardListener == null) {
                clipboardListener = new ClipboardChangesListener();
            }
            
            clip.addClipboardListener(clipboardListener);
        }
    }
    
    synchronized void detachActions() {
        ExplorerUtils.activateActions(explorerManager, false);
        
        Clipboard clipboard = getClipboard();
        
        if (clipboard instanceof ExClipboard) {
            ExClipboard clip = (ExClipboard) clipboard;
            
            clip.removeClipboardListener(clipboardListener);
        }
    }
    
    private class ClipboardChangesListener implements ClipboardListener {
        
        public void clipboardChanged(ClipboardEvent ev) {
            if (!ev.isConsumed()) {
                updatePasteAction();
            }
        }
        
    }
    
    final public static class ResolvableHelper implements java.io.Serializable {
        static final long serialVersionUID = 7424646018839457544L;
        
        public Object readResolve() {
            return Lg3dComponentInspector.getDefault();
        }
        
    }
}