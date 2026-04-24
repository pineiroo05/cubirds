package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.List;
import es.uvigo.esei.aed1.tads.list.LinkedList;


public class Table {

private List<Card>[] mesa;

    public Table() {
        this.mesa = new List[4];
        for (int i = 0; i < 4; i++) {
            this.mesa[i] = new LinkedList<>();
        }
    }

    /*
    ESTO CREO Q HABIA QUE ELIMINARLO TAMBIEN O ALGO

    public void addCartaFila(int fila, Card card) {
        if (fila >= 0 && fila < mesa.length) {
            mesa[fila].addFirst(card);
        }
    }*/

    /**
     * Comprueba si hay alguna carta con la misma especie
     * @param fila 
     * @param species: tipos de pájaro
     * @return true si hay alguna carta con la misma especie, false si no 
     */
    public boolean hasMismaEspecie(int fila, TypeBird species) {
        if (fila >= 0 && fila < mesa.length) {
            for (Card card : mesa[fila]) {
                if (card.getTypeBird().equals(species)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Muestra el estado de la mesa.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== CARTAS EN LA MESA ===\n");
        for (int i = 0; i < mesa.length; i++) {
            sb.append("Fila ").append(i + 1).append(": ").append(mesa[i]).append("\n");
        }
        return sb.toString();
    }
}
