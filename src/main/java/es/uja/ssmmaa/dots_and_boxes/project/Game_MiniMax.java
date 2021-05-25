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
     * @param node
     * @param depth
     * @param maximizingPlayer
     * @return
     */
    public static Pair<Integer, Node> minimax(Node node, int depth, boolean maximizingPlayer) {
//        graph.tablero.show();

        if (depth == 0 /*|| isTerminalNode(node)*/) {
            int val = heuristic(node, maximizingPlayer);
            if (Game_MiniMax.DEBUG) {
                System.out.print(String.format("%d %" + (depth + 1) + "s ", depth, " "));
                System.out.print("Node: " + node);
                System.out.println(" heuristic: " + val);
            }
            return new Pair(val, node);
        }
        int value = 0;
        Node v_node = node;
        Pair<Integer, Node> p;
        ArrayList<Node> childOfNode = childOfNode(node, maximizingPlayer);
        if (Game_MiniMax.DEBUG) {
            System.out.println("depth: " + depth + " childs: " + childOfNode.size());
        }
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

    /** <
     * De un punto puedes crear hasta 4 nodos hijos
     *
     *    |          1
     *  - * -      4 * 2
     *    |          3 x = default
     * >
     *
     * @param node
     * @param maximizingPlayer
     * @return
     */
    private static ArrayList<Node> childOfNode(Node node, boolean maximizingPlayer) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int x = 0; x < SIZE_TABLERO; x++) {
            for (int y = 0; y < SIZE_TABLERO; y++) {

                // 1. Caja arriba derecha
                Node new_node_1 = new Node();
                new_node_1.ficha = new JuegoEncerrado.NonoFicha();
                new_node_1.ficha.setColor(node.ficha.getColor());
                new_node_1.posicion = new NonoPosicion(x - 1, y);
                if (node.tablero_test.isPositionValid(new_node_1.posicion)) {
                    new_node_1.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
                    if (!new_node_1.tablero_test.checkIfExist(new_node_1.posicion, Vocabulario.Orientacion.VERTICAL)) {
                        new_node_1.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
//                        new_node_1.tablero_test.addNewPosition(new_node_1.posicion, new_node_1.ficha);
                        nodes.add(new_node_1);
                    }
                }

                // 2. Caja abajo derecha
                Node new_node_2 = new Node();
                new_node_2.ficha = new JuegoEncerrado.NonoFicha();
                new_node_1.ficha.setColor(node.ficha.getColor());
                new_node_2.posicion = new NonoPosicion(x, y);
                if (node.tablero_test.isPositionValid(new_node_2.posicion)) {
                    new_node_2.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
                    if (!new_node_2.tablero_test.checkIfExist(new_node_2.posicion, Vocabulario.Orientacion.HORIZONTAL)) {
                        new_node_2.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
//                        new_node_2.tablero_test.addNewPosition(new_node_2.posicion, new_node_2.ficha);
                        nodes.add(new_node_2);
                    }
                }

                // 3. Caja abajo izquierda
                Node new_node_3 = new Node();
                new_node_3.ficha = new JuegoEncerrado.NonoFicha();
                new_node_1.ficha.setColor(node.ficha.getColor());
                new_node_3.posicion = new NonoPosicion(x, y);
                if (node.tablero_test.isPositionValid(new_node_3.posicion)) {
                    new_node_3.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
                    if (!new_node_3.tablero_test.checkIfExist(new_node_3.posicion, Vocabulario.Orientacion.VERTICAL)) {
                        new_node_3.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
//                        new_node_3.tablero_test.addNewPosition(new_node_3.posicion, new_node_3.ficha);
                        nodes.add(new_node_3);
                    }
                }

                // 4. Caja arriba izquierda
                Node new_node_4 = new Node();
                new_node_4.ficha = new JuegoEncerrado.NonoFicha();
                new_node_1.ficha.setColor(node.ficha.getColor());
                new_node_4.posicion = new NonoPosicion(x, y - 1);
                if (node.tablero_test.isPositionValid(new_node_4.posicion)) {
                    new_node_4.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
                    if (!new_node_4.tablero_test.checkIfExist(new_node_4.posicion, Vocabulario.Orientacion.HORIZONTAL)) {
                        new_node_4.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
//                        new_node_4.tablero_test.addNewPosition(new_node_4.posicion, new_node_4.ficha);
                        nodes.add(new_node_4);
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
            throw new Error("Posición no válida en heuristic");
        }
        int value = analizeSection(t, pos, ficha);

        return value;
    }

    public static boolean isTerminalNode(Node node) {
        return !node.tablero_test.isPositionValid(node.posicion) && !node.tablero_test.checkIfExist(node.posicion, node.ficha.orientacion);
    }

    /** <
     *  Horizontal        Vertical
     *      | 1               |
     *    - * -             - * -
     *      | 2             2 | 1
     * >
     * @param object
     * @param pos
     * @param ficha
     * @return
     */
    public static int analizeSection(NonoTablero object, NonoPosicion pos, NonoFicha ficha) {
        int value = 0;
        NonoTablero copy = (NonoTablero) NonoTablero.clone(object);
        int x = pos.getCoorX();
        int y = pos.getCoorY();
        NonoPosicion test;

        if (ficha.orientacion == Vocabulario.Orientacion.VERTICAL) {
            if (copy.isPositionValid(pos) && !copy.checkIfExist(pos, Vocabulario.Orientacion.VERTICAL)) {
                copy.addNewPosition(pos, ficha);
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
                copy.addNewPosition(pos, ficha);

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

    /**
     * Busca en un tablero la mejor posición con la que suma más puntos
     *
     * @param object
     * @return
     */
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
}
