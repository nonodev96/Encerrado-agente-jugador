/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import es.uja.ssmmaa.ontologia.Vocabulario.TipoJuego;
import java.util.Random;

/**
 *
 * @author pedroj
 */
public interface Constantes {

    public static final Random aleatorio = new Random();
    public static final long ESPERA = 1000; // 1 segundo
    public static final long TIME_OUT = 2000; // 2 segundos;
    public static final int NO_ENCONTRADO = -1;
    public static final int ACEPTAR = 85; // 85% de aceptación para la operación 
    public static final int PRIMERO = 0;
    public static final int SEGUNDO = 1;

    public static final int MAX_PARTIDAS = 3;
    public static final int MAX_JUGADORES_PARTIDA = 2;
    public static final int SIZE_TABLERO = 8;

    public static final TipoJuego MY_GAME = TipoJuego.QUATRO;

    public enum NonoOrientacion {
        HORIZONTAL, VERTICAL;
    }

    public enum Status {
        RET_CANCEL(0), RET_OK(1);

        private int statusValue;

        private Status(int statusValue) {
            this.statusValue = statusValue;
        }
    }

}
