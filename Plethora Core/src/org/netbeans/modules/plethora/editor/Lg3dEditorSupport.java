/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dEditorSupport.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.netbeans.core.api.multiview.MultiViewHandler;
import org.netbeans.core.api.multiview.MultiViews;
import org.netbeans.core.spi.multiview.CloseOperationHandler;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.netbeans.modules.java.JavaEditor;
import org.netbeans.modules.plethora.lg3d.Lg3dDataObject;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.awt.UndoRedo;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileStatusEvent;
import org.openide.filesystems.FileStatusListener;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.CloneableEditorSupport;
import org.openide.text.PositionRef;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.TopComponentGroup;
import org.openide.windows.WindowManager;

/**
 * Editor that combines facilities and functionalities of
 * both the Java and the LG3D editors
 *
 * @see JavaViewDescription
 * @see Lg3dViewDescription
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
@SuppressWarnings("unchecked")
public class Lg3dEditorSupport extends JavaEditor {
    
    public static final String MV_JAVA_ID = "java"; // NOI18N
    public static final String MV_LG3D_ID = "lg3d"; //NOI18N
    
    private static final int JAVA_ELEMENT_INDEX = 0;
    private static final int LG3D_ELEMENT_INDEX = 1;
    
    private static final Integer FILE_NOT_CHANGED_READ_ONLY = 0;
    private static final Integer FILE_CHANGED_WRITABLE = 1;
    private static final Integer FILE_CHANGED_READ_ONLY = 2;
    private static final Integer FILE_NOT_CHANGED_WRITABLE = 3;
    
    private static Set openedEditorsSet = Collections.synchronizedSet(new HashSet());
    private static Map fileSystemStatusListenersMap = new HashMap();
    private static PropertyChangeListener propertyChangeListener;
    
    private Lg3dDataObject lg3dDataObject;
    private CloneableTopComponent multiView;
    private UndoRedo.Manager undoRedoManager;
    private JavaCodeGenerator javaCodeGenerator;
    private int elementIndexToOpen;
    
    public Lg3dEditorSupport(Lg3dDataObject lg3dDataObject) {
        super(lg3dDataObject);
        this.lg3dDataObject = lg3dDataObject;
    }
    
    // Creation
    
    private void addFileStatusListener(FileSystem fileSystem) {
        FileStatusListener fileStatusListener = (FileStatusListener) fileSystemStatusListenersMap.get(fileSystem);
        
        if (fileStatusListener == null) {
            
            fileStatusListener = new FileStatusListener() {
                public void annotationChanged(FileStatusEvent ev) {
                    Iterator iterator = openedEditorsSet.iterator();
                    
                    while (iterator.hasNext()) {
                        Lg3dEditorSupport lg3dEditorSupport = (Lg3dEditorSupport) iterator.next();
                        
                        if (ev.hasChanged(lg3dEditorSupport.lg3dDataObject.getPrimaryFile())
                        || ev.hasChanged(lg3dEditorSupport.lg3dDataObject.getLg3dFileObject())) {
                            lg3dEditorSupport.updateTitles();
                        }
                        
                    }
                }
            };
            
            fileSystem.addFileStatusListener(fileStatusListener);
            fileSystemStatusListenersMap.put(fileSystem, fileStatusListener);
        }
    }
    
    protected CloneableEditorSupport.Pane createPane() {
        MultiViewDescription[] descriptions = new MultiViewDescription[] {
            new JavaViewDescription(lg3dDataObject),
            new Lg3dViewDescription(lg3dDataObject)};
        
        Mode editorMode = WindowManager.getDefault().findMode(CloneableEditorSupport.EDITOR_MODE);
        
        CloneableTopComponent cloneableTopComponent = MultiViewFactory.createCloneableMultiView(
                descriptions,
                descriptions[JAVA_ELEMENT_INDEX],
                new CloseHandler(this));
        
        if (editorMode != null) {
            editorMode.dockInto(cloneableTopComponent);
        }
        
        try {
            addFileStatusListener(lg3dDataObject.getPrimaryFile().getFileSystem());
        } catch (FileStateInvalidException ex) {
            org.openide.ErrorManager.getDefault().notify(ex);
        }
        
        return (CloneableEditorSupport.Pane) cloneableTopComponent;
    }
    
    // Set TC
    
    /* Some code has been omitted */
    private static void addPropertyChangeListener() {
        if (propertyChangeListener == null) {
            
            propertyChangeListener = new PropertyChangeListener() {
                
                public void propertyChange(PropertyChangeEvent ev) {
                    if (TopComponent.Registry.PROP_ACTIVATED.equals(ev.getPropertyName())) {
                        // Activated TopComponent has changed
                        TopComponent active = TopComponent.getRegistry().getActivated();
                        
                        if (getSelectedElementIndex(active) != -1) {
                            // It is our multiview
                            Lg3dEditorSupport lg3dEditorSupport = getLg3dEditorSupport(active);
                            
                            if (lg3dEditorSupport != null) {
                                lg3dEditorSupport.multiView = (CloneableTopComponent) active;
                            }
                        }
                    } else if (TopComponent.Registry.PROP_OPENED.equals(ev.getPropertyName())) {
                        // Set of opened TopComponents has changed - hasn't some of our views been closed?
                        CloneableTopComponent closedTopComponent = null;
                        Set oldSet = (Set) ev.getOldValue();
                        Set newSet = (Set) ev.getNewValue();
                        
                        if (newSet.size() < oldSet.size()) {
                            Iterator iterator = oldSet.iterator();
                            
                            while (iterator.hasNext()) {
                                Object object = iterator.next();
                                
                                if (! newSet.contains(object)) {
                                    if (object instanceof CloneableTopComponent) {
                                        closedTopComponent = (CloneableTopComponent) object;
                                    }
                                    break;
                                }
                            }
                        }
                        
                        if (getSelectedElementIndex(closedTopComponent) != -1) {
                            // It is our multiview
                            Lg3dEditorSupport lg3dEditorSupport = getLg3dEditorSupport(closedTopComponent);
                            
                            if (lg3dEditorSupport != null) {
                                lg3dEditorSupport.multiViewClosed(closedTopComponent);
                            }
                        }
                        
                        TopComponent active = TopComponent.getRegistry().getActivated();
                        
                        if ((active != null) && (getSelectedElementIndex(active) != -1)) {
                            // It is our multiview
                            Lg3dEditorSupport lg3dEditorSupport = getLg3dEditorSupport(active);
                            
                            if (lg3dEditorSupport != null) {
                                lg3dEditorSupport.updateTitles();
                            }
                        }
                    }
                }
            };
            
            TopComponent.getRegistry().addPropertyChangeListener(propertyChangeListener);
        }
    }
    
    public void setTopComponent(TopComponent topComponent) {
        multiView = (CloneableTopComponent) topComponent;
        openedEditorsSet.add(this);
        addPropertyChangeListener();
        updateTitles();
    }
    
    // Display Name
    
    public String[] getDisplayName() {
        boolean canWrite = lg3dDataObject.canWrite();
        String title = lg3dDataObject.getNodeDelegate().getDisplayName();
        Integer mode;
        
        if (lg3dDataObject.isModified()) {
            mode = canWrite ? FILE_CHANGED_WRITABLE : FILE_NOT_CHANGED_READ_ONLY;
        } else {
            mode = canWrite ? FILE_NOT_CHANGED_WRITABLE : FILE_NOT_CHANGED_READ_ONLY;
        }
        
        return new String[] {
            Bundle.getFormattedBundleString("FMT_FormMVTCTitle", mode, title),
            null
        };
    }
    
    private void doUpdateTitles() {
        if ((multiView == null) || (!lg3dDataObject.isValid())) { // Issue 67544
            return;
        }
        
        String[] titles = getDisplayName();
        Enumeration enumeration = multiView.getReference().getComponents();
        
        while (enumeration.hasMoreElements()) {
            TopComponent topComponent = (TopComponent) enumeration.nextElement();
            topComponent.setDisplayName(titles[0]);
            topComponent.setHtmlDisplayName(titles[1]);
            topComponent.setToolTipText(FileUtil.getFileDisplayName(lg3dDataObject.getPrimaryFile()));
        }
        
        multiView.setToolTipText(FileUtil.getFileDisplayName(lg3dDataObject.getPrimaryFile()));
        multiView.setDisplayName(titles[0]);
        multiView.setHtmlDisplayName(titles[1]);
    }
    
    public void updateTitles() {
        if (java.awt.EventQueue.isDispatchThread()) {
            doUpdateTitles();
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    doUpdateTitles();
                }
            });
        }
    }
    
    // Open
    
    private void selectEditor(int index) {
        MultiViewHandler handler = MultiViews.findMultiViewHandler(multiView);
        handler.requestActive(handler.getPerspectives()[index]);
    }
    
    public void openLg3dEditor(boolean forceLg3dElement) {
        
        boolean switchToLg3d = forceLg3dElement || !openedEditorsSet.contains(this);
        
        if (switchToLg3d) {
            elementIndexToOpen = LG3D_ELEMENT_INDEX;
        }
        
        multiView = openCloneableTopComponent();
        multiView.requestActive();
        
        if (switchToLg3d) {
            selectEditor(LG3D_ELEMENT_INDEX);
        }
    }
    
    private void doOpen() {
        super.open();
        selectEditor(JAVA_ELEMENT_INDEX);
    }
    
    public void open() {
        if (EventQueue.isDispatchThread()) {
            doOpen();
        } else {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    doOpen();
                }
            });
        }
    }
    
    public void openAt(PositionRef positionRef) {
        openCloneableTopComponent();
        selectEditor(JAVA_ELEMENT_INDEX);
        super.openAt(positionRef);
    }
    
    public void openAtEventHandler(String handler) {
        javaCodeGenerator.regenerate(false);
        selectEditor(JAVA_ELEMENT_INDEX);
        
        InteriorSection section = findInteriorSection(JavaCodeGenerator.EVENT_SECTION_PREFIX + handler);
        
        if (section != null) {
            openAtPosition(section.getBegin());
        }
    }
    
    // Reload
    
    protected org.openide.util.Task reloadDocument() {
        if (multiView == null) {
            return super.reloadDocument();
        }
        
        org.openide.util.Task docLoadTask = super.reloadDocument();
        
        // after reloading is done, open the form editor again
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*
                 
                FormDesigner formDesigner = getFormEditor(true).getFormDesigner();
                if (formDesigner == null) {
                    formDesigner = (FormDesigner)multiviewTC.getClientProperty("formDesigner"); // NOI18N
                }
                if(formDesigner==null) {
                    // if formDesigner is null then it haven't been activated yet...
                    return;
                }
                 
                // close
                getFormEditor().closeForm();
                formEditor = null;
                 
                formDesigner.reset(getFormEditor(true));
                getFormEditor().setFormDesigner(formDesigner);
                 
                if(formDesigner.isShowing()) {
                    // load the form only if its open
                    loadForm();
                    FormEditor formEditor = getFormEditor();
                    formEditor.reportErrors(FormEditor.LOADING);
                    if (!formEditor.isFormLoaded()) { // there was a loading error
                        formDesigner.removeAll();
                    } else {
                        formDesigner.initialize();
                    }
                    ComponentInspector.getInstance().focusForm(formEditor);
                    formDesigner.revalidate();
                    formDesigner.repaint();
                }
                 */
            }
        });
        
        return docLoadTask;
    }
    
    // Save
    
    public void saveDocument() throws IOException {
        javaCodeGenerator.regenerate(true);
        super.saveDocument();
        lg3dDataObject.getLg3dDataDocument().saveDocument();
    }
    
    // Undo Redo
    
    protected UndoRedo.Manager createUndoRedoManager() {
        undoRedoManager = super.createUndoRedoManager();
        return undoRedoManager;
    }
    
    public void clearUndo() {
        undoRedoManager.discardAllEdits();
    }
    
    public boolean notifyModified() {
        lg3dDataObject.setModified(true);
        javaCodeGenerator.setCodeUpToDate(false);
        return super.notifyModified();
    }
    
    protected void notifyUnmodified() {
        lg3dDataObject.setModified(false);
        javaCodeGenerator.setCodeUpToDate(true);
        super.notifyUnmodified();
    }
    
    // Code Generation
    
    public JavaCodeGenerator getJavaCodeGenerator() {
        if( javaCodeGenerator == null ) {
            javaCodeGenerator = new JavaCodeGenerator(lg3dDataObject);
        }
        
        return javaCodeGenerator;
    }
    
    // Close
    
    private void removeFileStatusListener() {
        Iterator iterator = fileSystemStatusListenersMap.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ((FileSystem) entry.getKey()).removeFileStatusListener((FileStatusListener) entry.getValue());
        }
        
        fileSystemStatusListenersMap.clear();
    }
    
    private static void removePropertyChangeListener() {
        if (propertyChangeListener != null) {
            TopComponent.getRegistry().removePropertyChangeListener(propertyChangeListener);
            propertyChangeListener = null;
            
            TopComponentGroup group = WindowManager.getDefault().findTopComponentGroup("form"); // NOI18N
            
            if (group != null) {
                group.close();
            }
        }
    }
    
    /* Some code has been omitted */
    private void multiViewClosed(CloneableTopComponent multiViewTopComponent) {
        if (multiView == multiViewTopComponent) {
            multiView = null;
        }
        
        if (!multiViewTopComponent.getReference().getComponents().hasMoreElements()) {
            // Last view of this form closed
            notifyClosed();
        }
    }
    
    protected void notifyClosed() {
        openedEditorsSet.remove(this);
        
        if (openedEditorsSet.isEmpty()) {
            removePropertyChangeListener();
            removeFileStatusListener();
        }
        
        super.notifyClosed(); // Close java editor
        
        /*
        if (formEditor != null) {
            formEditor.closeForm();
            formEditor = null;
            multiView = null;
        }
         */
        
        elementIndexToOpen = JAVA_ELEMENT_INDEX;
    }
    
    // Util
    
    public boolean isLastView(TopComponent topComponent) {
        if (!(topComponent instanceof CloneableTopComponent)) {
            return false;
        }
        
        Enumeration enumeration =
                ((CloneableTopComponent)topComponent).getReference().getComponents();
        
        if (enumeration.hasMoreElements()) {
            enumeration.nextElement();
            if (enumeration.hasMoreElements()) {
                return false;
            }
        }
        
        return true;
    }
    
    public static Lg3dEditorSupport getLg3dEditorSupport(TopComponent topComponent) {
        Object dataObject = topComponent.getLookup().lookup(DataObject.class);
        
        return dataObject instanceof Lg3dDataObject
                ? ((Lg3dDataObject) dataObject).getLg3dEditorSupport()
                : null;
    }
    
    public static int getSelectedElementIndex(TopComponent topComponent) {
        if (topComponent != null) {
            MultiViewHandler handler = MultiViews.findMultiViewHandler(topComponent);
            
            if (handler != null) {
                String id = handler.getSelectedPerspective().preferredID();
                
                if (id.equals(MV_JAVA_ID)) {
                    return JAVA_ELEMENT_INDEX; // 0
                }
                
                if (id.equals(MV_LG3D_ID)) {
                    return LG3D_ELEMENT_INDEX; // 1
                }
            }
        }
        
        return -1;
    }
    
    // CloseHandler
    
    private static class CloseHandler implements CloseOperationHandler, Serializable {
        
        private static final long serialVersionUID = 1L;
        private Lg3dEditorSupport lg3dEditorSupport;
        
        public CloseHandler(Lg3dEditorSupport lg3dEditorSupport) {
            this.lg3dEditorSupport = lg3dEditorSupport;
        }
        
        public boolean resolveCloseOperation(CloseOperationState[] elements) {
            return (lg3dEditorSupport != null) ? lg3dEditorSupport.canClose() : true;
        }
    }
    
}