/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ShapeLoader.java
 *
 */

package org.netbeans.modules.plethora.model;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import org.jdesktop.lg3d.wg.Component3D;
import org.netbeans.modules.plethora.peers.Component3DWrapper;
import org.netbeans.modules.plethora.peers.Container3DPeer;
import org.netbeans.modules.plethora.peers.Container3DWrapper;
import org.netbeans.modules.plethora.peers.ShapePeer;
import org.netbeans.modules.plethora.peers.ShapeWrapper;

/**
 * Shape loader using reflection from its description
 *
 * @see Lg3dDataObject
 *
 * @author Ahmed Hamza and Mohamed El-Geish
 * @version 1.00
 */
class ShapeLoader {
    
    private static final String PEERS_PKG = "org.netbeans.modules.plethora.peers";
    private static final String WRAPPER_SUFFIX = "Wrapper";
    private static final String PEER_SUFFIX = "Peer";
    private static final String SET_PREFIX = "set";
    
    private static final Hashtable<String, Class> TYPES = new Hashtable<String, Class>();
    
    private static final Hashtable<String, Method> PARSERS = new Hashtable<String, Method>();
    
    static {
        TYPES.put("boolean", boolean.class);
        TYPES.put("double", double.class);
        TYPES.put("float", float.class);
        TYPES.put("short", short.class);
        TYPES.put("int", int.class);
        
        try {
            PARSERS.put("boolean", Boolean.class.getMethod("parseBoolean", String.class));
            PARSERS.put("double", Double.class.getMethod("parseDouble", String.class));
            PARSERS.put("float", Float.class.getMethod("parseFloat", String.class));
            PARSERS.put("short", Short.class.getMethod("parseShort", String.class));
            PARSERS.put("int", Integer.class.getMethod("parseInt", String.class));
            PARSERS.put("java.lang.String", String.class.getMethod("valueOf", Object.class));
            PARSERS.put("java.lang.Class", Class.class.getMethod("forName", String.class));
        } catch(Exception ex) {
        }
    }
    
    static ShapePeer createShapePeer(ComponentDescription componentDescription) {
        ShapePeer shapePeer = null;
        
        try {
            ShapeDescription shapeDescription = componentDescription.getShapeDescription();
            ShapeWrapper shapeWrapper = createShapeWrapper(shapeDescription);
            Component3DWrapper component3DWrapper = new Component3DWrapper();
            Class peerClass = Class.forName(PEERS_PKG +
                    shapeDescription.getType().substring(shapeDescription.getType().lastIndexOf('.')) +
                    PEER_SUFFIX);
            
            Class shapePeerClasses[] = new Class[] {
                IdPair.class,
                shapeWrapper.getClass(),
                Component3DWrapper.class
            };
            
            Object shapePeerValues[] = new Object[] {
                new IdPair(componentDescription.getId(), shapeDescription.getId()),
                shapeWrapper,
                component3DWrapper
            };
            
            shapePeer = (ShapePeer) peerClass.getConstructor(shapePeerClasses).newInstance(shapePeerValues);
            
            setObjectProperties(component3DWrapper, componentDescription.getProperties());
            setEventHandlers(component3DWrapper, componentDescription.getUserEvents());
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return shapePeer;
    }
    
    static ShapePeer createContainer3DPeer(ContainerDescription containerDescription) {
        ShapePeer container3DPeer = null;
        
        try {
            ShapeDescription shapeDescription = new ShapeDescription(containerDescription.getId(),
                    containerDescription.getType());
            
            shapeDescription.setProperties(containerDescription.getProperties());
            
            ShapeWrapper container3DWrapper = createShapeWrapper(shapeDescription);
            
            Class peerClass = Class.forName(PEERS_PKG +
                    containerDescription.getType().substring(containerDescription.getType().lastIndexOf('.')) +
                    PEER_SUFFIX);
            
            Class types[] = new Class[] {
                IdPair.class,
                container3DWrapper.getClass(),
                Component3DWrapper.class
            };
            
            Object values[] = new Object[] {
                new IdPair("$" + containerDescription.getId(), containerDescription.getId()),
                container3DWrapper,
                new Component3DWrapper()
            };
            
            container3DPeer = (ShapePeer) peerClass.getConstructor(types).newInstance(values);
            
            setObjectProperties(container3DWrapper, containerDescription.getProperties());
            setEventHandlers((Component3D) container3DWrapper, containerDescription.getUserEvents());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return container3DPeer;
    }
    
    private static ShapeWrapper createShapeWrapper(ShapeDescription shapeDescription) {
        ShapeWrapper shapeWrapper = null;
        
        try {
            Class wrapperClass = Class.forName(PEERS_PKG +
                    shapeDescription.getType().substring(shapeDescription.getType().lastIndexOf('.')) +
                    WRAPPER_SUFFIX);
            
            shapeWrapper = (ShapeWrapper) createObject(wrapperClass, shapeDescription.getParameters());
            setObjectProperties(shapeWrapper, shapeDescription.getProperties());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return shapeWrapper;
    }
    
    private static Object createObject(Class type, Vector<Parameter> params) throws Exception {
        Class paramClasses[] = new Class[params.size()];
        Object paramValues[] = new Object[params.size()];
        Method parserMethod;
        
        for (int i = 0; i < params.size(); ++i) {
            paramClasses[i] = getClassForName(params.get(i).getType());
            parserMethod = PARSERS.get(params.get(i).getType());
            
            if (params.get(i) instanceof SimpleParameter) {
                paramValues[i] = (parserMethod == null) ? null
                        : parserMethod.invoke(null, ((SimpleParameter)params.get(i)).getValue());
            } else if (params.get(i) instanceof ComplexParameter) {
                paramValues[i] = createObject(paramClasses[i], ((ComplexParameter) params.get(i)).getParameters());
            }
        }
        
        return type.getConstructor(paramClasses).newInstance(paramValues);
    }
    
    private static void setEventHandlers(Component3D obj, Vector<UserEvent> events) throws Exception {
        for(UserEvent event : events) {
            obj.getClass().getMethod(SET_PREFIX + event.getName(), String.class).invoke(obj, event.getHandler());
        }
    }
    
    private static void setObjectProperties(Object obj, Vector<Property> properties) throws Exception {
        Method setter;
        
        for(Property property : properties) {
            /* XXX: Very bad hack for "Translation" & "RotationAxis" */
            if (property.getName().equals("Translation")
            || property.getName().equals("RotationAxis")) {
                
                String[] params = ((SimpleProperty) property).getValue().split(",");
                float [] floats = new float[3];
                
                setter = obj.getClass().getMethod(SET_PREFIX + property.getName(), javax.vecmath.Vector3f.class);
                
                for (int i = 0 ; i < floats.length ; ++i) {
                    floats[i] = Float.parseFloat(params[i]);
                }
                
                setter.invoke(obj, new javax.vecmath.Vector3f(floats));
                /* XXX: End of hack */
            } else {
                setter = obj.getClass().getMethod(SET_PREFIX + property.getName(), getClassForName(property.getType()));
                
                if (property instanceof SimpleProperty) {
                    setter.invoke(obj, PARSERS.get(property.getType()).invoke(null, ((SimpleProperty) property).getValue()));
                } else { // property instanceof ComplexProperty
                    setter.invoke(obj, createObject(getClassForName(property.getType()), ((ComplexProperty) property).getParameters()));
                }
            }
        }
    }
    
    private static Class getClassForName(String name) throws ClassNotFoundException {
        Class type = TYPES.get(name);
        
        return type != null ? type : Class.forName(name);
    }
    
}