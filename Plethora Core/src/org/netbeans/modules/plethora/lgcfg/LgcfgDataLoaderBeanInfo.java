/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * LgcfgDataLoaderBeanInfo.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lgcfg;

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.SimpleBeanInfo;
import org.openide.loaders.UniFileLoader;
import org.openide.util.Utilities;

/**
 * LGCGF data loader bean info
 *
 * @see LgcfgDataLoader
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class LgcfgDataLoaderBeanInfo extends SimpleBeanInfo {
    
    public BeanInfo[] getAdditionalBeanInfo() {
        try {
            return new BeanInfo[] {Introspector.getBeanInfo(UniFileLoader.class)};
        } catch (IntrospectionException ex) {
            org.openide.ErrorManager.getDefault().notify(ex);
            return null;
        }
    }
    
    public Image getIcon(int type) {
        if (type == BeanInfo.ICON_COLOR_16x16 || type == BeanInfo.ICON_MONO_16x16) {
            return Utilities.loadImage(LgcfgDataNode.LGCFG_ICON_BASE);
        } else {
            return Utilities.loadImage(LgcfgDataNode.LGCFG_ICON_32);
        }
        
    }
    
}
