/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoFicha;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoPosicion;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoTablero;
import es.uja.ssmmaa.dots_and_boxes.util.Tuple;
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

        NonoFicha nonoFicha_horizontal_rival = new NonoFicha();
        nonoFicha_horizontal_rival.setColor(Vocabulario.Color.ROJO);
        nonoFicha_horizontal_rival.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
        NonoFicha nonoFicha_vertical_rival = new NonoFicha();
        nonoFicha_vertical_rival.setColor(Vocabulario.Color.ROJO);
        nonoFicha_vertical_rival.setOrientacion(Vocabulario.Orientacion.VERTICAL);

        JuegoEncerrado j = new JuegoEncerrado();
        NonoTablero tablero = j.nonoTablero;

        tablero.addNewPosition(new NonoPosicion(0, 0), nonoFicha_horizontal);
        tablero.addNewPosition(new NonoPosicion(0, 0), nonoFicha_vertical);
        tablero.addNewPosition(new NonoPosicion(0, 1), nonoFicha_vertical);

        //tablero.show();
        //TEST
        int depth = 1;
        int pointsPlayerA = 0;
        int pointsPlayerB = 0;
        Node root = new Node();
        root.depth = 99;
        root.tablero_test = (NonoTablero) NonoTablero.clone(tablero);

        // Simulamos una partida de 21 movimientos
        for (int i = 0; i < 21; i++) {

            boolean alternate = i % 2 == 0;

            root.posicion = new NonoPosicion(0, 0);
            root.ficha = new JuegoEncerrado.NonoFicha();
            root.ficha.setColor(alternate ? Vocabulario.Color.NEGRO : Vocabulario.Color.ROJO);
            root.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
            root.tablero_test = (NonoTablero) NonoTablero.clone(tablero);
            Pair<Integer, Node> p = Game_MiniMax.minimax(root, alternate ? depth : depth + 1, true);
//            Pair<Integer, Node> p = Game_MiniMax.minimax(root, alternate?2:3, true);
            int mm_value = p.getKey();
            Node mm_node = p.getValue();

            System.out.println("Best: " + mm_node);
            //String path = Arrays.toString(mm_node.queue.toArray());
            System.out.println(String.format("Path: %-2d %s", 2, mm_node.next));

            // DATA
            System.out.println("Minimax: " + mm_node.posicion + " - " + mm_node.ficha + " with (" + mm_value + ") points");
            System.out.println("Depth: " + mm_node.depth);

            Tuple last = mm_node.next;
            tablero.addNewPosition(last.posicion, last.ficha);

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

    public static void main_(String[] args) {
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

        tablero.addNewPosition(new NonoPosicion(2, 2), nonoFicha_horizontal);
        tablero.addNewPosition(new NonoPosicion(2, 4), nonoFicha_horizontal);
        tablero.show();
    }
}
