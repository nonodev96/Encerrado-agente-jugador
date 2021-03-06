/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.gui;

import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado;
import es.uja.ssmmaa.ontologia.Vocabulario;

/**
 *
 * @author nonodev96
 */
public class Juego_UI extends javax.swing.JFrame {

    public JuegoEncerrado juegoEncerrado;

    /**
     *
     * @param agenteJugador
     */
    public Juego_UI() {
        initComponents();
        this.setTitle("Jugador UI");

        this.juegoEncerrado = new JuegoEncerrado();

        this.encerrado_UI.juegoEncerrado = juegoEncerrado;
        this.encerrado_UI.setSize(400, 300);
        this.encerrado_UI.setVisible(true);
        this.jButton_enviar.setEnabled(false);
    }

    public void updateJuego(JuegoEncerrado juegoEncerrado) {
        this.juegoEncerrado = juegoEncerrado;
        this.encerrado_UI.juegoEncerrado = juegoEncerrado;
        this.encerrado_UI.updateUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTextField_POS_X = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_POS_Y = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton_enviar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        encerrado_UI = new es.uja.ssmmaa.dots_and_boxes.gui.Encerrado_UI();
        jRadioButton_IA = new javax.swing.JRadioButton();
        jRadioButton_persona = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField_POS_X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_POS_XActionPerformed(evt);
            }
        });

        jLabel1.setText("X: ");

        jLabel2.setText("Y: ");

        jTextField_POS_Y.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_POS_YActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Horizontal");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Vertical");

        jButton_enviar.setText("Enviar");
        jButton_enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_enviarActionPerformed(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));

        encerrado_UI.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout encerrado_UILayout = new javax.swing.GroupLayout(encerrado_UI);
        encerrado_UI.setLayout(encerrado_UILayout);
        encerrado_UILayout.setHorizontalGroup(
            encerrado_UILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        encerrado_UILayout.setVerticalGroup(
            encerrado_UILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ENCERRADO", encerrado_UI);

        buttonGroup2.add(jRadioButton_IA);
        jRadioButton_IA.setSelected(true);
        jRadioButton_IA.setText("IA");
        jRadioButton_IA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_IAActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton_persona);
        jRadioButton_persona.setText("Persona");
        jRadioButton_persona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_personaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_POS_X)
                                    .addComponent(jTextField_POS_Y)))
                            .addComponent(jRadioButton_IA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRadioButton_persona, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_POS_X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_POS_Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton2)
                    .addComponent(jButton_enviar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_persona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton_IA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_POS_XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_POS_XActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_POS_XActionPerformed

    private void jTextField_POS_YActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_POS_YActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_POS_YActionPerformed

    private void jButton_enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_enviarActionPerformed

    }//GEN-LAST:event_jButton_enviarActionPerformed

    private void jRadioButton_personaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_personaActionPerformed
        this.jButton_enviar.setEnabled(true);
    }//GEN-LAST:event_jRadioButton_personaActionPerformed

    private void jRadioButton_IAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_IAActionPerformed
        this.jButton_enviar.setEnabled(false);
    }//GEN-LAST:event_jRadioButton_IAActionPerformed

    public static void main(String[] args) {
        /*
        frame.add(game);
        frame.setSize(j.nonoTablero.SIZE_X * 35, j.nonoTablero.SIZE_Y * 40);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Juego_UI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private es.uja.ssmmaa.dots_and_boxes.gui.Encerrado_UI encerrado_UI;
    private javax.swing.JButton jButton_enviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton_IA;
    private javax.swing.JRadioButton jRadioButton_persona;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField_POS_X;
    private javax.swing.JTextField jTextField_POS_Y;
    // End of variables declaration//GEN-END:variables
}
