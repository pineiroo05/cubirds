package gal.uvigo.esei.aed1.cubirds.core;

//Tendria mas sentido un stack??????
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DeckOfCards {
    /**
     * Constructor: crea una baraja de cartas ordenada a partir del enumerado
     */
    private List<Card> cartas;
    public DeckOfCards() {
        this.cartas=new ArrayList<>();
        for(Card card:Card.values()){
            cartas.add(card);
        }
    }

    //Fisher-yates
    public void barajar(){
        Random rnd=new Random();
        for(int i=cartas.size()-1; i>0; i--){
            int posAleatoria=rnd.nextInt(i+1);
            //Collections.swap(cartas, i, posAleatoria);
            Card aux=cartas.get(i);
            cartas.set(i, cartas.get(posAleatoria));
            cartas.set(posAleatoria, aux);
        }
    }

    public Card extraerCarta(){
        if(cartas.isEmpty()){
            return null;
        }
        return cartas.remove(0); //La que esta siempre al principio
    }

    public void devolverCarta(Card carta){
        if(carta!=null){
            cartas.add(carta);
        }
    }

    public boolean isEmpty(){
        return cartas.size()==0;
    }
}
