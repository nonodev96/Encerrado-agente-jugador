/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.SIZE_TABLERO;
import es.uja.ssmmaa.dots_and_boxes.project.Constantes.NonoOrientacion;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoPosicion;
import es.uja.ssmmaa.dots_and_boxes.util.Tuple;
import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Orientacion;
import es.uja.ssmmaa.ontologia.encerrado.Ficha;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;
import es.uja.ssmmaa.ontologia.juegoTablero.Posicion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author nono_
 */
public class JuegoEncerrado {

    // Debug y pruebas para mantener una partida
    public NonoTablero nonoTablero;

    public JuegoEncerrado() {
        this.nonoTablero = new NonoTablero();
    }

    public static class NonoTablero {

        public HashMap<NonoPosicion, NonoFicha[]> positions;
        public int SIZE_X;
        public int SIZE_Y;

        public NonoTablero() {
            this.positions = new HashMap<>();
            this.SIZE_X = SIZE_TABLERO;
            this.SIZE_Y = SIZE_TABLERO;
        }

        public NonoTablero(int size_x, int size_y) {
            this.positions = new HashMap<>();
            this.SIZE_X = size_x;
            this.SIZE_Y = size_y;
        }

        /**
         * @param pos
         * @param ori
         * @return
         */
        public boolean isPositionValid(NonoPosicion pos, Orientacion ori) {
            // Comprueba si es la primera posicion
            if (pos.getCoorX() < 0 || pos.getCoorY() < 0) {
                return false;
            }
            if (pos.getCoorX() == SIZE_X - 1) {
                if (ori == Orientacion.VERTICAL) {
                    return false;
                }
            }
            // Comprueba la ultima fila
            if (pos.getCoorY() == SIZE_Y - 1) {
                if (ori == Orientacion.HORIZONTAL) {
                    return false;
                }
            }
            if (this.positions.isEmpty()) {
                return true;
            }
            /** <
             * 0. Walls
             *            *
             *            |
             *        * - x - *
             *            |
             *            *
             * >
             */
            int[] checkPositionsX_aux = new int[]{-1, +0, +0};
            int[] checkPositionsY_aux = new int[]{+0, +0, -1};
            int checkPositionLenght_aux = 3;
            int walls = 0;
            for (int iter = 0; iter < checkPositionLenght_aux; iter++) {
                int x = checkPositionsX_aux[iter];
                int y = checkPositionsY_aux[iter];
                if (iter == 0) {
                    if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.VERTICAL)) {
                        walls++;
                    }
                }
                if (iter == 1) {
                    if (ori == Orientacion.HORIZONTAL) {
                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.VERTICAL)) {
                            walls++;
                        }
                    }
                    if (ori == Orientacion.VERTICAL) {
                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.HORIZONTAL)) {
                            walls++;
                        }
                    }
                }
                if (iter == 2) {
                    if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.HORIZONTAL)) {
                        walls++;
                    }
                }
            }
            if (walls > 0) {
                return true;
            }
            /** <
             * 1. TOP
             *          1=|
             *        3 - * - 2
             *            ?
             *            x
             * >
             */
//            if (ori == Orientacion.VERTICAL) {
//                int top_c = 0;
//                int[] checkPositionsX = new int[]{-1, +0, +0};
//                int[] checkPositionsY = new int[]{+0, +0, -1};
//                int checkPositionLenght = 3;
//                for (int iter = 0; iter < checkPositionLenght; iter++) {
//                    int x = checkPositionsX[iter];
//                    int y = checkPositionsY[iter];
//                    if (iter == 0) {
//                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.VERTICAL)) {
//                            top_c++;
//                        }
//                    }
//                    if (iter == 1 || iter == 2) {
//                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.HORIZONTAL)) {
//                            top_c++;
//                        }
//                    }
//                }
//                if (top_c > 0) {
//                    return true;
//                }
//            }
            /** <
             * 2. RIGHT
             *          1=|
             *        x ? * - 2
             *          3=|
             * >
             */
            if (ori == Orientacion.HORIZONTAL) {
                int[] checkPositionsX_2 = new int[]{-1, +0};
                int[] checkPositionsY_2 = new int[]{+1, +1};
                int right_c = 0;
                int checkPositionLenght_2 = 2;
                for (int iter = 0; iter < checkPositionLenght_2; iter++) {
                    int x = checkPositionsX_2[iter];
                    int y = checkPositionsY_2[iter];
                    if (iter == 0 || iter == 1) {
                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.VERTICAL)) {
                            right_c++;
                        }
                    }
                    if (iter == 1) {
                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.HORIZONTAL)) {
                            right_c++;
                        }
                    }
                }
                if (right_c > 0) {
                    return true;
                }
            }
            /** <
             * 3. DOWN
             *            x
             *            ?
             *        3 - * - 1
             *          2=|
             * >
             */
            if (ori == Orientacion.VERTICAL) {
                int[] checkPositionsX_3 = new int[]{+1, +1};
                int[] checkPositionsY_3 = new int[]{+0, -1};
                int checkPositionLenght_3 = 2;
                int down_c = 0;
                for (int iter = 0; iter < checkPositionLenght_3; iter++) {
                    int x = checkPositionsX_3[iter];
                    int y = checkPositionsY_3[iter];
                    if (iter == 0) {
                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.VERTICAL)) {
                            down_c++;
                        }
                    }
                    if (iter == 0 || iter == 1) {
                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.HORIZONTAL)) {
                            down_c++;
                        }
                    }
                }
                if (down_c > 0) {
                    return true;
                }
            }
            /** <
             * 4. LEFT
             *          1=|
             *        3 - * ? x
             *          2=|
             *
             * >
             */
            if (ori == Orientacion.HORIZONTAL) {
//                int[] checkPositionsX_4 = new int[]{+1, +0, +0};
//                int[] checkPositionsY_4 = new int[]{+1, -1, -2};
//                int checkPositionLenght_4 = 3;
//                int left_c = 0;
//                for (int iter = 0; iter < checkPositionLenght_4; iter++) {
//                    int x = checkPositionsX_4[iter];
//                    int y = checkPositionsY_4[iter];
//                    if (iter == 0 || iter == 1) {
//                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.VERTICAL)) {
//                            left_c++;
//                        }
//                    }
//                    if (iter == 2) {
//                        if (this.checkIfExist(new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y), Orientacion.HORIZONTAL)) {
//                            left_c++;
//
//                        }
//                    }
//                }
//                if (left_c > 0) {
//                    return true;
//                }
            }
            return false;
        }

        /** <
         * addNewPosition
         *
         *          * -
         *          |
         * >
         * @param pos
         * @param ficha
         */
        public void addNewPosition(NonoPosicion pos, NonoFicha ficha) {
            if (!isPositionValid(pos, ficha.getOrientacion())) {
                // Comentar y descomentar para hacer debug
                this.show();
                throw new Error("Posicion no válida: " + pos + " " + ficha.getOrientacion());
            }
            if (checkIfExist(pos, ficha.getOrientacion())) {
                this.show();
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
            for (int x = 0; x < SIZE_X; x++) {

                for (int parity = 0; parity < 2; parity++) {
                    if (parity == 0) {
                        System.out.print("|x" + x);
                    } else {
                        System.out.print("|  ");
                    }
                    for (int y = 0; y < SIZE_Y; y++) {
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

        public boolean checkIfExist(NonoPosicion pos, Orientacion ori) {
            NonoFicha[] pos_ori = this.positions.getOrDefault(pos, new NonoFicha[2]);
            if (pos_ori != null) {
                if (pos_ori[NonoOrientacion.HORIZONTAL.ordinal()] != null
                        && pos_ori[NonoOrientacion.HORIZONTAL.ordinal()].getOrientacion() == ori) {
                    return true;
                }
                if (pos_ori[NonoOrientacion.VERTICAL.ordinal()] != null
                        && pos_ori[NonoOrientacion.VERTICAL.ordinal()].getOrientacion() == ori) {
                    return true;
                }
            }

            return false;
        }

        public boolean checkAllWalls(NonoPosicion pos) {
            int[] checkPositionsX = new int[]{+0, +1, +0};
            int[] checkPositionsY = new int[]{+0, +0, +1};
            int checkPositionLenght = 3;
            boolean allWalls = true;
            for (int iter = 0; iter < checkPositionLenght; iter++) {
                int x = checkPositionsX[iter];
                int y = checkPositionsY[iter];
                NonoPosicion test_wall = new NonoPosicion(pos.getCoorX() + x, pos.getCoorY() + y);

                if (iter == 0 || iter == 1) {
                    if (!this.checkIfExist(test_wall, Vocabulario.Orientacion.HORIZONTAL)) {
                        allWalls = false;
                    }
                }
                if (iter == 0 || iter == 2) {
                    if (!this.checkIfExist(test_wall, Vocabulario.Orientacion.VERTICAL)) {
                        allWalls = false;
                    }
                }
            }
            return allWalls;
        }

        public ArrayList<Tuple> childOfNode(Node node, int depth) {
            ArrayList<Tuple> nodes = new ArrayList<>();
            for (int x = 0; x < node.tablero_test.SIZE_X; x++) {
                for (int y = 0; y < node.tablero_test.SIZE_Y; y++) {

                    // 1. Caja arriba derecha
//                Node new_node_1 = new Node();
//                new_node_1.ficha = new JuegoEncerrado.NonoFicha();
//                new_node_1.ficha.setColor(node.ficha.getColor());
//                new_node_1.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
//                new_node_1.posicion = new NonoPosicion(x - 1, y);
//                if (!nodes.contains(new_node_1)) {
//                    if (node.tablero_test.checkPositionValid(new_node_1.posicion, new_node_1.ficha.getOrientacion())) {
//                        new_node_1.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
//                        if (!new_node_1.tablero_test.checkIfExist(new_node_1.posicion, new_node_1.ficha.getOrientacion())) {
//                            new_node_1.tablero_test.addNewPosition(new_node_1.posicion, new_node_1.ficha);
//                            nodes.add(new_node_1);
//                        }
//                    }
//                }
                    // 2. Caja abajo derecha
                    Tuple new_node_2 = new Tuple();
                    new_node_2.posicion = new NonoPosicion(x, y);
                    new_node_2.ficha = new JuegoEncerrado.NonoFicha();
                    new_node_2.ficha.setColor(node.ficha.getColor());
                    new_node_2.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
                    if (this.isPositionValid(new_node_2.posicion, new_node_2.ficha.getOrientacion())) {
                        if (!this.checkIfExist(new_node_2.posicion, new_node_2.ficha.getOrientacion())) {
                            nodes.add(new_node_2);
                        }
                    }

                    // 3. Caja abajo izquierda
                    Tuple new_node_3 = new Tuple();
                    new_node_3.posicion = new NonoPosicion(x, y);
                    new_node_3.ficha = new JuegoEncerrado.NonoFicha();
                    new_node_3.ficha.setColor(node.ficha.getColor());
                    new_node_3.ficha.setOrientacion(Vocabulario.Orientacion.VERTICAL);
                    if (this.isPositionValid(new_node_3.posicion, new_node_3.ficha.getOrientacion())) {
                        if (!this.checkIfExist(new_node_3.posicion, new_node_3.ficha.getOrientacion())) {
                            nodes.add(new_node_3);
                        }
                    }

                    // 4. Caja arriba izquierda
//                Node new_node_4 = new Node();
//                new_node_4.ficha = new JuegoEncerrado.NonoFicha();
//                new_node_4.ficha.setColor(node.ficha.getColor());
//                new_node_4.ficha.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);
//                new_node_4.posicion = new NonoPosicion(x, y - 1);
//                if (!nodes.contains(new_node_4)) {
//                    if (node.tablero_test.checkPositionValid(new_node_4.posicion, new_node_4.ficha.getOrientacion())) {
//                        new_node_4.tablero_test = (NonoTablero) NonoTablero.clone(node.tablero_test);
//                        if (!new_node_4.tablero_test.checkIfExist(new_node_4.posicion, new_node_4.ficha.getOrientacion())) {
//                            new_node_4.tablero_test.addNewPosition(new_node_4.posicion, new_node_4.ficha);
//                            nodes.add(new_node_4);
//                        }
//                    }
//
//                }
                }
            }
            return nodes;
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
    public static class NonoPosicion extends Posicion implements Cloneable {

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
    public static class NonoFicha extends Ficha implements Cloneable {

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

        // Overriding clone() method of Object class
        public Object clone() {
            try {
                return (NonoFicha) super.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(JuegoEncerrado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
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
