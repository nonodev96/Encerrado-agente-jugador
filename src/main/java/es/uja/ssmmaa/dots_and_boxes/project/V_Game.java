/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Color;
import es.uja.ssmmaa.ontologia.encerrado.Ficha;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;

/**
 *
 * @author nono_
 */
public class V_Game {

    private String idJuego;

    public Color color;
    public JuegoEncerrado juego;
    public Vocabulario.TipoJuego tipoJuego;
    public Vocabulario.Modo modo;

    public V_Game() {
        this.juego = new JuegoEncerrado();
        this.juego.nonoTablero = new JuegoEncerrado.NonoTablero(8, 8);
    }

    public V_Game(String idJuego, Jugador jugador, Color color, int size_x, int size_y) {
        this.idJuego = idJuego;
        this.color = color;
        this.juego = new JuegoEncerrado();
        this.juego.nonoTablero = new JuegoEncerrado.NonoTablero(size_x, size_y);
    }

    @Override
    public int hashCode() {
        return idJuego.hashCode();
    }

}
