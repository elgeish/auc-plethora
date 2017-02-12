/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dToolBar.java
 * Created on December 04, 2006
 */

package org.netbeans.modules.plethora.editor;

import java.awt.Insets;
import java.util.ArrayList;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.Component3D;
import org.netbeans.modules.plethora.inspector.Lg3dComponentInspector;
import org.netbeans.modules.plethora.peers.ShapePeer;
import org.netbeans.modules.plethora.peers.ShapeWrapper;
import org.openide.nodes.Node;

/**
 * Toolbar for the designer
 *
 * @see Lg3dEditorComponent
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class Lg3dToolBar extends javax.swing.JToolBar {
    
    private Lg3dComponentInspector inspector;
    
    public Lg3dToolBar() {
        initComponents();
    }
    
    private void initComponents() {
        setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));
        setFloatable(false);
        setRollover(true);
        
        inspector = Lg3dComponentInspector.getInstance();
        
        javax.swing.JButton alignLeftButton = new javax.swing.JButton();
        javax.swing.JButton alignRightButton = new javax.swing.JButton();
        javax.swing.JButton alignUpButton = new javax.swing.JButton();
        javax.swing.JButton alignDownButton = new javax.swing.JButton();
        javax.swing.JButton alignInButton = new javax.swing.JButton();
        javax.swing.JButton alignOutButton = new javax.swing.JButton();
        
        add(javax.swing.Box.createHorizontalStrut(4));
        add(new javax.swing.JToolBar.Separator());
        
        alignLeftButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/plethora/resources/align-left.png")));
        alignLeftButton.setFocusPainted(false);
        alignLeftButton.setMargin(new Insets(0, 0, 0, 0));
        alignLeftButton.setOpaque(false);
        alignLeftButton.setToolTipText("Align left");
        alignLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignLeftButtonActionPerformed(evt);
            }
        });
        add(alignLeftButton);
        
        alignRightButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/plethora/resources/align-right.png")));
        alignRightButton.setFocusPainted(false);
        alignRightButton.setMargin(new Insets(0, 0, 0, 0));
        alignRightButton.setOpaque(false);
        alignRightButton.setToolTipText("Align right");
        alignRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignRightButtonActionPerformed(evt);
            }
        });
        add(alignRightButton);
        
        alignUpButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/plethora/resources/align-up.png")));
        alignUpButton.setFocusPainted(false);
        alignUpButton.setMargin(new Insets(0, 0, 0, 0));
        alignUpButton.setOpaque(false);
        alignUpButton.setToolTipText("Align up");
        alignUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignUpButtonActionPerformed(evt);
            }
        });
        add(alignUpButton);
        
        alignDownButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/plethora/resources/align-down.png")));
        alignDownButton.setFocusPainted(false);
        alignDownButton.setMargin(new Insets(0, 0, 0, 0));
        alignDownButton.setOpaque(false);
        alignDownButton.setToolTipText("Align down");
        alignDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignDownButtonActionPerformed(evt);
            }
        });
        add(alignDownButton);
        
        alignInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/plethora/resources/align-in.png")));
        alignInButton.setFocusPainted(false);
        alignInButton.setMargin(new Insets(0, 0, 0, 0));
        alignInButton.setOpaque(false);
        alignInButton.setToolTipText("Align in");
        alignInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignInButtonActionPerformed(evt);
            }
        });
        add(alignInButton);
        
        alignOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/plethora/resources/align-out.png")));
        alignOutButton.setFocusPainted(false);
        alignOutButton.setMargin(new Insets(0, 0, 0, 0));
        alignOutButton.setOpaque(false);
        alignOutButton.setToolTipText("Align out");
        alignOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignOutButtonActionPerformed(evt);
            }
        });
        add(alignOutButton);
        
        add(javax.swing.Box.createHorizontalGlue());
    }
    
    private ArrayList<Component3D> getSelectedComponents() {
        ArrayList<Component3D> array = new ArrayList<Component3D>();
        
        for(Node node : inspector.getExplorerManager().getSelectedNodes()) {
            if (node instanceof ShapePeer) {
                array.add(((ShapePeer) node).getComponent());
            }
        }
        
        return array;
    }
    
    private void alignLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Component3D> components = getSelectedComponents();
        float minX = Integer.MAX_VALUE;
        Vector3f vector = new Vector3f();
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            
            if (vector.x < minX) {
                minX = vector.x;
            }
        }
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            component.setTranslation(minX, vector.y, vector.z);
        }
    }
    
    private void alignRightButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Component3D> components = getSelectedComponents();
        float maxX = Integer.MIN_VALUE;
        Vector3f vector = new Vector3f();
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            
            if (vector.x > maxX) {
                maxX = vector.x;
            }
        }
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            component.setTranslation(maxX, vector.y, vector.z);
        }
    }
    
    private void alignUpButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Component3D> components = getSelectedComponents();
        float maxY = Integer.MIN_VALUE;
        Vector3f vector = new Vector3f();
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            
            if (vector.y > maxY) {
                maxY = vector.y;
            }
        }
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            component.setTranslation(vector.x, maxY, vector.z);
        }
    }
    
    private void alignDownButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Component3D> components = getSelectedComponents();
        float minY = Integer.MAX_VALUE;
        Vector3f vector = new Vector3f();
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            
            if (vector.y < minY) {
                minY = vector.y;
            }
        }
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            component.setTranslation(vector.x, minY, vector.z);
        }
    }
    
    private void alignInButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Component3D> components = getSelectedComponents();
        float minZ = Integer.MAX_VALUE;
        Vector3f vector = new Vector3f();
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            
            if (vector.z < minZ) {
                minZ = vector.z;
            }
        }
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            component.setTranslation(vector.x, vector.y, minZ);
        }
    }
    
    private void alignOutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Component3D> components = getSelectedComponents();
        float maxZ = Integer.MIN_VALUE;
        Vector3f vector = new Vector3f();
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            
            if (vector.z > maxZ) {
                maxZ = vector.z;
            }
        }
        
        for(Component3D component : components) {
            component.getTranslation(vector);
            component.setTranslation(vector.x, vector.y, maxZ);
        }
    }
    
}