/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.interfaces;

import es.uja.ssmmaa.ontologia.Vocabulario;
import jade.core.AID;

/**
 * Comportamiento necesario para que un agente pueda añadir y eliminar agentes
 * que coincidan con la suscripción establecida en el servicio de páginas
 * amarillas.
 *
 * @author pedroj
 */
public interface SubscripcionDF {

    public void addAgent(AID agente, Vocabulario.TipoJuego juego, Vocabulario.TipoServicio tipoServicio);

    public boolean removeAgent(
            AID agente,
            Vocabulario.TipoJuego juego,
            Vocabulario.TipoServicio tipoServicio
    );
}
