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
 * Lg3dFileWizardIterator.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.project;

import java.io.IOException;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.spi.java.project.support.ui.templates.JavaTemplates;
import org.openide.WizardDescriptor;

/**
 * Wizard iterator to create an LG3D project folder with
 * the LG3D libraries added to it
 *
 * @author Mohamed El-Geish 
 * @version 1.00
 */
public class Lg3dFileWizardIterator implements WizardDescriptor.InstantiatingIterator {

    private static WizardDescriptor.InstantiatingIterator instance;

    public static WizardDescriptor.InstantiatingIterator createIterator() {
        if( instance == null ) {
            instance = JavaTemplates.createJavaTemplateIterator();
        }
        return instance;
    }

    public Set instantiate() throws IOException {
        return instance.instantiate();
    }

    public void initialize(WizardDescriptor wizard) {
        instance.initialize(wizard);
    }

    public void uninitialize(WizardDescriptor wizard) {
        instance.uninitialize(wizard);
    }

    public WizardDescriptor.Panel current() {
        return instance.current();
    }

    public String name() {
        return instance.name();
    }

    public boolean hasNext() {
        return instance.hasNext();
    }

    public boolean hasPrevious() {
        return instance.hasPrevious();
    }

    public void nextPanel() {
        instance.nextPanel();
    }

    public void previousPanel() {
        instance.previousPanel();
    }

    public void addChangeListener(ChangeListener l) {
        instance.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        instance.removeChangeListener(l);
    }
    
}