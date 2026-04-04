package gal.uvigo.esei.aed1.cubirds.core;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name; // nombre del jugador
    private List<Card> mano; // mano de cartas del jugador
    private Player next; // referencia al siguiente jugador (creo que hacer una estructura circular de
                         // nodos para ordenar los jugadores rentaría)

    public Player(String name, Player next) {
        this.name = name;
        this.next = next;
        this.mano = new ArrayList<>();
    }

    // getters
    public String getName() {
        return this.name;
    }

    public Player getNext() {
        return this.next;
    }

    public int getHandSize() {
        return mano.size();
    }

    public List<Card> getHand() {
        return mano;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    /**
     * 
     * @return true si la mano del jugador esta vacia, si no returns false
     */
    public boolean isHandEmpty() {
        return this.mano.size() == 0;
    }

    /**
     * Añade la carta a la mano del jugador si esta existe
     * 
     * @param carta
     */
    public void robarCarta(Card carta) {
        if (carta != null) {
            mano.add(carta);
        }
    }
}
