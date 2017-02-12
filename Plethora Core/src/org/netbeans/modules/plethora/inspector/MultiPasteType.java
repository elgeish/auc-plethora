/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * MultiPasteType.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.inspector;

import java.awt.datatransfer.Transferable;
import org.openide.util.Mutex;
import org.openide.util.MutexException;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.datatransfer.PasteType;

/**
 * Paste type used for ExTransferable.Multi
 *
 * @see Lg3dComponentInspector
 *
 * @author Sarah Nadi, May Sayed, and Mohamed El-Geish
 * @version 1.00
 */
class MultiPasteType extends PasteType implements Mutex.ExceptionAction {
    
    private Transferable[] transIn;
    private PasteType[] pasteTypes;
    
    MultiPasteType(Transferable[] t, PasteType[] p) {
        transIn = t;
        pasteTypes = p;
    }
    
    /* Performs the paste action */
    public Transferable paste() throws java.io.IOException {
        if (java.awt.EventQueue.isDispatchThread()) {
            return doPaste();
        } else { // Reinvoke synchronously in AWT thread
            try {
                return (Transferable) Mutex.EVENT.readAccess(this);
            } catch (MutexException ex) {
                Exception e = ex.getException();
                
                if (e instanceof java.io.IOException) {
                    throw (java.io.IOException) e;
                } else { // Should not happen, ignore
                    e.printStackTrace();
                    return ExTransferable.EMPTY;
                }
            }
        }
    }
    
    public Object run() throws Exception {
        return doPaste();
    }
    
    private Transferable doPaste() throws java.io.IOException {
        Transferable[] transOut = new Transferable[transIn.length];
        
        for (int i = 0 ; i < pasteTypes.length ; i++) {
            Transferable newTrans = pasteTypes[i].paste();
            
            transOut[i] = newTrans != null ? newTrans : transIn[i];
        }
        
        return new ExTransferable.Multi(transOut);
    }
}