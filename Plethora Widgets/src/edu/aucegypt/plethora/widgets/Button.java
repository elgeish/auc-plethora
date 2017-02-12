/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Button.java
 * Created on December 04, 2006
 */

package edu.aucegypt.plethora.widgets;

import java.awt.Color;
import java.awt.Font;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * Button
 * 
 * @see WidgetItem
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
public class Button extends WidgetItem implements LgEventSource {
    
    private GlassyPanel panel;
    private GlassyPanel marker;
    private Component3D markerComp;
    private Component3D textComponent3D;
    private TransformGroup textTransformGroup;
    private Text2D text2D;
    private boolean isSelected = false;
    
    public Button() {
        this("");
    }
    
    public Button(String s) {
        textComponent3D = new Component3D();
        textTransformGroup = new TransformGroup();
        panel = new GlassyPanel(0.03f, 0.015f, 0.001f, new SimpleAppearance(0.01f,0.01f,0.01f));
        text2D = new Text2D(s, new Color3f(Color.WHITE), Font.DIALOG, 30, Font.PLAIN);
        
        Transform3D t3d = new Transform3D();
        t3d.set(new Vector3f(-0.18f, -0.07f, 0f));
        textTransformGroup.setTransform(t3d);
        
        textTransformGroup.addChild(text2D);
        textComponent3D.addChild(textTransformGroup);
        textComponent3D.setScale(0.05f);
        
        setMouseEventPropagatable(true);
        
        addChild(textComponent3D);
        addChild(panel);
        setRotationAxis(-0.99f, 0.3f, 0.6f);
        setRotationAngle((float)Math.toRadians(60));
        this.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource lgEventSource) {
                changeScale(1f);
            }
        }));
        
        this.addListener(new MousePressedEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource lgEventSource, boolean b) {
                changeScale(0.7f); 
            }
        }));
        
        this.setAnimation(new NaturalMotionAnimation(100));
        addListener(new MouseEnteredEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource lgEventSource, boolean b) {
                if (b) {
                    changeScale(1.1f);
                } else {
                    changeScale(1f);
                }
            }
        }));
    }
    
    public void setMarker(boolean b) {
        if (b) {
            marker = new GlassyPanel(0.005f, 0.015f, 0.001f, new SimpleAppearance(0.5f, 0, 0));
            markerComp = new Component3D();
            markerComp.addChild(marker);
            markerComp.changeTranslation(0.019f, 0, 0);
            super.addChild(markerComp);
        } else {
            if (markerComp != null) {
                removeChild(markerComp);
                markerComp.removeAllChildren();
                markerComp = null;
                marker = null;
            }
        }
    }
    
    public void setText(String text) {
        text2D.setString(text);
    }
    
    public String getText() {
        return text2D.getString();
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    
}