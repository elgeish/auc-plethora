/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * JavaViewDescription.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import java.io.Serializable;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.modules.java.parser.JavaParser;
import org.netbeans.modules.plethora.lg3d.Lg3dDataNode;
import org.netbeans.modules.plethora.lg3d.Lg3dDataObject;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.nodes.Node;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Description of the code view element of the multi-view.
 * It provides the name, icon, persistence type, help context,
 * and the visual representation of the element
 * 
 * @see JavaEditorComponent
 * @see Lg3dEditorSupport
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class JavaViewDescription implements MultiViewDescription, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Lg3dDataObject lg3dDataObject;
    
    public JavaViewDescription(Lg3dDataObject lg3dDataObject) {
        this.lg3dDataObject = lg3dDataObject;
    }

    public MultiViewElement createElement() {
        JavaEditorComponent javaEditorComponent = new JavaEditorComponent(lg3dDataObject);
        Node[] nodes = javaEditorComponent.getActivatedNodes();
        
        lg3dDataObject.getLg3dEditorSupport().prepareDocument();
        
        if ((nodes == null) || (nodes.length == 0)) {
            javaEditorComponent.setActivatedNodes(new Node[] {lg3dDataObject.getNodeDelegate()});
        }
        
        lg3dDataObject.getCookie(JavaParser.class);
        
        return javaEditorComponent;
    }
    
    public String getDisplayName() {
        return Bundle.getBundleString("CTL_SourceTabCaption");
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
        return Lg3dEditorSupport.MV_JAVA_ID;
    }
    
}