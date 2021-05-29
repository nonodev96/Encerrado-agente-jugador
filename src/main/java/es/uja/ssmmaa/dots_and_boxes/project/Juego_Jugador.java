/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import es.uja.ssmmaa.dots_and_boxes.gui.Juego_UI;
import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.SIZE_TABLERO;
import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Color;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;
import java.util.HashMap;
import java.util.Objects;

/**
 * Este es el objeto que representa un juego, ya que un jugador puede tener 3
 * partidas simultaneas
 *
 * @author nono_
 */
public class Juego_Jugador {

    // Para identificar un juego
    private final String idJuego;

    // Color de la ficha de nuestro jugador
    public Color color;
    // Tipo de juego
    public Vocabulario.TipoJuego tipoJuego;
    // Modo de juego
    public Vocabulario.Modo modo;
    // Control y UI
    public JuegoEncerrado juego;
    public Juego_UI juego_UI;
    public int points;

    // nombre Jugador -> Puntos
    public HashMap<String, Integer> listaJugadores_puntos;

    public Juego_Jugador() {
        this.idJuego = "ID_JUEGO";
        this.color = Color.NULO;

        this.juego = new JuegoEncerrado();
        this.juego.nonoTablero = new JuegoEncerrado.NonoTablero(SIZE_TABLERO, SIZE_TABLERO);
        this.juego_UI = new Juego_UI();
        this.points = 0;
        this.listaJugadores_puntos = new HashMap<>();
    }

    public Juego_Jugador(String idJuego, Jugador jugador, Color color, int size_x, int size_y) {
        this.idJuego = idJuego;
        this.color = color;
        this.juego = new JuegoEncerrado();
        this.juego.nonoTablero = new JuegoEncerrado.NonoTablero(size_x, size_y);
        this.juego_UI = new Juego_UI();
        this.points = 0;
        this.listaJugadores_puntos = new HashMap<>();
    }

    @Override
    public int hashCode() {
        return idJuego.hashCode();
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
        final Juego_Jugador other = (Juego_Jugador) obj;
        if (!Objects.equals(this.idJuego, other.idJuego)) {
            return false;
        }
        return true;
    }

}
