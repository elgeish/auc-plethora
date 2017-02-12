/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * LgcfgDataLoader.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lgcfg;

import java.io.IOException;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

/**
 * Loader for LGCFG files. Recognizes file with extension .lgcfg and
 * associates it with its MIME type (text/x-lgcfg)
 *
 * @see LgcfgDataObject
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class LgcfgDataLoader extends UniFileLoader {
    
    public static final String MIME = "text/x-lgcfg";
    
    private static final long serialVersionUID = 1L;
    
    public LgcfgDataLoader() {
        super(LgcfgDataObject.class.getName());
    }
    
    protected String defaultDisplayName() {
        return Bundle.getBundleString("LBL_Lgcfg_loader_name");
    }
    
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(MIME);
    }
    
    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new LgcfgDataObject(primaryFile, this);
    }
    
    protected String actionsContext() {
        return "Loaders/" + MIME + "/Actions";
    }
    
}