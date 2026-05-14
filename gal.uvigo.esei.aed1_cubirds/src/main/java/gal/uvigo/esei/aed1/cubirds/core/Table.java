package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class Table {
    private List<Card>[] mesa;

    @SuppressWarnings("unchecked")
    public Table() {
        this.mesa = new List[4];
        for (int i = 0; i < 4; i++) {
            this.mesa[i] = new LinkedList<>();
        }
    }

    public List<Card> colocarCartas(int fila, List<Card> cartas, boolean derecha){
        List<Card> capturadas=new LinkedList<>();
        if(fila<0 || fila>=mesa.length || cartas.isEmpty()){
            return capturadas;
        }
        TypeBird especie=cartas.get(0).getTypeBird();
        //En caso de q la fila donde quiero meter las cartas no tenga esa misma especie
        if(!hasMismaEspecie(fila, especie)){
            return colocarSinCapturar(fila, cartas, derecha);
        }
        return colocarCapturando(fila, cartas, derecha, especie);
    }

    public List<Card> colocarSinCapturar(int fila, List<Card> cartas, boolean derecha){
        for(Card carta:cartas){
            if(derecha){
                mesa[fila].addLast(carta);
            }else{
                mesa[fila].addFirst(carta);
            }
        }
        return new LinkedList<>(); 
    }

    public List<Card> colocarCapturando(int fila, List<Card> cartas, boolean derecha, TypeBird especie){
        List<Card> capturadas=new LinkedList<>();
        if(derecha){
            for(Card carta:cartas){
                mesa[fila].addLast(carta);
            }
            //A partir de aqui buscamos las cartas: miramos desde el principio si coincide o no y se guarda segun me interese
            int limite=mesa[fila].size()-cartas.size(); //Ignoraria las ultimas q añadi para q no se las lleve tmb
            int ultimaPosicion=-1; //IMPORTANTE 
            for(int i=0; i<limite; i++){
                if(mesa[fila].get(i).getTypeBird().equals(especie)){
                    ultimaPosicion=i;
                }
            }
            if(ultimaPosicion!=-1){
                int i=ultimaPosicion+1;
                while(i<limite){
                    capturadas.addLast(mesa[fila].get(i));
                    mesa[fila].remove(i);
                    limite--;
                }
            }
        }else{
            for(Card carta:cartas){
                mesa[fila].addFirst(carta);
            }
            
            int inicio=cartas.size();
            int primeraPosicion=-1;
            for(int i=inicio; i<mesa[fila].size(); i++){
                if(mesa[fila].get(i).getTypeBird().equals(especie)){
                    primeraPosicion=i;
                    break;
                }
            }
            if(primeraPosicion!=-1){
                int i=inicio;
                while(i<primeraPosicion){
                    capturadas.addLast(mesa[fila].get(i));
                    mesa[fila].remove(i);
                    primeraPosicion--;
                }
            }
        }
        return capturadas;
    }

    /*public List<Card> colocarCartas(int fila, List<Card> cartas, boolean derecha) throws IllegalArgumentException {
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
        } else {
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
    }*/

    /**
     * Comprueba si hay alguna carta con la misma especie
     * 
     * @param fila
     * @param species: tipos de pájaro
     * @return true si hay alguna carta con la misma especie, false si no
     */
    public boolean hasMismaEspecie(int fila, TypeBird species) {
        //if (fila >= 0 && fila < mesa.length) {
            for (Card card : mesa[fila]) {
                if (card.getTypeBird().equals(species)) {
                    return true;
                }
            }
        //}
        return false;
    }

    public void colocarCartasIniciales(DeckOfCards baraja, List<Player> listaJugadores) {
        for (int i = 0; i < 4; i++) {
            while (mesa[i].size() < 3) {
                Card carta = baraja.extraerCarta();
                // no puede haber especies repetidas en la misma fila al inicio
                if (!hasMismaEspecie(i, carta.getTypeBird())) {
                    mesa[i].addFirst(carta);;
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
