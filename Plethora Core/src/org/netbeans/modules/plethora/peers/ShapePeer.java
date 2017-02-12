/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ShapePeer.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import edu.aucegypt.plethora.widgets.Appearance;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.Action;
import javax.swing.text.JTextComponent;
import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.wg.Component3D;
import org.netbeans.modules.plethora.model.ComplexParameter;
import org.netbeans.modules.plethora.model.IdPair;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;
import org.netbeans.modules.plethora.properties.ShapePropertiesSetFactory;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.actions.CopyAction;
import org.openide.actions.CutAction;
import org.openide.actions.DeleteAction;
import org.openide.actions.MoveDownAction;
import org.openide.actions.MoveUpAction;
import org.openide.actions.PasteAction;
import org.openide.actions.PropertiesAction;
import org.openide.actions.RenameAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.Sheet;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for a Component3D. It's an abstract class that's intended to be extended by
 * other classes whose peers must be added to a Component3D
 *
 * @see ShapeWrapper
 *
 * @author Mohamed El-Geish and Sarah Nadi
 * @version 1.00
 */
public abstract class ShapePeer extends AbstractNode implements ActiveEditorDrop {
    
    public static final String IDENTIFIER = "Identifier";
    
    private static final Hashtable<Class, Class> PRIMITIVE_TYPES = new Hashtable<Class, Class>();
    
    static {
        PRIMITIVE_TYPES.put(Integer.class, int.class);
        PRIMITIVE_TYPES.put(Float.class, float.class);
        PRIMITIVE_TYPES.put(Double.class, double.class);
        PRIMITIVE_TYPES.put(Short.class, short.class);
    }
    
    private boolean isCopy;
    
    private Component3DWrapper component3DWrapper;
    private ShapeWrapper shapeWrapper;
    private Sheet sheet;
    private Sheet.Set eventsSet;
    private Sheet.Set componentPropertiesSet;
    private Sheet.Set shapePropertiesSet;
    
    /* for the palette item in sub-classes */
    protected ShapePeer() {
        super(Children.LEAF);
        
        try {
            shapeWrapper = (ShapeWrapper) Class.forName(getClass().getName().replaceFirst("Peer", "Wrapper")).newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /* for DnD use in sub-classes */
    protected ShapePeer(IdPair idPair, ShapeWrapper shapeWrapper) {
        this(idPair, shapeWrapper, new Component3DWrapper());
    }
    
    /* for shape loading use in sub-classes */
    protected ShapePeer(IdPair idPair, ShapeWrapper shapeWrapper, Component3DWrapper component3DWrapper) {
        super(Children.LEAF, Lookups.fixed(new Object[] {shapeWrapper, component3DWrapper}));
        
        this.shapeWrapper = shapeWrapper;
        this.shapeWrapper.setName(idPair.getShapeId());
        this.shapeWrapper.setShapePeer(this);
        
        this.component3DWrapper = component3DWrapper;
        this.component3DWrapper.removeAllChildren();
        this.component3DWrapper.addChild((Node) shapeWrapper);
        this.component3DWrapper.setShapePeer(this);
        this.component3DWrapper.setName(idPair.getComponentId());
        
        super.setName(idPair.getShapeId());
        setDisplayName(getName() + " [" + getLg3dClassName().substring(getLg3dClassName().lastIndexOf('.') + 1) + "]");
    }
    
    public boolean addChild(ShapePeer peer) {
        return false;
    }
    
    public Component3D getComponent() {
        return component3DWrapper;
    }
    
    public ShapeWrapper getShapeWrapper() {
        return shapeWrapper;
    }
    
    public ShapePeer createShapePeer(IdPair idPair) {
        try {
            return (ShapePeer) getClass().getConstructor(IdPair.class, shapeWrapper.getClass())
            .newInstance(idPair, shapeWrapper.getClass().newInstance());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    protected ShapePeer createCopy() {
        try {
            IdPair idPair = shapeWrapper.getLg3dDataDocument().addElement(getLg3dClassName());
            Property[] properties = shapePropertiesSet.getProperties();
            Class[] shapeWrapperArgClasses = new Class[properties.length];
            Object[] shapeWrapperArgs = new Object[properties.length];
            
            for (int i = 0 ; i < properties.length; ++i) {
                shapeWrapperArgClasses[i] = properties[i].getValueType();
                shapeWrapperArgs[i] = properties[i].getValue();
            }
            
            ShapePeer newPeer = (ShapePeer) getClass().getConstructor(IdPair.class, shapeWrapper.getClass(), Component3DWrapper.class).newInstance(
                    idPair,
                    shapeWrapper.getClass().getConstructor(shapeWrapperArgClasses).newInstance(shapeWrapperArgs),
                    new Component3DWrapper());
            
            newPeer.createSheet();
            
            for (Property property : componentPropertiesSet.getProperties()) {
                if (!property.getName().equals(IDENTIFIER) && property.canWrite()) {
                    newPeer.componentPropertiesSet.get(property.getName()).setValue(property.getValue());
                }
            }
            
            return newPeer;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    protected boolean isCopy() {
        return isCopy;
    }
    
    protected ShapePeer doCopy(ShapePeer droppedShapePeer) {
        ShapePeer newShapePeer = droppedShapePeer.createCopy();
        
        if (getShapeWrapper().getLg3dDataDocument().CONTAINER_LIST.contains(droppedShapePeer.getLg3dClassName())) {
            
            ShapePeer newChildPeer;
            
            for(org.openide.nodes.Node node : droppedShapePeer.getChildren().getNodes()) {
                
                newChildPeer = doCopy((ShapePeer) node);
                newShapePeer.addChild(newChildPeer);
                
                try {
                    getShapeWrapper().getLg3dDataDocument().moveComponentToContainer(
                            newShapePeer.getComponent().getName(),
                            newChildPeer.getComponent().getName());
                } catch (XPathExpressionException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return newShapePeer;
    }
    
    protected Sheet.Set getEventsSet() {
        return eventsSet;
    }
    
    protected Sheet.Set getComponentPropertiesSet() {
        return componentPropertiesSet;
    }
    
    protected Sheet.Set getShapePropertiesSet() {
        return shapePropertiesSet;
    }
    
    public String getLg3dClassName() {
        return shapeWrapper.getClass().getSuperclass().getName();
    }
    
    public static void reconstruct(ShapePeer shapePeer, Object... shapeWrapperArgs) {
        org.openide.nodes.Node parentNode = shapePeer.getParentNode();
        Children children = null;
        
        if (parentNode != null) {
            children = parentNode.getChildren();
        }
        
        Component3D parentComponent = (Component3D) shapePeer.component3DWrapper.getParent();
        Node shapeWrapper = (Node) shapePeer.shapeWrapper;
        IdPair idPair = new IdPair(shapePeer.component3DWrapper.getName(), shapeWrapper.getName());
        Class[] shapeWrapperArgClasses = new Class[shapeWrapperArgs.length];
        Class type;
        
        for (int i = 0; i < shapeWrapperArgs.length; ++i) {
            type = PRIMITIVE_TYPES.get(shapeWrapperArgs[i].getClass());
            shapeWrapperArgClasses[i] = type != null ? type : shapeWrapperArgs[i].getClass();
        }
        
        try {
            shapePeer.destroy();
            
            shapePeer = (ShapePeer) shapePeer.getClass().getConstructor(IdPair.class, shapeWrapper.getClass(), Component3DWrapper.class).newInstance(
                    idPair,
                    shapeWrapper.getClass().getConstructor(shapeWrapperArgClasses).newInstance(shapeWrapperArgs),
                    shapePeer.component3DWrapper
                    );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (children != null) {
            children.add(new ShapePeer[] {shapePeer});
        }
        
        parentComponent.addChild(shapePeer.component3DWrapper);
    }
    
    static Parameter createAppearanceParameter(Appearance ap) {
        Vector<Parameter> params = new Vector<Parameter>(3);
        
        params.add(new SimpleParameter(float.class.getName(), ap.getRed() + "f"));
        params.add(new SimpleParameter(float.class.getName(), ap.getGreen() + "f"));
        params.add(new SimpleParameter(float.class.getName(), ap.getBlue() + "f"));
        params.add(new SimpleParameter(float.class.getName(), ap.getAlpha() + "f"));
        
        return  new ComplexParameter(Appearance.class.getName(), params);
    }
    
    /* Overriding AbstractNode */
    
    protected Sheet createSheet() {
        if (sheet == null) {
            sheet = Sheet.createDefault();
            
            eventsSet = ShapePropertiesSetFactory.createEventsSheetSet(component3DWrapper);
            
            componentPropertiesSet = ShapePropertiesSetFactory.createSheetSet(component3DWrapper);
            shapePropertiesSet = ShapePropertiesSetFactory.createSheetSet(shapeWrapper);
            
            sheet.put(componentPropertiesSet);
            sheet.put(shapePropertiesSet);
            
            eventsSet.setValue("tabName", Bundle.getBundleString("CTL_EventsTabName"));
            sheet.put(eventsSet);
        }
        
        return sheet;
    }
    
    public boolean canDestroy() {
        return true;
    }
    
    public boolean canRename() {
        return true;
    }
    
    public boolean canCut() {
        return true;
    }
    
    public void destroy() throws java.io.IOException {
        ((Component3D) component3DWrapper.getParent()).removeChild(component3DWrapper);
        super.destroy();
    }
    
    public void setName(String id) {
        if (!org.openide.util.Utilities.isJavaIdentifier(id)) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                    Bundle.getBundleString("MSG_JavaVariableName"), NotifyDescriptor.INFORMATION_MESSAGE));
            return;
        }
        
        try {
            if (getShapeWrapper().getLg3dDataDocument().renameElement(getName(), id)) {
                super.setName(id);
                setDisplayName(getName() + " [" + getLg3dClassName().substring(getLg3dClassName().lastIndexOf('.') + 1) + "]");
                getShapeWrapper().setName(id);
            } else {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Bundle.getBundleString("MSG_IdAlreadyExists"), NotifyDescriptor.INFORMATION_MESSAGE));
            }
        } catch (XPathExpressionException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    public Transferable clipboardCopy() throws IOException {
        isCopy = true;
        return super.clipboardCopy();
    }
    
    public Transferable clipboardCut() throws IOException {
        isCopy = false;
        return super.clipboardCopy();
    }
    
    public Action[] getActions(boolean popup) {
        return new Action[] {
            SystemAction.get(CopyAction.class),
            SystemAction.get(CutAction.class),
            SystemAction.get(PasteAction.class),
            null,
            SystemAction.get(MoveUpAction.class),
            SystemAction.get(MoveDownAction.class),
            null,
            SystemAction.get(RenameAction.class),
            SystemAction.get(DeleteAction.class),
            null,
            SystemAction.get(PropertiesAction.class)
        };
    }
    
    /* Implementing ActiveEditorDrop */
    
    public boolean handleTransfer(JTextComponent jTextComponent) {
        return true;
    }
    
}