/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoTablero;
import java.util.Objects;

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
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.posicion);
        hash = 67 * hash + Objects.hashCode(this.ficha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (Objects.equals(this.posicion.getCoorX(), other.posicion.getCoorX())
                && Objects.equals(this.posicion.getCoorY(), other.posicion.getCoorY())
                && Objects.equals(this.ficha.getOrientacion(), other.ficha.getOrientacion())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return posicion.toString() + " " + ficha.toString();
    }
}
