/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import es.uja.ssmmaa.ontologia.encerrado.Ficha;

/**
 *
 * @author nono_
 */
public class V_Game {

    public Ficha ficha;
    public JuegoEncerrado juego;

    public V_Game() {
        this.juego = new JuegoEncerrado();
        this.juego.nonoTablero = new JuegoEncerrado.NonoTablero(8, 8);
    }

}
