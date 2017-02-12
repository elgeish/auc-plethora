/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Bundle.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.resources;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import org.openide.util.NbBundle;

/**
 * I18N support
 *
 * @see org.openide.util.NbBundle
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Bundle {
    
    public static ResourceBundle getBundle() {
        return NbBundle.getBundle(Bundle.class);
    }

    public static String getBundleString(String key) {
        return NbBundle.getBundle(Bundle.class).getString(key);
    }
    
    public static String getBundleString(String key, Object... args) {
        return NbBundle.getMessage(Bundle.class, key, args);
    }

    public static String getFormattedBundleString(String key, Object... args) {
        return MessageFormat.format(getBundleString(key), args);
    }    
}