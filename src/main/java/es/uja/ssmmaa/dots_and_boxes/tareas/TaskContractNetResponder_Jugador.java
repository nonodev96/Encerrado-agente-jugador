/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.tareas;

import es.uja.ssmmaa.dots_and_boxes.agentes.AgenteJugador;
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

/**
 *
 * @author nono_
 */
public class TaskContractNetResponder_Jugador extends ContractNetResponder {

    private AgenteJugador myAgent_Jugador;

    public TaskContractNetResponder_Jugador(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        ACLMessage replyPropose = cfp.createReply();

        this.myAgent_Jugador.addMsgConsole("Hemos recibido una propuesta de: " + cfp.getSender().getName());
        Action action = null;
        try {
            action = (Action) this.myAgent_Jugador.getManager().extractContent(cfp);
        } catch (Codec.CodecException | OntologyException ex) {
            this.myAgent_Jugador.addMsgConsole("Error al extraer el contenido en la proposición abierta de: " + cfp.getSender().getName());
            throw new NotUnderstoodException(replyPropose);
        }
        PedirMovimiento pedirMovimiento = (PedirMovimiento) action.getAction();

        // TODO
        MovimientoEntregadoLinea movimientoEntregadoLinea = this.myAgent_Jugador.IA(pedirMovimiento);

        Partida partida = pedirMovimiento.getPartida();

        EstadoPartida estadoPartida = new EstadoPartida();
        estadoPartida.setPartida(partida);

        boolean miTurno = true;

        if (miTurno) {
            replyPropose.setPerformative(ACLMessage.PROPOSE);
            estadoPartida.setEstadoPartida(Vocabulario.Estado.SEGUIR_JUGANDO);

        } else {
            replyPropose.setPerformative(ACLMessage.REFUSE);
            estadoPartida.setEstadoPartida(Vocabulario.Estado.ABANDONO);

        }

        try {
            this.myAgent_Jugador.getManager().fillContent(replyPropose, estadoPartida);
        } catch (Codec.CodecException | OntologyException e) {
            this.myAgent_Jugador.addMsgConsole("ERROR: Un error a ocurrido en handleCfp");
            throw new NotUnderstoodException(replyPropose);
        }
        this.myAgent_Jugador.addMsgConsole("Aceptamos la propuesta de: " + cfp.getSender().getName());
        return replyPropose;
    }

    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        ACLMessage reply = accept.createReply();
        try {

            Action a = (Action) this.myAgent_Jugador.getManager().extractContent(accept);
            MovimientoEntregadoLinea movimientoEntregadoLinea = (MovimientoEntregadoLinea) a.getAction();

            Partida partida = movimientoEntregadoLinea.getPartida();
            Movimiento movimiento = movimientoEntregadoLinea.getMovimiento();

            Ficha ficha = (Ficha) movimiento.getFicha();
            Jugador jugador = ficha.getJugador();

            String idJuego = partida.getJuego().getIdJuego();

            if (!jugador.getNombre().equals(this.myAgent_Jugador.getAID().getLocalName())) {
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
                Juego_Jugador jj = this.myAgent_Jugador.getJuego(idJuego);
                jj.juego.nonoTablero.addNewPosition(nonoPosicion, nonoFicha);
                
                jj.juego_UI.updateJuego(jj.juego);
            }

            this.myAgent_Jugador.addMsgConsole(
                    "El oponente del agente: "
                    + this.myAgent_Jugador.getLocalName()
                    + " orientacion: " + movimientoEntregadoLinea.getOrientacion()
            );

            //Informamos que estamos conformes en realizar la acción
//            Done d = new Done(pedirMovimiento);
//            this.myAgent_Jugador.getManager().fillContent(reply, d);
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
