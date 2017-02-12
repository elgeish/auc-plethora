/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * RotationAdapter.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.adapters;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionFloat2;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseDraggedEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

/**
 * RotationAdapter
 * 
 * @see Component3DWarapper
 *
 * @author Moataz Nour
 * @version 1.00
 */
public class RotationAdapter extends MouseDraggedEventAdapter {
    
    private Point3f previousPoint = null;
    private int updateChangesCount = 0;
    private Vector3f currentRotationAxis = null;
    
    public RotationAdapter() {
        super(MouseEvent3D.ButtonId.BUTTON3, new ActionFloat2() {
            public void performAction(LgEventSource lgEventSource, float f, float f0) {
            }
        });
    }
    
    public void processEvent(LgEvent event) {
        MouseDraggedEvent3D mme3d = (MouseDraggedEvent3D) event;
        Component3D sourceComp= (Component3D) mme3d.getSource();
        Point3f currentPoint = new Point3f();
        Vector3f currentTranslation = new Vector3f();
        
        sourceComp.getTranslation(currentTranslation);
        mme3d.getDragPoint(currentPoint);
        
        if (! mme3d.getButton().equals(MouseEvent3D.ButtonId.BUTTON3)) {
            return;
        }
        
        if (previousPoint == null) {
            previousPoint = currentPoint;
        } else {
            float currentRotationAngle = (float) sourceComp.getRotationAngle();
            
            Vector3f displacementVector = getDisplacementVector(currentPoint);
            
            ++updateChangesCount;
            
            displacementVector.z = sourceComp.getTranslation(new Vector3f()).z;
            
            if (currentRotationAxis == null) {
                currentRotationAxis = getAxisVector(displacementVector);
            }
            
            sourceComp.setRotationAxis(currentRotationAxis.x,
                    currentRotationAxis.y, currentRotationAxis.z);
            
            sourceComp.setRotationAngle((float) currentRotationAngle +
                    getRotationAngle(currentPoint, previousPoint));
            
            if (updateChangesCount == 15) {
                previousPoint = currentPoint;
                currentRotationAxis = getAxisVector(displacementVector);
                updateChangesCount = 0;
            }
        }
    }
    
    protected Vector3f getDisplacementVector(Point3f currentPoint) {
        return new Vector3f(
                currentPoint.x - previousPoint.x,
                currentPoint.y - previousPoint.y,
                currentPoint.z - previousPoint.z);
    }
    
    protected Vector3f getAxisVector(Vector3f displacementVector) {
        double axisX = -displacementVector.y / (Math.pow(displacementVector.x, 2.0) + Math.pow(displacementVector.y, 2.0));
        double axisY=  displacementVector.x /  (Math.pow(displacementVector.x, 2.0) + Math.pow(displacementVector.y, 2.0));
        
        Vector3f axisVector = new Vector3f((float) axisX, (float) axisY, (float) displacementVector.z);
        
        return axisVector;
    }
    
    protected float getRotationAngle(Point3f currentPoint, Point3f previousPoint) {
        return new Vector3f(previousPoint).angle(new Vector3f(currentPoint));
    }
    
}