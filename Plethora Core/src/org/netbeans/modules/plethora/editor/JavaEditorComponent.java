/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * JavaEditorComponent.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.text.Document;
import org.netbeans.core.api.multiview.MultiViewHandler;
import org.netbeans.core.api.multiview.MultiViews;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.netbeans.modules.java.JavaEditor;
import org.netbeans.modules.plethora.lg3d.Lg3dDataObject;
import org.openide.cookies.EditorCookie;
import org.openide.loaders.DataObject;
import org.openide.text.CloneableEditorSupport;
import org.openide.text.DataEditorSupport;
import org.openide.text.NbDocument;
import org.openide.windows.TopComponent;

/**
 * Java Editor with the visual representation and the event handlers on it
 * 
 * @see JavaViewDescription
 * @see Lg3dEditorSupport
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class JavaEditorComponent extends JavaEditor.JavaEditorComponent
        implements MultiViewElement, CloneableEditorSupport.Pane {
    
    private static final long serialVersionUID = 1L;
    
    private transient JComponent toolbar;
    private transient MultiViewElementCallback callback;
    private Lg3dDataObject lg3dDataObject;
    private Lg3dEditorSupport lg3dEditorSupport;
    
    public JavaEditorComponent(Lg3dDataObject lg3dDataObject) {
        super(lg3dDataObject.getLg3dEditorSupport());
        this.lg3dDataObject = lg3dDataObject;
        lg3dEditorSupport = lg3dDataObject.getLg3dEditorSupport();
    }
    
    public JComponent getToolbarRepresentation() {
        if (toolbar == null) {
            JEditorPane editorPane = getEditorPane();
            
            if (editorPane != null) {
                Document document = editorPane.getDocument();
                
                if (document instanceof NbDocument.CustomToolbar) {
                    toolbar = ((NbDocument.CustomToolbar) document).createToolbar(editorPane);
                }
            }
            
            if (toolbar == null) {
                toolbar = new JPanel();
            }
        }
        
        return toolbar;
    }
    
    public JComponent getVisualRepresentation() {
        return this;
    }
    
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
        lg3dEditorSupport.setTopComponent(callback.getTopComponent());
    }
    
    public CloseOperationState canCloseElement() {
        // if this is not the last cloned java editor component, closing is OK
        if (! lg3dEditorSupport.isLastView(this.callback.getTopComponent())) {
            return CloseOperationState.STATE_OK;
        }
        
        // return a placeholder state - to be sure our CloseHandler is called
        return MultiViewFactory.createUnsafeCloseState(
                "ID_JAVA_CLOSING", // dummy ID - NOI18N
                MultiViewFactory.NOOP_CLOSE_ACTION,
                MultiViewFactory.NOOP_CLOSE_ACTION);
    }
    
    public void requestVisible() {
        if (this.callback != null) {
            this.callback.requestVisible();
        } else {
            super.requestVisible();
        }
    }
    
    public void requestActive() {
        if (this.callback != null) {
            this.callback.requestActive();
        } else {
            super.requestActive();
        }
    }
    
    public void componentClosed() {
        // Issue 52286 & 55818
        super.canClose(null, true);
        super.componentClosed();
    }
    
    public void componentShowing() {
        super.componentShowing();
        lg3dEditorSupport.getJavaCodeGenerator().regenerate(true);
    }
    
    public void componentOpened() {
        super.componentOpened();

        /* If this DataObject is NOT ours, open it in its editor */
        
        DataObject dataObject = ((DataEditorSupport) cloneableEditorSupport()).getDataObject();
        
        if ((callback != null) && !(dataObject instanceof Lg3dDataObject)) {
            callback.getTopComponent().close(); // Issue 67879
            ((EditorCookie) dataObject.getCookie(EditorCookie.class)).open();
        }
    }
    
    public void componentActivated() {
        super.componentActivated();
    }
    
    public void componentDeactivated() {
        super.componentDeactivated();
    }
    
    public void componentHidden() {
        super.componentHidden();
    }
    
    public void updateName() {
        super.updateName();
        
        if (callback != null) {
            String[] titles = lg3dEditorSupport.getDisplayName();
            
            setDisplayName(titles[0]);
            setHtmlDisplayName(titles[1]);
        }
    }
    
    protected boolean closeLast() {
        return true;
    }
    
    protected boolean isActiveTC() {
        TopComponent selectedTopComponent = getRegistry().getActivated();
        
        if (selectedTopComponent == null) {
            return false;
        }
        
        if (selectedTopComponent == this) {
            return true;
        }
        
        MultiViewHandler handler = MultiViews.findMultiViewHandler(selectedTopComponent);
        
        return (handler != null
                && handler.getSelectedPerspective().preferredID().equals(Lg3dEditorSupport.MV_JAVA_ID));
    }
}