/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.gui;

import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado;
import es.uja.ssmmaa.ontologia.Vocabulario;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author nonodev96
 */
public class Encerrado_UI extends javax.swing.JPanel {

    JuegoEncerrado juegoEncerrado;

    public Encerrado_UI() {
        initComponents();
        this.juegoEncerrado = new JuegoEncerrado();
        this.setVisible(true);
        this.setSize(new Dimension(this.getWidth(), this.getHeight()));
    }

    public Encerrado_UI(JuegoEncerrado j) {
        initComponents();
        this.juegoEncerrado = j;
        this.setVisible(true);
        this.setSize(new Dimension(this.getWidth(), this.getHeight()));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int translate = 5;
        int size_x = this.getWidth() / (juegoEncerrado.nonoTablero.SIZE_X + 1);
        int size_y = this.getHeight() / (juegoEncerrado.nonoTablero.SIZE_Y + 1);
        for (int x = 0; x < juegoEncerrado.nonoTablero.SIZE_X; x++) {
            for (int y = 0; y < juegoEncerrado.nonoTablero.SIZE_Y; y++) {
                int xdraw = (x * size_x) + size_x;
                int ydraw = (y * size_y) + size_y;
                g.drawOval(xdraw, ydraw, 10, 10);

                JuegoEncerrado.NonoPosicion pos = new JuegoEncerrado.NonoPosicion(x, y);
                JuegoEncerrado.NonoFicha[] ficha = juegoEncerrado.nonoTablero.getPosicion(pos);

                if (juegoEncerrado.nonoTablero.checkIfExist(pos, Vocabulario.Orientacion.HORIZONTAL)) {
                    g.setColor(Encerrado_UI.translateColor(ficha[0].getColor()));
                    g.drawLine(xdraw + translate, ydraw + translate, xdraw + size_x + 5, ydraw + translate);
                }
                if (juegoEncerrado.nonoTablero.checkIfExist(pos, Vocabulario.Orientacion.VERTICAL)) {
                    g.setColor(Encerrado_UI.translateColor(ficha[1].getColor()));
                    g.drawLine(xdraw + translate, ydraw + translate, xdraw + translate, ydraw + size_y + 5);
                }

            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public static java.awt.Color translateColor(Vocabulario.Color v_color) {
        java.awt.Color c = new Color(0);
        switch (v_color) {
            case ROJO:
                c = Color.RED;
                break;
            case NEGRO:
                c = Color.BLACK;
                break;
            case AZUL:
                c = Color.BLUE;
                break;
            case BLANCO:
                c = Color.WHITE;
                break;
            case VERDE:
                c = Color.GREEN;
                break;
            case NULO:
                c = Color.PINK;
                break;
        }
        return c;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(400, 300));
        setMinimumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
