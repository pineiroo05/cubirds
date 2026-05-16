package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class DiscardedCards {

    private List<Card> cartas;

    public DiscardedCards() {
        this.cartas = new LinkedList<>();
    }

    /**
     * Metodo auxiliar de añadirCartas
     */
    public void añadirCarta(Card carta) {
        cartas.addLast(carta);
    }

    //devuelve el tamaño de la lista de descartes
    public int getTamano() {
        return this.cartas.size();
    }
    
    /**
     * añade una lista completa de cartas a los descartes.
     */
    public void añadirCartas(List<Card> lista) {
        for (int i = 0; i < lista.size(); i++) {
            añadirCarta(lista.get(i));
        }
    }
    /**
     * devuelve todas las cartas almacenadas y vacía el montón.
     */
    public List<Card> extraerTodas() {
        List<Card> aDevolver = new LinkedList<>();
        while (!this.cartas.isEmpty()) {
            Card aux = this.cartas.get(0);
            this.cartas.removeFirst();
            aDevolver.addLast(aux);
        }
        return aDevolver;
    }
}
