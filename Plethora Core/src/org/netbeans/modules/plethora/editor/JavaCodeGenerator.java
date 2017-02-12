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
 * JavaCodeGenerator.java
 * Created on November 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.netbeans.modules.java.JavaEditor;
import org.netbeans.modules.plethora.lg3d.Lg3dDataObject;
import org.netbeans.modules.plethora.resources.Bundle;
import org.openide.text.IndentEngine;

/**
 * Transformer from XML to Java code. It’s responsible for injecting
 * the generated code snippets into the java editor
 *
 * @see Lg3dDataObject
 *
 * @author Mohamed El-Geish 
 * @version 1.00
 */
public class JavaCodeGenerator {
    
    public static final String INIT_COMPONENTS_SECTION_NAME = "initComponents"; // NOI18N
    public static final String VARIABLES_SECTION_NAME = "variables"; // NOI18N
    public static final String EVENT_SECTION_PREFIX = "event_"; // NOI18N
    
    private static final String TAB = "    ";
    private static final String VARIABLES_DECLARATION_MSG = Bundle.getBundleString("MSG_VariablesDeclaration");
    private static final String GENERATED_CODE_MSG = Bundle.getBundleString("MSG_GeneratedCode");
    private static final String VARS_XSL_FILE_PATH = JavaCodeGenerator.class.getResource("/org/netbeans/modules/plethora/resources/lg3d-vars-1_0.xsl").toExternalForm();
    private static final String INIT_XSL_FILE_PATH = JavaCodeGenerator.class.getResource("/org/netbeans/modules/plethora/resources/lg3d-init-1_0.xsl").toExternalForm();
    private static final String EVENTS_XSL_FILE_PATH = JavaCodeGenerator.class.getResource("/org/netbeans/modules/plethora/resources/lg3d-events-1_0.xsl").toExternalForm();
    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
    
    private Lg3dEditorSupport lg3dEditorSupport;
    private DOMSource xmlSource;
    private JavaEditor.SimpleSection initComponentsSection;
    private JavaEditor.SimpleSection variablesSection;
    private IndentEngine indentEngine;
    private Transformer variablesTransformer;
    private Transformer initComponentsTransformer;
    private Transformer eventsTransformer;
    private boolean canGenerate;
    private boolean isCodeUpToDate;
    
    public JavaCodeGenerator(Lg3dDataObject lg3dDataObject) {
        lg3dEditorSupport = lg3dDataObject.getLg3dEditorSupport();
        xmlSource = lg3dDataObject.getLg3dDataDocument().getSource();
        initComponentsSection = lg3dEditorSupport.findSimpleSection(INIT_COMPONENTS_SECTION_NAME);
        variablesSection = lg3dEditorSupport.findSimpleSection(VARIABLES_SECTION_NAME);
        indentEngine = IndentEngine.find(lg3dEditorSupport.getDocument());
        canGenerate = lg3dDataObject.canWrite();
        isCodeUpToDate = false;
        
        try {
            variablesTransformer = TRANSFORMER_FACTORY.newTransformer(new StreamSource(VARS_XSL_FILE_PATH));
            initComponentsTransformer = TRANSFORMER_FACTORY.newTransformer(new StreamSource(INIT_XSL_FILE_PATH));
            eventsTransformer = TRANSFORMER_FACTORY.newTransformer(new StreamSource(EVENTS_XSL_FILE_PATH));
        } catch (Exception ex) {
            org.openide.ErrorManager.getDefault().notify(ex); // Shouldn't happen
        }
        
        if (initComponentsSection == null || variablesSection == null) {
            org.openide.ErrorManager.getDefault().notify(new Exception("ERROR: Cannot initialize guarded sections. Code generation is disabled.")); // NOI18N
            canGenerate = false;
        }
    }

    public void setCodeUpToDate(boolean isCodeUpToDate) {
        this.isCodeUpToDate = isCodeUpToDate;
    }
    
    private void regenerateVariables() {
        StringWriter buffer = new StringWriter();
        Writer writer = indentEngine.createWriter(
                lg3dEditorSupport.getDocument(),
                variablesSection.getBegin().getOffset(),
                buffer);
        
        try {
            writer.write(TAB + "// <editor-fold desc=\" " + VARIABLES_DECLARATION_MSG + " \">\n");
            variablesTransformer.transform(xmlSource, new StreamResult(writer));
            writer.write(TAB + "// </editor-fold>");
            writer.close();
            
            variablesSection.setText(buffer.toString());
        } catch (Exception ex) {
            org.openide.ErrorManager.getDefault().notify(ex); // Shouldn't happen
        }
    }
    
    private void regenerateInitComponents() {
        StringWriter buffer = new StringWriter();
        Writer writer = indentEngine.createWriter(
                lg3dEditorSupport.getDocument(),
                initComponentsSection.getBegin().getOffset(),
                buffer);
        
        try {
            writer.write(TAB + "// <editor-fold defaultstate=\"collapsed\" desc=\" " + GENERATED_CODE_MSG + " \">\n");
            writer.write("private void initComponents() {\n"); // NOI18N
            initComponentsTransformer.transform(xmlSource, new StreamResult(writer));
            writer.write("}\n" + TAB + "// </editor-fold>");
            writer.close();
            
            initComponentsSection.setText(buffer.toString());
        } catch (Exception ex) {
            org.openide.ErrorManager.getDefault().notify(ex); // Shouldn't happen
        }
    }
    
    private void regenerateEventHandlers() {
        String[] handlers;
        String handlerName;
        JavaEditor.InteriorSection interiorSection;
        Writer writer;
        StringWriter buffer = new StringWriter();
        ArrayList<String> sectionNames = new ArrayList<String>();
        
        try {
            eventsTransformer.transform(xmlSource, new StreamResult(buffer));
            buffer.close();
            handlers = buffer.toString().split("\n");
            
            for( int i = 0 ; i < handlers.length ; ++i ) {
                handlerName = handlers[i].trim();
                if (!handlerName.equals("")) {
                    handlerName = handlerName.substring(13, handlerName.indexOf('(')); // private void handlerName(...
                    interiorSection = lg3dEditorSupport.findInteriorSection(EVENT_SECTION_PREFIX + handlerName);
                    sectionNames.add(EVENT_SECTION_PREFIX + handlerName);
                    
                    if (interiorSection == null) {
                        interiorSection = lg3dEditorSupport.createInteriorSectionAfter(initComponentsSection, EVENT_SECTION_PREFIX + handlerName);
                        buffer = new StringWriter();
                        writer = indentEngine.createWriter(
                                lg3dEditorSupport.getDocument(),
                                interiorSection.getBegin().getOffset(),
                                buffer);
                        writer.write(handlers[i]);
                        writer.close();
                        interiorSection.setHeader(buffer.toString());
                        interiorSection.setBottom(TAB + "}");
                    }
                }
            }
            
            Iterator iterator = lg3dEditorSupport.getGuardedSections();
            Lg3dEditorSupport.GuardedSection guardedSection;
            
            while (iterator.hasNext()) {
                guardedSection = (Lg3dEditorSupport.GuardedSection) iterator.next();
                
                if (guardedSection instanceof Lg3dEditorSupport.InteriorSection
                        && ! sectionNames.contains(guardedSection.getName())) {
                    guardedSection.removeSection();
                }
            }
            
        } catch (Exception ex) {
            org.openide.ErrorManager.getDefault().notify(ex); // Shouldn't happen
        }
    }
    
    public void regenerate(boolean force) {
        if (canGenerate && (!isCodeUpToDate || force)) {
            isCodeUpToDate = true;
            regenerateVariables();
            regenerateInitComponents();
            regenerateEventHandlers();
            lg3dEditorSupport.clearUndo();
        }
    }
    
}