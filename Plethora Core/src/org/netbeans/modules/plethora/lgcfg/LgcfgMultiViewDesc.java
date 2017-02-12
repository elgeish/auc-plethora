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