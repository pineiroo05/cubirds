package gal.uvigo.esei.aed1.cubirds.core;


import gal.uvigo.esei.aed1.cubirds.iu.IU;
import java.util.List;
import java.util.ArrayList;

public class Game {

    private final IU iu;
    private DeckOfCards baraja;
    private Table mesa;
    private List<Player> listaJugadores;
    

    public Game(IU iu) {
        this.iu = iu;
        this.baraja = crearBaraja(); // creamos la baraja de 110 cartas
        this.listaJugadores = new ArrayList<>();
    }

    /**
     * crea la baraja de pájaros con las 110 cartas definidas en el enumerado card.
     * @return una instancia de DeckOfCards barajada.
     */
    private DeckOfCards crearBaraja() {
        DeckOfCards nuevaBaraja = new DeckOfCards();
        nuevaBaraja.barajar();
        return nuevaBaraja;
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
            listaJugadores.add(nuevo);
        }

        
        for (int i = 0; i < listaJugadores.size(); i++) {
            Player actual = listaJugadores.get(i);
            
            if (i == listaJugadores.size() - 1) {
                actual.setNext(listaJugadores.get(0)); // El último apunta al primero
            } else {
                actual.setNext(actual.getNext());
            }
        }
    }
    private void crearMesa() {
        this.mesa = new Table();
        iu.displayMessage("Tablero preparado: 4 filas creadas y vacías.");
    }

    /**
     * prepara el estado inicial del juego:
     * baraja el mazo
     * reparte 8 cartas a cada jugador
     * coloca 3 cartas en cada una de las 4 filas de la mesa
     */
    public void prepararPartida() {
        //no barajo porque se barajó al crear la bajara

        // repartir 8 cartas a cada jugador
        for (Player jugador : listaJugadores) {
            for (int i = 0; i < 8; i++) {
                Card carta = baraja.extraerCarta();
                
                jugador.anadirCarta(carta); 
                
            }
        }

        // colocar 3 cartas en cada una de las 4 filas de la mesa
        for (int i = 0; i < 4; i++) {
            while (mesa.getFilaSize(i) < 3) {
                Card carta = baraja.extraerCarta();
                
                //no puede haber especies repetidas en la misma fila al inicio
                if (!mesa.hasMismaEspecie(i, carta.getTypeBird())) {
                    mesa.addCartaFila(i, carta);
                } else {
                    baraja.devolverCarta(carta);
                }
            }
        }
        
        // Muestra el estado inicial en mesa y por jugador
        iu.displayMessage(mesa.toString());

        for (Player jugador : listaJugadores) {
            iu.displayMessage(jugador.toString());
        }
    }
    
    /**
     * Metodo principal para jugar
     */
    public void play() {
        crearJugadores();
        crearMesa();
        prepararPartida();
        iu.displayMessage("¡Partida preparada! Empieza " + listaJugadores.get(0).getName());
        }

}
