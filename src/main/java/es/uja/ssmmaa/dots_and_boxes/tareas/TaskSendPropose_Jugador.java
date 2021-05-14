/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.tareas;

import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.quatro.Ficha;
import es.uja.ssmmaa.ontologia.juegoTablero.CompletarPartida;
import es.uja.ssmmaa.ontologia.juegoTablero.MovimientoEntregadoLinea;
import es.uja.ssmmaa.ontologia.quatro.Ficha;
import es.uja.ssmmaa.ontologia.quatro.FichaEntregada;
import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import static jade.lang.acl.ACLMessage.ACCEPT_PROPOSAL;
import static jade.lang.acl.ACLMessage.REJECT_PROPOSAL;
import jade.proto.ProposeInitiator;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author nono_
 */
public class TaskSendPropose_Jugador extends ProposeInitiator {

    private final TasksJugador agente;
    private final ContentManager manager;

    public TaskSendPropose_Jugador(Agent a, ACLMessage msg) {
        super(a, msg);
        this.agente = (TasksJugador) a;
        this.manager = agente.getManager();
    }

    public TaskSendPropose_Jugador(Agent a, ACLMessage msg, AgentAction agentAction) {
        super(a, msg);
        this.agente = (TasksJugador) a;
        this.manager = agente.getManager();
    }

    @Override
    protected void handleOutOfSequence(ACLMessage msg) {
        System.out.println("ERROR en Proponer Juego_________________\n" + msg);
    }

    @Override
    protected void handleAllResponses(Vector responses) {
        AID agenteJuego = null;
        Iterator it = responses.iterator();

        while (it.hasNext()) {
            ACLMessage msg = (ACLMessage) it.next();

            agenteJuego = msg.getSender();
            try {
                ContentElement respuesta = manager.extractContent(msg);

                switch (msg.getPerformative()) {
                    case ACCEPT_PROPOSAL:
                        // Envio MovimientoEntregadoLinea A Tablero
                        // Trato la respuesta con <MovimientoEntregadoLinea>
                        // =========================
                        if (respuesta instanceof MovimientoEntregadoLinea) {
                            MovimientoEntregadoLinea mel = (MovimientoEntregadoLinea) respuesta;
                            Vocabulario.Orientacion o = mel.getOrientacion();

                            this.agente.addMsgConsole("Orientacion" + o.name());
                        }

                        // Envio EstadoPartida A Tablero
                        // Trato la respuesta con <FichaEntregada>
                        // =========================
                        if (respuesta instanceof FichaEntregada) {
                            FichaEntregada fe = (FichaEntregada) respuesta;
                            Ficha f = fe.getFicha();
                            System.out.println("Ficha" + f.getForma().name());
                        }
                        // Envio EstadoPartida a Tablero
                        // Trato la respuesta con <?>
                        break;
                    case REJECT_PROPOSAL:

                        break;
                    default:
                }
            } catch (Codec.CodecException | OntologyException ex) {
                System.out.println("El contenido del mensaje es incorrecto\n"
                        + agenteJuego.getLocalName() + "\n" + msg + "\n" + ex);
            }
        }
    }

}
