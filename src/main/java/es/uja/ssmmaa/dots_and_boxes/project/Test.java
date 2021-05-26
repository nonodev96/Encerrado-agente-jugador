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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        NonoFicha nonoFicha_horizontal_rival = new NonoFicha();
        nonoFicha_horizontal_rival.setColor(Vocabulario.Color.ROJO);
        nonoFicha_horizontal_rival.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
        NonoFicha nonoFicha_vertical_rival = new NonoFicha();
        nonoFicha_vertical_rival.setColor(Vocabulario.Color.ROJO);
        nonoFicha_vertical_rival.setOrientacion(Vocabulario.Orientacion.VERTICAL);

        JuegoEncerrado j = new JuegoEncerrado();
        NonoTablero tablero = j.nonoTablero;

        tablero.addNewPosition(new NonoPosicion(0, 0), nonoFicha_horizontal);
//        tablero.addNewPosition(new NonoPosicion(0, 1), nonoFicha_horizontal_rival);
//        tablero.addNewPosition(new NonoPosicion(0, 2), nonoFicha_horizontal);
////        tablero.addNewPosition(new NonoPosicion(0, 1), nonoFicha_vertical_rival);
//        tablero.addNewPosition(new NonoPosicion(0, 3), nonoFicha_horizontal);
//        tablero.addNewPosition(new NonoPosicion(0, 2), nonoFicha_vertical_rival);
//        tablero.addNewPosition(new NonoPosicion(0, 4), nonoFicha_horizontal);
//        tablero.addNewPosition(new NonoPosicion(0, 3), nonoFicha_vertical_rival);
//        tablero.addNewPosition(new NonoPosicion(0, 5), nonoFicha_horizontal);
//        tablero.addNewPosition(new NonoPosicion(0, 4), nonoFicha_vertical_rival);
//        tablero.addNewPosition(new NonoPosicion(0, 6), nonoFicha_horizontal);
//        tablero.addNewPosition(new NonoPosicion(0, 5), nonoFicha_vertical_rival);
//        tablero.addNewPosition(new NonoPosicion(0, 7), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(0, 6), nonoFicha_vertical_rival);
////        tablero.addNewPosition(new NonoPosicion(1, 0), nonoFicha_horizontal);
////        tablero.addNewPosition(new NonoPosicion(1, 1), nonoFicha_vertical_rival);
////        tablero.addNewPosition(new NonoPosicion(2, 0), nonoFicha_horizontal_rival);
////        tablero.addNewPosition(new NonoPosicion(2, 1), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(1, 4), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(2, 4), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(3, 4), nonoFicha_horizontal);
////        tablero.addNewPosition(new NonoPosicion(3, 5), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(3, 3), nonoFicha_horizontal);
//        tablero.addNewPosition(new NonoPosicion(3, 2), nonoFicha_horizontal);
//        tablero.addNewPosition(new NonoPosicion(3, 2), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(2, 2), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(2, 1), nonoFicha_horizontal);
//        tablero.addNewPosition(new NonoPosicion(1, 1), nonoFicha_vertical);
//        tablero.addNewPosition(new NonoPosicion(3, 5), nonoFicha_horizontal);
//        tablero.show();

        //TEST
        int depth = 1;
        int pointsPlayerA = 0;
        int pointsPlayerB = 0;
        Node root = new Node();
        root.tablero_test = (NonoTablero) NonoTablero.clone(tablero);

        // Simulamos una partida de 21 movimientos
        for (int i = 0; i < 10; i++) {

            boolean alternate = i % 2 == 0;

            root.ficha = new JuegoEncerrado.NonoFicha();
            root.ficha.setColor(alternate ? Vocabulario.Color.NEGRO : Vocabulario.Color.ROJO);
            root.posicion = new NonoPosicion(0, 0);
            root.tablero_test = (NonoTablero) NonoTablero.clone(tablero);
//            Pair<Integer, Node> p = Game_MiniMax.minimax(root, alternate ? depth : depth + 1, true);
            Pair<Integer, Node> p = Game_MiniMax.minimax(root, depth, true);
            int mm_value = p.getKey();
            Node mm_node = p.getValue();

            // DATA
//            System.out.println("root" + root.toString());
//            System.out.println("Node:    " + root.posicion + " - " + root.ficha + " ->   ");
            System.out.println("Minimax: " + mm_node.posicion + " - " + mm_node.ficha + " with (" + mm_value + ") points");
            tablero.addNewPosition(mm_node.posicion, mm_node.ficha);
            if (alternate) {
                pointsPlayerA += mm_value;
            } else {
                pointsPlayerB += mm_value;
            }

        }
        System.out.println("PointsPlayerA: " + pointsPlayerA);
        System.out.println("PointsPlayerB: " + pointsPlayerB);
        tablero.show();

    }

    public static void main_0(String[] args) {
        ArrayList<Node> nodes = new ArrayList<>();
        Node node_1 = new Node();
        node_1.posicion = new NonoPosicion(0, 1);
        node_1.ficha = new NonoFicha();
        node_1.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
        nodes.add(node_1);

        Node node_2 = new Node();
        node_2.posicion = new NonoPosicion(0, 1);
        node_2.ficha = new NonoFicha();
        node_2.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
        if (!nodes.contains(node_2)) {
            System.out.println("hello");
        }
    }
}
