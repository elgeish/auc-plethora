/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Frame3DPeer.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.io.IOException;
import java.util.List;
import javax.swing.Action;
import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.wg.Component3D;
import org.netbeans.modules.plethora.editor.Lg3dDesigner;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.lg3d.Lg3dDataNode;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.openide.actions.NewTemplateAction.Cookie;
import org.openide.actions.PasteAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.PasteType;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for a Frame3D
 *
 * @author Sarah Nadi, May Sayed, and Mohamed El-Geish
 * @version 1.00
 */
public class Frame3DPeer extends AbstractNode {
    
    Lg3dDataDocument lg3dDataDocument;
    
    public Frame3DPeer() {
        super(new Index.ArrayChildren());
        setIconBaseWithExtension(Lg3dDataNode.LG3D_ICON_BASE);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
    }
    
    public boolean addChild(ShapePeer peer) {
        Component3D component3D = peer.getComponent();
        Component3D parentComponent3D = (Component3D) component3D.getParent();
        
        if (peer.getParentNode() != null && peer.getParentNode().getChildren() != null) {
            peer.getParentNode().getChildren().remove(new ShapePeer[] {peer});
        }
        
        if (parentComponent3D != null) {
            parentComponent3D.removeChild(component3D);
        }
        
        Lg3dDesigner.getLg3dDesigner(lg3dDataDocument).add(component3D);
        
        return getChildren().add(new ShapePeer[] {peer});
    }
    
    public boolean canRename() {
        return false;
    }
    
    public PasteType getDropType(Transferable transferable, final int action, int index) {
        final ShapePeer droppedShapePeer = (ShapePeer) NodeTransfer.node(transferable, DnDConstants.ACTION_COPY_OR_MOVE + NodeTransfer.CLIPBOARD_CUT);
        
        if (droppedShapePeer == null) {
            return null;
        }
        
        final ShapeWrapper shape = (ShapeWrapper) droppedShapePeer.getLookup().lookup(ShapeWrapper.class);
        final boolean isCut = (action & DnDConstants.ACTION_MOVE) != 0 || ! droppedShapePeer.isCopy();
        
        return new PasteType() {
            public Transferable paste() throws IOException {
                ShapePeer newShapePeer = null;
                
                if (isCut) {
                    newShapePeer = droppedShapePeer;
                } else if(droppedShapePeer.isCopy()) {
                    // newShapePeer = droppedShapePeer.createCopy();
                    newShapePeer = doCopy(droppedShapePeer);
                }
                
                /* Adding the peer to the current (dropped at) node */
                addChild(newShapePeer);
                
                try {
                    
                    lg3dDataDocument.moveComponentToContainer(
                            null,
                            newShapePeer.getComponent().getName());
                    
                } catch (XPathExpressionException ex) {
                    ex.printStackTrace();
                }
                
                return null;
            }
        };
    }
    
    public Cookie getCookie(Class type) {
        Children children = getChildren();
        
        if (type.isInstance(children)) {
            return (Cookie) children;
        }
        
        return super.getCookie(type);
    }
    
    @SuppressWarnings("unchecked")
    protected void createPasteTypes(Transferable transferable, List list) {
        super.createPasteTypes(transferable, list);
        
        PasteType pasteType = getDropType(transferable, DnDConstants.ACTION_COPY, -1);
        
        if (pasteType != null) {
            list.add(pasteType);
        }
    }
    
    public Action[] getActions(boolean popup) {
        return new Action[] {
            SystemAction.get(PasteAction.class)
        };
    }
    
    
    protected ShapePeer doCopy(ShapePeer droppedShapePeer) {
        ShapePeer newShapePeer = droppedShapePeer.createCopy();
        
        if (lg3dDataDocument.CONTAINER_LIST.contains(droppedShapePeer.getLg3dClassName())) {
            
            ShapePeer newChildPeer;
            
            for(Node node : droppedShapePeer.getChildren().getNodes()) {
                
                newChildPeer = doCopy((ShapePeer) node);
                newShapePeer.addChild(newChildPeer);
                
                try {
                    lg3dDataDocument.moveComponentToContainer(
                            newShapePeer.getComponent().getName(),
                            newChildPeer.getComponent().getName());
                } catch (XPathExpressionException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return newShapePeer;
    }
    
}