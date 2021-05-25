/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.SIZE_TABLERO;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoFicha;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoPosicion;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoTablero;
import es.uja.ssmmaa.ontologia.Vocabulario;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author nono_
 */
public class Game_MiniMax {

    private static final boolean DEBUG = false;

    public Game_MiniMax() {
    }

    /**
     * Devuelve la puntuacion y el nodo
     *
     * @param graph
     * @param node
     * @param depth
     * @param maximizingPlayer
     * @return
     */
    public static Pair<Integer, Node> minimax(Node node, int depth, boolean maximizingPlayer) {
//        graph.tablero.show();
        if (Game_MiniMax.DEBUG) {
            System.out.println("depth: " + depth);
        }
        if (depth == 0 /*|| isTerminalNode(node)*/) {
            int val = heuristic(node, maximizingPlayer);
            if (Game_MiniMax.DEBUG) {
//                System.out.print(String.format("%d %" + (depth + 1) + "s ", depth, " "));
//                System.out.print("Node: " + node);
//                System.out.println(" heuristic: " + val);
            }
            return new Pair(val, node);
        }
        int value = 0;
        Node v_node = node;
        Pair<Integer, Node> p;
        ArrayList<Node> childOfNode = childOfNode(node, maximizingPlayer);
        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;
            for (Node child : childOfNode) {
                p = Game_MiniMax.minimax(child, depth - 1, false);
                if (value < p.getKey()) {
                    value = Math.max(value, p.getKey());
                    v_node = p.getValue();
                }
                if (Game_MiniMax.DEBUG) {
                    System.out.println(String.format("MAX depth(%2d) %-69s%10s%16s", depth, p.getValue(), "-> old: " + value, "new: " + p.getKey()));
                }
            }
            return new Pair(value, v_node);
        } else {
            value = Integer.MAX_VALUE;
            for (Node child : childOfNode) {
                p = Game_MiniMax.minimax(child, depth - 1, true);
                if (value > p.getKey()) {
                    value = Math.min(value, p.getKey());
                    v_node = p.getValue();
                }
                if (Game_MiniMax.DEBUG) {
                    System.out.println(String.format("MIN depth(%2d) %-69s%10s%16s", depth, p.getValue(), "-> old: " + value, "new: " + p.getKey()));
                }
            }
            return new Pair(value, v_node);
        }
    }

    private static ArrayList<Node> childOfNode(Node node, boolean maximizingPlayer) {
        ArrayList<Node> nodes = new ArrayList<>();
        /* codigo antiguo, solo construia una parte del arbol
        int[] checkPositionsX = new int[]{+0, +1, -1, +0, +0};
        int[] checkPositionsY = new int[]{+0, +0, +0, -1, +1};
        int checkPositionLenght = 5;

        for (int iter = 0; iter < checkPositionLenght; iter++) {
            int x = checkPositionsX[iter];
            int y = checkPositionsY[iter];
            Node new_node = new Node();
            new_node.ficha = new JuegoEncerrado.NonoFicha();
            new_node.ficha.setColor(maximizingPlayer ? Vocabulario.Color.NEGRO : Vocabulario.Color.ROJO);
            new_node.posicion = new NonoPosicion(node.posicion.getCoorX() + x, node.posicion.getCoorY() + y);

            if (node.tablero_test.isPositionValid(new_node.posicion)) {
                if (!node.tablero_test.checkIfExist(new_node.posicion, Vocabulario.Orientacion.HORIZONTAL)) {
                    new_node.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
                    new_node.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
//                    new_node.tablero_test.addNewPosition(new_node.posicion, new_node.ficha);
                    nodes.add(new_node);
                }
                if (!node.tablero_test.checkIfExist(new_node.posicion, Vocabulario.Orientacion.VERTICAL)) {
                    new_node.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
                    new_node.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
//                    new_node.tablero_test.addNewPosition(new_node.posicion, new_node.ficha);
                    nodes.add(new_node);
                }
            }
        }
         */
        for (int x = 0; x < SIZE_TABLERO; x++) {
            for (int y = 0; y < SIZE_TABLERO; y++) {
                Node new_node_h = new Node();
                new_node_h.ficha = new JuegoEncerrado.NonoFicha();
                new_node_h.ficha.setColor(maximizingPlayer ? Vocabulario.Color.NEGRO : Vocabulario.Color.ROJO);
                new_node_h.posicion = new NonoPosicion(x, y);
                new_node_h.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);

                if (node.tablero_test.isPositionValid(new_node_h.posicion)) {
                    if (!new_node_h.tablero_test.checkIfExist(new_node_h.posicion, Vocabulario.Orientacion.HORIZONTAL)) {
                        new_node_h.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
                        nodes.add(new_node_h);
                    }
                }
                Node new_node_v = new Node();
                new_node_v.ficha = new JuegoEncerrado.NonoFicha();
                new_node_v.ficha.setColor(maximizingPlayer ? Vocabulario.Color.NEGRO : Vocabulario.Color.ROJO);
                new_node_v.posicion = new NonoPosicion(x, y);
                new_node_v.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);

                if (node.tablero_test.isPositionValid(new_node_v.posicion)) {
                    if (!new_node_v.tablero_test.checkIfExist(new_node_v.posicion, Vocabulario.Orientacion.VERTICAL)) {
                        new_node_v.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
                        nodes.add(new_node_v);
                    }
                }
            }
        }
        return nodes;
    }

    /**
     *
     * @param node
     * @return
     */
    public static int heuristic(Node node, boolean maximizingPlayer) {
        NonoTablero t = (NonoTablero) NonoTablero.clone(node.tablero_test);
        NonoPosicion pos = node.posicion;
        NonoFicha ficha = node.ficha;

        if (!t.isPositionValid(pos)) {
            return -10;
        }
        int value = analizeSection(t, pos, ficha);

        return value;
    }

    public static boolean isTerminalNode(Node node) {
        return !node.tablero_test.isPositionValid(node.posicion);
        //&& !graph.tablero.checkIfExist(node.posicion, node.ficha.orientacion);
    }

    public static Pair<NonoPosicion, NonoFicha> theBestIA(NonoTablero object) {
        int bestValue = 0;
        NonoPosicion test;
        NonoPosicion pos = new NonoPosicion(0, 0);

        NonoPosicion posicion = new NonoPosicion(0, 0);
        NonoPosicion posicionToReturn = new NonoPosicion(0, 0);
        NonoFicha fichaToReturn = new NonoFicha();
        NonoFicha ficha = new NonoFicha();
        NonoTablero copy;
        for (int x = 0; x < SIZE_TABLERO; x++) {
            for (int y = 0; y < SIZE_TABLERO; y++) {
                int value = 0;
                pos.setCoorX(x);
                pos.setCoorY(y);
                copy = (NonoTablero) NonoTablero.clone(object);

                // Caja de la derecha y la izquierda
                if (copy.isPositionValid(pos) && !copy.checkIfExist(pos, Vocabulario.Orientacion.VERTICAL)) {
                    NonoFicha f = new NonoFicha();
                    f.setOrientacion(Vocabulario.Orientacion.VERTICAL);
                    copy.addNewPosition(pos, f);

                    test = new NonoPosicion(x, y);
                    // caja
                    if (copy.checkAllWalls(test)) {
                        value++;
                        posicion = new NonoPosicion(pos.getCoorX(), pos.getCoorY());
                        ficha = (NonoFicha) f.clone();
                    }

                    // Izquierda
                    test = new NonoPosicion(x, y - 1);
                    if (copy.isPositionValid(test) && copy.checkAllWalls(test)) {
                        value++;
                        posicion = new NonoPosicion(pos.getCoorX(), pos.getCoorY());
                        ficha = (NonoFicha) f.clone();
                    }
                }

                // Caja de arriba y abajo
                if (copy.isPositionValid(pos) && !copy.checkIfExist(pos, Vocabulario.Orientacion.HORIZONTAL)) {
                    NonoFicha f = new NonoFicha();
                    f.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
                    copy.addNewPosition(pos, f);

                    test = new NonoPosicion(x, y);
                    // caja
                    if (copy.checkAllWalls(test)) {
                        value++;
                        posicion = new NonoPosicion(pos.getCoorX(), pos.getCoorY());
                        ficha = (NonoFicha) f.clone();
                    }

                    // arriba
                    test = new NonoPosicion(x - 1, y);
                    if (copy.isPositionValid(test) && copy.checkAllWalls(test)) {
                        value++;
                        posicion = new NonoPosicion(pos.getCoorX(), pos.getCoorY());
                        ficha = (NonoFicha) f.clone();
                    }
                }

                if (value > bestValue) {
                    bestValue = value;
                    fichaToReturn = new NonoFicha(null, null, ficha.getOrientacion());
                    posicionToReturn = new NonoPosicion(posicion.getCoorX(), posicion.getCoorY());
                }

            }
        }
        return new Pair<>(posicionToReturn, fichaToReturn);
    }

    public static int analizeSection(NonoTablero object, NonoPosicion pos, NonoFicha ficha) {
        int value = 0;
        NonoTablero copy = (NonoTablero) NonoTablero.clone(object);
        int x = pos.getCoorX();
        int y = pos.getCoorY();
        NonoPosicion test;
        if (ficha.orientacion == Vocabulario.Orientacion.VERTICAL) {
            if (copy.isPositionValid(pos) && !copy.checkIfExist(pos, Vocabulario.Orientacion.VERTICAL)) {
                NonoFicha f = new NonoFicha();
                f.setOrientacion(Vocabulario.Orientacion.VERTICAL);
                copy.addNewPosition(pos, f);
                // caja
                test = new NonoPosicion(x, y);
                if (copy.checkAllWalls(test)) {
                    value++;
                }

                // Izquierda
                test = new NonoPosicion(x, y - 1);
                if (copy.isPositionValid(test) && copy.checkAllWalls(test)) {
                    value++;
                }
            }
        } else if (ficha.orientacion == Vocabulario.Orientacion.HORIZONTAL) {
            // Caja de arriba y abajo
            if (copy.isPositionValid(pos) && !copy.checkIfExist(pos, Vocabulario.Orientacion.HORIZONTAL)) {
                NonoFicha f = new NonoFicha();
                f.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
                copy.addNewPosition(pos, f);

                test = new NonoPosicion(x, y);
                // caja
                if (copy.checkAllWalls(test)) {
                    value++;
                }

                // arriba
                test = new NonoPosicion(x - 1, y);
                if (copy.isPositionValid(test) && copy.checkAllWalls(test)) {
                    value++;
                }
            }
        }

        return value;
    }
}
