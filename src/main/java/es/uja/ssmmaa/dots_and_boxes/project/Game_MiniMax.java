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

import java.util.ArrayList;
import java.util.Arrays;
import javafx.util.Pair;

/**
 *
 * @author nono_
 */
public class Game_MiniMax {

    private static final boolean DEBUG = false;
    private static final boolean DEBUG_SHOW = false;

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
        if (DEBUG_SHOW) {
            node.tablero_test.show();
        }
        if (depth == 0 || isTerminalNode(node)) {
            int val = heuristic(node);
            if (Game_MiniMax.DEBUG) {
                System.out.print(String.format("Depth: %-2d", depth));
                System.out.print(String.format("Node %-" + (70) + "s ", node));
                System.out.println(" heuristic: " + val);
            }
            return new Pair(val, node);
        }
        int value = 0;
        Node v_node = node;
        Pair<Integer, Node> p;
        ArrayList<Node> childOfNode = childOfNode(node, depth);
        if (Game_MiniMax.DEBUG) {
            System.out.println("depth: " + depth + " childs: " + childOfNode.size());
            System.out.println(Arrays.toString(childOfNode.toArray()));
            System.out.println(node.tablero_test.show());
        }
        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;
            for (Node child : childOfNode) {
                p = Game_MiniMax.minimax(child, depth - 1, false);
                if (value < p.getKey()) {
                    value = Math.max(value, p.getKey());
                    v_node = p.getValue();
                    v_node.next = new Tuple(child.posicion, child.ficha);
                }
                if (Game_MiniMax.DEBUG) {
                    System.out.println(String.format("MAX depth(%2d) %s %10s %16s", depth, p.getValue(), ">-> old: " + value, "new: " + p.getKey()));
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
                    v_node.next = new Tuple(child.posicion, child.ficha);
                }
                if (Game_MiniMax.DEBUG) {
                    System.out.println(String.format("MIN depth(%2d) %s %10s %16s", depth, p.getValue(), ">-> old: " + value, "new: " + p.getKey()));
                }
            }
            return new Pair(value, v_node);
        }
    }

    /**
     * <pre>
     * De un punto puedes crear hasta 4 nodos hijos
     *
     *    |          1
     *  - * -      4 * 2
     *    |          3 x = default
     * </pre>
     *
     * @param node
     * @param maximizingPlayer
     * @return
     */
    private static ArrayList<Node> childOfNode(Node node, int depth) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int x = 0; x < node.tablero_test.SIZE_X; x++) {
            for (int y = 0; y < node.tablero_test.SIZE_Y; y++) {

                // 1. Caja arriba derecha
//                Node new_node_1 = new Node();
//                new_node_1.ficha = new JuegoEncerrado.NonoFicha();
//                new_node_1.ficha.setColor(node.ficha.getColor());
//                new_node_1.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
//                new_node_1.posicion = new NonoPosicion(x - 1, y);
//                if (!nodes.contains(new_node_1)) {
//                    if (node.tablero_test.checkPositionValid(new_node_1.posicion, new_node_1.ficha.getOrientacion())) {
//                        new_node_1.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
//                        if (!new_node_1.tablero_test.checkIfExist(new_node_1.posicion, new_node_1.ficha.getOrientacion())) {
//                            new_node_1.tablero_test.addNewPosition(new_node_1.posicion, new_node_1.ficha);
//                            nodes.add(new_node_1);
//                        }
//                    }
//                }
                // 2. Caja abajo derecha
                Node new_node_2 = new Node();
                new_node_2.ficha = new JuegoEncerrado.NonoFicha();
                new_node_2.ficha.setColor(node.ficha.getColor());
                new_node_2.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
                new_node_2.posicion = new NonoPosicion(x, y);
                if (!nodes.contains(new_node_2)) {
                    new_node_2.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
                    if (new_node_2.tablero_test.isPositionValid(new_node_2.posicion, new_node_2.ficha.getOrientacion())) {
                        if (!new_node_2.tablero_test.checkIfExist(new_node_2.posicion, new_node_2.ficha.getOrientacion())) {
                            new_node_2.tablero_test.addNewPosition(new_node_2.posicion, new_node_2.ficha);
                            new_node_2.depth = depth;
                            nodes.add(new_node_2);
                        }
                    }
                }

                // 3. Caja abajo izquierda
                Node new_node_3 = new Node();
                new_node_3.ficha = new JuegoEncerrado.NonoFicha();
                new_node_3.ficha.setColor(node.ficha.getColor());
                new_node_3.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
                new_node_3.posicion = new NonoPosicion(x, y);
                if (!nodes.contains(new_node_3)) {
                    new_node_3.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
                    if (new_node_3.tablero_test.isPositionValid(new_node_3.posicion, new_node_3.ficha.getOrientacion())) {
                        if (!new_node_3.tablero_test.checkIfExist(new_node_3.posicion, new_node_3.ficha.getOrientacion())) {
                            new_node_3.tablero_test.addNewPosition(new_node_3.posicion, new_node_3.ficha);
                            new_node_3.depth = depth;
                            nodes.add(new_node_3);
                        }
                    }
                }
                // 4. Caja arriba izquierda
//                Node new_node_4 = new Node();
//                new_node_4.ficha = new JuegoEncerrado.NonoFicha();
//                new_node_4.ficha.setColor(node.ficha.getColor());
//                new_node_4.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
//                new_node_4.posicion = new NonoPosicion(x, y - 1);
//                if (!nodes.contains(new_node_4)) {
//                    if (node.tablero_test.checkPositionValid(new_node_4.posicion, new_node_4.ficha.getOrientacion())) {
//                        new_node_4.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
//                        if (!new_node_4.tablero_test.checkIfExist(new_node_4.posicion, new_node_4.ficha.getOrientacion())) {
//                            new_node_4.tablero_test.addNewPosition(new_node_4.posicion, new_node_4.ficha);
//                            nodes.add(new_node_4);
//                        }
//                    }
//
//                }
            }
        }
        return nodes;
    }

    /**
     *
     * @param node
     * @return
     */
    public static int heuristic(Node node) {
        NonoTablero t = (NonoTablero) NonoTablero.clone(node.tablero_test);
        NonoPosicion pos = node.posicion;
        NonoFicha ficha = node.ficha;

        if (!t.isPositionValid(pos, ficha.getOrientacion())) {
            throw new Error("Posición no válida en heuristic" + node.posicion + node.ficha.getOrientacion());
        }
        int value = analizeSection(t, pos, ficha);

        return value;
    }

    public static boolean isTerminalNode(Node node) {
        return !node.tablero_test.isPositionValid(node.posicion, node.ficha.getOrientacion());
        //&& !node.tablero_test.checkIfExist(node.posicion, node.ficha.getOrientacion());
    }

    /**
     * <pre>
     *  Horizontal        Vertical
     *      | 1               |
     *    - * -             - * -
     *      | 2             2 | 1
     * </pre>
     *
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
            //if (copy.isPositionValid(pos, ficha.getOrientacion())/* && !copy.checkIfExist(pos, Vocabulario.Orientacion.VERTICAL)*/) {
//                copy.addNewPosition(pos, ficha);
            // caja
            test = new NonoPosicion(x, y);
            if (copy.checkAllWalls(test)) {
                value++;
            }

            // Izquierda
            test = new NonoPosicion(x, y - 1);
            if (copy.isPositionValid(test, ficha.getOrientacion()) && copy.checkAllWalls(test)) {
                value++;
            }
            //}
        } else if (ficha.orientacion == Vocabulario.Orientacion.HORIZONTAL) {
            // Caja de arriba y abajo
            //if (copy.isPositionValid(pos, ficha.getOrientacion()) /*&& !copy.checkIfExist(pos, Vocabulario.Orientacion.HORIZONTAL)*/) {
//                copy.addNewPosition(pos, ficha);

            test = new NonoPosicion(x, y);
            // caja
            if (copy.checkAllWalls(test)) {
                value++;
            }

            // arriba
            test = new NonoPosicion(x - 1, y);
            if (copy.isPositionValid(test, ficha.getOrientacion()) && copy.checkAllWalls(test)) {
                value++;
            }
            //}
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
        for (int x = 0; x < object.SIZE_X; x++) {
            for (int y = 0; y < object.SIZE_Y; y++) {
                int value = 0;
                pos.setCoorX(x);
                pos.setCoorY(y);
                copy = (NonoTablero) NonoTablero.clone(object);

                // Caja de la derecha y la izquierda
                if (copy.isPositionValid(pos, Vocabulario.Orientacion.VERTICAL) && !copy.checkIfExist(pos, Vocabulario.Orientacion.VERTICAL)) {
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
                    if (copy.isPositionValid(test, Vocabulario.Orientacion.VERTICAL) && copy.checkAllWalls(test)) {
                        value++;
                        posicion = new NonoPosicion(pos.getCoorX(), pos.getCoorY());
                        ficha = (NonoFicha) f.clone();
                    }
                }

                // Caja de arriba y abajo
                if (copy.isPositionValid(pos, Vocabulario.Orientacion.HORIZONTAL) && !copy.checkIfExist(pos, Vocabulario.Orientacion.HORIZONTAL)) {
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
                    if (copy.isPositionValid(test, Vocabulario.Orientacion.HORIZONTAL) && copy.checkAllWalls(test)) {
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
