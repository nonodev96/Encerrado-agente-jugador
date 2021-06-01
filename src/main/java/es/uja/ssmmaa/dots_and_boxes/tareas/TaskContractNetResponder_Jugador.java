/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.tareas;

import es.uja.ssmmaa.dots_and_boxes.agentes.AgenteJugador;
import es.uja.ssmmaa.dots_and_boxes.project.Game_MiniMax;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado;
import es.uja.ssmmaa.dots_and_boxes.project.Juego_Jugador;
import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.Color;
import es.uja.ssmmaa.ontologia.Vocabulario.Orientacion;
import es.uja.ssmmaa.ontologia.encerrado.Ficha;
import es.uja.ssmmaa.ontologia.juegoTablero.EstadoPartida;
import es.uja.ssmmaa.ontologia.juegoTablero.FichaJuego;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;
import es.uja.ssmmaa.ontologia.juegoTablero.Movimiento;
import es.uja.ssmmaa.ontologia.juegoTablero.MovimientoEntregadoLinea;
import es.uja.ssmmaa.ontologia.juegoTablero.Partida;
import es.uja.ssmmaa.ontologia.juegoTablero.PedirMovimiento;
import es.uja.ssmmaa.ontologia.juegoTablero.Posicion;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Done;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nono_
 */
public class TaskContractNetResponder_Jugador extends ContractNetResponder {

    private AgenteJugador myAgent_Jugador;

    public TaskContractNetResponder_Jugador(Agent a, MessageTemplate mt) {
        super(a, mt);
        this.myAgent_Jugador = (AgenteJugador) a;
    }

    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        this.myAgent_Jugador.addMsgConsole("Hemos recibido una propuesta de: ");
        this.myAgent_Jugador.addMsgConsole(cfp.getSender().toString());
        ACLMessage replyPropose = cfp.createReply();

        Action action = null;
        try {
            action = (Action) this.myAgent_Jugador.getManager().extractContent(cfp);
        } catch (Codec.CodecException | OntologyException ex) {
            this.myAgent_Jugador.addMsgConsole("Error al extraer el contenido en la proposición abierta de: " + cfp.getSender().getName());
            throw new NotUnderstoodException(replyPropose);
        }
        PedirMovimiento pedirMovimiento = (PedirMovimiento) action.getAction();

        // TODO
        Partida partida = pedirMovimiento.getPartida();
        EstadoPartida estadoPartida = new EstadoPartida();
        estadoPartida.setPartida(partida);

        boolean miTurno = pedirMovimiento.getJugadorActivo().getAgenteJugador().getLocalName().equals(this.myAgent_Jugador.getAID().getLocalName());

        if (miTurno) {
            replyPropose.setPerformative(ACLMessage.PROPOSE);
            estadoPartida.setEstadoPartida(Vocabulario.Estado.SEGUIR_JUGANDO);
            MovimientoEntregadoLinea movimientoEntregadoLinea = this.myAgent_Jugador.IA(pedirMovimiento);

            try {
                this.myAgent_Jugador.getManager().fillContent(replyPropose, movimientoEntregadoLinea);
            } catch (Codec.CodecException | OntologyException ex) {
                this.myAgent_Jugador.addMsgConsole("ERROR: Un error a ocurrido en handleCfp MovimientoEntregadoLinea");
                this.myAgent_Jugador.addMsgConsole(ex.toString());
                throw new NotUnderstoodException(replyPropose);
            }

        } else {
            replyPropose.setPerformative(ACLMessage.PROPOSE);
            estadoPartida.setEstadoPartida(Vocabulario.Estado.SEGUIR_JUGANDO);
            try {
                this.myAgent_Jugador.getManager().fillContent(replyPropose, estadoPartida);
            } catch (Codec.CodecException | OntologyException ex) {
                this.myAgent_Jugador.addMsgConsole("ERROR: Un error a ocurrido en handleCfp EstadoPartida");
                this.myAgent_Jugador.addMsgConsole(ex.toString());
                throw new NotUnderstoodException(replyPropose);
            }
        }

        return replyPropose;
    }

    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        ACLMessage reply = accept.createReply();
        this.myAgent_Jugador.addMsgConsole("Aceptamos la propuesta de: " + accept.getSender().getName());
        try {

            ContentElement a = this.myAgent_Jugador.getManager().extractContent(accept);
            MovimientoEntregadoLinea movimientoEntregadoLinea = (MovimientoEntregadoLinea) a;

            Partida partida = movimientoEntregadoLinea.getPartida();
            Movimiento movimiento = movimientoEntregadoLinea.getMovimiento();

            Ficha ficha = (Ficha) movimiento.getFicha();
            Jugador jugador = ficha.getJugador();

            String idJuego = partida.getJuego().getIdJuego();

            Posicion posicion = movimiento.getPosicion();
            int x = posicion.getCoorX();
            int y = posicion.getCoorY();
            Orientacion orientacion = movimientoEntregadoLinea.getOrientacion();
            Color color = ficha.getColor();

            JuegoEncerrado.NonoPosicion nonoPosicion;
            JuegoEncerrado.NonoFicha nonoFicha;

            nonoPosicion = new JuegoEncerrado.NonoPosicion(x, y);
            nonoFicha = new JuegoEncerrado.NonoFicha();

            nonoFicha.setColor(color);
            nonoFicha.setJugador(jugador);
            nonoFicha.setOrientacion(orientacion);

            // Mi estructura
            // Aqui añadimos el movimiento y lo realizamos
            Juego_Jugador jj = this.myAgent_Jugador.getJuego(idJuego);
            jj.setColorFromJugador(jugador, color);
            jj.juego.nonoTablero.addNewPosition(nonoPosicion, nonoFicha);

            int points = Game_MiniMax.analizeSection(jj.juego.nonoTablero, nonoPosicion, nonoFicha);

//            boolean winner = jj.addPoints(jugador, points);
            boolean winner = jj.juego.nonoTablero.positions.size() > 5;

            jj.juego_UI.updateJuego(jj.juego);

            this.myAgent_Jugador.addMsgConsole(
                    "Soy: " + this.myAgent_Jugador.getLocalName()
                    + " el que mueve es: " + jugador.getAgenteJugador().getLocalName()
                    + " orientacion: " + movimientoEntregadoLinea.getOrientacion()
                    + " Winner " + winner
            );

            EstadoPartida estadoPartida = new EstadoPartida();
            estadoPartida.setPartida(partida);
            if (winner) {
                estadoPartida.setEstadoPartida(Vocabulario.Estado.GANADOR);
            } else {
                estadoPartida.setEstadoPartida(Vocabulario.Estado.SEGUIR_JUGANDO);
            }
            //Informamos que estamos conformes en realizar la acción
            this.myAgent_Jugador.getManager().fillContent(reply, estadoPartida);
        } catch (Codec.CodecException | OntologyException e) {
            this.myAgent_Jugador.addMsgConsole("ERROR: " + e.getMessage());
        }
        reply.setPerformative(ACLMessage.INFORM);
        return reply;
    }

    @Override
    protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
        super.handleRejectProposal(cfp, propose, reject);
        this.myAgent_Jugador.addMsgConsole("Se rechaza la proposición de" + propose.getSender().getLocalName());
    }

}
