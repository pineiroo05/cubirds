package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;
import gal.uvigo.esei.aed1.cubirds.iu.IU;

public class Game {

    private final IU iu;
    private DeckOfCards baraja;
    private Table mesa;
    private List<Player> listaJugadores;
    private DiscardedCards descartes;

    public Game(IU iu) {
        this.iu = iu;
        this.baraja = new DeckOfCards(); // creamos la baraja de 110 cartas
        this.mesa = new Table();
        this.listaJugadores = new LinkedList<>();
        this.descartes = new DiscardedCards();
    }

    private void crearJugadores() {
        int numJugadores = iu.pedirNumJugadores();
        for (int i = 0; i < numJugadores; i++) {
            String nombre = iu.pedirNombreJugador(i);
            listaJugadores.addLast(new Player(nombre));

        }
    }

    private void turnoJugador(Player jugador) {
        iu.displayMessage("Turno de " + jugador.getName());
        iu.displayMessage(jugador.toString()); // Mano al inicio del turno
        // SELECCION DE LA ESPECIE
        int especie;
        do {
            especie = iu.readNumber("Elige la especie a jugar: ");
        } while (especie < 0 || especie >= jugador.getHandSize());
        // SELECCION DE LA FILA
        int fila;
        do {
            fila = iu.readNumber("Elige una fila (1-4): ");
        } while (fila < 1 || fila > 4);

        boolean derecha;
        //SELECCION DEL LADO
        String lado;
        do {
            lado = iu.readString("¿Derecha? (s/n): ");
            derecha = lado.equalsIgnoreCase("s");
        } while (!lado.equalsIgnoreCase("s") && !lado.equalsIgnoreCase("n"));
        // SACAR CARTA Y COLOCARLA
        List<Card> cartasJugar = jugador.sacarCartasEspecie(especie);
        List<Card> cartasCapturadas = mesa.colocarCartas(fila - 1, cartasJugar, derecha, baraja, descartes);
        for (int i = 0; i < cartasCapturadas.size(); i++) {
            jugador.anadirCarta(cartasCapturadas.get(i));
        }
        iu.displayMessage(jugador.toString());
        //OPCION BAJAR BANDADA
        if(!jugador.isHandEmpty()){
            intentarFormarBandada(jugador);
        }
        iu.displayMessage(mesa.toString());
    }

    private void intentarFormarBandada(Player jugador){
            String respuesta;
            do {
                respuesta = iu.readString("¿Deseas añadir una especie a tu zona de juego? (s/n): ");
            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            if (respuesta.equalsIgnoreCase("s")) {
                int pos;
                do {
                    // muestra las bandadas que ya tiene el jugador
                    for (int i = 0; i < jugador.getZonaJuego().length; i++) {
                        if (jugador.getZonaJuego()[i] > 0) {
                            iu.displayMessage("Tienes bandada de " + TypeBird.values()[i]);
                        }
                    }
                    pos = iu.readNumber("Elige la especie a bajar de tu mano a tu zona de juego: ");
                } while (pos < 0 || pos >= jugador.getHandSize());

                if(jugador.puedeFormarBandada(pos)){
                    TypeBird especie=jugador.bajarBandada(pos, descartes);
                    iu.displayMessage("Se ha bajado la especie y se ha sumado una bandada de " + especie + " a la zona de juego");
                    
                    for(int i=0; i<jugador.getZonaJuego().length; i++){
                        if(jugador.getZonaJuego()[i]>0){
                            iu.displayMessage("Tienes bandada de " + TypeBird.values()[i]);
                        }
                    }
                }else{
                    int numCartas=jugador.numCartasEspecie(pos);
                    int bandadaMinima=jugador.devolverCartasEspecie(pos).get(0).getSmallFlock();
                    iu.displayMessage("No es posible bajar la especie, solo tienes " + numCartas+ " y necesitas al menos " + bandadaMinima);
                }
            }
        iu.displayMessage(mesa.toString());
    }

    private boolean repartirCartas() {
        // No hay suficientes cartas para repartir
        if (baraja.getCartas().size() < listaJugadores.size() * 8) {
            return false;
        }

        for (Player jugador : listaJugadores) {
            for (int i = 0; i < 8; i++) {
                Card carta = baraja.extraerCarta();
                jugador.anadirCarta(carta);
            }
        }
        return true;
    }

    public void finalizarFaltaCartas() {
        iu.displayMessage("No ha sido posible realizar el reparto de cartas.");
        Player ganador = null;
        int maxCartas = -1;
        for (Player p : listaJugadores) {
            int total = 0;
            for (int count : p.getZonaJuego()) {
                total += count;
            }
            if (total > maxCartas) {
                maxCartas = total;
                ganador = p;
            }
        }
        if (ganador != null) {
            iu.displayMessage("El jugador " + ganador.getName() + " ha ganado la partida por tener más bandadas completas.");
        }
    }

    /**
     * Metodo principal para jugar
     */
    public void play() {
        crearJugadores();

        baraja.barajar();
        if (!repartirCartas()) {
            finalizarFaltaCartas();
            return;
        }
        mesa.colocarCartasIniciales(baraja);
        iu.displayMessage(mesa.toString());

        iu.displayMessage("¡Partida preparada! Empieza " + listaJugadores.get(0).getName());

        do {
            for (Player jugador : listaJugadores) {
                turnoJugador(jugador);
                if(jugador.haGanado()){
                    iu.displayMessage(jugador.getName()+" ha conseguido 7 bandadas. Ha ganado 1 partida!!");
                    return;
                }
                // El jugador actual tiene la mano vacia? los otros meten su mano en los descartes
                if (jugador.isHandEmpty()) {
                    for (Player otroJugador : listaJugadores) {
                        if (!otroJugador.equals(jugador)) {
                            descartes.añadirCartas(otroJugador.vaciarMano());
                        }
                    }
                    List<Card> descartesRecuperados = descartes.extraerTodas();
                    for (int i = 0; i < descartesRecuperados.size(); i++) {
                        baraja.getCartas().addLast(descartesRecuperados.get(i));
                    }
                    baraja.barajar();
                    if (!repartirCartas()) {
                        finalizarFaltaCartas();
                        return;
                    }
                    iu.displayMessage("-Se reparten nuevas cartas-");
                }
            }
        } while (true);
        
    }
}
