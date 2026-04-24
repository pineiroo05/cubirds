package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.List;
import es.uvigo.esei.aed1.tads.list.LinkedList;

public class Player {
    private String name; // nombre del jugador
    private List<Card> manoCartas; // mano de cartas del jugador

    public Player(String name, Player next) {
        this.name = name;
        this.manoCartas = new LinkedList<>();
    }

    // getters
    public String getName() {
        return this.name;
    }

    public int getHandSize() {
        return manoCartas.size();
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
        while (i < manoCartas.size() && !manoCartas.get(i).getTypeBird().equals(carta.getTypeBird())) {
            i++;
        }
        manoCartas.add(i, carta);
    }

    /* ESTO HACE FALTA???

    public void anadirCarta(List<Card> cartas) {
        for (Card carta : cartas) {
            anadirCarta(carta);
        }
    }
    */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== JUGADORES ===\n");
        sb.append("Jugador: ").append(name).append("\n");
        sb.append("Mano: ").append(manoCartas).append("\n");
        return sb.toString();
    }
}
