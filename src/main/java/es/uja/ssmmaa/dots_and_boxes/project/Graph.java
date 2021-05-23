/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.project;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nono_
 */
public class Graph implements Cloneable {

    JuegoEncerrado.NonoTablero tablero;

    public Graph(JuegoEncerrado.NonoTablero tablero) {
        this.tablero = tablero;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone(); //To change body of generated methods, choose Tools | Templates.
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
