/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.util;

import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.SIZE_TABLERO;
import es.uja.ssmmaa.ontologia.Vocabulario.Color;
import es.uja.ssmmaa.ontologia.juegoTablero.FichaJuego;

/**
 *
 * @author nono_
 */
public class Juego_Jugador {

    public Color colorFicha;
    public FichaJuego fichaJuego;

    public NonoTablero nonoTablero;

    public class NonoTablero {

        int tablero[][] = new int[SIZE_TABLERO][SIZE_TABLERO];

        public NonoTablero() {
            for (int i = 0; i < SIZE_TABLERO; i++) {
                for (int j = 0; j < SIZE_TABLERO; j++) {
                    tablero[i][j] = 0;
                }
            }
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
            for (int i = 0; i < SIZE_TABLERO; i++) {
                for (int j = 0; j < SIZE_TABLERO; j++) {
                    if (tablero[i][j] == 0) {
                        System.out.print(" *");

                    } else {
                        System.out.print("  ");

                    }
                    System.out.print(" |");
                }
                System.out.println("");
            }
            System.out.println("========================================");
        }

    }
}
