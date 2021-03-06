/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssmmaa.dots_and_boxes.agentes;

import static es.uja.ssmmaa.ontologia.Vocabulario.JUEGOS;
import static es.uja.ssmmaa.ontologia.Vocabulario.TIPOS_SERVICIO;

import es.uja.ssmmaa.ontologia.Vocabulario;
import es.uja.ssmmaa.ontologia.Vocabulario.TipoJuego;
import es.uja.ssmmaa.ontologia.Vocabulario.TipoServicio;
import es.uja.ssmmaa.ontologia.juegoTablero.EstadoPartida;
import es.uja.ssmmaa.ontologia.juegoTablero.Juego;
import es.uja.ssmmaa.ontologia.juegoTablero.Movimiento;
import es.uja.ssmmaa.ontologia.juegoTablero.Partida;
import es.uja.ssmmaa.ontologia.juegoTablero.MovimientoEntregadoLinea;
import es.uja.ssmmaa.ontologia.encerrado.Encerrado;
import es.uja.ssmmaa.ontologia.juegoTablero.Jugador;
import es.uja.ssmmaa.ontologia.juegoTablero.PedirMovimiento;
import es.uja.ssmmaa.ontologia.juegoTablero.Posicion;

import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.TIME_OUT;
import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.MY_GAME;

import es.uja.ssmmaa.dots_and_boxes.gui.ConsolaJFrame;
import es.uja.ssmmaa.dots_and_boxes.project.Game_MiniMax;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado;
import es.uja.ssmmaa.dots_and_boxes.project.Node;
import es.uja.ssmmaa.dots_and_boxes.project.Juego_Jugador;
import es.uja.ssmmaa.dots_and_boxes.tareas.TaskIniciatorSubscription_Jugador;
import es.uja.ssmmaa.dots_and_boxes.tareas.TaskResponsePropose_Jugador;
import es.uja.ssmmaa.dots_and_boxes.tareas.TaskSendPropose_Jugador;
import es.uja.ssmmaa.dots_and_boxes.interfaces.TasksJugadorSubs;
import es.uja.ssmmaa.dots_and_boxes.tareas.TareaSubscripcionDF;
import es.uja.ssmmaa.dots_and_boxes.interfaces.SubscripcionDF;
import static es.uja.ssmmaa.dots_and_boxes.project.Constantes.SIZE_TABLERO;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoFicha;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoPosicion;
import es.uja.ssmmaa.dots_and_boxes.project.JuegoEncerrado.NonoTablero;
import es.uja.ssmmaa.dots_and_boxes.tareas.TaskContractNetResponder_Jugador;
import es.uja.ssmmaa.dots_and_boxes.util.GestorSubscripciones;
import es.uja.ssmmaa.dots_and_boxes.util.Tuple;
import es.uja.ssmmaa.ontologia.encerrado.Ficha;
import es.uja.ssmmaa.ontologia.quatro.Quatro;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.MicroRuntime;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionInitiator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javafx.util.Pair;

/**
 * <
 * <h2>AgenteJugador - Dots and boxes</h2>
 * <pre>
 * Es el agente que adopta el rol de jugador para uno de los juegos disponibles,
 * es decir, solo sabr?? jugar a un ??nico juego. El jugador debe dise??arse para
 * seguir correctamente las reglas del juego y ser?? responsable de mantener un
 * estado correcto del juego, es decir, no tiene permitido hacer trampas. Este
 * agente act??a de forma aut??noma, esto es, no requiere supervisi??n de un
 * usuario humano. Sus tareas principales ser??n:
 *
 * - Aceptar la participaci??n en un juego que le proponga un AgenteMonitor.
 * Al menos debe aceptar jugar 3 juegos simult??neos.
 *
 * - Realizar los turnos de juego que le sean solicitados para una partida
 * perteneciente a uno de sus juegos activos.
 *
 * - Recabar informaci??n del resultado de las partidas que est?? jugando.
 * De esta forma puede decidir si la partida ha concluido o no.
 *
 * Es necesario conocer el n??mero de juegos activos en los que est?? participando
 * el jugador.
 * </pre>
 *
 * @author nono_
 */
public class AgenteJugador extends Agent implements SubscripcionDF, TasksJugadorSubs {

    private final GestorSubscripciones gestor;

    // Para la generaci??n y obtenci??n del contenido de los mensages
    private ContentManager manager;
    // El lenguaje utilizado por el agente para la comunicaci??n es SL 
    private final Codec codec = new SLCodec();
    // Las ontolog??a que utilizar?? el agente
    private Ontology ontology;
    // Agente consola
    private ConsolaJFrame UI_consola;

    // Deber??a ser Map<AID, Arraylist<Product>>, pero a json no le gusta AID como clave :(
    public AID agente_jugador_AID;

    private final Map<String, Deque<AID>> agentesConocidos;
    private final Map<String, TaskIniciatorSubscription_Jugador> subActivas;

    public ArrayList<Juego> list_of_games;
    public LinkedList<String> mensajes;

    private final Map<String, Juego_Jugador> juegosMap;

    public AgenteJugador() {
        this.gestor = new GestorSubscripciones();
        this.agentesConocidos = new HashMap<>();
        this.subActivas = new HashMap<>();

        this.list_of_games = new ArrayList<>();
        this.mensajes = new LinkedList<>();
        this.juegosMap = new HashMap<>();
    }

    @Override
    protected void setup() {
        // Inicializaci??n de las variables del agente

        this.agente_jugador_AID = getAID();
        this.UI_consola = new ConsolaJFrame(this);

        //Registro de la Ontolog??a
        this.manager = new ContentManager();
        try {
            this.ontology = Vocabulario.getOntology(MY_GAME);
            // (ContentManager)
            this.manager = getContentManager();
            this.manager.registerLanguage(this.codec);
            this.manager.registerOntology(this.ontology);
        } catch (BeanOntologyException ex) {
            this.addMsgConsole("Error al registrar la ontolog??a \n" + ex);
            this.doDelete();
        }

        // Registro del agente en las P??ginas Amarrillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(Vocabulario.TipoServicio.JUGADOR.name());
        sd.setName(MY_GAME.name());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        init();
    }

    @Override
    protected void takeDown() {
        // Eliminar registro del agente en las P??ginas Amarillas
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Despedida
        this.addMsgConsole("Finaliza la ejecuci??n del agente: " + this.getName());

        // Liberaci??n de recursos, incluido el GUI
        this.UI_consola.dispose();

        MicroRuntime.stopJADE();
    }

    public void init() {
        for (TipoServicio servicio : TIPOS_SERVICIO) {
            for (TipoJuego juego : JUEGOS) {
                agentesConocidos.put(servicio.name() + juego.name(), new LinkedList<>());
            }
        }

        // Suscripci??n al servicio de p??ginas amarillas
        // Para localiar a los agentes 
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription templateSd = new ServiceDescription();
        templateSd.setType(Vocabulario.TipoServicio.JUGADOR.name());
        templateSd.setName(MY_GAME.name());
        template.addServices(templateSd);
        addBehaviour(new TareaSubscripcionDF(this, template));

        // Plantilla para responder mensajes Request-Response
//        MessageTemplate template_RR = MessageTemplate.and(
//                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
//                MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
//        );
//        addBehaviour(new TaskResponse_Jugador(this, template_RR, this));
        // Plantilla para responder mensajes FIPA_CONTRACT_NET
        MessageTemplate template_CN = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.CFP),
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET)
        );
        addBehaviour(new TaskContractNetResponder_Jugador(this, template_CN));

        // Plantilla para responder mensajes FIPA_PROPOSE
        MessageTemplate template_RP = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE),
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE)
        );
        addBehaviour(new TaskResponsePropose_Jugador(this, template_RP));

    }

    @Override
    public void addAgent(AID agente, TipoJuego juego, Vocabulario.TipoServicio servicio) {
        this.addMsgConsole("=============================================");
        this.addMsgConsole("addAgent  AgentID: " + agente.getLocalName());
        this.addMsgConsole("addAgent    juego: " + juego.name());
        this.addMsgConsole("addAgent Servicio: " + servicio.name());
        this.addMsgConsole("=============================================");
        Deque<AID> lista = this.agentesConocidos.getOrDefault(servicio.name() + juego.name(), new LinkedList<>());

        switch (servicio) {
            case JUGADOR:

                break;
//            case MONITOR:
//                lista.add(agente);
//                break;
            case ORGANIZADOR:

                lista.add(agente);
                break;
            case TABLERO:
                ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
                msg.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
                msg.setSender(this.getAID());
                msg.setLanguage(codec.getName());
                msg.setOntology(ontology.getName());
                // AID Tablero
                msg.addReceiver(agente);
                msg.setReplyByDate(new Date(System.currentTimeMillis() + TIME_OUT));

                TaskIniciatorSubscription_Jugador task = new TaskIniciatorSubscription_Jugador(this, msg);
                addBehaviour(task);

                lista.add(agente);

                break;
        }

        this.agentesConocidos.put(servicio.name() + juego.name(), lista);
    }

    @Override
    public boolean removeAgent(AID agente, TipoJuego juego, Vocabulario.TipoServicio servicio) {
        boolean to_return = false;
        Deque<AID> lista = this.agentesConocidos.getOrDefault(servicio.name() + juego.name(), new LinkedList<>());

        // Seleccionamos el que vamos a borrar
        ArrayList<AID> list_to_delete = new ArrayList<>();
        for (AID aid : lista) {
            if (aid.getLocalName().equals(agente.getLocalName())) {
                list_to_delete.add(aid);

                to_return = true;
            }
        }

        this.cancelSubscription(agente);

        // Actualizamos
        lista.removeAll(list_to_delete);
        this.agentesConocidos.put(servicio.name() + juego.name(), lista);

        return to_return;
    }

    /**
     * ========================================================================
     */
    public int get_size_actives_games() {
        return this.list_of_games.size();
    }

    /*
     * Ontologogias
     * ========================================================================
     */
    @Override
    public Ontology getOntology() {
        return this.ontology;
    }

    @Override
    public ContentManager getManager() {
        return this.manager;
    }

    @Override
    public GestorSubscripciones getGestor() {
        return this.gestor;
    }

    @Override
    public void addSubscription(AID agente, SubscriptionInitiator sub) {
        this.subActivas.put(agente.getLocalName(), (TaskIniciatorSubscription_Jugador) sub);
    }

    @Override
    public void cancelSubscription(AID agente) {
        this.addMsgConsole("cancelSubscription: " + agente.getLocalName());
        TaskIniciatorSubscription_Jugador sub = this.subActivas.remove(agente.getLocalName());

        if (sub != null) {
            sub.cancel(agente, true);
            this.addMsgConsole("SUSBCRIPCI??N CANDELADA PARA\n" + agente);
        }
    }

    @Override
    public void setResultado(AID agenteOrganizador, ContentElement resultado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Juego_Jugador getJuego(String idJuego) {
        return this.juegosMap.get(idJuego);
    }

    public void putJuego(String idJuego, Juego_Jugador game) {
        this.juegosMap.put(idJuego, game);
    }

    /*
     * Logs
     * ========================================================================
     */
    @Override
    public void addMsgConsole(String msg) {
        this.UI_consola.addMensaje(msg);
    }

    /*
     * Subscriptions
     * ========================================================================
     */
    /**
     * <p>
     * status = ACLMessage.REFUSE status = ACLMessage.PROPOSE
     * </p>
     *
     * @param status Representa el ACLMessage status
     * @param partida Representa la partida
     * @param estado Representa el estado de una partida
     */
    private void Inform_EstadoPartida(int status, Partida partida, Vocabulario.Estado estado) {
        this.addMsgConsole("EstadoPartida");
        // Contenido del mensaje representado en la ontolog??a
        EstadoPartida estadoPartida = new EstadoPartida();
        estadoPartida.setPartida(partida);
        estadoPartida.setEstadoPartida(estado);

        // Creamos el mensaje a enviar
        ACLMessage msg = new ACLMessage(status);
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
        msg.setSender(getAID());
        msg.setLanguage(codec.getName());
        msg.setOntology(ontology.getName());
        msg.setReplyByDate(new Date(System.currentTimeMillis() + TIME_OUT));

        try {
            // Completamos en contenido del mensajes
            manager.fillContent(msg, estadoPartida);
        } catch (Codec.CodecException | OntologyException ex) {
            this.addMsgConsole("Error en la construcci??n del mensaje en Proponer Juego \n" + ex);
        }
        TaskSendPropose_Jugador task = new TaskSendPropose_Jugador(this, msg);
        addBehaviour(task);
    }

    /**
     * Este m??todo solo se ejecuta si es el turno del jugador
     *
     * @param pedirMovimiento
     * @return
     */
    public MovimientoEntregadoLinea IA(PedirMovimiento pedirMovimiento) {
        Jugador jugador = pedirMovimiento.getJugadorActivo();
        AID jugadorAID = jugador.getAgenteJugador();
        String nombre = jugador.getNombre();
        boolean todoCorrecto = false;

        Partida partida = pedirMovimiento.getPartida();
        String idPartida = partida.getIdPartida();
        Juego juego = partida.getJuego();
        String idJuego = juego.getIdJuego();
        TipoJuego tipoJuego = juego.getTipoJuego();

        int maxRondas = partida.getMaxRondas();
        int ronda = partida.getRonda();

        // Se recoge el juego para poder modificarlo.
        Juego_Jugador nonoJuegoJugador = this.getJuego(idJuego);
        this.addMsgConsole("Juego_Jugador: " + nonoJuegoJugador.toString());

        // Si eres la primera posici??n
        if (nonoJuegoJugador.juego.nonoTablero.tableroIsEmpty()) {
            MovimientoEntregadoLinea movimientoEntregadoLinea = new MovimientoEntregadoLinea();

            Posicion posicion = new Posicion();
            posicion.setCoorX(0);
            posicion.setCoorY(0);

            Movimiento movimiento = new Movimiento();
            movimiento.setPosicion(posicion);
            movimiento.setFicha(new Ficha(jugador, nonoJuegoJugador.getColorFromJugador(jugador)));

            movimientoEntregadoLinea.setPartida(partida);
            movimientoEntregadoLinea.setMovimiento(movimiento);
            movimientoEntregadoLinea.setOrientacion(Vocabulario.Orientacion.HORIZONTAL);

            return movimientoEntregadoLinea;
        }

        // 
        Node root = new Node();
        
        root.tablero_test = (NonoTablero) NonoTablero.clone(nonoJuegoJugador.juego.nonoTablero);
        root.posicion = new NonoPosicion(0, 0);
        root.ficha = new NonoFicha(jugador, nonoJuegoJugador.getColorFromJugador(jugador), Vocabulario.Orientacion.HORIZONTAL);
        
        ArrayList<Tuple> root_p = root.tablero_test.childOfNode(root, 1);

        if (root_p != null) {
            root.posicion = root_p.get(0).getPosicion();
            root.ficha = root_p.get(0).getFicha();
        }

        Pair<Integer, Node> p = Game_MiniMax.minimax(root, 1, true);
//        Node best = p.getValue();
        Tuple best = p.getValue().next;

        // En caso de movimiento valido
        MovimientoEntregadoLinea movimientoEntregadoLinea = new MovimientoEntregadoLinea();

        Posicion posicion = new Posicion();
        posicion.setCoorX(best.posicion.getCoorX());
        posicion.setCoorY(best.posicion.getCoorY());

        Movimiento movimiento = new Movimiento();

        movimiento.setFicha(new Ficha(jugador, nonoJuegoJugador.getColorFromJugador(jugador)));
        movimiento.setPosicion(posicion);

        movimientoEntregadoLinea.setPartida(partida);
        movimientoEntregadoLinea.setMovimiento(movimiento);
        movimientoEntregadoLinea.setOrientacion(best.ficha.getOrientacion());

        return movimientoEntregadoLinea;
    }

    public void CrearJuego(Juego juegoPropuesto_Juego, Encerrado juegoPropuesto_Encerrado, Vocabulario.Modo juegoPropuesto_Modo) {
        String idJuego = juegoPropuesto_Juego.getIdJuego();
        TipoJuego tipoJuego = juegoPropuesto_Juego.getTipoJuego();
        int numJugadores = juegoPropuesto_Encerrado.getNumJugadores();
        int filas = juegoPropuesto_Encerrado.getFilas();
        int columnas = juegoPropuesto_Encerrado.getColumnas();

        Jugador jugador = new Jugador(this.getAID().getLocalName(), this.getAID());

        Juego_Jugador juego = new Juego_Jugador(idJuego, jugador, filas, columnas);
        juego.tipoJuego = tipoJuego;
        juego.modo = juegoPropuesto_Modo;
        juego.juego_UI.setVisible(true);

        this.putJuego(idJuego, juego);
    }

}
