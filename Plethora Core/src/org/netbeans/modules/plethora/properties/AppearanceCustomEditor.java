/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * AppearanceCustomEditor.java
 * Created on December 05, 2006
 */

package org.netbeans.modules.plethora.properties;

import edu.aucegypt.plethora.widgets.Appearance;
import javax.swing.SpinnerNumberModel;
import javax.vecmath.Color3f;

/**
 * Custom Editor for Appearance
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class AppearanceCustomEditor extends javax.swing.JPanel {
    
    private AppearancePropertyEditor appearancePropertyEditor;
    
    public AppearanceCustomEditor(AppearancePropertyEditor appearancePropertyEditor) {
        initComponents();
        this.appearancePropertyEditor = appearancePropertyEditor;
        
        Appearance ap = (Appearance) appearancePropertyEditor.getValue();
        
        try {
            colorChooser.setColor(new Color3f(ap.getRed(), ap.getGreen(), ap.getBlue()).get());
            alphaSpinner.setValue(ap.getAlpha());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        colorChooser = new javax.swing.JColorChooser();
        panel = new javax.swing.JPanel();
        alphaSpinner = new javax.swing.JSpinner();
        label = new javax.swing.JLabel();

        colorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                AppearanceCustomEditor.this.propertyChange(evt);
            }
        });

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Alpha"));
        alphaSpinner.setModel(new SpinnerNumberModel(0.0, null, null, 0.1));
        alphaSpinner.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                AppearanceCustomEditor.this.propertyChange(evt);
            }
        });

        label.setText("Alpha (Opacity)");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alphaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alphaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(colorChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(colorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void propertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_propertyChange
        Color3f color = new Color3f(colorChooser.getColor());
        
        appearancePropertyEditor.setValue(new Appearance(
                color.x,
                color.y,
                color.z,
                Float.parseFloat(alphaSpinner.getValue().toString())));
    }//GEN-LAST:event_propertyChange
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner alphaSpinner;
    private javax.swing.JColorChooser colorChooser;
    private javax.swing.JLabel label;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
    
}