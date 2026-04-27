package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class Player {
    private String name;
    private List<List<Card>> manoCartas;

    public Player(String name, Player next) {
        this.name = name;
        this.manoCartas = new LinkedList<>();

        for(TypeBird type:TypeBird.values()){
            manoCartas.addLast(new LinkedList<>());
        }
    }

    public String getName() {
        return this.name;
    }

    public int getHandSize() {
        int contador=0;
        for(List<Card> sublista:manoCartas){
            contador+=sublista.size();
        }
        return contador;
    }

    public boolean isHandEmpty() {
        return getHandSize() == 0;
    }

    public void anadirCarta(Card carta){
        TypeBird tipoPajaro=carta.getTypeBird();
        int i=tipoPajaro.ordinal();
        manoCartas.get(i).addLast(carta);
    }

    public boolean tieneEspecie(TypeBird tipo) {
        return manoCartas.get(tipo.ordinal()).size() > 0;
    }

    public List<TypeBird> getEspeciesDisponibles() {
        List<TypeBird> disponibles = new LinkedList<>();
        for (TypeBird tipo : TypeBird.values()) {
            if (tieneEspecie(tipo)) {
                disponibles.addLast(tipo);
            }
        }
        return disponibles;
    }

    public List<Card> sacarCartasEspecie(TypeBird tipo) {
        int index = tipo.ordinal();
        List<Card> resultado = manoCartas.get(index);
        manoCartas.set(index, new LinkedList<>());
        return resultado;  
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Jugador: ").append(name).append("\n");
        sb.append("Mano:\n");

        for (int i = 0; i < manoCartas.size(); i++) {
            List<Card> actual=manoCartas.get(i);
            if(actual.size()>0){
                for(int j=0; j<actual.size(); j++){
                    sb.append(actual.get(j).toString()).append(" ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
