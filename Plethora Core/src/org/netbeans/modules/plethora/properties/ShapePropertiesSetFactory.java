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
 * ShapePropertiesSetFactory.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.util.ArrayList;
import java.util.Hashtable;
import org.jdesktop.lg3d.wg.Component3D;
import org.netbeans.modules.plethora.peers.ShapeWrapper;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Utility class that creates a Sheet.Set for the given Object to work on
 *
 * @see #createSheetSet
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
@SuppressWarnings("unchecked")
public class ShapePropertiesSetFactory {
    
    private static final String PATH_BASE = "/org/netbeans/modules/plethora/resources/wrappers/";
    private static final String EXT = ".xml";
    private static final String EVENTS_FILE_NAME = "Events";
    
    private static final String ATTR_NAME = "Name";
    private static final String ATTR_TYPE = "Type";
    private static final String ATTR_GETTER = "Getter";
    private static final String ATTR_SETTER = "Setter";
    private static final String ATTR_DEFAULT_VALUE = "DefaultValue";
    private static final String ATTR_DISPLAY_NAME = "DisplayName";
    private static final String ATTR_EXPERT = "Expert";
    private static final String ATTR_PREFERRED = "Preferred";
    private static final String ATTR_SHORT_DISCRIPTION = "ShortDescription";
    private static final String ATTR_PROPERTY_EDITOR_CLASS = "PropertyEditorClass";
    
    private static final Hashtable<String, Class> TYPES = new Hashtable<String, Class>();
    
    static {
        TYPES.put("boolean", boolean.class);
        TYPES.put("double", double.class);
        TYPES.put("float", float.class);
        TYPES.put("short", short.class);
        TYPES.put("int", int.class);
    }
    
    public static Sheet.Set createSheetSet(ShapeWrapper shape) {
        return createSheetSet(shape, null);
    }
    
    public static Sheet.Set createSheetSet(ShapeWrapper shape, String fileName) {
        Sheet.Set set = new Sheet.Set();
        String className = shape.getClass().getName().substring(shape.getClass().getName().lastIndexOf('.') + 1);
        
        if (fileName == null) {
            fileName = PATH_BASE + className + EXT;
            set.setName(className.replaceAll("Wrapper", ""));
            set.setShortDescription("(" + shape.getClass().getName().replaceAll("Wrapper", "") + ") Properties.");
        } else {
            set.setName(fileName);
            fileName = PATH_BASE + fileName + EXT;
        }
        
        try {
            Document document = XMLUtil.parse(new InputSource(
                    ShapePropertiesSetFactory.class.getResourceAsStream(fileName)),
                    false, false, null, null);
            
            NodeList nodes = document.getDocumentElement().getChildNodes();
            ArrayList<PropertySupport.Reflection> properties = new ArrayList<PropertySupport.Reflection>();
            
            PropertySupport.Reflection property;
            Element element;
            Class typeClass;
            String type;
            
            if (nodes != null) {
                for (int i = 0 ; i < nodes.getLength() ; ++i) {
                    if (nodes.item(i).getNodeType() == Document.ELEMENT_NODE) {
                        element = (Element) nodes.item(i);
                        
                        type = element.getAttribute(ATTR_TYPE);
                        typeClass = TYPES.get(type);
                        
                        if (typeClass == null) {
                            typeClass = Class.forName(type);
                        }
                        
                        property = new PropertySupportReflection(shape, typeClass,
                                element.getAttribute(ATTR_GETTER),
                                element.getAttribute(ATTR_SETTER).equals("null") ?
                                    null : element.getAttribute(ATTR_SETTER),
                                element.getAttribute(ATTR_DEFAULT_VALUE).equals("null") ?
                                    null : element.getAttribute(ATTR_DEFAULT_VALUE));
                        
                        property.setName(element.getAttribute(ATTR_NAME));
                        property.setDisplayName(element.getAttribute(ATTR_DISPLAY_NAME));
                        property.setExpert(Boolean.parseBoolean(element.getAttribute(ATTR_EXPERT)));
                        property.setPreferred(Boolean.parseBoolean(element.getAttribute(ATTR_PREFERRED)));
                        property.setShortDescription("(" + type + ") " +
                                element.getAttribute(ATTR_SHORT_DISCRIPTION));
                        
                        if (!element.getAttribute(ATTR_PROPERTY_EDITOR_CLASS).equals("")) {
                            property.setPropertyEditorClass(Class.forName(element.getAttribute(ATTR_PROPERTY_EDITOR_CLASS)));
                        }
                        
                        properties.add(property);
                    }
                }
                set.put(properties.toArray(new PropertySupport.Reflection[] {}));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return set;
    }
    
    public static Sheet.Set createEventsSheetSet(Component3D component) {
        Sheet.Set set = createSheetSet((ShapeWrapper) component, EVENTS_FILE_NAME);
        
        try {
            for(final org.openide.nodes.Node.Property property : set.getProperties()) {
                
                if (property.getValue() == null || property.getValue().equals("")) {
                    property.setValue("");
                    property.setValue("initialEditValue", component.getName() + property.getName());
                } else {
                    property.setValue("initialEditValue", property.getValue());
                }
                
                property.setValue("postSetAction", new EventPropertyEditor.PostSetAction(property));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return set;
    }
    
}