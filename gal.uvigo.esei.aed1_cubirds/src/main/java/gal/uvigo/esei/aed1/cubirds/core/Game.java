package gal.uvigo.esei.aed1.cubirds.core;

import gal.uvigo.esei.aed1.cubirds.iu.IU;
import es.uvigo.esei.aed1.tads.list.List;
import es.uvigo.esei.aed1.tads.list.LinkedList;

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
            listaJugadores.addFirst(nuevo);
        }
    }
    
    /**
     * Metodo principal para jugar
     */
    public void play() {
        crearJugadores();
        
        mesa.repartirCartas(baraja, listaJugadores);
        mesa.colocarCartasIniciales(baraja, listaJugadores);
        baraja.barajar();
        iu.displayMessage(mesa.toString());
        for (Player jugador : listaJugadores) {
            iu.displayMessage(jugador.toString());
        }

        iu.displayMessage("¡Partida preparada! Empieza " + listaJugadores.get(0).getName());
    }
}
