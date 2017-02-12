/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Menu.java
 * Created on December 04, 2006
 */

package edu.aucegypt.plethora.widgets;

import java.util.Enumeration;
import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * Menu
 * 
 * @see Widget
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
public class Menu extends Widget {
    
    private int count;
    private static float MIN_SCALE = 0.5f;
    private static float MAX_SCALE = 1f;
    private float DISPLACEMENT = 0.012f;
    
    public Menu() {
        count = 0;
        setScale(MIN_SCALE);
        setAnimation(new NaturalMotionAnimation(1000));
        addListener(new MouseEnteredEventAdapter(new ScaleAdjuster(this)));
    }
    
    public void addChild(Component3D i) {
        i.changeTranslation(0f, count * DISPLACEMENT, 0f);
        count++;
        super.addChild(i);
    }
    
    public void removeChild(Node n) {
        super.removeChild(n);
        
        Enumeration elements = getAllChildren();
        Component3D i = null;
        int num = 0;
        
        while(elements.hasMoreElements() && (i = (Component3D) elements.nextElement()) != null) {
            i.changeTranslation(0, num * DISPLACEMENT, 0);
        }
    }
    
    class ScaleAdjuster implements ActionBoolean {
        
        private Component3D comp;
        
        public ScaleAdjuster(Component3D comp) {
            this.comp = comp;
        }
        
        public void performAction(LgEventSource lgEventSource, boolean b) {
            if (!b) {
                changeScale(MIN_SCALE, 1000);
            } else {
                changeScale(MAX_SCALE, 1000);
            }
        }
    }
}