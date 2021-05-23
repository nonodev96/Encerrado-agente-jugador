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
import java.util.Arrays;
import javafx.util.Pair;

/**
 *
 * @author nono_
 */
public class Game_MiniMax {

    public Game_MiniMax() {
    }

    public static Pair<Integer, Node> minimax(Graph graph, Node node, int depth, boolean maximizingPlayer) {

        if (depth == 0 || isTerminalNode(graph, node)) {
            int val = heuristic(graph, node);
            System.out.print(String.format("%d %" + (depth + 1) + "s ", depth, " "));
            System.out.print(node.posicion);
            System.out.println(" heuristic: " + val);
            return new Pair(val, node);
        }
        int value = 1234;
        Node v_node = null;
        Pair<Integer, Node> p;
        ArrayList<Node> childOfNode = childOfNode(graph, node, maximizingPlayer);
        System.out.println("Size: " + childOfNode.size());
        System.out.println(Arrays.toString(childOfNode.toArray()));
        if (maximizingPlayer) {
            value = -12345;
            for (Node child : childOfNode) {
                p = Game_MiniMax.minimax(graph, child, depth - 1, false);
                value = Math.max(value, p.getKey());
                v_node = p.getValue();
            }
            return new Pair(value, v_node);
        } else {
            value = +12345;
            for (Node child : childOfNode) {
                p = Game_MiniMax.minimax(graph, child, depth - 1, true);
                value = Math.min(value, p.getKey());
                v_node = p.getValue();
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

            if (graph.tablero.isPositionValid(new_node.posicion)) {
                if (!graph.tablero.checkIfExist(new_node.posicion, Vocabulario.Orientacion.HORIZONTAL)) {
                    new_node.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
                    new_node.tablero_test = (NonoTablero) graph.tablero.clone();
//                    new_node.tablero_test.addNewPosition(new_node.posicion, new_node.ficha);
                    nodes.add(new_node);
                }
                if (!graph.tablero.checkIfExist(new_node.posicion, Vocabulario.Orientacion.VERTICAL)) {
                    new_node.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
                    new_node.tablero_test = (NonoTablero) graph.tablero.clone();
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
    public static int heuristic(Graph graph, Node node) {
        NonoTablero t = graph.tablero;
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
//        if (pos.getCoorX() == 0) {
        int[] checkPositionsX = new int[]{+0, +1, +0};
        int[] checkPositionsY = new int[]{+0, +0, +1};
        int checkPositionLenght = 3;
        boolean allWalls = true;
        for (int iter = 0; iter < checkPositionLenght; iter++) {
            int x = checkPositionsX[iter];
            int y = checkPositionsY[iter];
            NonoPosicion test_wall = new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y);

            if (iter == 0 || iter == 1) {
                if (!t.checkIfExist(test_wall, Vocabulario.Orientacion.HORIZONTAL)) {
                    System.out.println(node.posicion + "h");
                    allWalls = false;
                }
            }

            if (iter == 0 || iter == 2) {
                if (!t.checkIfExist(test_wall, Vocabulario.Orientacion.VERTICAL)) {
                    System.out.println(node.posicion + "v");
                    allWalls = false;
                }
            }
        }
        if (allWalls) {
            System.out.println("==============>");
            value++;
        }
//        }

        return value;
    }

    public static boolean isTerminalNode(Graph graph, Node node) {
        return !graph.tablero.isPositionValid(node.posicion);
        //&& !graph.tablero.checkIfExist(node.posicion, node.ficha.orientacion);
    }
}
