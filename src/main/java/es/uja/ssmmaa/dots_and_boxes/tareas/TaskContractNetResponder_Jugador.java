/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.tareas;

import es.uja.ssmmaa.dots_and_boxes.agentes.AgenteJugador;
import es.uja.ssmmaa.ontologia.juegoTablero.Partida;
import es.uja.ssmmaa.ontologia.juegoTablero.PedirMovimiento;
import jade.content.Concept;
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
        try {
            this.myAgent_Jugador.addMsgConsole("Hemos recibido una propuesta de: " + cfp.getSender().getName());
            Action a = (Action) this.myAgent_Jugador.getManager().extractContent(cfp);

            Concept c = new Concept() {
            };

            a.setAction(c);
            this.myAgent_Jugador.getManager().fillContent(replyPropose, a);
        } catch (Codec.CodecException | OntologyException e) {
            this.myAgent_Jugador.addMsgConsole("ERROR: Un error a ocurrido en handleCfp");
            replyPropose.setPerformative(ACLMessage.NOT_UNDERSTOOD);
            throw new NotUnderstoodException(replyPropose);
        }
        replyPropose.setPerformative(ACLMessage.PROPOSE);
        this.myAgent_Jugador.addMsgConsole("Aceptamos la propuesta de: " + cfp.getSender().getName());
        return replyPropose;
    }

    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        ACLMessage reply = accept.createReply();
        try {

            Action a = (Action) this.myAgent_Jugador.getManager().extractContent(accept);
            PedirMovimiento pedirMovimiento = (PedirMovimiento) a.getAction();
            
            this.myAgent_Jugador.addMsgConsole(
                    "El oponente del agente: "
                    + this.myAgent_Jugador.getLocalName()
                    + " es el jugador: " + pedirMovimiento.getJugadorActivo().getNombre()
                    + " en la partida: " + pedirMovimiento.getPartida().getIdPartida()
            );
            
            reply = processPedirMovimiento(cfp, propose, accept, reply);

            //Informamos que estamos conformes en realizar la acción
            Done d = new Done(pedirMovimiento);
            this.myAgent_Jugador.getManager().fillContent(reply, d);
        } catch (Codec.CodecException | OntologyException e) {
            this.myAgent_Jugador.addMsgConsole("ERROR: " + e.getMessage());
        }
        reply.setPerformative(ACLMessage.INFORM);
        return reply;
    }

    private ACLMessage processPedirMovimiento(ACLMessage cfp, ACLMessage propose, ACLMessage accept, ACLMessage reply) {

        return reply;
    }

}
