/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.tareas;

import es.uja.ssmmaa.ontologia.juegoTablero.ClasificacionJuego;
import es.uja.ssmmaa.ontologia.juegoTablero.IncidenciaJuego;
import es.uja.ssmmaa.ontologia.juegoTablero.Justificacion;

import es.uja.ssmmaa.dots_and_boxes.agentes.AgenteJugador;
import es.uja.ssmmaa.ontologia.juegoTablero.Juego;

import static jade.lang.acl.ACLMessage.AGREE;
import static jade.lang.acl.ACLMessage.FAILURE;
import static jade.lang.acl.ACLMessage.NOT_UNDERSTOOD;
import static jade.lang.acl.ACLMessage.REFUSE;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;
import jade.util.leap.List;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author nono_
 */
public class TaskIniciatorSubscription_Jugador extends SubscriptionInitiator {

    private final AgenteJugador myAgent_Jugador;

    public TaskIniciatorSubscription_Jugador(Agent a, ACLMessage msg) {
        super(a, msg);
        this.myAgent_Jugador = (AgenteJugador) a;
    }

    @Override
    protected void handleOutOfSequence(ACLMessage msg) {
        // Ha llegado un mensaje fuera de la secuencia del protocolo
        this.myAgent_Jugador.addMsgConsole("ERROR en Informar Juego___________________\n" + msg);
    }

    @Override
    protected void handleAllResponses(Vector responses) {
        ContentManager manager;
        Justificacion justificacion = null;
        Iterator it = responses.iterator();

        if (responses.isEmpty()) {
            this.myAgent_Jugador.addMsgConsole("EL ORGANIZADOR NO RESPONDE A LA SUSCRIPCIÓN");
        }

        while (it.hasNext()) {
            ACLMessage msg = (ACLMessage) it.next();
            AID emisor = msg.getSender();
            manager = this.myAgent_Jugador.getManager();

            if (manager == null) {
                this.myAgent_Jugador.addMsgConsole("NO SE ENTIENDE EL MENSAJE\n" + msg);
                throw new NullPointerException("manager error");
            }
            try {
                justificacion = (Justificacion) manager.extractContent(msg);
                switch (msg.getPerformative()) {
                    case NOT_UNDERSTOOD:
                        this.myAgent_Jugador.addMsgConsole("El agente " + emisor + " no entiende la suscripción\n" + justificacion);
                        break;
                    case REFUSE:
                        myAgent_Jugador.addMsgConsole("El agente " + emisor + " rechaza la suscripción\n" + justificacion);
                        break;
                    case FAILURE:
                        this.myAgent_Jugador.addMsgConsole("El agente " + emisor + " no ha completado la suscripción\n" + justificacion);
                        break;
                    case AGREE:
                        this.myAgent_Jugador.addSubscription(emisor, this);
                        this.myAgent_Jugador.addMsgConsole("El agente " + emisor + " ha aceptado la suscripción\n" + justificacion);
                        break;
                    default:
                        this.myAgent_Jugador.addMsgConsole("El agente " + emisor + " envía un mensaje desconocido\n" + msg);
                }
            } catch (Codec.CodecException | OntologyException ex) {
                this.myAgent_Jugador.addMsgConsole(emisor.getLocalName() + " El contenido del mensaje es incorrecto\n\t" + ex);
            }
        }
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        ContentElement contenido;
        ContentManager manager = this.myAgent_Jugador.getManager();

        try {
            contenido = manager.extractContent(inform);

            if (contenido instanceof ClasificacionJuego) {
                // Finalización correcta del juego
                ClasificacionJuego clasificacion = (ClasificacionJuego) contenido;
                List listPuntuaciones = clasificacion.getListaPuntuacion();
                List listJugadores = clasificacion.getListaJugadores();
                Juego j = clasificacion.getJuego();
                this.myAgent_Jugador.addMsgConsole("CLASIFICACION\n" + clasificacion);
            } else if (contenido instanceof IncidenciaJuego) {
                // El juego no ha finalizado
                IncidenciaJuego incidenciaJuego = (IncidenciaJuego) contenido;
                incidenciaJuego.getDetalle();
                Juego j = incidenciaJuego.getJuego();
                this.myAgent_Jugador.addMsgConsole("INCIDENCIA\n" + incidenciaJuego);
            }
            
            // TODO
            // this.myAgent_Jugador.setResultado(inform.getSender(), contenido);
        } catch (Codec.CodecException | OntologyException ex) {
            this.myAgent_Jugador.addMsgConsole("Error en el formato del mensaje del agente " + inform.getSender().getLocalName());
        }
    }
}
