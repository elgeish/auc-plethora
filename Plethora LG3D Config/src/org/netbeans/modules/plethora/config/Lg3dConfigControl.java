/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dConfigControl.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.config;

import org.jdesktop.lg3d.displayserver.ConfigControl;
import org.jdesktop.lg3d.scenemanager.SceneManager;
import org.jdesktop.lg3d.scenemanager.glassy.GlassySceneManager;

/**
 * LG3D Configuration Control
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dConfigControl extends ConfigControl {
    
    public Lg3dConfigControl() {
    }
    
    public SceneManager createSceneManager() {
        return new GlassySceneManager();
    }

    public void processConfig() {
    }
    
}