/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dEditorComponent.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.dnd.DropTarget;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.JComponent;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.lg3d.Lg3dDataObject;
import org.netbeans.modules.plethora.peers.ShapePeer;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.awt.StatusDisplayer;
import org.openide.awt.UndoRedo;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 * Top-component that hosts the Lg3dDesignerPanel and associate itself with
 * the palette window and the Lg3dDropTargetListener
 *
 * @see Lg3dDesigner
 * @see Lg3dDataDocument
 * @see Lg3dComponentInspector
 * @see Lg3dDropTargetListener
 * @see Lg3dViewDescription
 * @see Lg3dEditorSupport
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dEditorComponent extends TopComponent implements MultiViewElement {
    
    private static final long serialVersionUID = 1L;
    private static final String PALETTE_FOLDER_NAME = "Lg3dPalette";
    
    private transient MultiViewElementCallback callback;
    private Lg3dDataObject lg3dDataObject;
    private Lg3dEditorSupport lg3dEditorSupport;
    private PaletteController paletteController;
    private ShapePeer shapePeer;
    private Lg3dDesigner lg3dDesigner;
    private Lg3dToolBar lg3dToolBar;
    
    public Lg3dEditorComponent(Lg3dDataObject lg3dDataObject) {
        this(lg3dDataObject, new InstanceContent());
    }
    
    public Lg3dEditorComponent(Lg3dDataObject lg3dDataObject, InstanceContent instanceContent) {
        super(new AbstractLookup(instanceContent));
        
        this.lg3dDataObject = lg3dDataObject;
        lg3dEditorSupport = lg3dDataObject.getLg3dEditorSupport();
        paletteController = initializePalette();
        lg3dDesigner = Lg3dDesigner.getLg3dDesigner(lg3dDataObject.getLg3dDataDocument());
        lg3dToolBar = new Lg3dToolBar();
        
        paletteController.addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (PaletteController.PROP_SELECTED_ITEM.equals(evt.getPropertyName())) {
                    Lookup selectedItem = paletteController.getSelectedItem();
                    
                    if( selectedItem != null ) {
                        ActiveEditorDrop selectedNode = (ActiveEditorDrop) selectedItem.lookup(ActiveEditorDrop.class);
                        
                        if( selectedNode != null ){
                            shapePeer = (ShapePeer) selectedNode;
                            StatusDisplayer.getDefault().setStatusText(shapePeer.getLg3dClassName());
                        }
                    }
                }
            }
            
        });
        
        instanceContent.add(paletteController);
        
        setDropTarget(new DropTarget(this, new Lg3dDropTargetListener(this)));
        setLayout(new BorderLayout());
        add("Center", lg3dDesigner);
    }
    
    private PaletteController initializePalette() {
        try {
            return PaletteFactory.createPalette(PALETTE_FOLDER_NAME, new PaletteActions() {
                public Action[] getCustomCategoryActions(Lookup lookup) {
                    return new Action[0];
                }
                public Action[] getCustomItemActions(Lookup lookup) {
                    return new Action[0];
                }
                public Action[] getCustomPaletteActions() {
                    return new Action[0];
                }
                public Action[] getImportActions() {
                    return new Action[0];
                }
                public Action getPreferredAction(Lookup lookup) {
                    return null;
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Lg3dDesigner getLg3dDesigner() {
        return lg3dDesigner;
    }
    
    public ShapePeer getSelectedShapePeer() {
        return shapePeer;
    }
    
    public Lg3dDataObject getLg3dDataObject() {
        return lg3dDataObject;
    }
    
    public JComponent getToolbarRepresentation() {
        return lg3dToolBar;
    }
    
    public JComponent getVisualRepresentation() {
        return this;
    }
    
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
        lg3dEditorSupport.setTopComponent(callback.getTopComponent());
        
        // add FormDesigner as a client property so it can be obtained
        // from multiview TopComponent (it is not sufficient to put
        // it into lookup - only content of the lookup of the active
        // element is accessible)
        
        // callback.getTopComponent().putClientProperty("formDesigner", this); // NOI18N
    }
    
    public int getPersistenceType() {
        // Only MultiViewDescriptor is stored, not MultiViewElement
        return TopComponent.PERSISTENCE_NEVER;
    }
    
    public UndoRedo getUndoRedo() {
        /*
        UndoRedo ur = formModel != null ? formModel.getUndoRedoManager() : null;
        return ur != null ? ur : super.getUndoRedo();
         */
        return super.getUndoRedo();
    }
    
    protected String preferredID() {
        return lg3dDataObject.getName();
    }
    
    public void requestVisible() {
        if (callback != null) {
            callback.requestVisible();
        } else {
            super.requestVisible();
        }
    }
    
    public void requestActive() {
        if (callback != null) {
            callback.requestActive();
        } else {
            super.requestActive();
        }
    }
    
    public void componentOpened() {
        super.componentOpened();
        
        if ((lg3dEditorSupport == null) && (callback != null)) { // Issue 67879
            callback.getTopComponent().close();
            
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    // FormEditorSupport.checkFormGroupVisibility();
                }
            });
        }
    }
    
    public void componentClosed() {
        super.componentClosed();
    }
    
    public void componentShowing() {
        super.componentShowing();
        
        if (getComponentCount() == 0) {
            lg3dDesigner = Lg3dDesigner.getLg3dDesigner(lg3dDataObject.getLg3dDataDocument());
            add("Center", lg3dDesigner);
            revalidate();
        }
        
        Lg3dComponentInspector.getInstance().setLg3dDataDocument(lg3dDataObject.getLg3dDataDocument());
    }
    
    public void componentHidden() {
        Lg3dComponentInspector.getInstance().setLg3dDataDocument(null);
        super.componentHidden();
    }
    
    public void componentActivated() {
        super.componentActivated();
    }
    
    public void componentDeactivated() {
        super.componentDeactivated();
    }
    
    public CloseOperationState canCloseElement() {
        // if this is not the last cloned designer, closing is OK
        if (! lg3dEditorSupport.isLastView(callback.getTopComponent())) {
            return CloseOperationState.STATE_OK;
        }
        
        // return a placeholder state - to be sure our CloseHandler is called
        return MultiViewFactory.createUnsafeCloseState(
                "ID_LG3D_CLOSING", // dummy ID // NOI18N
                MultiViewFactory.NOOP_CLOSE_ACTION,
                MultiViewFactory.NOOP_CLOSE_ACTION);
    }
    
}