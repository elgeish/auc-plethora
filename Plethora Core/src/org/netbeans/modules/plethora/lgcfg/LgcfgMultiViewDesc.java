/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * LgcfgMultiViewDesc.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lgcfg;

import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.modules.xml.multiview.DesignMultiViewDesc;
import org.netbeans.modules.plethora.resources.Bundle;

/**
 * Description of the LGCFG Editor view element of the multi-view.
 * It provides the name, icon, persistence type, help context,
 * and the visual representation of the element
 *
 * @see LgcfgDataObject
 * @see LgcfgMultiViewElement
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class LgcfgMultiViewDesc extends DesignMultiViewDesc {
    
    private LgcfgDataObject lgcfgDataObject;
    
    public LgcfgMultiViewDesc(LgcfgDataObject lgcfgDataObject) {
        super(lgcfgDataObject, Bundle.getBundleString("CTL_GuiTabCaption"));
        this.lgcfgDataObject = lgcfgDataObject;
    }
    
    public MultiViewElement createElement() {
        return new LgcfgMultiViewElement(lgcfgDataObject);
    }
    
    public java.awt.Image getIcon() {
        return org.openide.util.Utilities.loadImage(LgcfgDataNode.LGCFG_ICON_BASE);
    }
    
    public String preferredID() {
        return "gui";
    }
}