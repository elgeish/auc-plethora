/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ModelLoaderPeer.java
 * Created on November 27, 2006
 */

package org.netbeans.modules.plethora.peers;

import org.netbeans.modules.plethora.model.IdPair;
import org.openide.nodes.Sheet;

/**
 * Peer that represents node delegate, and contains a property sheet and wrapper
 * for an ObjModelLoader
 *
 * @see ShapePeer
 * @see ModelLoaderWrapper
 *
 * @author Mohamed El-Geish and Moataz Nour
 * @version 1.00
 */
public class ModelLoaderPeer extends ShapePeer {
    
    public static final String ICON_BASE = "org/netbeans/modules/plethora/resources/peers/model-loader.png";
    
    public ModelLoaderPeer() {
    }
    
    public ModelLoaderPeer(IdPair idPair, ModelLoaderWrapper modelLoaderWrapper) {
        super(idPair, modelLoaderWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
    
    public ModelLoaderPeer(IdPair idPair, ModelLoaderWrapper modelLoaderWrapper, Component3DWrapper component3DWrapper) {
        super(idPair, modelLoaderWrapper, component3DWrapper);
        setIconBaseWithExtension(ICON_BASE);
    }
    
   protected ShapePeer createCopy() {
        try {
            IdPair idPair = getShapeWrapper().getLg3dDataDocument().addElement(getLg3dClassName());
            ModelLoaderWrapper wrapper = (ModelLoaderWrapper) getShapeWrapper();
            java.io.File file = new java.io.File(wrapper.getFilePath());
            ShapePeer newPeer = new ModelLoaderPeer(idPair,
                    new ModelLoaderWrapper(file.getParent(), file.getName(), wrapper.getLoaderClass()),
                    new Component3DWrapper());
            
            newPeer.createSheet();
            Sheet.Set newComponentPropertiesSet = newPeer.getComponentPropertiesSet();

            for (Property property : getComponentPropertiesSet().getProperties()) {
                if (!property.getName().equals(IDENTIFIER) && property.canWrite()) {
                    newComponentPropertiesSet.get(property.getName()).setValue(property.getValue());
                }
            }

            return newPeer;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

}