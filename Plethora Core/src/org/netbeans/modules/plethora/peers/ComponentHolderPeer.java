/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ComponentHolderPeer.java
 * Created on December 05, 2006
 */

package org.netbeans.modules.plethora.peers;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.io.IOException;
import java.util.List;
import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.wg.Component3D;
import org.netbeans.modules.plethora.model.IdPair;
import org.netbeans.modules.plethora.properties.ShapePropertiesSetFactory;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.actions.NewTemplateAction.Cookie;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.nodes.Sheet;
import org.openide.util.datatransfer.PasteType;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for a ComponentHolder
 *
 * @see ShapePeer
 * @see ComponentHolderWrapper
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class ComponentHolderPeer extends ShapePeer {
    
    public static final String ICON_BASE = "org/netbeans/modules/plethora/resources/peers/component-holder.png";
    
    private Sheet sheet;
    private Sheet.Set eventsSet;
    private Sheet.Set shapePropertiesSet;
    
    public ComponentHolderPeer() {
    }
    
    public ComponentHolderPeer(IdPair idPair, ComponentHolderWrapper componentHolderWrapper) {
        this(idPair, componentHolderWrapper, new Component3DWrapper());
    }
    
    public ComponentHolderPeer(IdPair idPair, ComponentHolderWrapper componentHolderWrapper, Component3DWrapper component3DWrapper) {
        super(idPair, componentHolderWrapper, component3DWrapper);
        
        setIconBaseWithExtension(ICON_BASE);
        setChildren(new Index.ArrayChildren());
        getComponent().setName(componentHolderWrapper.getName());
    }
    
    public boolean addChild(ShapePeer peer) {
        if (peer != null) {
            
            ComponentHolderWrapper componentHolderWrapper = (ComponentHolderWrapper) getShapeWrapper();
            Component3D component3D = peer.getComponent();
            Component3D parentComponent3D = (Component3D) component3D.getParent();
            
            if (peer.getParentNode() != null && peer.getParentNode().getChildren() != null) {
                peer.getParentNode().getChildren().remove(new ShapePeer[] {peer});
            }
            
            if (parentComponent3D != null) {
                parentComponent3D.removeChild(component3D);
            }
            
            componentHolderWrapper.addChild(component3D);
        }
        
        return getChildren().add(new ShapePeer[] {peer});
    }
    
    protected Sheet createSheet() {
        if (sheet == null) {
            sheet = Sheet.createDefault();
            
            shapePropertiesSet = ShapePropertiesSetFactory.createSheetSet(getShapeWrapper());
            sheet.put(shapePropertiesSet);
            
            eventsSet = ShapePropertiesSetFactory.createEventsSheetSet((ComponentHolderWrapper) getShapeWrapper());
            eventsSet.setValue("tabName", Bundle.getBundleString("CTL_EventsTabName"));
            sheet.put(eventsSet);
        }
        
        return sheet;
    }
    
    protected Sheet.Set getEventsSet() {
        return eventsSet;
    }
    
    public PasteType getDropType(Transferable transferable, final int action, int index) {
        final ShapePeer droppedShapePeer = (ShapePeer) NodeTransfer.node(transferable, DnDConstants.ACTION_COPY_OR_MOVE + NodeTransfer.CLIPBOARD_CUT);
        Node parent = getParentNode();
        
        if (droppedShapePeer == null) {
            return null;
        }
        
        final ShapeWrapper shape = (ShapeWrapper) droppedShapePeer.getLookup().lookup(ShapeWrapper.class);
        final boolean isCut = (action & DnDConstants.ACTION_MOVE) != 0 || ! droppedShapePeer.isCopy();
        
        if (shape == null || (isCut && this.equals(droppedShapePeer))) {
            return null; // Can't Cut-n-Paste this on this
        }
        
        while (parent != null) {
            if (droppedShapePeer == parent) {
                return null;
            }
            
            parent = parent.getParentNode();
        }
        
        return new PasteType() {
            public Transferable paste() throws IOException {
                ShapePeer newShapePeer = null;
                
                if (isCut) {
                    newShapePeer = droppedShapePeer;
                } else if(droppedShapePeer.isCopy()) {
                    newShapePeer = doCopy(droppedShapePeer);
                }
                
                /* Adding the peer to the current (dropped at) node */
                addChild(newShapePeer);
                
                try {
                    
                    getShapeWrapper().getLg3dDataDocument().moveComponentToContainer(
                            getComponent().getName(),
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
    
    protected ShapePeer createCopy() {
        try {
            IdPair idPair = getShapeWrapper().getLg3dDataDocument().addElement(getLg3dClassName());
            ComponentHolderPeer newPeer = (ComponentHolderPeer) createShapePeer(idPair);
            
            newPeer.createSheet();
            
            for (Property property : shapePropertiesSet.getProperties()) {
                if (property.canWrite()) {
                    newPeer.shapePropertiesSet.get(property.getName()).setValue(property.getValue());
                }
            }
            
            return newPeer;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
}