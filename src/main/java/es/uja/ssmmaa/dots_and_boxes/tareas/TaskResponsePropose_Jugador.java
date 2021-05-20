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
import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.MAX_PARTIDAS;
import es.uja.ssmmaa.ontologia.juegoTablero.AgenteJuego;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;
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
        this.myAgent_jugador.addMsgConsole("        --> prepareResponse");
        ProponerJuego proponerJuego;
        ACLMessage reply = null;
        try {
            proponerJuego = (ProponerJuego) this.myAgent_jugador.getManager().extractContent(propose);
            reply = response_propuesta_de_juego(propose, proponerJuego);

        } catch (Codec.CodecException | OntologyException ex) {
            this.myAgent_jugador.addMsgConsole("Error al extraer la propuesta de juego en " + this.myAgent_jugador.getLocalName() + " de " + propose.getSender().getLocalName());
        }

        return reply;
    }

    /**
     * TODO
     *
     * @param propose
     * @return
     */
    private ACLMessage response_propuesta_de_juego(ACLMessage propose, ProponerJuego proponerJuego) {
        ACLMessage reply = propose.createReply();
        this.myAgent_jugador.addMsgConsole("content: ");

        // ===================================================================
        ProponerJuego propuesta_de_juego = proponerJuego;
        InfoJuego info_juego_propuesto = propuesta_de_juego.getInfoJuego();
        Juego juego_propuesto = propuesta_de_juego.getJuego();
        Modo modo_juego_propuesto = propuesta_de_juego.getModo();

        int errors = 0;
        Justificacion justificacion = new Justificacion();
        // TODO ...
        if (this.myAgent_jugador.get_size_actives_games() >= MAX_PARTIDAS) {
            justificacion.setDetalle(Vocabulario.Motivo.JUEGOS_ACTIVOS_SUPERADOS);
            errors++;
        }
        if (juego_propuesto.getTipoJuego() != Vocabulario.TipoJuego.ENCERRADO) {
            justificacion.setDetalle(Vocabulario.Motivo.TIPO_JUEGO_NO_IMPLEMENTADO);
            errors++;
        }

        //justificacion.setDetalle(Vocabulario.Motivo.TIPO_JUEGO_NO_IMPLEMENTADO);
        if (errors == 0) {

            reply.setPerformative(ACLMessage.REJECT_PROPOSAL);

            try {
                this.myAgent_jugador.getManager().fillContent(reply, justificacion);
            } catch (Codec.CodecException | OntologyException ex) {
                this.myAgent_jugador.addMsgConsole("Error al meter la justificación de juego rechazado de " + this.myAgent_jugador.getLocalName() + " para " + propose.getSender().getLocalName());
            }
        } else {
            Juego juego = new Juego();
            juego.setIdJuego(juego_propuesto.getIdJuego());
            juego.setTipoJuego(juego_propuesto.getTipoJuego());

            JuegoAceptado juegoAceptado = new JuegoAceptado();
            juegoAceptado.setJuego(juego);
            // TODO? agente juego
            Jugador agenteJuego = new Jugador();
            agenteJuego.setAgenteJugador(this.myAgent_jugador.getAID());
            agenteJuego.setNombre(this.myAgent_jugador.getName());
            juegoAceptado.setAgenteJuego(agenteJuego);

            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

            try {
                this.myAgent_jugador.getManager().fillContent(reply, juegoAceptado);
            } catch (Codec.CodecException | OntologyException ex) {
                this.myAgent_jugador.addMsgConsole("Error al meter la juego aceptado de " + this.myAgent_jugador.getLocalName() + " para " + propose.getSender().getLocalName());
            }
        }

        return reply;
    }
}
