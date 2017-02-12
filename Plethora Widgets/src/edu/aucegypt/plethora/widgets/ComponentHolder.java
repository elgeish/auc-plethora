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
 * ComponentHolder.java
 * Created on December 04, 2006
 */

package edu.aucegypt.plethora.widgets;

import java.util.Enumeration;
import java.util.Vector;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.AcceleratingMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

/**
 * ComponentHolder
 *
 * @author Ahmed Hamza
 * @version 1.00
 */
public class ComponentHolder extends Container3D {
    
    private static final float FORCE_FACTOR = 5;
    private static final int ANIMATION_DURATION = 1000;
    
    private Component3D centralComp;
    private Container3D centralContainer;
    private Vector<Vector3f> originDisplacements;
    private Vector<Vector3f> originPositions;
    private boolean exploded = false;
    
    public ComponentHolder() {
        centralComp = new Component3D();
        Sphere s = new Sphere(0.025f, Sphere.GENERATE_NORMALS, 30, new SimpleAppearance(0.7f, 0.7f, 0.7f, 0.4f));
        originDisplacements = new Vector();
        originPositions = new Vector();
        
        centralComp.addChild(s);
        centralComp.addListener(new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, new ActionNoArg() {
            public void performAction(LgEventSource lgEventSource) {
                ComponentHolder.this.explode();
            }
        }));
        
        super.addChild(centralComp);
    }
    
    public void addChild(Component3D child){
        super.addChild(child);
        
        Vector3f temp = new Vector3f();
        child.getTranslation(temp);
        originPositions.add(temp);
        originDisplacements.add( calculateInitialTranslation(child, ((Sphere)centralComp.getChild(0)).getRadius()) );
        child.changeTranslation(originDisplacements.get(originDisplacements.size()-1));
    }
    
    public void explode() {
        Enumeration explodingChildren = getAllChildren();
        Component3D child = null;
        int index = 0;
        child = (Component3D) explodingChildren.nextElement();
        child.setAnimation(new AcceleratingMotionAnimation(ANIMATION_DURATION));
        
        while(explodingChildren.hasMoreElements()) {
            child= (Component3D)  explodingChildren.nextElement();
            child.setAnimation(new AcceleratingMotionAnimation(ANIMATION_DURATION));
            performAnimation(child, index);
            ++index;
        }
        
        exploded = !exploded;
    }
    
    Vector3f calculateInitialTranslation(Component3D child, float parentRadius) {
        Vector3f temp = new Vector3f();
        Vector3f displacement= getTranslationTo(child, temp);
        
        displacement.set(
                ( ( displacement.x / FORCE_FACTOR ) < parentRadius) ? displacement.x / FORCE_FACTOR : parentRadius,
                ( ( displacement.y / FORCE_FACTOR ) < parentRadius) ? displacement.y / FORCE_FACTOR : parentRadius,
                ( ( displacement.z / FORCE_FACTOR ) < parentRadius) ? displacement.z / FORCE_FACTOR : parentRadius
                );
        
        return displacement;
    }
    
    void performAnimation(Component3D child, int index) {
        if (exploded) {
            child.changeTranslation(originDisplacements.get(index));
        } else {
            child.changeTranslation(originPositions.get(index));
        }        
    }
    
}