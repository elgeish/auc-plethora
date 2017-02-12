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
 * Lg3dDataObject.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lg3d;

import java.io.IOException;
import org.netbeans.modules.java.JavaDataObject;
import org.netbeans.modules.java.JavaEditor;
import org.netbeans.modules.plethora.editor.Lg3dEditorSupport;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.openide.cookies.EditCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.FileEntry;
import org.openide.nodes.Node;
import org.xml.sax.SAXException;

/**
 * Data layer of the pair of both files: the .java and the .lg3d
 * it gives a reference to the LG3D editor
 * 
 * @see Lg3dDataLoader
 * @see Lg3dDataNode
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dDataObject extends JavaDataObject {
    
    private FileEntry lg3dFileEntry;
    private Lg3dDataDocument lg3dDataDocument;
    transient private Lg3dEditorSupport lg3dEditorSupport;
    transient private OpenEdit openEdit;
    
    public Lg3dDataObject(FileObject lg3dFileObject, FileObject javaFileObject, Lg3dDataLoader loader) throws DataObjectExistsException, IOException {
        super(javaFileObject, loader);
        lg3dFileEntry = (FileEntry) registerEntry(lg3dFileObject);
        getCookieSet().add(new Class[] { OpenCookie.class, EditCookie.class}, this);
    }
    
    public Node.Cookie createCookie(Class type) {
        if( OpenCookie.class.equals(type) || EditCookie.class.equals(type) ) {
            if( openEdit == null ) {
                openEdit = new OpenEdit();
            }
            return openEdit;
        }
        return super.createCookie(type);
    }
    
    /** Provides node that should represent this data object. When a node for
     * representation in a parent is requested by a call to getNode(parent) it
     * is the exact copy of this node with only parent changed. This
     * implementation creates instance <CODE>DataNode</CODE>.  <P> This method
     * is called only once.
     *
     * @return the node representation for this data object
     * @see DataNode
     */
    
    protected Node createNodeDelegate() {
        return new Lg3dDataNode(this);
    }
    
    // from JavaDataObject
    protected JavaEditor createJavaEditor() {
        if( lg3dEditorSupport == null ) {
            lg3dEditorSupport = new Lg3dEditorSupport(this);
        }
        
        return lg3dEditorSupport;
    }

    public Lg3dDataDocument getLg3dDataDocument() {
        if (lg3dDataDocument == null) {
            lg3dDataDocument = new Lg3dDataDocument(this);
        }
        
        return lg3dDataDocument;
    }
        
    public Lg3dEditorSupport getLg3dEditorSupport() {
        return (Lg3dEditorSupport) getJavaEditor();
    }

    public FileEntry getLg3dFileEntry() {
        return lg3dFileEntry;
    }
        
    public FileObject getLg3dFileObject() {
        return lg3dFileEntry.getFile();
    }
    
    public boolean canWrite() {
        return getPrimaryFile().canWrite() && getLg3dFileObject().canWrite();
    }
    
    private class OpenEdit implements OpenCookie, EditCookie {
        
        // open form editor with form designer selected
        public void open() {
            getLg3dEditorSupport().openLg3dEditor(true);
        }
        
        // open form editor with java editor selected (form not loaded)
        public void edit() {
            getLg3dEditorSupport().open();
        }
        
    }
        
}