/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * KeyAction.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.adapters;

import java.awt.event.KeyEvent;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.netbeans.modules.plethora.peers.Component3DWrapper;
import org.openide.awt.StatusDisplayer;

/**
 * KeyAction
 * 
 * @see Component3DWarapper
 *
 * @author Moataz Nour
 * @version 1.00
 */
public class KeyAction implements ActionBooleanInt {
    
    private final float KEY_TRANSLATION_OFFSET = 0.001f;
    private final float KEY_SCALE_FACTOR = 2;
    private final float KEY_DIFFERENCE_ANGLE = (float) Math.toRadians(5);
    
    public void performAction(LgEventSource lgEventSource, boolean pressed, int i) {
        
        if (!pressed) {
            return;
        }
        
        Component3DWrapper sourceComponent = (Component3DWrapper) lgEventSource;
        Vector3f currentTranslation = sourceComponent.getTranslation();
        float currentRotationAngle= sourceComponent.getRotationAngle();
        
        switch(i) {
            
            /* The cursors are used to change the object's translation in the 2D plane */
            case KeyEvent.VK_LEFT:
                sourceComponent.setTranslation(currentTranslation.x - KEY_TRANSLATION_OFFSET,
                        currentTranslation.y, currentTranslation.z);
                
                StatusDisplayer.getDefault().setStatusText("[" +
                        (currentTranslation.x - KEY_TRANSLATION_OFFSET) + ", " +
                        currentTranslation.y + ", " +
                        currentTranslation.z + "]");
                break;
                
            case KeyEvent.VK_UP:
                sourceComponent.setTranslation(currentTranslation.x,
                        currentTranslation.y + KEY_TRANSLATION_OFFSET, currentTranslation.z);
                
                StatusDisplayer.getDefault().setStatusText("[" +
                        currentTranslation.x + ", " +
                        (currentTranslation.y + KEY_TRANSLATION_OFFSET) + ", " +
                        currentTranslation.z + "]");
                break;
                
            case KeyEvent.VK_RIGHT:
                sourceComponent.setTranslation(currentTranslation.x+ KEY_TRANSLATION_OFFSET,
                        currentTranslation.y, currentTranslation.z);
                
                StatusDisplayer.getDefault().setStatusText("[" +
                        (currentTranslation.x+ KEY_TRANSLATION_OFFSET) + ", " +
                        currentTranslation.y + ", " +
                        currentTranslation.z + "]");
                break;
                
            case KeyEvent.VK_DOWN:
                sourceComponent.setTranslation(currentTranslation.x,
                        currentTranslation.y - KEY_TRANSLATION_OFFSET, currentTranslation.z);
                
                StatusDisplayer.getDefault().setStatusText("[" +
                        currentTranslation.x + ", " +
                        (currentTranslation.y - KEY_TRANSLATION_OFFSET) + ", " +
                        currentTranslation.z + "]");
                break;
                
            /*
             * Num keys are used for rotating the component about the vertical,
             * horizontal and 45 degree-axes
             */
            case KeyEvent.VK_NUMPAD8:
                sourceComponent.setRotationAxis(1, 0, currentTranslation.z);
                sourceComponent.setRotationAngle(currentRotationAngle - KEY_DIFFERENCE_ANGLE);
                break;
                
            case KeyEvent.VK_NUMPAD2:
                sourceComponent.setRotationAxis(1, 0, currentTranslation.z);
                sourceComponent.setRotationAngle(currentRotationAngle + KEY_DIFFERENCE_ANGLE);
                break;
                
            case KeyEvent.VK_NUMPAD4:
                sourceComponent.setRotationAxis(0, 1, currentTranslation.z);
                sourceComponent.setRotationAngle(currentRotationAngle - KEY_DIFFERENCE_ANGLE);
                break;
                
            case KeyEvent.VK_NUMPAD6:
                sourceComponent.setRotationAxis(0, 1, currentTranslation.z);
                sourceComponent.setRotationAngle(currentRotationAngle + KEY_DIFFERENCE_ANGLE);
                break;
                
            case KeyEvent.VK_NUMPAD7:
                sourceComponent.setRotationAxis(1, 1, currentTranslation.z);
                sourceComponent.setRotationAngle(currentRotationAngle - KEY_DIFFERENCE_ANGLE);
                break;
                
            case KeyEvent.VK_NUMPAD3:
                sourceComponent.setRotationAxis(1, 1, currentTranslation.z);
                sourceComponent.setRotationAngle(currentRotationAngle + KEY_DIFFERENCE_ANGLE);
                break;
                
            case KeyEvent.VK_NUMPAD1:
                sourceComponent.setRotationAxis(-1, 1, currentTranslation.z);
                sourceComponent.setRotationAngle(currentRotationAngle - KEY_DIFFERENCE_ANGLE);
                break;
                
            case KeyEvent.VK_NUMPAD9:
                sourceComponent.setRotationAxis(-1, 1, currentTranslation.z);
                sourceComponent.setRotationAngle(currentRotationAngle + KEY_DIFFERENCE_ANGLE);
                break;
                
            case KeyEvent.VK_NUMPAD5:
                sourceComponent.setRotationAxis(1, 0, 0);
                sourceComponent.setRotationAngle(0);
                break;
                
            /* The * and / keys in the numpad change the scale of the component */
            case KeyEvent.VK_MULTIPLY:
                sourceComponent.setScale(sourceComponent.getScale() * KEY_SCALE_FACTOR);
                break;
                
            case KeyEvent.VK_DIVIDE:
                sourceComponent.setScale(sourceComponent.getScale() / KEY_SCALE_FACTOR);
                break;
                
            /* The + and - keys in the numpad change the depth of the component */
            case KeyEvent.VK_ADD:
                 sourceComponent.setTranslation(currentTranslation.x,
                        currentTranslation.y, currentTranslation.z + KEY_TRANSLATION_OFFSET);
                
                StatusDisplayer.getDefault().setStatusText("[" +
                        currentTranslation.x + ", " +
                        currentTranslation.y + ", " +
                        (currentTranslation.z + KEY_TRANSLATION_OFFSET) + "]");
                break;
                
            case KeyEvent.VK_SUBTRACT:
                 sourceComponent.setTranslation(currentTranslation.x,
                        currentTranslation.y, currentTranslation.z - KEY_TRANSLATION_OFFSET);
                
                StatusDisplayer.getDefault().setStatusText("[" +
                        currentTranslation.x + ", " +
                        currentTranslation.y + ", " +
                        (currentTranslation.z - KEY_TRANSLATION_OFFSET) + "]");
                break;
        }
    }
}