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
 * Lg3dDesigner.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import java.util.concurrent.Semaphore;
import javax.media.j3d.Canvas3D;
import org.jdesktop.lg3d.displayserver.AppConnectorPrivate;
import org.jdesktop.lg3d.displayserver.UniverseFactory;
import org.jdesktop.lg3d.wg.Component3D;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;

/**
 * Panel that starts and connects to LG3D then contains it running within
 * 
 * @see Lg3dEditorComponent
 * @see Lg3dEditorSupport
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dDesigner extends javax.swing.JPanel {
    
    private static Lg3dDesigner instance = null;
    
    private Lg3dDesignerFrame lg3dDesignerFrame;
    private AppConnectorPrivate appConnector;
    private Semaphore lg3dStarted;
    
    public static Lg3dDesigner getLg3dDesigner(Lg3dDataDocument lg3dDataDocument) {
        if (instance == null) {
            instance = new Lg3dDesigner(lg3dDataDocument);
        }
        
        return instance;
    }
    
    public static void clear() {
        if (instance.lg3dDesignerFrame != null) {
            instance.lg3dDesignerFrame.removeAllChildren();
        }
    }
    
    public void add(Component3D component) {
        if (lg3dDesignerFrame != null) {
            lg3dDesignerFrame.addChild(component);
        }
    }
    
    private Lg3dDesigner(final Lg3dDataDocument lg3dDataDocument) {
        lg3dStarted = new Semaphore(0);

        System.setProperty("lg.platformConfigClass", "org.netbeans.modules.plethora.config.Lg3dPlatformConfig");
        System.setProperty("lg.configclass", "org.netbeans.modules.plethora.config.Lg3dConfigControl");
        System.setProperty("j3d.sortShape3DBounds", "true");
        
        setLayout(new java.awt.BorderLayout());
        
        Thread thread = new Thread() {
            public void run() {
                org.jdesktop.lg3d.displayserver.Main.main(null);
            }
        };
        
        UniverseFactory.addUniverseListener(new UniverseFactory.UniverseListener() {
            public void universeCreated(Canvas3D canvas) {
                Lg3dDesigner.this.add("Center", canvas);
                Lg3dDesigner.this.validate();
                appConnector = AppConnectorPrivate.getAppConnector();
                lg3dDesignerFrame = Lg3dDesignerFrame.getLg3dDesignerFrame(lg3dDataDocument);
                
                /* XXX hack 2 add frame */
                final java.util.Timer timer = new java.util.Timer();
                timer.schedule(new java.util.TimerTask() {
                    public void run() {
                        try {
                            appConnector.addFrame3D(lg3dDesignerFrame);
                            timer.cancel();
                        } catch (Exception ex) {}
                    }
                }, 500, 500);
            }
        });
        
        thread.start();
    }
}