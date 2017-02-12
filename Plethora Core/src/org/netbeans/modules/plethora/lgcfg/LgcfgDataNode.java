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
 * LgcfgDataNode.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lgcfg;

import org.openide.loaders.DataNode;
import org.openide.nodes.Children;

/**
 * Visual representation of the .lgcfg file. It provides the list of actions
 * on that node as well as the icon of the file
 *
 * @see LgcfgDataObject
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class LgcfgDataNode extends DataNode {
    
    public static final String LGCFG_ICON_BASE = "org/netbeans/modules/plethora/resources/lgcfg-icon.png";
    public static final String LGCFG_ICON_32= "org/netbeans/modules/plethora/resources/lgcfg-icon32.png";
    
    public LgcfgDataNode(LgcfgDataObject obj) {
        super(obj, Children.LEAF);
        setIconBaseWithExtension(LGCFG_ICON_BASE);
    }
    
}