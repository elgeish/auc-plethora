/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Installer.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.utils;

import java.beans.PropertyEditorManager;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.vecmath.Vector3f;
import org.netbeans.modules.plethora.editor.Lg3dDesigner;
import org.netbeans.modules.plethora.properties.Vector3fPropertyEditor;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. The restored() method is called
 * when the module is starting up
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Installer extends ModuleInstall {
    
    public void restored() {
        try {
            
            PropertyEditorManager.registerEditor(Vector3f.class, Vector3fPropertyEditor.class);
            SplashScreen.showSplashScreen();
            
            /* To fix menus that don't work properly */
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

            Lg3dDesigner.getLg3dDesigner(null);
            
            new java.util.Timer().schedule(new java.util.TimerTask() {
                public void run() {
                    SplashScreen.closeSplashScreen();
                }
            }, 3000);
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "Plethora",
                    JOptionPane.ERROR_MESSAGE,
                    new ImageIcon(getClass().getResource("/org/netbeans/modules/plethora/resources/lg3d-icon32.png")));
        }
    }
    
}