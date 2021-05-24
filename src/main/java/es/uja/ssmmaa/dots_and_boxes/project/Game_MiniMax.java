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
    public static Pair<Integer, Node> minimax(Graph graph, Node node, int depth, boolean maximizingPlayer) {
//        graph.tablero.show();
        System.out.println("depth: " + depth);
        if (depth == 0 || isTerminalNode(graph, node)) {
            int val = heuristic(graph, node, maximizingPlayer);
            System.out.print(String.format("%d %" + (depth + 1) + "s ", depth, " "));
            System.out.print("Node: " + node);
            System.out.println(" heuristic: " + val);
            graph.tablero.show();
            return new Pair(val, node);
        }
        int value = 0;
        Node v_node = node;
        Pair<Integer, Node> p;
        ArrayList<Node> childOfNode = childOfNode(graph, node, maximizingPlayer);

        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;
            for (Node child : childOfNode) {
                p = Game_MiniMax.minimax(graph, child, depth - 1, false);
                if (value < p.getKey()) {
                    value = Math.max(value, p.getKey());
                    v_node = p.getValue();
                }
                System.out.println("Value: " + value + " " + p.getKey() + " " + p.getValue());
            }
            return new Pair(value, v_node);
        } else {
            value = Integer.MAX_VALUE;
            for (Node child : childOfNode) {
                p = Game_MiniMax.minimax(graph, child, depth - 1, true);
                if (value > p.getKey()) {
                    value = Math.min(value, p.getKey());
                    v_node = p.getValue();
                }
                System.out.println("Value: " + value + " " + p.getKey() + " " + p.getValue());
            }
            return new Pair(value, v_node);
        }
    }

    private static ArrayList<Node> childOfNode(Graph graph, Node node, boolean maximizingPlayer) {
        ArrayList<Node> nodes = new ArrayList<>();
        int[] checkPositionsX = new int[]{+0, +1, -1, +0, +0};
        int[] checkPositionsY = new int[]{+0, +0, +0, -1, +1};
        int checkPositionLenght = 3;

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

        return nodes;
    }

    /**
     *
     * @param node
     * @return
     */
    public static int heuristic(Graph graph, Node node, boolean maximizingPlayer) {
        NonoTablero t = (NonoTablero) NonoTablero.clone(node.tablero_test);
        NonoPosicion pos = node.posicion;
        NonoFicha ficha = node.ficha;
//|| t.checkIfExist(pos, ficha.orientacion)
        if (!t.isPositionValid(pos)) {
            return -10;
        }
        // Esto creo que deberia ir en childOfNode
        // TODO
        t.addNewPosition(pos, ficha);

        int value = 0;
        NonoPosicion test;

        if (ficha.orientacion == Vocabulario.Orientacion.HORIZONTAL) {
            test = new NonoPosicion(pos.getCoorX(), pos.getCoorY());
            if (t.isPositionValid(test) && t.checkAllWalls(test)) {
//                value = maximizingPlayer ? value++ : value--;
                value++;
            }

            test = new NonoPosicion(pos.getCoorX() - 1, pos.getCoorY());
            if (t.isPositionValid(test) && t.checkAllWalls(test)) {
//                value = maximizingPlayer ? value++ : value--;
                value++;
            }
        } else if (ficha.orientacion == Vocabulario.Orientacion.VERTICAL) {
            test = new NonoPosicion(pos.getCoorX(), pos.getCoorY());
            if (t.isPositionValid(test) && t.checkAllWalls(test)) {
//                value = maximizingPlayer ? value++ : value--;
                value++;
            }

            test = new NonoPosicion(pos.getCoorX(), pos.getCoorY() - 1);
            if (t.isPositionValid(test) && t.checkAllWalls(test)) {
//                value = maximizingPlayer ? value++ : value--;
                value++;
            }
        }
        return value;
    }

    public static boolean isTerminalNode(Graph graph, Node node) {
        return !graph.tablero.isPositionValid(node.posicion);
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
        System.out.println("V: " + bestValue);
        return new Pair<>(posicionToReturn, fichaToReturn);
    }
}
