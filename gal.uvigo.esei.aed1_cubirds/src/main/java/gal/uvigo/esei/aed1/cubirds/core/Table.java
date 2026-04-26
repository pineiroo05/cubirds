package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;


public class Table {
    private List<Card>[] mesa;

    public Table() {
        this.mesa = new List[4];
        for (int i = 0; i < 4; i++) {
            this.mesa[i] = new LinkedList<>();
        }
    }


    /**
    * Añade una carta a la fila indicada. 
     * Comprueba si el índice es nulo o no está en la mesa.
     * @param fila 
     * @param card: carta a añadir
    */
    public void addCartaFila(int fila, Card card) {
        if (fila >= 0 && fila < mesa.length) {
            mesa[fila].addFirst(card);
        }
    }

    /**
     * Comprueba si hay alguna carta con la misma especie
     * @param fila 
     * @param species: tipos de pájaro
     * @return true si hay alguna carta con la misma especie, false si no 
     */
    public boolean hasMismaEspecie(int fila, TypeBird species) {
        if (fila >= 0 && fila < mesa.length) {
            for (Card card : mesa[fila]) {
                if (card.getTypeBird().equals(species)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void repartirCartas(DeckOfCards baraja, List<Player> listaJugadores){
        for(Player juagdor:listaJugadores){
            for(int i=0; i<8; i++){
                Card carta=baraja.extraerCarta();
                juagdor.anadirCarta(carta);
            }
        }
    }

    public void colocarCartasIniciales(DeckOfCards baraja, List<Player> listaJugadores){
        for (int i = 0; i < 4; i++) {
            while (mesa[i].size() < 3) {
                Card carta = baraja.extraerCarta();
                //no puede haber especies repetidas en la misma fila al inicio
                if (!hasMismaEspecie(i, carta.getTypeBird())) {
                    addCartaFila(i, carta);
                }else {
                    baraja.getCartas().addLast(carta);
                }
            }
        }
    }

    /**
     * Muestra el estado de la mesa.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n=== CARTAS EN LA MESA ===\n");
        for (int i = 0; i < mesa.length; i++) {
            sb.append("Fila ").append(i + 1).append(": ");
            for(Card carta:mesa[i]){
                sb.append(carta.toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
