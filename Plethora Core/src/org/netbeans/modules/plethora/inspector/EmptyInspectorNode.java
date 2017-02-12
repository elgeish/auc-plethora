/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * EmptyInspectorNode.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.inspector;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Node for empty ComponentInspector
 *
 * @see Lg3dComponentInspector
 *
 * @author Sarah Nadi, May Sayed, and Mohamed El-Geish
 * @version 1.00
 */
class EmptyInspectorNode extends AbstractNode {

    public static final String EMPTY_INSPECTOR_ICON_BASE = "org/netbeans/modules/plethora/resources/empty-inspector-icon.png";
    
    public EmptyInspectorNode() {
        super(Children.LEAF);
        setName("[Empty]");
        setIconBaseWithExtension(EMPTY_INSPECTOR_ICON_BASE);
    }
    
    public boolean canRename() {
        return false;
    }
    
}