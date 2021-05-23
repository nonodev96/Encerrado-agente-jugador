/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoFicha;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoPosicion;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoTablero;
import es.uja.ssmmaa.ontologia.Vocabulario;
import javafx.util.Pair;

/**
 *
 * @author nono_
 */
public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {

        NonoFicha nonoFicha_horizontal = new NonoFicha();
        nonoFicha_horizontal.setColor(Vocabulario.Color.NEGRO);
        nonoFicha_horizontal.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
        NonoFicha nonoFicha_vertical = new NonoFicha();
        nonoFicha_vertical.setColor(Vocabulario.Color.NEGRO);
        nonoFicha_vertical.setOrientacion(Vocabulario.Orientacion.VERTICAL);

        NonoFicha nonoFicha_rival_horizontal = new NonoFicha();
        nonoFicha_rival_horizontal.setColor(Vocabulario.Color.ROJO);
        nonoFicha_rival_horizontal.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
        NonoFicha nonoFicha_rival_vertical = new NonoFicha();
        nonoFicha_rival_vertical.setColor(Vocabulario.Color.ROJO);
        nonoFicha_rival_vertical.setOrientacion(Vocabulario.Orientacion.VERTICAL);

        JuegoEncerrado j = new JuegoEncerrado();
        NonoTablero tablero = j.nonoTablero;
        Graph g = new Graph(tablero);

        g.tablero.addNewPosition(new NonoPosicion(0, 0), nonoFicha_horizontal);
        g.tablero.addNewPosition(new NonoPosicion(0, 0), nonoFicha_rival_vertical);
        g.tablero.addNewPosition(new NonoPosicion(1, 0), nonoFicha_horizontal);
        g.tablero.addNewPosition(new NonoPosicion(0, 1), nonoFicha_rival_horizontal);
        g.tablero.addNewPosition(new NonoPosicion(1, 1), nonoFicha_horizontal);
        g.tablero.addNewPosition(new NonoPosicion(1, 1), nonoFicha_rival_vertical);
        g.tablero.addNewPosition(new NonoPosicion(2, 1), nonoFicha_horizontal);
        g.tablero.addNewPosition(new NonoPosicion(1, 2), nonoFicha_rival_vertical);
        g.tablero.addNewPosition(new NonoPosicion(0, 2), nonoFicha_vertical);
        g.tablero.addNewPosition(new NonoPosicion(1, 0), nonoFicha_rival_vertical);
        g.tablero.addNewPosition(new NonoPosicion(2, 0), nonoFicha_vertical);
        g.tablero.addNewPosition(new NonoPosicion(3, 0), nonoFicha_rival_horizontal);
        g.tablero.addNewPosition(new NonoPosicion(3, 1), nonoFicha_vertical);
//        g.tablero.addNewPosition(new JuegoEncerrado.NonoPosicion(3, 1), nonoFicha_rival_horizontal);
        g.tablero.addNewPosition(new NonoPosicion(2, 0), nonoFicha_rival_horizontal);
//        g.tablero.addNewPosition(new JuegoEncerrado.NonoPosicion(0, 1), nonoFicha_vertical);

        //TEST
        int depth = 2;
        Node n = new Node();
        n.ficha = new JuegoEncerrado.NonoFicha();
        n.ficha.setColor(Vocabulario.Color.NEGRO);
        n.posicion = new NonoPosicion(0, 1);
        n.tablero_test = (NonoTablero) g.tablero.clone();
        NonoTablero g2 = (NonoTablero) g.tablero.clone();
//        Pair<Integer, Node> p = Game_MiniMax.minimax(g, n, depth, true);
//        int mm_value = p.getKey();
//        Node mm_node = p.getValue();

        // DATA
//        System.out.println("N" + n.toString());
//        System.out.println("Node: " + n.posicion + " - " + n.ficha + " ->   " + mm_value + " : " + mm_node);
//        g.tablero.checkPoints(new JuegoEncerrado.NonoPosicion(0, 0), Vocabulario.Orientacion.VERTICAL);
        g.tablero.addNewPosition(new JuegoEncerrado.NonoPosicion(0, 1), nonoFicha_vertical);
        g.tablero.show();
        
        g2.show();
        System.out.println("G1" + g.tablero.positions);
        System.out.println("G2" + g2.positions);
    }

    public static void main_2(String[] args) {
        JuegoEncerrado j = new JuegoEncerrado();

        JuegoEncerrado.NonoFicha nonoFicha_horizontal = new JuegoEncerrado.NonoFicha();
        nonoFicha_horizontal.setColor(Vocabulario.Color.NEGRO);
        nonoFicha_horizontal.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
        JuegoEncerrado.NonoFicha nonoFicha_vertical = new JuegoEncerrado.NonoFicha();
        nonoFicha_vertical.setColor(Vocabulario.Color.NEGRO);
        nonoFicha_vertical.setOrientacion(Vocabulario.Orientacion.VERTICAL);

        JuegoEncerrado.NonoFicha nonoFicha_rival_horizontal = new JuegoEncerrado.NonoFicha();
        nonoFicha_rival_horizontal.setColor(Vocabulario.Color.ROJO);
        nonoFicha_rival_horizontal.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
        JuegoEncerrado.NonoFicha nonoFicha_rival_vertical = new JuegoEncerrado.NonoFicha();
        nonoFicha_rival_vertical.setColor(Vocabulario.Color.ROJO);
        nonoFicha_rival_vertical.setOrientacion(Vocabulario.Orientacion.VERTICAL);

        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(0, 0), nonoFicha_horizontal);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(0, 0), nonoFicha_rival_vertical);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(1, 0), nonoFicha_horizontal);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(0, 1), nonoFicha_rival_horizontal);

        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(1, 1), nonoFicha_horizontal);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(1, 1), nonoFicha_rival_vertical);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(2, 1), nonoFicha_horizontal);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(1, 2), nonoFicha_rival_vertical);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(0, 2), nonoFicha_vertical);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(1, 0), nonoFicha_rival_vertical);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(2, 0), nonoFicha_vertical);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(3, 0), nonoFicha_rival_horizontal);
        j.nonoTablero.addNewPosition(new JuegoEncerrado.NonoPosicion(3, 1), nonoFicha_vertical);

//        j.nonoTablero.addNewPosition(new NonoPosicion(1, 1), Orientacion.HORIZONTAL);
//        j.nonoTablero.addNewPosition(new NonoPosicion(1, 1), Orientacion.VERTICAL);
        j.nonoTablero.checkPoints(new JuegoEncerrado.NonoPosicion(0, 0), Vocabulario.Orientacion.VERTICAL);
        j.nonoTablero.show();
    }
}
