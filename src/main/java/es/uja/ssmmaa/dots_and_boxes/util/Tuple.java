/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.util;

import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoFicha;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoPosicion;
import es.uja.ssmmaa.dots_and_boxes.project.Node;
import java.util.Objects;

/**
 *
 * @author nonodev96
 */
public class Tuple {

    public JuegoEncerrado.NonoPosicion posicion;
    public JuegoEncerrado.NonoFicha ficha;

    public Tuple(NonoPosicion first, NonoFicha second) {
        this.posicion = first;
        this.ficha = second;
    }

    // getters and setters
    public Tuple() {
        this.posicion = null;
        this.ficha = null;
    }

    public NonoPosicion getPosicion() {
        return posicion;
    }

    public void setPosicion(NonoPosicion posicion) {
        this.posicion = posicion;
    }

    public NonoFicha getFicha() {
        return ficha;
    }

    public void setFicha(NonoFicha ficha) {
        this.ficha = ficha;
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
        final Tuple other = (Tuple) obj;
        if (Objects.equals(this.posicion.getCoorX(), other.posicion.getCoorX())
                && Objects.equals(this.posicion.getCoorY(), other.posicion.getCoorY())
                && Objects.equals(this.ficha.getOrientacion(), other.ficha.getOrientacion())) {
            return true;
        }
        return false;
    }

}
