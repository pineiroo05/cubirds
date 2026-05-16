package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class Player {
    private String name;
    private List<List<Card>> manoCartas;
    private List<Card> zonaJuego;

    public Player(String name) {
        this.name = name;
        this.manoCartas = new LinkedList<>();
        this.zonaJuego = new LinkedList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getHandSize() {
        /*int contador=0;
        for(List<Card> sublista:manoCartas){
            contador+=sublista.size();
        }
        return contador;*/
        return manoCartas.size();
    }

    public boolean isHandEmpty() {
        return getHandSize() == 0;
    }
    /**
     *incrementa el contador de una especie determinada en la zona de juego.
     */
    public void añadirAZonaJuego(List<Card> cartasGanadas) {
        for (int i = 0; i < cartasGanadas.size(); i++) {
            this.zonaJuego.addLast(cartasGanadas.get(i));
        }
    }

    public void anadirCarta(Card carta){
        boolean encontrado=false;
        for(int i=0; i<manoCartas.size(); i++){
            List<Card> sublista=manoCartas.get(i);
            if(!sublista.isEmpty() && sublista.get(0).getTypeBird().equals(carta.getTypeBird())){
                sublista.addLast(carta);
                encontrado=true;
                break;
            }
        }
        if(!encontrado){
            List<Card> nuevo=new LinkedList<>();
            nuevo.addLast(carta);
            manoCartas.addLast(nuevo);
        }
    }
   

    /*public boolean tieneEspecie(TypeBird tipo) {
        int i=tipo.ordinal();
        return i<manoCartas.size()&&manoCartas.get(i).size()>0;
    }

    public List<TypeBird> getEspeciesDisponibles() {
        List<TypeBird> disponibles = new LinkedList<>();
        for (TypeBird tipo : TypeBird.values()) {
            if (tieneEspecie(tipo)) {
                disponibles.addLast(tipo);
            }
        }
        return disponibles;
    }*/

    public List<Card> sacarCartasEspecie(int pos) {
        return manoCartas.remove(pos);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Jugador: ").append(name).append("\n");
        sb.append("Mano:\n");
        
        for (int i = 0; i < manoCartas.size(); i++) {
            List<Card> actual = manoCartas.get(i);
            sb.append(" ").append(i).append(":");
            for(int j=0; j<actual.size(); j++){
                sb.append(actual.get(j).toString());
            }
            sb.append("\n");
        }
    
        return sb.toString();
    }
}
