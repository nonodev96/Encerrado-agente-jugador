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
import jade.content.Concept;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
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
        this.myAgent_jugador.addMsgConsola("        --> ProposeResponder(Agent a, MessageTemplate mt)");
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
        String content = propose.getContent();
        this.myAgent_jugador.addMsgConsola("content: " + content);

        GsonUtil<ProponerJuego> gson = new GsonUtil<>();

        // ===================================================================
        ProponerJuego propuesta_de_juego = gson.decode(content, ProponerJuego.class);
        InfoJuego info_juego_propuesto = propuesta_de_juego.getInfoJuego();
        Juego juego_propuesto = propuesta_de_juego.getJuego();
        Modo modo_juego_propuesto = propuesta_de_juego.getModo();

        // ===================================================================
        Justificacion justificacion = new Justificacion();
        if (justificacion.getJuego().getTipoJuego() == Vocabulario.TipoJuego.TRES_EN_RAYA) {
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
        ACLMessage reply = propose.createReply();
        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        reply.setContent(content);

        return reply;
    }
}
