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

        tablero.addNewPosition(new NonoPosicion(0, 0), nonoFicha_horizontal);
        tablero.addNewPosition(new NonoPosicion(0, 0), nonoFicha_rival_vertical);
        tablero.addNewPosition(new NonoPosicion(1, 0), nonoFicha_horizontal);
        tablero.addNewPosition(new NonoPosicion(0, 1), nonoFicha_rival_horizontal);
        tablero.addNewPosition(new NonoPosicion(1, 1), nonoFicha_horizontal);
        tablero.addNewPosition(new NonoPosicion(1, 1), nonoFicha_rival_vertical);
        tablero.addNewPosition(new NonoPosicion(2, 1), nonoFicha_horizontal);
        tablero.addNewPosition(new NonoPosicion(1, 2), nonoFicha_rival_vertical);
        tablero.addNewPosition(new NonoPosicion(0, 2), nonoFicha_vertical);
        tablero.addNewPosition(new NonoPosicion(1, 0), nonoFicha_rival_vertical);
        tablero.addNewPosition(new NonoPosicion(2, 0), nonoFicha_vertical);
        tablero.addNewPosition(new NonoPosicion(3, 0), nonoFicha_rival_horizontal);
//        tablero.addNewPosition(new NonoPosicion(3, 1), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(2, 0), nonoFicha_rival_horizontal);
//        tablero.addNewPosition(new NonoPosicion(0, 1), nonoFicha_vertical);
        tablero.addNewPosition(new NonoPosicion(3, 0), nonoFicha_vertical);
        tablero.addNewPosition(new NonoPosicion(4, 0), nonoFicha_horizontal);
        tablero.addNewPosition(new NonoPosicion(4, 1), nonoFicha_vertical);
        tablero.addNewPosition(new NonoPosicion(2, 1), nonoFicha_vertical);

        //TEST
        int depth = 3;

        Node root = new Node();
        root.ficha = new JuegoEncerrado.NonoFicha();
        root.ficha.setColor(Vocabulario.Color.NEGRO);
        root.posicion = new NonoPosicion(0, 0);
        root.tablero_test = (NonoTablero) NonoTablero.clone(tablero);
        if (true) {
//            Pair<NonoPosicion, NonoFicha> p = Game_MiniMax.theBestIA(g.tablero);
//            System.out.println("P :" + p.getKey());
//            System.out.println("P :" + p.getValue());
            Pair<Integer, Node> p = Game_MiniMax.minimax(root, depth, true);
            int mm_value = p.getKey();
            Node mm_node = p.getValue();

            // DATA
            System.out.println("");
            System.out.println("root" + root.toString());
            System.out.println("Node:    " + root.posicion + " - " + root.ficha + " ->   ");
            System.out.println("Minimax: " + mm_node.posicion + " - " + mm_node.ficha + " with (" + mm_value + ") points");
//            int v = Game_MiniMax.analizeSection(tablero, new NonoPosicion(3, 1), nonoFicha_vertical);
//            System.out.println("V" + v);
        }

        tablero.show();

//        System.out.println("G1" + g.tablero.positions);
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
