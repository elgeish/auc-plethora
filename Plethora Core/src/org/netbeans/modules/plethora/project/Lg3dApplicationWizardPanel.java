/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dApplicationWizardPanel.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.project;

import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Wizard Panel to host the visual representation of the wizard
 *
 * @author Mohamed El-Geish 
 * @version 1.00
 */
public class Lg3dApplicationWizardPanel implements WizardDescriptor.Panel,
        WizardDescriptor.ValidatingPanel, WizardDescriptor.FinishablePanel {
    
    private WizardDescriptor wizardDescriptor;
    private Lg3dApplicationPanelVisual component;
    
    /** Creates a new instance of templateWizardPanel */
    public Lg3dApplicationWizardPanel() {
    }
    
    public Component getComponent() {
        if (component == null) {
            component = new Lg3dApplicationPanelVisual(this);
            component.setName(Bundle.getBundleString("LBL_CreateProjectStep"));
        }
        return component;
    }
    
    public HelpCtx getHelp() {
        return new HelpCtx(Lg3dApplicationWizardPanel.class);
    }
    
    public boolean isValid() {
        getComponent();
        return component.valid(wizardDescriptor);
    }
    
    private final Set/*<ChangeListener>*/ listeners = new HashSet(1);
    
    @SuppressWarnings("unchecked")
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    
    @SuppressWarnings("unchecked")
    protected final void fireChangeEvent() {
        Iterator it;
        synchronized (listeners) {
            it = new HashSet(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            ((ChangeListener) it.next()).stateChanged(ev);
        }
    }
    
    public void readSettings(Object settings) {
        wizardDescriptor = (WizardDescriptor) settings;
        component.read(wizardDescriptor);
    }
    
    public void storeSettings(Object settings) {
        WizardDescriptor d = (WizardDescriptor) settings;
        component.store(d);
    }
    
    public boolean isFinishPanel() {
        return true;
    }
    
    public void validate() throws WizardValidationException {
        getComponent();
        component.validate(wizardDescriptor);
    }
    
}
