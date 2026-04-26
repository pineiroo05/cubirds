package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.List;
import es.uvigo.esei.aed1.tads.list.LinkedList;

public class Player {
    private String name;
    private List<Card> manoCartas;

    public Player(String name, Player next) {
        this.name = name;
        this.manoCartas = new LinkedList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getHandSize() {
        return manoCartas.size();
    }

    public boolean isHandEmpty() {
        return this.manoCartas.size() == 0;
    }

    // REHACER CON LO DE LISTA DE LISTAS
    public void anadirCarta(Card carta){
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
        StringBuilder sb = new StringBuilder();
        sb.append("Jugador: ").append(name).append("\n");
        sb.append("Mano: ");
        for(Card carta:manoCartas){
            sb.append(carta.toString()).append(" ");
        }
        return sb.toString();
    }
}
