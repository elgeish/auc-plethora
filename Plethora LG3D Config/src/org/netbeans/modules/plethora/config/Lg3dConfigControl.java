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