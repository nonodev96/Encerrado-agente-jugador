/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.gui;

import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoFicha;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoPosicion;
import es.uja.ssmmaa.ontologia.Vocabulario;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

    JuegoEncerrado j;

    Graphics2D g2d;

    public Game(JuegoEncerrado j) {
        this.j = j;
    }

    @Override
    public void paint(Graphics g) {
        g2d = (Graphics2D) g;
        for (int x = 0; x < j.nonoTablero.SIZE_X; x++) {
            for (int y = 0; y < j.nonoTablero.SIZE_Y; y++) {
                int xdraw = (x * 20) + 30;
                int ydraw = (y * 20) + 30;
                g2d.drawOval(xdraw, ydraw, 10, 10);

                NonoPosicion pos = new JuegoEncerrado.NonoPosicion(x, y);
                NonoFicha[] ficha = j.nonoTablero.positions.get(pos);

                if (j.nonoTablero.checkIfExist(pos, Vocabulario.Orientacion.HORIZONTAL)) {
                    g2d.setColor(Game.translateColor(ficha[0].getColor()));
                    g2d.drawRect(xdraw + 5, ydraw + 5, 20, 2);
                }
                if (j.nonoTablero.checkIfExist(pos, Vocabulario.Orientacion.VERTICAL)) {
                    g2d.setColor(Game.translateColor(ficha[1].getColor()));
                    g2d.drawRect(xdraw + 5, ydraw + 5, 2, 20);
                }

            }
        }
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
        }
        return c;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Encerrado");
        JuegoEncerrado j = new JuegoEncerrado();
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(1, 1), new JuegoEncerrado.NonoFicha(null, Vocabulario.Color.ROJO, Vocabulario.Orientacion.HORIZONTAL));
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(1, 1), new JuegoEncerrado.NonoFicha(null, Vocabulario.Color.NEGRO, Vocabulario.Orientacion.VERTICAL));
        Game game = new Game(j);

        frame.add(game);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(1, 2), new JuegoEncerrado.NonoFicha(null, Vocabulario.Color.NEGRO, Vocabulario.Orientacion.VERTICAL));

        frame.add(game);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
