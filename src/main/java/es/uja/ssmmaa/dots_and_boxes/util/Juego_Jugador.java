/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.util;

import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.SIZE_TABLERO;
import es.uja.ssmmaa.dots_and_boxes.util.Juego_Jugador.NonoPosicion;
import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Color;
import es.uja.ssmmaa.ontologia.Vocabulario.Orientacion;
import es.uja.ssmmaa.ontologia.juegoTablero.FichaJuego;
import es.uja.ssmmaa.ontologia.juegoTablero.Posicion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author nono_
 */
public class Juego_Jugador {

    public Color colorFicha;
    public FichaJuego fichaJuego;
    public NonoTablero nonoTablero;

    public Juego_Jugador() {
        this.nonoTablero = new NonoTablero();
    }

    public static void main(String[] args) {
        Juego_Jugador j = new Juego_Jugador();
        j.nonoTablero.addNewPosition(new NonoPosicion(0, 1), Orientacion.HORIZONTAL);
        j.nonoTablero.addNewPosition(new NonoPosicion(0, 2), Orientacion.VERTICAL);
        j.nonoTablero.addNewPosition(new NonoPosicion(0, 2), Orientacion.HORIZONTAL);
        j.nonoTablero.show();
    }

    public class NonoTablero {

        public HashMap<NonoPosicion, Vocabulario.Orientacion[]> posiciones;

        public NonoTablero() {
            this.posiciones = new HashMap<>();
        }

        public void addNewPosition(NonoPosicion posicion, Vocabulario.Orientacion orientacion) {
            Orientacion[] o = this.posiciones.getOrDefault(posicion, new Orientacion[2]);
            if (orientacion == Orientacion.HORIZONTAL) {
                o[0] = Orientacion.HORIZONTAL;
            }
            if (orientacion == Orientacion.VERTICAL) {
                o[1] = Orientacion.VERTICAL;
            }
            this.posiciones.put(posicion, o);
        }

        /** < 8 x 8
         * ====================================
         * |   *   * - * - *   *   *   *   *  |
         * |           |                      |
         * |   *   *   *   *   *   *   *   *  |
         * |                                  |
         * |   *   *   *   *   *   *   *   *  |
         * |                                  |
         * |   *   *   *   *   *   *   *   *  |
         * |                                  |
         * |   *   *   *   *   *   *   *   *  |
         * |                                  |
         * |   *   *   *   *   *   *   *   *  |
         * |                                  |
         * |   *   *   *   *   *   *   *   *  |
         * |                                  |
         * |   *   *   *   *   *   *   *   *  |
         * |                                  |
         * ====================================
         *
         * El 0 es la horizontal
         * El 1 es la vertical
         * >
         */
        public void show() {
            System.out.println("====================================");
            for (int x = 0; x < SIZE_TABLERO; x++) {

                for (int parity = 0; parity < 2; parity++) {
                    System.out.print("|  ");
                    for (int y = 0; y < SIZE_TABLERO; y++) {
                        NonoPosicion test = new NonoPosicion(x, y);
                        Orientacion[] o = this.posiciones.getOrDefault(test, new Orientacion[2]);

                        if (parity == 0) {
                            System.out.print(" * ");
                        } else {
                            System.out.print(" ");
                        }
                        if (o[parity] == null) {
                            System.out.print(" ");
                        } else {
                            if (o[parity] == Orientacion.HORIZONTAL && parity == 0) {
                                System.out.print("-");
                            } else if (o[parity] == Orientacion.VERTICAL && parity == 1) {
                                System.out.print("|");
                            }
                        }
                        if (parity == 1) {
                            System.out.print("  ");
                        }

                    }
                    System.out.println("|");
                }
            }
            System.out.println("====================================");
        }

    }

    /**
     * Esta clase es static ya que Posicion lo exige, ademas se necesita los
     * métodos de hashCode y equals para que el HashMap funcione
     */
    public static class NonoPosicion extends Posicion {

        public NonoPosicion(int CordX, int CordY) {
            super(CordX, CordY);
        }

        @Override
        public int hashCode() {
            int hash = 17;
            hash = ((hash + getCoorX()) << 5) - (hash + getCoorX());
            hash = ((hash + getCoorY()) << 5) - (hash + getCoorY());
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
            final NonoPosicion other = (NonoPosicion) obj;
            return true;
        }

    }
}
