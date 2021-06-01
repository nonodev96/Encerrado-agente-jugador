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
import static es.uja.ssmmaa.ontologia.Vocabulario.Color.AZUL;
import static es.uja.ssmmaa.ontologia.Vocabulario.Color.BLANCO;
import static es.uja.ssmmaa.ontologia.Vocabulario.Color.NEGRO;
import static es.uja.ssmmaa.ontologia.Vocabulario.Color.ROJO;
import static es.uja.ssmmaa.ontologia.Vocabulario.Color.VERDE;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;
import java.util.HashMap;
import java.util.Map;
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

    private final Jugador jugador;

    // Tipo de juego
    public Vocabulario.TipoJuego tipoJuego;
    // Modo de juego
    public Vocabulario.Modo modo;
    // Control y UI
    public JuegoEncerrado juego;
    public Juego_UI juego_UI;

    // nombre Jugador -> Puntos
    public HashMap<String, Integer> listaJugadores_puntos;
    public HashMap<String, Color> listaJugadores_colores;

    public Juego_Jugador() {
        this.idJuego = "ID_JUEGO";

        this.juego = new JuegoEncerrado();
        this.juego.nonoTablero = new JuegoEncerrado.NonoTablero(SIZE_TABLERO, SIZE_TABLERO);
        this.juego_UI = new Juego_UI();
        this.listaJugadores_puntos = new HashMap<>();
        this.listaJugadores_colores = new HashMap<>();
        this.jugador = new Jugador();
    }

    public Juego_Jugador(String idJuego, Jugador jugador, int size_x, int size_y) {
        this.idJuego = idJuego;

        this.juego = new JuegoEncerrado();
        this.juego.nonoTablero = new JuegoEncerrado.NonoTablero(size_x, size_y);
        this.juego_UI = new Juego_UI();
        this.listaJugadores_puntos = new HashMap<>();
        this.listaJugadores_colores = new HashMap<>();
        this.jugador = jugador;
    }

    public Color setColorFromJugador(Jugador jugador, Color color) {
        String name = jugador.getAgenteJugador().getLocalName();

        if (!this.listaJugadores_colores.containsValue(color)) {
            this.listaJugadores_colores.put(name, color);
            return this.listaJugadores_colores.get(name);
        }
        return null;
    }

    public Color getColorFromJugador(Jugador jugador) {
        String name = jugador.getAgenteJugador().getLocalName();
        if (this.listaJugadores_colores.containsKey(name)) {
            return this.listaJugadores_colores.get(name);
        }

        Color[] colors = new Color[]{BLANCO, NEGRO, AZUL, ROJO, VERDE};
        for (Color color_1 : colors) {
            if (!this.listaJugadores_colores.containsValue(color_1)) {
                this.listaJugadores_colores.put(name, color_1);
                return this.listaJugadores_colores.get(name);
            }
        }
        return null;
    }

    /**
     *
     * @param jugador
     * @param points
     * @return Si los puntos del jugador superna el máximo return true
     */
    public boolean addPoints(Jugador jugador, int points) {
        String name = jugador.getAgenteJugador().getLocalName();

        int old_points = this.listaJugadores_puntos.getOrDefault(name, 0);
        int new_points = old_points + points;
        this.listaJugadores_puntos.put(name, new_points);

        int maxPoints = this.juego.nonoTablero.SIZE_X * this.juego.nonoTablero.SIZE_Y;

        int otherPoints = 0;
        for (Map.Entry<String, Integer> entry : this.listaJugadores_puntos.entrySet()) {
            if (!this.jugador.getAgenteJugador().getLocalName().equals(name)) {
                otherPoints += entry.getValue();
            }
        }
        // Si soy yo me declaro ganador
        if (this.jugador.getAgenteJugador().getLocalName().equals(name)) {
            // Si yo supero los puntos de los demás nos declaramos ganador
            return new_points >= ((maxPoints - otherPoints));
        }
        return false;
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

    @Override
    public String toString() {
        return "Juego_Jugador{" + "idJuego=" + idJuego + ", tipoJuego=" + tipoJuego + ", modo=" + modo + '}';
    }

}
