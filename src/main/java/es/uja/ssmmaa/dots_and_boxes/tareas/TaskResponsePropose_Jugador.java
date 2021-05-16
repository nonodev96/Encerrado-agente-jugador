/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.tareas;

import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Modo;
import es.uja.ssmmaa.ontologia.juegoTablero.InfoJuego;
import es.uja.ssmmaa.ontologia.juegoTablero.Juego;
import es.uja.ssmmaa.ontologia.juegoTablero.JuegoAceptado;
import es.uja.ssmmaa.ontologia.juegoTablero.Justificacion;
import es.uja.ssmmaa.ontologia.juegoTablero.ProponerJuego;
import es.uja.ssmmaa.dots_and_boxes.agentes.AgenteJugador;
import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;
import jade.content.onto.basic.Action;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nonodev96
 */
public class TaskResponsePropose_Jugador extends ProposeResponder {

    private final AgenteJugador myAgent_jugador;

    public TaskResponsePropose_Jugador(Agent a, MessageTemplate mt) {
        super(a, mt);
        this.myAgent_jugador = (AgenteJugador) a;
        this.myAgent_jugador.addMsgConsole("        --> ProposeResponder(Agent a, MessageTemplate mt)");
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage propose) throws NotUnderstoodException, RefuseException {
        System.out.println("        --> prepareResponse");
        Action a;

        try {
            a = (Action) this.myAgent_jugador.getManager().extractContent(propose);
            Concept c = a.getAction();
            System.out.println("C : " + c.toString());
        } catch (Codec.CodecException | OntologyException ex) {
            Logger.getLogger(TaskResponsePropose_Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }

        ACLMessage reply = response_propuesta_de_juego(propose);

        return reply;
    }

    /**
     * TODO
     *
     * @param propose
     * @return
     */
    private ACLMessage response_propuesta_de_juego(ACLMessage propose) {
        ACLMessage reply = propose.createReply();

        this.myAgent_jugador.addMsgConsole("content: ");

        // ===================================================================
        ProponerJuego propuesta_de_juego = new ProponerJuego();
        InfoJuego info_juego_propuesto = propuesta_de_juego.getInfoJuego();
        Juego juego_propuesto = propuesta_de_juego.getJuego();
        Modo modo_juego_propuesto = propuesta_de_juego.getModo();

        if (false) {
            Justificacion justificacion = new Justificacion();
            // TODO ...
            if (this.myAgent_jugador.get_size_actives_games() >= 3) {
                justificacion.setDetalle(Vocabulario.Motivo.JUEGOS_ACTIVOS_SUPERADOS);
            }
            if (juego_propuesto.getTipoJuego() != Vocabulario.TipoJuego.ENCERRADO) {
                justificacion.setDetalle(Vocabulario.Motivo.TIPO_JUEGO_NO_IMPLEMENTADO);
            }

            reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
        } else {
            Juego juego = new Juego();
            juego.setIdJuego(juego_propuesto.getIdJuego());
            juego.setTipoJuego(juego_propuesto.getTipoJuego());

            JuegoAceptado juego_aceptado = new JuegoAceptado();
            juego_aceptado.setJuego(juego);
            // TODO
//        juego_aceptado.setAgenteJuego(agenteJuego);
            try {
                // TODO revisar -->
                // meter en el mensaje la aceptacion de juego
                this.myAgent_jugador.getManager().fillContent(reply, juego_aceptado);
            } catch (Codec.CodecException | OntologyException ex) {
                Logger.getLogger(TaskResponsePropose_Jugador.class.getName()).log(Level.SEVERE, null, ex);
            }
            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        }
        return reply;
    }
}
