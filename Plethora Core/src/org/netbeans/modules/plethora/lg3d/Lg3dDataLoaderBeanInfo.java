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
 * Lg3dDataLoaderBeanInfo.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lg3d;

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.SimpleBeanInfo;
import org.netbeans.modules.java.JavaDataLoader;
import org.openide.util.Utilities;

/**
 * LG3D Form data loader bean info
 *
 * @see Lg3dDataLoader
 * @see Lg3dDataNode
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dDataLoaderBeanInfo extends SimpleBeanInfo {
    
    /**
     * Gets BeanInfo for {@link Lg3dDataLoader} class
     * @return the bean info
     * @see Lg3dDataLoader
     * @see java.beans.BeanInfo
     * @version 1.00, Sep 28, 2006
     */
    public BeanInfo[] getAdditionalBeanInfo() {
        try {
            return new BeanInfo[] {Introspector.getBeanInfo(JavaDataLoader.class)};
        } catch (IntrospectionException ex) {
            org.openide.ErrorManager.getDefault().notify(ex);
            return null;
        }
    }
    
    /**
     * Gets icon of the bean for the LG3D file type
     * @param type type of icon (supports BeanInfo.ICON_COLOR_16x16 and BeanInfo.ICON_MONO_16x16)
     * @return the icon image
     * @see java.beans.BeanInfo
     * @version 1.00, Sep 28, 2006
     */
    public Image getIcon(int type) {
        if( type == BeanInfo.ICON_COLOR_16x16 || type == BeanInfo.ICON_MONO_16x16 ) {
            return Utilities.loadImage(Lg3dDataNode.LG3D_ICON_BASE); // NOI18N
        } else {
            return Utilities.loadImage(Lg3dDataNode.LG3D_ICON_32); // NOI18N
        }
    }
    
}