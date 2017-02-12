/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dViewDescription.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import java.io.Serializable;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.modules.plethora.lg3d.Lg3dDataNode;
import org.netbeans.modules.plethora.lg3d.Lg3dDataObject;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Description of the LG3D Editor view element of the multi-view.
 * It provides the name, icon, persistence type, help context,
 * and the visual representation of the element
 *
 * @see Lg3dEditorComponent
 * @see Lg3dEditorSupport
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dViewDescription implements MultiViewDescription, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Lg3dDataObject lg3dDataObject;
        
    public Lg3dViewDescription(Lg3dDataObject lg3dDataObject) {
        this.lg3dDataObject = lg3dDataObject;
    }

    public MultiViewElement createElement() {
        return new Lg3dEditorComponent(lg3dDataObject);
    }
    
    public String getDisplayName() {
        return Bundle.getBundleString("CTL_DesignTabCaption");
    }
    
    public org.openide.util.HelpCtx getHelpCtx() {
        return org.openide.util.HelpCtx.DEFAULT_HELP;
    }
    
    public java.awt.Image getIcon() {
        return Utilities.loadImage(Lg3dDataNode.LG3D_ICON_BASE);
    }
    
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ONLY_OPENED;
    }
    
    public String preferredID() {
        return Lg3dEditorSupport.MV_LG3D_ID;
    }
    
    /*
     *
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(dataObject);
        }

        public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException
        {
            Object firstObject = in.readObject();
            if (firstObject instanceof FormDataObject)
                dataObject = (DataObject) firstObject;
        }
     *
     */
    
}