/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.tareas;

import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Modo;
import es.uja.ssmmaa.ontologia.juegoTablero.Juego;
import es.uja.ssmmaa.ontologia.juegoTablero.JuegoAceptado;
import es.uja.ssmmaa.ontologia.juegoTablero.Justificacion;
import es.uja.ssmmaa.ontologia.juegoTablero.ProponerJuego;
import es.uja.ssmmaa.dots_and_boxes.agentes.AgenteJugador;
import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.MAX_PARTIDAS;
import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.MY_GAME;
import es.uja.ssmmaa.ontologia.encerrado.Encerrado;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;
import es.uja.ssmmaa.ontologia.quatro.Quatro;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;
import jade.content.onto.basic.Action;

/**
 *
 * @author nonodev96
 */
public class TaskResponsePropose_Jugador extends ProposeResponder {

    private final AgenteJugador myAgent_Jugador;

    public TaskResponsePropose_Jugador(Agent a, MessageTemplate mt) {
        super(a, mt);
        this.myAgent_Jugador = (AgenteJugador) a;
        this.myAgent_Jugador.addMsgConsole("        --> ProposeResponder(Agent a, MessageTemplate mt)");
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage propose) throws NotUnderstoodException, RefuseException {
        this.myAgent_Jugador.addMsgConsole("        --> prepareResponse");
        ACLMessage reply = null;
        try {
            Action action = (Action) this.myAgent_Jugador.getManager().extractContent(propose);
            ProponerJuego proponerJuego = (ProponerJuego) action.getAction();
            // 
            reply = responsePropuestaDeJuego(propose, proponerJuego);

        } catch (Codec.CodecException | OntologyException ex) {
            this.myAgent_Jugador.addMsgConsole("Error al extraer la propuesta de juego en " + this.myAgent_Jugador.getLocalName() + " de " + propose.getSender().getLocalName());
        }

        return reply;
    }

    /**
     * TODO
     *
     * @param propose
     * @return
     */
    private ACLMessage responsePropuestaDeJuego(ACLMessage propose, ProponerJuego proponerJuego) {
        ACLMessage reply = propose.createReply();
        this.myAgent_Jugador.addMsgConsole("content: ");

        // ===================================================================
        ProponerJuego propuesta_de_juego = proponerJuego;
        Juego juegoPropuesto_Juego = propuesta_de_juego.getJuego();
        Encerrado juegoPropuesto = (Encerrado) propuesta_de_juego.getInfoJuego();
//        Quatro juegoPropuesto = (Quatro) propuesta_de_juego.getInfoJuego();
        Modo juegoPropuesto_Modo = propuesta_de_juego.getModo();

        int numJugadores = juegoPropuesto.getNumJugadores();

        int errors = 0;
        Justificacion justificacion = new Justificacion();
        justificacion.setJuego(juegoPropuesto_Juego);

        // TODO ... m??s comprobaciones
        if (numJugadores < 2 || numJugadores >= 6) {
            justificacion.setDetalle(Vocabulario.Motivo.TIPO_JUEGO_NO_IMPLEMENTADO);
            errors++;
        }
        if (juegoPropuesto_Modo == Modo.TORNEO) {
            justificacion.setDetalle(Vocabulario.Motivo.TIPO_JUEGO_NO_IMPLEMENTADO);
            errors++;
        }
        if (this.myAgent_Jugador.get_size_actives_games() >= MAX_PARTIDAS) {
            justificacion.setDetalle(Vocabulario.Motivo.JUEGOS_ACTIVOS_SUPERADOS);
            errors++;
        }
        if (juegoPropuesto_Juego.getTipoJuego() != MY_GAME) {
            justificacion.setDetalle(Vocabulario.Motivo.TIPO_JUEGO_NO_IMPLEMENTADO);
            errors++;
        }

        this.myAgent_Jugador.addMsgConsole("Log PropuestaJuego: " + propuesta_de_juego.toString());
        if (errors != 0) {

            reply.setPerformative(ACLMessage.REJECT_PROPOSAL);

            try {
                this.myAgent_Jugador.getManager().fillContent(reply, justificacion);
            } catch (Codec.CodecException | OntologyException ex) {
                this.myAgent_Jugador.addMsgConsole("Error al meter la justificaci??n de juego rechazado de " + this.myAgent_Jugador.getLocalName() + " para " + propose.getSender().getLocalName());
            }
        } else {
            Juego juego = new Juego();
            juego.setIdJuego(juegoPropuesto_Juego.getIdJuego());
            juego.setTipoJuego(juegoPropuesto_Juego.getTipoJuego());

            JuegoAceptado juegoAceptado = new JuegoAceptado();
            juegoAceptado.setJuego(juego);
            // TODO? agente juego
            Jugador agenteJuego = new Jugador();
            agenteJuego.setAgenteJugador(this.myAgent_Jugador.getAID());
            agenteJuego.setNombre(this.myAgent_Jugador.getName());
            juegoAceptado.setAgenteJuego(agenteJuego);

            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

            try {
                this.myAgent_Jugador.getManager().fillContent(reply, juegoAceptado);
            } catch (Codec.CodecException | OntologyException ex) {
                this.myAgent_Jugador.addMsgConsole("Error al meter la juego aceptado de " + this.myAgent_Jugador.getLocalName() + " para " + propose.getSender().getLocalName());
            }

            // Creamos el juego
            this.myAgent_Jugador.CrearJuego(juegoPropuesto_Juego, juegoPropuesto, juegoPropuesto_Modo);
        }

        return reply;
    }
}
