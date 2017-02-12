/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dUniverseFactory.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.config;

import com.sun.j3d.utils.universe.ConfiguredUniverse;
import java.awt.GraphicsConfiguration;
import java.net.URL;
import java.util.logging.Logger;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import org.jdesktop.lg3d.displayserver.UniverseFactory;

/**
 * LG3D Universe Factory
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dUniverseFactory extends UniverseFactory {
    
    private Logger logger = Logger.getLogger("lg.displayserver");
    private Canvas3D c3d = null;
    
    public ConfiguredUniverse createUniverse(URL configURL) {
        
        GraphicsConfiguration config = ConfiguredUniverse.getPreferredConfiguration();
        
        c3d = new Canvas3D(config) {
            public void paint(java.awt.Graphics g) {
                super.paint(g);
                java.awt.Toolkit.getDefaultToolkit().sync();
            }
        };
        
        ConfiguredUniverse ret =  new com.sun.j3d.utils.universe.ConfiguredUniverse(c3d);
        
        View view = ret.getViewer().getView();
        view.setScreenScalePolicy(View.SCALE_EXPLICIT);
        view.setCoexistenceCenteringEnable(true);
        view.setWindowEyepointPolicy(View.RELATIVE_TO_WINDOW);
        view.setWindowMovementPolicy(View.PHYSICAL_WORLD);
        view.setWindowResizePolicy(View.VIRTUAL_WORLD);
        view.setFrontClipDistance(0.01f);
        view.setBackClipDistance(10f);
        
        ViewPlatform vp = ret.getViewingPlatform().getViewPlatform();
        
        vp.setViewAttachPolicy(View.NOMINAL_SCREEN);
        
        c3d.getScreen3D().setPhysicalScreenWidth(0.365f);
        c3d.getScreen3D().setPhysicalScreenHeight(0.292f);
        
        ret.getViewingPlatform().setNominalViewingTransform();
        
        if (listener != null) {
            listener.universeCreated(c3d);
        } else {
            throw new RuntimeException("No Canvas3D listener registered");
        }
        
        return ret;
    }
    
}