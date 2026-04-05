package gal.uvigo.esei.aed1.cubirds.core;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name; // nombre del jugador
    private List<Card> manoCartas; // mano de cartas del jugador
    private Player next; // referencia al siguiente jugador (creo que hacer una estructura circular de
                         // nodos para ordenar los jugadores rentaría)

    public Player(String name, Player next) {
        this.name = name;
        this.next = next;
        this.manoCartas = new ArrayList<>();
    }

    // getters
    public String getName() {
        return this.name;
    }

    public Player getNext() {
        return this.next;
    }

    public int getHandSize() {
        return manoCartas.size();
    }

    public List<Card> getHand() {
        return manoCartas;
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
        return this.manoCartas.size() == 0;
    }

    public void anadirCarta(Card carta) throws NullPointerException {
        if (carta == null) {
            throw new NullPointerException();
        }
        int i = 0;
        // Busca la pos donde va metida la carta. Salta las especies q no coinciden
        while (i < manoCartas.size() && !manoCartas.get(i).getTypeBird().equals(carta.getTypeBird())) {
            i++;
        }
        // Al encontrar el grupo de una especie, se va para el final
        if (i < manoCartas.size()) {
            while (i < manoCartas.size() && manoCartas.get(i).getTypeBird().equals(carta.getTypeBird())) {
                i++;
            }
        }
        manoCartas.add(i, carta);
    }

    // Este es para cuando la mano esta vacia
    public void anadirCarta(List<Card> cartas) {
        for (Card carta : cartas) {
            anadirCarta(carta);
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== JUGADORES ===\n");
        sb.append("Jugador: ").append(name).append("\n");
        sb.append("Mano: ").append(manoCartas).append("\n");
        return sb.toString();
    }
}
