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
 * LgcfgDataObject.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.lgcfg;

import java.io.IOException;
import org.netbeans.modules.xml.multiview.DesignMultiViewDesc;
import org.netbeans.modules.xml.multiview.XmlMultiViewDataObject;
import org.netbeans.spi.xml.cookies.CheckXMLSupport;
import org.netbeans.spi.xml.cookies.DataObjectAdapters;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Data layer of the pair of the lgcfg file. It gives a reference to
 * the LGCFG multi-view editor
 *
 * @see LgcfgDataLoader
 * @see LgcfgDataNode
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class LgcfgDataObject extends XmlMultiViewDataObject {

    public static final String DTD_PUBLIC_ID = "-//NetBeans//DTD LG3D Application Configuration 1.6//EN";
    public static final String DTD_SYSTEM_ID = "nbfs://nbhost/SystemFileSystem/xml/entities/NetBeans/DTD_LG3D_Application_Configuration_1_6";
    
    private Document document;
    
    public LgcfgDataObject(FileObject pf, LgcfgDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        
        CookieSet cookies = getCookieSet();
        InputSource inputSource = DataObjectAdapters.inputSource(this);
        
        cookies.add(new CheckXMLSupport(inputSource));
        // cookies.add(new ValidateXMLSupport(inputSource));
        
        try {
            document = XMLUtil.parse(inputSource, true, false, null, null);
        } catch (Exception ex) {
            org.openide.ErrorManager.getDefault().notify(org.openide.ErrorManager.EXCEPTION, ex);
        }
    }
    
    protected Node createNodeDelegate() {
        return new LgcfgDataNode(this);
    }

    protected DesignMultiViewDesc[] getMultiViewDesc() {
        //return new DesignMultiViewDesc[] {new LgcfgMultiViewDesc(this)};
        return new DesignMultiViewDesc[0];
    }

    protected String getPrefixMark() {
        return null;
    }
    
    public Document getDocument() {
        return document;
    }
    
}