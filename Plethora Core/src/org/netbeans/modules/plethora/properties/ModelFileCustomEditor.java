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
 * ModelFileCustomEditor.java
 * Created on November 28, 2006
 */

package org.netbeans.modules.plethora.properties;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Custom Editor for model files
 *
 * @author Moataz Nour and Mohamed El-Geish
 * @version 1.00
 */
public class ModelFileCustomEditor extends javax.swing.JPanel {
    
    private static final String _3DS = "3ds";
    private static final String LW = "lw";
    private static final String LWO = "lwo";
    private static final String LWS = "lws";
    private static final String OBJ = "obj";
    
    private ModelFilePropertyEditor modelFilePropertyEditor;
    
    public ModelFileCustomEditor(ModelFilePropertyEditor modelFilePropertyEditor) {
        initComponents();
        this.modelFilePropertyEditor = modelFilePropertyEditor;
        fileLabel.setText((String) modelFilePropertyEditor.getValue());
        
        if (fileLabel.getText().equals("")) {
            fileLabel.setText("[Please choose a file]");
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        fileChooser = new javax.swing.JFileChooser();
        fileLabel = new javax.swing.JLabel();
        browseButton = new javax.swing.JButton();

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("3D Studio Files (*.3ds)", _3DS));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Lightwave Files (*.lw|*.lwo|*.lws)", LW, LWO, LWS));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Object Files (*.obj)", OBJ));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("All Supported Files (*.3ds|*.lw|*.lwo|*.lws|*.obj)", _3DS, LW, LWO, LWS, OBJ));
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });

        fileLabel.setText("File");

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        fileChooser.showOpenDialog(this);
    }//GEN-LAST:event_browseButtonActionPerformed
    
    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        if(evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
            String path = fileChooser.getSelectedFile().getPath();
            
            modelFilePropertyEditor.setValue(path);
            fileLabel.setText(path);
        }
    }//GEN-LAST:event_fileChooserActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel fileLabel;
    // End of variables declaration//GEN-END:variables
    
}