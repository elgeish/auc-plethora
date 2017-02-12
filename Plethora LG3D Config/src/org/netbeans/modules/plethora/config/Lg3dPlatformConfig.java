/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dPlatformConfig.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.config;

import java.util.logging.Logger;
import org.jdesktop.lg3d.displayserver.PlatformConfig;
import org.jdesktop.lg3d.displayserver.UniverseFactory;
import org.jdesktop.lg3d.displayserver.UniverseFactoryInterface;

/**
 * LG3D Platform Configuration
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dPlatformConfig extends PlatformConfig {

    public String getFoundationWinSys() {
        return "org.jdesktop.lg3d.displayserver.fws.awt.WinSysAWT";
    }

    public String getNativeWinIntegration() {
        return null;
    }

    public String getNativeWinLookAndFeel() {
        return null;
    }

    public boolean isClientServer() {
        return false;
    }
    
    public UniverseFactory getUniverseFactory() {
        return new Lg3dUniverseFactory();
    }

    public int getRmiPort() {
        return 44819; // Was 44817
    }

    public int getLgServerPort() {
        return 44818; // Was 44816
    }

    public boolean isFullScreenAntiAliasing() {
        return false;
    }

    public void logConfig(Logger logger) {
        logger.config("Using org.jdesktop.plethora.config.Lg3dPlatformConfig");
    }

}