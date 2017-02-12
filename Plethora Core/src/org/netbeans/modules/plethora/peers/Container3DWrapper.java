/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Container3DWrapper.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.peers;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDraggedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.event.Component3DManualMoveEvent;
import org.jdesktop.lg3d.wg.event.Component3DManualResizeEvent;
import org.jdesktop.lg3d.wg.event.Component3DParkedEvent;
import org.jdesktop.lg3d.wg.event.Component3DToBackEvent;
import org.jdesktop.lg3d.wg.event.Component3DToFrontEvent;
import org.jdesktop.lg3d.wg.event.Component3DVisualAppearanceEvent;
import org.jdesktop.lg3d.wg.event.KeyEvent3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEnteredEvent3D;
import org.jdesktop.lg3d.wg.event.MouseMotionEvent3D;
import org.jdesktop.lg3d.wg.event.MouseMovedEvent3D;
import org.jdesktop.lg3d.wg.event.MouseWheelEvent3D;
import org.netbeans.modules.plethora.adapters.DepthTranslationAction;
import org.netbeans.modules.plethora.adapters.KeyAction;
import org.netbeans.modules.plethora.adapters.RotationAdapter;
import org.netbeans.modules.plethora.adapters.TranslationAction;
import org.netbeans.modules.plethora.editor.JavaCodeGenerator;
import org.netbeans.modules.plethora.editor.Lg3dEditorSupport;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;
import org.openide.nodes.Node;

/**
 * Wrapper for a Container3D.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see ShapePeer
 * @see Lg3dDataDocument
 *
 * @author Moataz Nour and Mohamed El-Geish
 * @version 1.00
 */
public class Container3DWrapper extends Container3D implements ShapeWrapper {
    
    private static final String PROP_KEY_EVENT_SOURCE = "KeyEventSource";
    private static final String PROP_MOUSE_EVENT_ENABLED = "MouseEventEnabled";
    private static final String PROP_MOUSE_EVENT_PROPAGATABLE = "MouseEventPropagatable";
    private static final String PROP_PICKABLE = "Pickable";
    private static final String PROP_PREFERRED_SIZE = "PreferredSize";
    private static final String PROP_ROTATION_ANGLE = "RotationAngle";
    private static final String PROP_ROTATION_AXIS = "RotationAxis";
    private static final String PROP_SCALE = "Scale";
    private static final String PROP_TRANSLATION = "Translation";
    private static final String PROP_TRANSPARENCY = "Transparency";
    private static final String PROP_VISIBLE = "Visible";
    
    private static final String EVENT_COMP_MANUAL_MOVE = "Component3DManualMove";
    private static final String EVENT_COMP_MANUAL_RESIZE = "Component3DManualResize";
    private static final String EVENT_COMP_PARKED = "Component3DParked";
    private static final String EVENT_COMP_TO_BACK = "Component3DToBack";
    private static final String EVENT_COMP_TO_FRONT = "Component3DToFront";
    private static final String EVENT_COMP_VISUAL_APPEARANCE = "Component3DVisualAppearance";
    private static final String EVENT_KEY_PRESSED = "KeyPressed";
    private static final String EVENT_MOUSE_DRAGGED = "MouseDragged";
    private static final String EVENT_MOUSE_ENTERED = "MouseEntered";
    private static final String EVENT_MOUSE_MOTION = "MouseMotion";
    private static final String EVENT_MOUSE_MOVED = "MouseMoved";
    private static final String EVENT_MOUSE_PRESSED = "MousePressed";
    private static final String EVENT_MOUSE_WHEEL = "MouseWheel";
    
    private String component3DManualMove = "";
    private String component3DManualResize = "";
    private String component3DParked = "";
    private String component3DToBack = "";
    private String component3DToFront = "";
    private String component3DVisualAppearance = "";
    private String keyPressed = "";
    private String mouseDragged = "";
    private String mouseEntered = "";
    private String mouseMotion = "";
    private String mouseMoved = "";
    private String mousePressed = "";
    private String mouseWheel = "";
    
    private Lg3dDataDocument lg3dDataDocument;
    private ShapePeer shapePeer;
    
    
    public Container3DWrapper() {
        super();
        
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
        
        addListener(new MousePressedEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource lgEventSource, boolean b) {
                Lg3dComponentInspector.getInstance().selectNode(shapePeer);
            }
        }));
        
        addListener(new RotationAdapter());
        addListener(new MouseDraggedEventAdapter(new TranslationAction()));
        addListener(new MouseWheelEventAdapter(new DepthTranslationAction()));
        addListener(new KeyPressedEventAdapter(new KeyAction()));
    }
    
    public ShapePeer getShapePeer() {
        return shapePeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        this.shapePeer = shapePeer;
    }
    
    public void setProperty(String property, Class type, Object value) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.setProperty(getName(), property, type.getName(), String.valueOf(value));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setProperty(String property, Class type, Parameter... parameters) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.setProperty(getName(), property, type.getName(), parameters);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void resetProperty(String property) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.removeProperty(getName(), property);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Lg3dDataDocument getLg3dDataDocument() {
        return lg3dDataDocument;
    }
        
    public void setKeyEventSource(boolean keyEventSource) {
        if (keyEventSource) {
            resetProperty(PROP_KEY_EVENT_SOURCE);
        } else {
            setProperty(PROP_KEY_EVENT_SOURCE, boolean.class, keyEventSource);
        }
        
        super.setKeyEventSource(keyEventSource);
    }
    
    public void setMouseEventEnabled(boolean enabled) {
        if (enabled) {
            resetProperty(PROP_MOUSE_EVENT_ENABLED);
        } else {
            setProperty(PROP_MOUSE_EVENT_ENABLED, boolean.class, enabled);
        }
        
        super.setMouseEventEnabled(enabled);
    }
    
    public void setMouseEventPropagatable(boolean enabled) {
        if (!enabled) {
            resetProperty(PROP_MOUSE_EVENT_PROPAGATABLE);
        } else {
            setProperty(PROP_MOUSE_EVENT_PROPAGATABLE, boolean.class, enabled);
        }
        
        super.setMouseEventPropagatable(enabled);
    }
    
    public Vector3f getPreferredSize() {
        Vector3f vector = new Vector3f();
        
        getPreferredSize(vector);
        
        return vector;
    }
    
    public void setPreferredSize(Vector3f vector) {
        if (vector.x == 0 && vector.y == 0 && vector.z == 0) {
            resetProperty(PROP_PREFERRED_SIZE);
        } else {
            setProperty(PROP_PREFERRED_SIZE, Vector3f.class,
                    new SimpleParameter(float.class.getName(), String.valueOf(vector.x)),
                    new SimpleParameter(float.class.getName(), String.valueOf(vector.y)),
                    new SimpleParameter(float.class.getName(), String.valueOf(vector.z)));
        }
        
        super.setPreferredSize(vector);
    }
    
    public void setRotationAngle(float angle) {
        if (angle == 0) {
            resetProperty(PROP_ROTATION_ANGLE);
        } else {
            setProperty(PROP_ROTATION_ANGLE, float.class, angle + "f");
        }
        
        super.setRotationAngle(angle);
    }
    
    public Vector3f getRotationAxis() {
        Vector3f vector = new Vector3f();
        
        getRotationAxis(vector);
        
        return vector;
    }
    
    public void setRotationAxis(Vector3f vector) {
        if (vector.x == 1 && vector.y == 0 && vector.z == 0) {
            resetProperty(PROP_ROTATION_AXIS);
        } else {
            setProperty(PROP_ROTATION_AXIS, Vector3f.class,
                    vector.x + "f, " + vector.y + "f, " + vector.z + "f");
        }
        
        super.setRotationAxis(vector.x, vector.y, vector.z);
    }
    
    public void setScale(float scale) {
        if (scale == 1) {
            resetProperty(PROP_SCALE);
        } else {
            setProperty(PROP_SCALE, float.class, scale + "f");
        }
        
        super.setScale(scale);
    }
    
    public Vector3f getTranslation() {
        Vector3f vector = new Vector3f();
        
        getTranslation(vector);
        
        return vector;
    }
    
    public void setTranslation(Vector3f vector) {
        if (vector.x == 0 && vector.y == 0 && vector.z == 0) {
            resetProperty(PROP_TRANSLATION);
        } else {
            setProperty(PROP_TRANSLATION, Vector3f.class,
                    vector.x + "f, " + vector.y + "f, " + vector.z + "f");
        }
        
        super.setTranslation(vector.x, vector.y, vector.z);
    }
    
    public void setTransparency(float transparency) {
        if (transparency == 0) {
            resetProperty(PROP_TRANSPARENCY);
        } else {
            setProperty(PROP_TRANSPARENCY, float.class, transparency + "f");
        }
        
        super.setTransparency(transparency);
    }
    
    public void setVisible(boolean visible) {
        if (visible) {
            resetProperty(PROP_VISIBLE);
        } else {
            setProperty(PROP_VISIBLE, boolean.class, visible);
        }
        
        super.setVisible(visible);
    }
    
    /* Event Handlers */
    
    private void setEventHandler(String name, Class type, String oldHandler, String newHandler) {
        if (newHandler.equals(oldHandler)) {
            return;
        }
        
        try {
            /* XXX: Hack to preserve user code */
            if (! newHandler.equals("")) {
                Lg3dEditorSupport lg3dEditorSupport = lg3dDataDocument.getLg3dDataObject().getLg3dEditorSupport();
                Lg3dEditorSupport.InteriorSection section = lg3dEditorSupport.findInteriorSection(JavaCodeGenerator.EVENT_SECTION_PREFIX + oldHandler);
                
                if (section != null) {
                    section.setName(JavaCodeGenerator.EVENT_SECTION_PREFIX + newHandler);
                    section.setHeader(section.getHeader().replaceFirst("private void " + oldHandler, "private void " + newHandler));
                }
            }
            /* XXX: End of hack */
            
            lg3dDataDocument.setEventHandler(getName(), name, type.getName(), newHandler);
            
            /* Set the new value as initial edit value */
            if (getShapePeer().getEventsSet() != null) {
                getShapePeer().getEventsSet().get(name).setValue("initialEditValue", newHandler);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String getComponent3DManualMove() {
        return component3DManualMove;
    }
    
    public void setComponent3DManualMove(String component3DManualMove) {
        setEventHandler(EVENT_COMP_MANUAL_MOVE, Component3DManualMoveEvent.class,
                this.component3DManualMove, component3DManualMove);
        this.component3DManualMove = component3DManualMove;
    }
    
    public String getComponent3DManualResize() {
        return component3DManualResize;
    }
    
    public void setComponent3DManualResize(String component3DManualResize) {
        setEventHandler(EVENT_COMP_MANUAL_RESIZE, Component3DManualResizeEvent.class,
                this.component3DManualResize, component3DManualResize);
        this.component3DManualResize = component3DManualResize;
    }
    
    public String getComponent3DParked() {
        return component3DParked;
    }
    
    public void setComponent3DParked(String component3DParked) {
        setEventHandler(EVENT_COMP_PARKED, Component3DParkedEvent.class,
                this.component3DParked, component3DParked);
        this.component3DParked = component3DParked;
    }
    
    public String getComponent3DToBack() {
        return component3DToBack;
    }
    
    public void setComponent3DToBack(String component3DToBack) {
        setEventHandler(EVENT_COMP_TO_BACK, Component3DToBackEvent.class,
                this.component3DToBack, component3DToBack);
        this.component3DToBack = component3DToBack;
    }
    
    public String getComponent3DToFront() {
        return component3DToFront;
    }
    
    public void setComponent3DToFront(String component3DToFront) {
        setEventHandler(EVENT_COMP_TO_FRONT, Component3DToFrontEvent.class,
                this.component3DToFront, component3DToFront);
        this.component3DToFront = component3DToFront;
    }
    
    public String getComponent3DVisualAppearance() {
        return component3DVisualAppearance;
    }
    
    public void setComponent3DVisualAppearance(String component3DVisualAppearance) {
        setEventHandler(EVENT_COMP_VISUAL_APPEARANCE, Component3DVisualAppearanceEvent.class,
                this.component3DVisualAppearance, component3DVisualAppearance);
        this.component3DVisualAppearance = component3DVisualAppearance;
    }
    
    public String getKeyPressed() {
        return keyPressed;
    }
    
    public void setKeyPressed(String keyPressed) {
        setEventHandler(EVENT_KEY_PRESSED, KeyEvent3D.class, this.keyPressed, keyPressed);
        this.keyPressed = keyPressed;
    }
    
    public String getMouseDragged() {
        return mouseDragged;
    }
    
    public void setMouseDragged(String mouseDragged) {
        setEventHandler(EVENT_MOUSE_DRAGGED, MouseDraggedEvent3D.class, this.mouseDragged, mouseDragged);
        this.mouseDragged = mouseDragged;
    }
    
    public String getMouseEntered() {
        return mouseEntered;
    }
    
    public void setMouseEntered(String mouseEntered) {
        setEventHandler(EVENT_MOUSE_ENTERED, MouseEnteredEvent3D.class, this.mouseEntered, mouseEntered);
        this.mouseEntered = mouseEntered;
    }
    
    public String getMouseMotion() {
        return mouseMotion;
    }
    
    public void setMouseMotion(String mouseMotion) {
        setEventHandler(EVENT_MOUSE_MOTION, MouseMotionEvent3D.class, this.mouseMotion, mouseMotion);
        this.mouseMotion = mouseMotion;
    }
    
    public String getMouseMoved() {
        return mouseMoved;
    }
    
    public void setMouseMoved(String mouseMoved) {
        setEventHandler(EVENT_MOUSE_MOVED, MouseMovedEvent3D.class, this.mouseMoved, mouseMoved);
        this.mouseMoved = mouseMoved;
    }
    
    public String getMousePressed() {
        return mousePressed;
    }
    
    public void setMousePressed(String mousePressed) {
        setEventHandler(EVENT_MOUSE_PRESSED, MouseButtonEvent3D.class, this.mousePressed, mousePressed);
        this.mousePressed = mousePressed;
    }
    
    public String getMouseWheel() {
        return mouseWheel;
    }
    
    public void setMouseWheel(String mouseWheel) {
        setEventHandler(EVENT_MOUSE_WHEEL, MouseWheelEvent3D.class, this.mouseWheel, mouseWheel);
        this.mouseWheel = mouseWheel;
    }
    
}