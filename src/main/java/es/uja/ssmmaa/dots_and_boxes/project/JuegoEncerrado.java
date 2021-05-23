/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.SIZE_TABLERO;
import es.uja.ssmmaa.dots_and_boxes.project.Constantes.NonoOrientacion;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoPosicion;
import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Orientacion;
import es.uja.ssmmaa.ontologia.encerrado.Ficha;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;
import es.uja.ssmmaa.ontologia.juegoTablero.Posicion;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nono_
 */
public class JuegoEncerrado {

    // Debug y pruebas para mantener una partida
    public Ficha ficha;
    public NonoTablero nonoTablero;

    public JuegoEncerrado() {
        this.nonoTablero = new NonoTablero();
    }

    public static class NonoTablero {

        public HashMap<NonoPosicion, NonoFicha[]> positions;

        public NonoTablero() {
            this.positions = new HashMap<>();
        }

        public boolean isPositionValid(NonoPosicion pos) {
            // Comprueba si es la primera posicion
            if (pos.getCoorX() < 0 || pos.getCoorY() < 0) {
                return false;
            }
            if (pos.getCoorX() == 0 && pos.getCoorY() == 0) {
                return true;
            }
            // Comprueba la primera fila
            if (pos.getCoorX() == 0) {
                NonoPosicion test_h = new NonoPosicion(pos.getCoorX(), pos.getCoorY() - 1);
                NonoFicha[] o = this.positions.getOrDefault(test_h, new NonoFicha[2]);
                if (o[NonoOrientacion.HORIZONTAL.ordinal()] != null) {
                    if (o[NonoOrientacion.HORIZONTAL.ordinal()].getOrientacion() == Orientacion.HORIZONTAL) {
                        return true;
                    }
                }
                // Que haya alguno a abajo
                NonoPosicion test_d = new NonoPosicion(pos.getCoorX() + 1, pos.getCoorY());
                NonoFicha[] o_d = this.positions.getOrDefault(test_d, new NonoFicha[2]);
                if (o_d[NonoOrientacion.VERTICAL.ordinal()] != null) {
                    if (o_d[NonoOrientacion.VERTICAL.ordinal()].getOrientacion() == Orientacion.VERTICAL) {
                        return true;
                    }
                }
            }
            // Comprueba la primera columna
            if (pos.getCoorY() == 0) {
                // Que haya alguno a la derecha
                NonoPosicion test_r = new NonoPosicion(pos.getCoorX(), pos.getCoorY() + 1);
                NonoFicha[] o_r = this.positions.getOrDefault(test_r, new NonoFicha[2]);
                if (o_r[NonoOrientacion.HORIZONTAL.ordinal()] != null) {
                    if (o_r[NonoOrientacion.HORIZONTAL.ordinal()].getOrientacion() == Orientacion.HORIZONTAL) {
                        return true;
                    }
                }
                NonoPosicion test_v = new NonoPosicion(pos.getCoorX() - 1, pos.getCoorY());
                NonoFicha[] o = this.positions.getOrDefault(test_v, new NonoFicha[2]);
                if (o[NonoOrientacion.VERTICAL.ordinal()] != null) {
                    if (o[NonoOrientacion.VERTICAL.ordinal()].getOrientacion() == Orientacion.VERTICAL) {
                        return true;
                    }
                }
            }
            // Comprueba las filas y columnas finales.
            if (pos.getCoorX() >= 0 && pos.getCoorX() < SIZE_TABLERO && pos.getCoorY() >= 0 && pos.getCoorY() < SIZE_TABLERO) {
                // Vamos iterando por las posiciones de arriba, abajo, izquierda derecha 
                // Si en alguno hay una ficha devolvemos true
                int[] checkPositionsX = new int[]{+1, -1, +0, +0};
                int[] checkPositionsY = new int[]{+0, +0, -1, +1};
                int checkPositionLenght = 4;
                for (int iter = 0; iter < checkPositionLenght; iter++) {
                    int x = checkPositionsX[iter];
                    int y = checkPositionsY[iter];
                    NonoPosicion test_tdlr = new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y);
                    NonoFicha[] o_tdlr = this.positions.getOrDefault(test_tdlr, new NonoFicha[2]);

                    if (o_tdlr[NonoOrientacion.HORIZONTAL.ordinal()] != null) {
                        if (o_tdlr[NonoOrientacion.HORIZONTAL.ordinal()].getOrientacion() == Orientacion.HORIZONTAL && (iter == 2 || iter == 3)) {
                            return true;
                        }
                    }
                    if (o_tdlr[NonoOrientacion.VERTICAL.ordinal()] != null) {
                        if (o_tdlr[NonoOrientacion.VERTICAL.ordinal()].getOrientacion() == Orientacion.VERTICAL && (iter == 0 || iter == 1)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public void addNewPosition(NonoPosicion pos, NonoFicha ficha) {
            if (!isPositionValid(pos)) {
                // Comentar y descomentar para hacer debug
                throw new Error("Posicion no válida" + pos);
            }
            if (checkIfExist(pos, ficha.getOrientacion())) {
                throw new Error("Ficha ya existia, " + pos + "" + ficha.getOrientacion());
            }
            NonoFicha[] o = this.positions.getOrDefault(pos, new NonoFicha[2]);
            if (ficha.getOrientacion() == Orientacion.HORIZONTAL) {
                o[NonoOrientacion.HORIZONTAL.ordinal()] = new NonoFicha();
                o[NonoOrientacion.HORIZONTAL.ordinal()].setOrientacion(Orientacion.HORIZONTAL);
            }
            if (ficha.getOrientacion() == Orientacion.VERTICAL) {
                o[NonoOrientacion.VERTICAL.ordinal()] = new NonoFicha();
                o[NonoOrientacion.VERTICAL.ordinal()].setOrientacion(Orientacion.VERTICAL);
            }
            this.positions.put(pos, o);

            // TODO
            // Aqui faltaria comprobar si al colocar una ficha si suma un punto
//            if (checkPoints(pos, ficha.getOrientacion())) {
//            }
        }

        /** <
         * Si la orientacion es horizontal compruebas el punto de arriba
         * Si la orientacion es vertical compruebas el punto de la izquierda
         *
         * Comprobamos los limites por arriba y por debajo
         * 11 - 12
         * 21 - 22
         * punto 11 seria el primero donde miramos la horizontal y vertical
         * punto 21 miramos la horizontal
         * punto 12 miramos la vertical
         * Si todos se cumplen sumamos un punto
         * >
         */
        public boolean checkPoints(NonoPosicion pos, Vocabulario.Orientacion ori) {
            int[] checkPositionsX = new int[]{+0, +0, +1};
            int[] checkPositionsY = new int[]{+0, -1, +0};
            int checkPositionLenght = 3;
            boolean allWalls = true;
            for (int iter = 0; iter < checkPositionLenght; iter++) {
                int x = checkPositionsX[iter];
                int y = checkPositionsY[iter];
                NonoPosicion test = new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y);
                NonoFicha[] o = this.positions.getOrDefault(test, new NonoFicha[2]);
//                System.out.println("hello: " + new NonoPosicion(x, y).toString() + " + " + pos.toString() + " = " + test.toString());
                if (o[NonoOrientacion.HORIZONTAL.ordinal()] != null) {
                    if (o[NonoOrientacion.HORIZONTAL.ordinal()].getOrientacion() == null && (iter == 0 || iter == 2)) {
                        allWalls = false;
                    }
                }
                if (o[NonoOrientacion.VERTICAL.ordinal()] != null) {
                    if (o[NonoOrientacion.VERTICAL.ordinal()].getOrientacion() == null && (iter == 0 || iter == 3)) {
                        allWalls = false;
                    }
                }
            }

            return allWalls;
        }

        /** < 8 x 8
         * x===================================
         * |   00- 01- 02  03  04  05  06  07 |
         * |   |   |   |                      |
         * |   10- 11- 12  13  14  15  16  17 |
         * |   |   |   |                      |
         * |   20  21- 22  23  24  25  26  27 |
         * |   |                              |
         * |   30- 31  32  33  34  35  36  37 |
         * |       |                          |
         * |   40  41  42  43  44  45  46  47 |
         * |                                  |
         * |   50  51  52  53  54  55  56  57 |
         * |                                  |
         * |   60  61  62  63  64  65  66  67 |
         * |                                  |
         * |   70  71  72  73  74  75  76  77 |
         * |                                  |
         * ===================================y
         *
         * El 0 es la horizontal
         * El 1 es la vertical
         * >
         */
        public void show() {
            System.out.println("====y0==y1==y2==y3==y4==y5==y6==y7==");
            System.out.println("|                                  |");
            for (int x = 0; x < SIZE_TABLERO; x++) {

                for (int parity = 0; parity < 2; parity++) {
                    if (parity == 0) {
                        System.out.print("|x" + x);
                    } else {
                        System.out.print("|  ");
                    }
                    for (int y = 0; y < SIZE_TABLERO; y++) {
                        NonoPosicion test = new NonoPosicion(x, y);
                        NonoFicha[] o = this.positions.getOrDefault(test, new NonoFicha[2]);

                        if (parity == 0) {
                            System.out.print(" " + x + "" + y);
                        } else {
                            System.out.print(" ");
                        }
                        if (o[parity] == null) {
                            System.out.print(" ");
                        } else {
                            if (o[parity].getOrientacion() == Orientacion.HORIZONTAL && parity == 0) {
                                System.out.print("-");
                            } else if (o[parity].getOrientacion() == Orientacion.VERTICAL && parity == 1) {
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
            System.out.println("====y0==y1==y2==y3==y4==y5==y6==y7==");
        }

        public Posicion theBestIA() {
            for (int x = 0; x < SIZE_TABLERO; x++) {
                for (int y = 0; y < SIZE_TABLERO; y++) {
                    NonoPosicion test = new NonoPosicion(x, y);
                    if (checkPoints(test, Orientacion.HORIZONTAL)) {
                        return test;
                    }
                    if (checkPoints(test, Orientacion.VERTICAL)) {
                        return test;
                    }
                }
            }
            return null;
        }

        public boolean checkIfExist(NonoPosicion pos, Orientacion ori) {
            NonoFicha[] pos_ori = this.positions.getOrDefault(pos, new NonoFicha[2]);
            if (pos_ori != null) {
                if (pos_ori[NonoOrientacion.HORIZONTAL.ordinal()] != null && pos_ori[NonoOrientacion.HORIZONTAL.ordinal()].getOrientacion() == ori) {
                    return true;
                }
                if (pos_ori[NonoOrientacion.VERTICAL.ordinal()] != null && pos_ori[NonoOrientacion.VERTICAL.ordinal()].getOrientacion() == ori) {
                    return true;
                }
            }

            return false;
        }

        public static Object clone(NonoTablero object) {
            NonoTablero copy = new NonoTablero();

            for (Map.Entry<NonoPosicion, NonoFicha[]> entry : object.positions.entrySet()) {
                NonoPosicion key = entry.getKey();
                NonoFicha[] value = entry.getValue();
                NonoFicha[] value_copy = new NonoFicha[2];
                for (NonoOrientacion orientacion : NonoOrientacion.values()) {
                    if (value[orientacion.ordinal()] != null) {
                        value_copy[orientacion.ordinal()] = new NonoFicha();
                        value_copy[orientacion.ordinal()].setColor(value[orientacion.ordinal()].getColor());
                        value_copy[orientacion.ordinal()].setJugador(value[orientacion.ordinal()].getJugador());
                        value_copy[orientacion.ordinal()].setOrientacion(value[orientacion.ordinal()].getOrientacion());
                    }
                }
                copy.positions.put(key, value_copy);
            }
            return copy;
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

    /**
     *
     */
    public static class NonoFicha extends Ficha {

        public Orientacion orientacion;

        public NonoFicha() {
            super();
        }

        public NonoFicha(Jugador jugador, Vocabulario.Color color) {
            super(jugador, color);
            this.orientacion = null;
        }

        public NonoFicha(Jugador jugador, Vocabulario.Color color, Orientacion orientacion) {
            super(jugador, color);
            this.orientacion = orientacion;
        }

        public Orientacion getOrientacion() {
            return orientacion;
        }

        public void setOrientacion(Orientacion orientacion) {
            this.orientacion = orientacion;
        }

        @Override
        public String toString() {
            return "Ficha{"
                    + "color=" + this.getColor() + ", "
                    + "orientacion=" + this.getOrientacion() + ""
                    + "}";
        }

    }
}
