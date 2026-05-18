package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.List;
import es.uvigo.esei.aed1.tads.list.LinkedList;
import java.util.Random;

public class DeckOfCards {
    /**
     * Constructor: crea una baraja de cartas ordenada a partir del enumerado
     */
    private List<Card> cartas;
    private Random rnd;

    public DeckOfCards() {
        this.cartas=new LinkedList<>();
        this.rnd=new Random();
        for(Card card:Card.values()){
            cartas.addFirst(card);
        }
    }

    public List<Card> getCartas(){
        return this.cartas;
    }

    public void barajar(){
        for(int i=cartas.size()-1; i>0; i--){
            int posAleatoria=rnd.nextInt(i+1);
            Card aux=cartas.get(i);
            cartas.set(i, cartas.get(posAleatoria));
            cartas.set(posAleatoria, aux);
        }
    }

    public Card extraerCarta(){
        return cartas.remove(0); 
    }

    public boolean isEmpty(){
        return cartas.size()==0;
    }

    public boolean reabastecerSiVacia(DiscardedCards descartes){
        if(!isEmpty()){
            return true;
        }
        if(descartes.getTamano()==0){
            return false;
        }
        List<Card> recuperadas=descartes.extraerTodas();
        for(int i=0; i<recuperadas.size(); i++){
            cartas.addLast(recuperadas.get(i));
        }
        barajar();
        return true;
    }
}
