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
 * Lg3dDataLoader.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lg3d;

import java.io.IOException;
import org.netbeans.modules.java.JavaDataLoader;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.FileEntry;
import org.openide.loaders.MultiDataObject;

/**
 * Loader for LG3D Forms. Recognizes file with extension .lg3d and .java and 
 * with extension class if there is their source and lg3d form file
 * 
 * @see Lg3dDataObject
 * @see Lg3dDataNode
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dDataLoader extends JavaDataLoader {
    
   /**
     * The standard extensions of the recognized files
     * @version 1.00, Sep 28, 2006
     */    
    public static final String LG3D_EXTENSION = "lg3d"; // NOI18N
    
    /* Generated Serialized Version UID */
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new instance of <code>Lg3dDataLoader</code>
     * @version 1.00, Sep 28, 2006
     */ 
    public Lg3dDataLoader() {
        super(Lg3dDataObject.class.getName());
    }
    
    /**
     * Gets the default display name of this loader
     * @return default display name
     * @version 1.00, Sep 28, 2006
     */
    protected String defaultDisplayName() {
        return Bundle.getBundleString("LBL_Lg3d_loader_name");
    }
    
    /**
     * Identifies the name of context in layer files where the 
     * loader wishes to store its own actions and also read them.
     * In principle any {@link javax.swing.Action} instance can be registered
     * in the context and it will be visible in the default DataNode
     * for data object created by this loader. Only SystemAction can however
     * be manipulated from DataLoader getActions/setActions methods.
     * <p>
     *  The default implementation returns null to indicate that no
     *  layer reading should be used (use defaultActions instead).
     * <p>
     *  {@link javax.swing.JSeparator} instances may be used to separate items.
     * <p>
     * Context name: <samp>Loaders/text/x-java/Actions</samp>
     * @return the string name of the context on layer files to read/write actions to
     * @version 1.00, Sep 28, 2006
     */
    protected String actionsContext() {
        return "Loaders/text/x-java/Actions";
    }
    
    /** 
     * For a given file finds a primary file
     * @param fileObject the file to find primary file for
     * @return the primary file for the file or null if the file is not recognized by this loader
     * @version 1.00, Sep 28, 2006
     */
    protected FileObject findPrimaryFile(FileObject fileObject) {
        if (fileObject.getExt().equals(LG3D_EXTENSION)) {
            return FileUtil.findBrother(fileObject, JAVA_EXTENSION);
        }
        
        FileObject javaFileObject = super.findPrimaryFile(fileObject);
        
        return (javaFileObject != null 
                && FileUtil.findBrother(javaFileObject, LG3D_EXTENSION) != null)
                    ? javaFileObject
                    : null;
    }
    
    /**
     * Creates the right data object for given primary file.
     * It is guaranteed that the provided file is realy primary file
     * returned from the method findPrimaryFile
     *
     * @param fileObject the primary file
     * @return the data object for this file
     * @exception DataObjectExistsException if the primary file already has data object
     * @version 1.00, Sep 28, 2006
     */
    protected MultiDataObject createMultiObject(FileObject fileObject)
            throws DataObjectExistsException, IOException {
        return new Lg3dDataObject(
                FileUtil.findBrother(fileObject, LG3D_EXTENSION),
                fileObject, this);
    }
    
    /**
     * This is from <code>JavaDataLoader</code>.
     * Probably needed in case FormDataObject is deserialized, then the secondary entry is created additionally.
     * By default, FileEntry.Numb is used for the class files; subclasses wishing to have useful
     * secondary files should override this for those files, typically to FileEntry
     *
     * @param secondaryFile secondary file to create entry for
     * @return the entry
     * @version 1.00, Sep 28, 2006
     */
    protected MultiDataObject.Entry createSecondaryEntry(MultiDataObject obj,
            FileObject secondaryFile) {
        
        if (secondaryFile.getExt().equals(LG3D_EXTENSION)) {
            FileEntry lg3dFileEntry = new FileEntry(obj, secondaryFile);           
            return lg3dFileEntry;
        }
        
        return super.createSecondaryEntry(obj, secondaryFile);
    }   
}