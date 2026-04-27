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
     * 
     * @param fila
     * @param card: carta a añadir
     */
    public void addCartaFila(int fila, Card card) {
        if (fila >= 0 && fila < mesa.length) {
            mesa[fila].addFirst(card);
        }
    }

    public List<Card> colocarCartas(int fila, List<Card> cartas, boolean derecha) {
    if (fila < 0 || fila >= mesa.length) {
        throw new IllegalArgumentException("Fila fuera de rango");
    }

    List<Card> capturadas = new LinkedList<>();
    TypeBird especie = cartas.get(0).getTypeBird();

    if (!hasMismaEspecie(fila, especie)) {
        // No hay cartas de esa especie en la fila -> colocar sin capturar
        for (Card c : cartas) {
            if (derecha) {
                mesa[fila].addLast(c);
            } else {
                mesa[fila].addFirst(c);
            }
        }
        return capturadas; // vacía
    }

    if (derecha) {
        for (Card c : cartas) {
            mesa[fila].addLast(c);
        }

        int tamano = mesa[fila].size();
        int numNuevas = cartas.size();
        int finBusqueda = tamano - numNuevas - 1;

        int indice = -1;
        for (int i = finBusqueda; i >= 0; i--) {
            if (mesa[fila].get(i).getTypeBird().equals(especie)) {
                indice = i;
                break;
            }
        }

        if (indice != -1) {
            for (int i = finBusqueda; i > indice; i--) {
                capturadas.addFirst(mesa[fila].get(i));
                mesa[fila].remove(i);
            }
        }

    } 
    else {
        for (Card c : cartas) {
            mesa[fila].addFirst(c);
        }

        int numNuevas = cartas.size();

        int indice = -1;
        for (int i = numNuevas; i < mesa[fila].size(); i++) {
            if (mesa[fila].get(i).getTypeBird().equals(especie)) {
                indice = i;
                break;
            }
        }

        if (indice != -1) {
            for (int i = indice - 1; i >= numNuevas; i--) {
                capturadas.addFirst(mesa[fila].get(i));
                mesa[fila].remove(i);
            }
        }
    }

    return capturadas;
}

    /**
     * Comprueba si hay alguna carta con la misma especie
     * 
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

    public void repartirCartas(DeckOfCards baraja, List<Player> listaJugadores) {
        for (Player juagdor : listaJugadores) {
            for (int i = 0; i < 8; i++) {
                Card carta = baraja.extraerCarta();
                juagdor.anadirCarta(carta);
            }
        }
    }

    public void colocarCartasIniciales(DeckOfCards baraja, List<Player> listaJugadores) {
        for (int i = 0; i < 4; i++) {
            while (mesa[i].size() < 3) {
                Card carta = baraja.extraerCarta();
                // no puede haber especies repetidas en la misma fila al inicio
                if (!hasMismaEspecie(i, carta.getTypeBird())) {
                    addCartaFila(i, carta);
                } else {
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
            for (Card carta : mesa[i]) {
                sb.append(carta.toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
