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
 * SplashScreen.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.utils;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * Plethora's splash screen
 *
 * @author Mohamed El-Geish 
 * @version 1.00
 */
public class SplashScreen extends Window {
    
    private static SplashScreen instance;
    
    private Image image;
    private boolean paintCalled = false;
    
    private SplashScreen() {
        super(new Frame());
        
        image = Toolkit.getDefaultToolkit().createImage(
                SplashScreen.class.getResource("/org/netbeans/modules/plethora/resources/splash.png"));
        
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(image, 0);
        
        try {
            mt.waitForID(0);
        } catch (InterruptedException ie) {
        }
        
        int imgWidth = image.getWidth(this);
        int imgHeight = image.getHeight(this);
        setSize(imgWidth, imgHeight);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenDim.width - imgWidth) / 2, (screenDim.height - imgHeight) / 2 - 120);
    }
    
    public void update(java.awt.Graphics g) {
        paint(g);
    }
    
    public void paint(java.awt.Graphics g) {
        g.drawImage(image, 0, 0, this);
        
        if (! paintCalled) {
            paintCalled = true;
            synchronized (this) { notifyAll(); }
        }
    }
    
    public static void showSplashScreen() {
        if (instance == null) {
            
            instance = new SplashScreen();
            instance.setVisible(true);
            
            if ( ! java.awt.EventQueue.isDispatchThread()
            && Runtime.getRuntime().availableProcessors() == 1) {
                synchronized(instance) {
                    while (!instance.paintCalled) {
                        try { instance.wait(); } catch (InterruptedException e) {}
                    }
                }
            }
        }
    }
    
    public static void closeSplashScreen() {
        if (instance != null) {
            instance.getOwner().dispose();
            instance = null;
        }
    }
    
}