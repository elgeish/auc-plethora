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
 * PropertySupportReflection.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import org.openide.nodes.PropertySupport;

/**
 * Property that supports default value
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class PropertySupportReflection extends PropertySupport.Reflection {
    
    private static final Hashtable<Class, Method> PARSERS = new Hashtable<Class, Method>();
    
    static {
        try {
            PARSERS.put(boolean.class, Boolean.class.getMethod("parseBoolean", String.class));
            PARSERS.put(double.class, Double.class.getMethod("parseDouble", String.class));
            PARSERS.put(float.class, Float.class.getMethod("parseFloat", String.class));
            PARSERS.put(short.class, Short.class.getMethod("parseShort", String.class));
            PARSERS.put(int.class, Integer.class.getMethod("parseInt", String.class));
            PARSERS.put(String.class, String.class.getMethod("valueOf", Object.class));
            PARSERS.put(javax.vecmath.Vector3f.class, Vector3fPropertyEditor.class.getMethod("parseVector3f", String.class));
            PARSERS.put(javax.vecmath.Color3f.class, Color3fPropertyEditor.class.getMethod("parseColor3f", String.class));
        } catch(Exception ex) {
        }
    }
    
    private String defaultValue;
    private boolean supportsDefaultValue;
    
    public PropertySupportReflection(Object instance, Class valueType, String getter, String setter, String defaultValue) throws NoSuchMethodException {
        super(instance, valueType, getter, setter);
        this.defaultValue = defaultValue;
        supportsDefaultValue = (defaultValue != null);
    }
    
    public boolean supportsDefaultValue() {
        return supportsDefaultValue;
    }
    
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue(PARSERS.get(getValueType()).invoke(null, defaultValue));
    }
    
}