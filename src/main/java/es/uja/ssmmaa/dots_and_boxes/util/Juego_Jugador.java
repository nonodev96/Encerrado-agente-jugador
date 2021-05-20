/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.util;

import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.SIZE_TABLERO;
import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Color;
import es.uja.ssmmaa.ontologia.juegoTablero.FichaJuego;
import es.uja.ssmmaa.ontologia.juegoTablero.Posicion;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author nono_
 */
public class Juego_Jugador {

    public Color colorFicha;
    public FichaJuego fichaJuego;

    public NonoTablero nonoTablero;

    public class NonoTablero {

        ArrayList<Pair<Posicion, Vocabulario.Orientacion>> posiciones;

        public NonoTablero() {
            this.posiciones = new ArrayList<>();
        }

        public void addNewPosition(Posicion posicion, Vocabulario.Orientacion orientacion) {
            this.posiciones.add(new Pair(posicion, orientacion));
        }

        /** <
         * ==============================================
         * |                                            |
         * |    *       *       *       *       *       |
         * |                                            |
         * |    *       *       *       *       *       |
         * |                                            |
         * |    *       *       *       *       *       |
         * |                                            |
         * |    *       *       *       *       *       |
         * |                                            |
         * |    *       *       *       *       *       |
         * |                                            |
         * ==============================================
         * >
         */
        public void show() {
            System.out.println("========================================");
            for (int x = 0; x < SIZE_TABLERO; x++) {
                for (int y = 0; y < SIZE_TABLERO; y++) {
                    System.out.print("\t *");
                    for (Pair<Posicion, Vocabulario.Orientacion> position : posiciones) {
                        if (position.getKey().getCoorX() == x && position.getKey().getCoorY() == y) {
                            if (position.getValue() == Vocabulario.Orientacion.HORIZONTAL) {
                                System.out.print(" - ");

                            } else {
                                System.out.print(" | ");
                            }
                        }
                    }
                    System.out.print(" |");
                }
                System.out.println("");
                System.out.println("");
            }
            System.out.println("========================================");
        }

    }
}
