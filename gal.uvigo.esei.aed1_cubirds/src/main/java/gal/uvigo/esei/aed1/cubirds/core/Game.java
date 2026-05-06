package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;
import gal.uvigo.esei.aed1.cubirds.iu.IU;

public class Game {

    private final IU iu;
    private DeckOfCards baraja;
    private Table mesa;
    private List<Player> listaJugadores;
    

    public Game(IU iu) {
        this.iu = iu;
        this.baraja = new DeckOfCards(); // creamos la baraja de 110 cartas
        this.mesa=new Table();
        this.listaJugadores = new LinkedList<>();
    }

    private void crearJugadores() {
        int numJugadores;
        //pedir número de jugadores (validación entre 2 y 5)
        do {
            numJugadores = iu.readNumber("Introduce el número de jugadores (2-5): ");
        } while (numJugadores < 2 || numJugadores > 5);
        // 2. Crear los objetos Player con sus nombres
        for (int i = 0; i < numJugadores; i++) {
            String nombre = iu.readString("Nombre del jugador " + (i + 1) + ": ");
            Player nuevo = new Player(nombre, null);
            listaJugadores.addLast(nuevo);
        }
    }

    private void turnoJugador(Player jugador) {
        iu.displayMessage("Turno de " + jugador.getName());
        iu.displayMessage(jugador.toString());
        // Mostrar especies disponibles
        List<TypeBird> especies = jugador.getEspeciesDisponibles();
        for (int i = 0; i < especies.size(); i++) {
            iu.displayMessage(i + ": " + especies.get(i));
        }
        // Elegir especie de la mano
        int opcion;
        do {
            opcion = iu.readNumber("Elige especie (0-" + (especies.size() - 1) + "): ");
        } while (opcion < 0 || opcion >= especies.size());
        TypeBird tipo = especies.get(opcion);
        // Elegir fila
        int fila; 
        do{
            fila = iu.readNumber("Elige fila (1-4): ");
        }while(fila < 1 || fila > 4);
        // Elegir lado
        boolean derecha = iu.readString("¿Derecha? (s/n): ").equalsIgnoreCase("s");
        // Sacar todas las cartas de esa especie de la mano
        int pos=tipo.ordinal();
        List<Card> cartas = jugador.sacarCartasEspecie(pos);
        // Colocar en mesa
        List<Card> capturadas = mesa.colocarCartas(fila-1, cartas, derecha);
        for (Card c : capturadas) {
            jugador.anadirCarta(c);
        }
        iu.displayMessage(mesa.toString());
    }

    private void repartirCartas(DeckOfCards baraja, List<Player> listaJugadores) {
        for (Player juagdor : listaJugadores) {
            for (int i = 0; i < 8; i++) {
                Card carta = baraja.extraerCarta();
                juagdor.anadirCarta(carta);
            }
        }
    }
    
    /**
     * Metodo principal para jugar
     */
    public void play() {
        crearJugadores();
        
        baraja.barajar();
        repartirCartas(baraja, listaJugadores);
        mesa.colocarCartasIniciales(baraja, listaJugadores);
        iu.displayMessage(mesa.toString());
        
        for (Player jugador : listaJugadores) {
            iu.displayMessage(jugador.toString());
        }

        iu.displayMessage("¡Partida preparada! Empieza " + listaJugadores.get(0).getName());

        boolean partidaAcabada=false;
        do{
            for (Player jugador : listaJugadores) {
                turnoJugador(jugador);
                if(jugador.isHandEmpty()){
                    iu.displayMessage("El jugador "+jugador.getName()+" se ha quedado sin cartas...");
                    partidaAcabada=true;
                    break;
                }
            }
        }while(partidaAcabada==false);
    }
}

