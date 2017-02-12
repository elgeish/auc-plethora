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
 * LgcfgMultiViewElement.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lgcfg;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.cookies.OpenCookie;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.w3c.dom.Document;

/**
 * Top-component that hosts the GUI editor for the configuration file (LGCFG)
 *
 * @see LgcfgDataObject
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class LgcfgMultiViewElement extends TopComponent implements MultiViewElement {
    
    private static final Integer FILE_NOT_CHANGED_READ_ONLY = new Integer(0);
    private static final Integer FILE_CHANGED_WRITABLE = new Integer(1);
    private static final Integer FILE_CHANGED_READ_ONLY = new Integer(2);
    private static final Integer FILE_NOT_CHANGED_WRITABLE = new Integer(3);
    
    private static final long serialVersionUID = 1L;
    
    private LgcfgDataObject lgcfgDataObject;
    
    public LgcfgMultiViewElement(LgcfgDataObject lgcfgDataObject) {
        super();
        this.lgcfgDataObject = lgcfgDataObject;
        
        setLayout(new java.awt.BorderLayout());
        
        org.w3c.dom.Node node = lgcfgDataObject.getDocument().getFirstChild();
        Children array = new Children.Array();
        Node[] nodes = null;
        
        if (node.hasAttributes()) {
            nodes = new Node[node.getAttributes().getLength()];
            
            for (int i = 0; i < nodes.length; ++i) {
                nodes[i] = new LgcfgNode(node.getAttributes().item(i).getNodeValue(), Children.LEAF);
            }
        }
        
        if( nodes != null ) {
            array.add(nodes);
        }
    }
    
    public JComponent getVisualRepresentation() {
        return this;
    }
    
    public JComponent getToolbarRepresentation() {
        return new JPanel();
    }
    
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        callback.updateTitle(getDisplayName());
        setToolTipText(Bundle.getBundleString("LBL_Lgcfg_loader_name"));
    }
    
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }
    
    public String getDisplayName() {
        boolean canWrite = lgcfgDataObject.getPrimaryFile().canWrite();
        Node node = lgcfgDataObject.getNodeDelegate();
        String title = node.getDisplayName();
        Integer mode;
        
        if (lgcfgDataObject.isModified()) {
            mode = canWrite ? FILE_CHANGED_WRITABLE : FILE_NOT_CHANGED_READ_ONLY;
        } else {
            mode = canWrite ? FILE_NOT_CHANGED_WRITABLE : FILE_NOT_CHANGED_READ_ONLY;
        }
        
        return Bundle.getFormattedBundleString("FMT_FormMVTCTitle", mode, title);
    }
    
    public void componentClosed() {
        super.componentClosed();
    }
    
    public void componentShowing() {
        super.componentShowing();
    }
    
    public void componentOpened() {
        super.componentOpened();
    }
    
    public void componentActivated() {
        super.componentActivated();
    }
    
    public void componentDeactivated() {
        super.componentDeactivated();
    }
    
    public void componentHidden() {
        super.componentHidden();
    }
    
    private class LgcfgNode extends AbstractNode {
        LgcfgNode(String displayName, Children children) {
            super(children);
            setDisplayName(displayName);
            setIconBaseWithExtension(LgcfgDataNode.LGCFG_ICON_BASE);
        }
    }
    
}