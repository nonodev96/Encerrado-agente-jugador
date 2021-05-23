/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoTablero;

/**
 *
 * @author nono_
 */
public class Node {

    JuegoEncerrado.NonoTablero tablero_test;
    JuegoEncerrado.NonoPosicion posicion;
    JuegoEncerrado.NonoFicha ficha;

    public Node() {
//        this.tablero_test = new JuegoEncerrado.NonoTablero();
    }

    @Override
    public String toString() {
        return posicion.toString() + " " + ficha.toString();
    }
}
