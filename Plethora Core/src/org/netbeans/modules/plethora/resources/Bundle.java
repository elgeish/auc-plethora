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