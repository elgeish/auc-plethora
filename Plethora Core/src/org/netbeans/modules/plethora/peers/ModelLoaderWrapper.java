/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ModelLoaderWrapper.java
 * Created on November 28, 2006
 */

package org.netbeans.modules.plethora.peers;

import com.microcrowd.loader.java3d.max3ds.Loader3DS;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.lw3d.Lw3dLoader;
import java.io.File;
import java.util.Hashtable;
import javax.xml.xpath.XPathExpressionException;
import org.jdesktop.lg3d.wg.ModelLoader;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.model.Lg3dDataDocument;
import org.netbeans.modules.plethora.model.Parameter;
import org.netbeans.modules.plethora.model.SimpleParameter;

/**
 * Wrapper for an ModelLoader.
 * It provides setters for the properties that don't have setters.
 * It also provides getters for the properties whose getters take an out parameter.
 * It also provides setters that wraps the super's setters and save
 * the property in the Lg3dDataDocument if it's changed and removes it from the
 * Lg3dDataDocument if it's restored to its default value
 *
 * @see ModelLoaderPeer
 * @see Lg3dDataDocument
 *
 * @author Moataz Nour and Mohamed El-Geish
 * @version 1.00
 */
public class ModelLoaderWrapper extends ModelLoader implements ShapeWrapper {
    
    private static final Hashtable<String, Class> LOADERS = new Hashtable<String, Class>();
    
    private Lg3dDataDocument lg3dDataDocument;
    private ModelLoaderPeer modelLoaderPeer;
    private String filePath;
    private Class loaderClass;
    
    static {
        LOADERS.put(".3ds", Loader3DS.class);
        LOADERS.put(".obj", ObjectFile.class);
        LOADERS.put(".lw", Lw3dLoader.class);
        LOADERS.put(".lwo", Lw3dLoader.class);
        LOADERS.put(".lws", Lw3dLoader.class);
    }
    
    public ModelLoaderWrapper() {
        this("", "", ObjectFile.class);
    }
    
    public ModelLoaderWrapper(String path, String fileName, Class loaderClass) {
        super(path, fileName, loaderClass);
        lg3dDataDocument = Lg3dComponentInspector.getInstance().getLg3dDataDocument();
        filePath = fileName.equals("") ? "" : path + "\\" + fileName;
        this.loaderClass = loaderClass;
    }
    
    public ShapePeer getShapePeer() {
        return modelLoaderPeer;
    }
    
    public void setShapePeer(ShapePeer shapePeer) {
        modelLoaderPeer = (ModelLoaderPeer) shapePeer;
    }
    
    public void setProperty(String property, Class type, Object value) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.setProperty(getName(), property, type.getName(), String.valueOf(value));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setProperty(String property, Class type, Parameter... parameters) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.setProperty(getName(), property, type.getName(), parameters);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void resetProperty(String property) {
        try {
            if (lg3dDataDocument != null) {
                lg3dDataDocument.removeProperty(getName(), property);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Lg3dDataDocument getLg3dDataDocument() {
        return lg3dDataDocument;
    }
    
    public void setParameters(File file, Class loaderClass) {
        try {
            lg3dDataDocument.setParameters(getName(),
                    new SimpleParameter(String.class.getName(), file.getParent().replace('\\', '/')),
                    new SimpleParameter(String.class.getName(), file.getName()),
                    new SimpleParameter(Class.class.getName(), loaderClass.getName()));
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setFilePath(String filePath) {
        File file = new File(filePath);
        loaderClass = LOADERS.get(file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase());
        
        setParameters(file, loaderClass);
        modelLoaderPeer.reconstruct(modelLoaderPeer, file.getParent(), file.getName(), loaderClass);
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public Class getLoaderClass() {
        return loaderClass;
    }

}