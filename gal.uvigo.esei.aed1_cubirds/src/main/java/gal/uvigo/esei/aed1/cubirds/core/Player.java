package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class Player {
    private String name;
    private List<List<Card>> manoCartas;
    private int[] zonaJuego;

    public Player(String name) {
        this.name = name;
        this.manoCartas = new LinkedList<>();
        this.zonaJuego = new int[8];
    }

    public String getName() {
        return this.name;
    }

    public int getHandSize() {
        return manoCartas.size();
    }

    public int[] getZonaJuego() {
        return zonaJuego;
    }

    public int numCartasEspecie(int pos){
        return manoCartas.get(pos).size();
    }

    public boolean isHandEmpty() {
        return manoCartas.size() == 0;
    }
    /**
     *suma 1 al valor de la especie q corresponda tras bajar la bandada
     */
    public void sumarContadorEspecie(TypeBird especie) {
        zonaJuego[especie.ordinal()]++; //ordinal pilla el num que le corresponda a la especie en el enum(flamenco es 0, tucan 1, etc)
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

    //Saca las cartas de la mano del jugador. Necesario para finalizar el juego
    public List<Card> vaciarMano(){
        List<Card> cartasMano=new LinkedList<>();
        while(!manoCartas.isEmpty()){
            List<Card> sublista=manoCartas.remove(0);
            while(!sublista.isEmpty()){
                cartasMano.addLast(sublista.remove(0));
            }
        }
        return cartasMano;
    }

    public List<Card> sacarCartasEspecie(int pos) {
        return manoCartas.remove(pos);
    }

    /*public List<Card> devolverCartasEspecie(int pos){
        return manoCartas.get(pos);
    }*/

    public boolean puedeFormarBandada(int pos){
        int numCartas=numCartasEspecie(pos);
        int bandadaMinima=manoCartas.get(pos).get(0).getSmallFlock();
        return numCartas>=bandadaMinima;
    }

    public TypeBird bajarBandada(int pos, DiscardedCards descartes){
        TypeBird especie = manoCartas.get(pos).getFirst().getTypeBird();
        descartes.añadirCartas(sacarCartasEspecie(pos));
        sumarContadorEspecie(especie);
        return especie;
    }

    public boolean haGanado(){
        int contador=0;
        for(int cont:zonaJuego){
            if(cont>0){
                contador++;
            }
        }
        return contador>=7;
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
