/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.tareas;

import es.uja.ssmmaa.curso1920.ontologia.Vocabulario;
import es.uja.ssmmaa.curso1920.ontologia.Vocabulario.Modo;
import es.uja.ssmmaa.curso1920.ontologia.juegoTablero.InfoJuego;
import es.uja.ssmmaa.curso1920.ontologia.juegoTablero.Juego;
import es.uja.ssmmaa.curso1920.ontologia.juegoTablero.JuegoAceptado;
import es.uja.ssmmaa.curso1920.ontologia.juegoTablero.Justificacion;
import es.uja.ssmmaa.curso1920.ontologia.juegoTablero.ProponerJuego;
import es.uja.ssmmaa.dots_and_boxes.agentes.AgenteJugador;
import es.uja.ssmmaa.dots_and_boxes.util.GsonUtil;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;

/**
 *
 * @author nonodev96
 */
public class TaskPropose_Jugador extends ProposeResponder {

    private AgenteJugador myAgent_jugador;

    public TaskPropose_Jugador(Agent a, MessageTemplate mt) {
        super(a, mt);
        this.myAgent_jugador = (AgenteJugador) a;
        System.out.println("        --> ProposeResponder(Agent a, MessageTemplate mt)");
    }

    public TaskPropose_Jugador(Agent a, MessageTemplate mt, DataStore store) {
        super(a, mt, store);
        this.myAgent_jugador = (AgenteJugador) a;
        System.out.println("        --> ProposeResponder(Agent a, MessageTemplate mt, DataStore store)");
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage propose) throws NotUnderstoodException, RefuseException {
        System.out.println("        --> prepareResponse");
        ACLMessage reply = propose.createReply();

        String content = propose.getContent();
        System.out.println("content: " + content);

        GsonUtil<ProponerJuego> gson = new GsonUtil<>();

        // ===================================================================
        ProponerJuego propuesta_de_juego = gson.decode(content, ProponerJuego.class);
        InfoJuego info_juego_propuesto = propuesta_de_juego.getInfoJuego();
        Juego juego_propuesto = propuesta_de_juego.getJuego();
        Modo modo_juego_propuesto = propuesta_de_juego.getModo();

        // ===================================================================
        Justificacion justificacion = new Justificacion();
        if (justificacion.getJuego().getTipoJuego() == Vocabulario.TipoJuego.QUATRO) {
            justificacion.setDetalle(Vocabulario.Motivo.TIPO_JUEGO_NO_IMPLEMENTADO);
        }
        if (this.myAgent_jugador.get_size_actives_games() >= 3) {
            justificacion.setDetalle(Vocabulario.Motivo.JUEGOS_ACTIVOS_SUPERADOS);
        }

        // ===================================================================
        Juego juego = new Juego();
        juego.setIdJuego(juego_propuesto.getIdJuego());
        juego.setTipoJuego(juego_propuesto.getTipoJuego());

        JuegoAceptado juego_aceptado = new JuegoAceptado();
        juego_aceptado.setJuego(juego);
        // -- TODO
//        juego_aceptado.setAgenteJuego(agenteJuego);

        // ===================================================================
        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        return reply;
    }

}
