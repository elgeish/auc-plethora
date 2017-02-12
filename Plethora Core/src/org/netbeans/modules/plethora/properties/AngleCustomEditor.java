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
 * AngleCustomEditor.java
 * Created on November 15, 2006
 */

package org.netbeans.modules.plethora.properties;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Custom Editor for angles
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class AngleCustomEditor extends JPanel {
    
    private static final int RADIUS = 100;
    private static final int DIAMETER = RADIUS * 2;
    private static final int MARGIN = RADIUS / 2;
    private static final int X = MARGIN + RADIUS;
    private static final int Y = X;
    
    private double theta;
    private int x;
    private int y;
    
    private JSpinner radiansSpinner;
    private JSpinner degreesSpinner;
    private JLabel radiansTextLabel;
    private JLabel degreesTextLabel;
    
    public AngleCustomEditor(final AnglePropertyEditor anglePropertyEditor) {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        radiansSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 2.0 * Math.PI, Math.PI / 6.0 ));
        degreesSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 360.0, 30.0 ));
        radiansTextLabel = new JLabel("Angle in radians:", javax.swing.SwingConstants.RIGHT);
        degreesTextLabel = new JLabel("Angle in degrees:", javax.swing.SwingConstants.RIGHT);
        
        theta = Double.parseDouble(anglePropertyEditor.getValue().toString());
        radiansSpinner.setValue(theta);
        degreesSpinner.setValue(Math.toDegrees(theta));
        
        theta = theta >= 180 ? theta - 2 * Math.PI : 2 * Math.PI - theta;
        x = (int) (X + RADIUS * Math.cos(theta));
        y = (int) (Y + RADIUS * Math.sin(theta));
        
        final Canvas canvas = new Canvas() {
            public void paint(Graphics g) {
                g.drawOval(MARGIN, MARGIN, DIAMETER, DIAMETER);
                g.fillRect(X - 5, Y - 5, 10, 10);
                g.drawLine(X, Y, MARGIN + DIAMETER, MARGIN + RADIUS);
                g.drawLine(X, Y, x, y);
                g.fillOval(x - 5, y - 5, 10, 10);
            }
        };
        
        canvas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                theta = Math.atan2(evt.getY() - Y, evt.getX() - X);
                theta = theta >= 0 ? 2 * Math.PI - theta : - theta;
                radiansSpinner.setValue(theta);
            }
        });
        
        radiansSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                if (radiansSpinner.getValue() != null) {
                    theta = ((Double) radiansSpinner.getValue()).doubleValue();
                    anglePropertyEditor.setValue((float) theta);
                    theta = theta >= 180 ? theta - 2 * Math.PI : 2 * Math.PI - theta;
                    x = (int) (X + RADIUS * Math.cos(theta));
                    y = (int) (Y + RADIUS * Math.sin(theta));
                    canvas.repaint();
                    degreesSpinner.setValue(Math.toDegrees(((Double) radiansSpinner.getValue()).doubleValue()));
                }
            }
        });
        
        degreesSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radiansSpinner.setValue(Math.toRadians(((Double) degreesSpinner.getValue()).doubleValue()));
            }
        });
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(layout.createSequentialGroup()
                .addComponent(degreesTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(degreesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(radiansTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radiansSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(canvas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, DIAMETER + 2 * MARGIN, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, DIAMETER + 2 * MARGIN, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(radiansSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(radiansTextLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(degreesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(degreesTextLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        setLayout(layout);
    }
    
    public void setValue(Object value) {
        radiansSpinner.setValue(Double.valueOf(value.toString()));
    }
    
}