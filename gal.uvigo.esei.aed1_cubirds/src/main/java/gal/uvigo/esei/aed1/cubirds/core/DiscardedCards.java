package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class DiscardedCards {

    private List<Card> cartas;

    public DiscardedCards() {
        this.cartas = new LinkedList<>();
    }

    /**
     * añade una carta al monton de descartes.
     */
    public void añadirCarta(Card carta) {
        if (carta != null) {
            cartas.addLast(carta);
        }
    }
    /**
     * añade una lista completa de cartas a los descartes.
     * util cuando un jugador descarta varias cartas a la vez.
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
        
        Card carta = this.cartas.get(0);
        this.cartas.removeFirst();
        aDevolver.addLast(carta);
    }
    
    return aDevolver;
    }
}
