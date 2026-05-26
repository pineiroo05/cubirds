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

    public List<Card> colocarCartas(int fila, List<Card> cartas, boolean derecha,DeckOfCards baraja, DiscardedCards descartes) {
        List<Card> capturadas = new LinkedList<>();
        if (fila < 0 || fila >= mesa.length || cartas.isEmpty()) {
            return capturadas;
        }
        TypeBird especie = cartas.get(0).getTypeBird();
        if (!hasMismaEspecie(fila, especie)) {
            capturadas = colocarSinCapturar(fila, cartas, derecha);
        } else {
            capturadas = colocarCapturando(fila, cartas, derecha, especie);
        }
        if (filaTieneEspecieUnica(fila)) {
            rellenarFila(fila, baraja, descartes);
        }
        return capturadas;
    }

    private List<Card> colocarSinCapturar(int fila, List<Card> cartas, boolean derecha) {
        for (Card carta : cartas) {
            if (derecha) {
                mesa[fila].addLast(carta);
            } else {
                mesa[fila].addFirst(carta);
            }
        }
        return new LinkedList<>();
    }

    private List<Card> colocarCapturando(int fila, List<Card> cartas, boolean derecha, TypeBird especie) {
        List<Card> capturadas = new LinkedList<>();
        if (derecha) {
            for (Card carta : cartas) {
                mesa[fila].addLast(carta);
            }
            // A partir de aqui buscamos las cartas: miramos desde el principio si coincide
            // o no y se guarda segun me interese
            int limite = mesa[fila].size() - cartas.size(); // Ignoraria las ultimas q añadi para q no se las lleve tmb
            int ultimaPosicion = -1; // IMPORTANTE
            for (int i = 0; i < limite; i++) {
                if (mesa[fila].get(i).getTypeBird().equals(especie)) {
                    ultimaPosicion = i;
                }
            }
            if (ultimaPosicion != -1) {
                int i = ultimaPosicion + 1;
                while (i < limite) {
                    capturadas.addLast(mesa[fila].get(i));
                    mesa[fila].remove(i);
                    limite--;
                }
            }
        } else {
            for (Card carta : cartas) {
                mesa[fila].addFirst(carta);
            }

            int inicio = cartas.size();
            int primeraPosicion = -1;
            for (int i = inicio; i < mesa[fila].size(); i++) {
                if (mesa[fila].get(i).getTypeBird().equals(especie)) {
                    primeraPosicion = i;
                    break;
                }
            }
            if (primeraPosicion != -1) {
                int i = inicio;
                while (i < primeraPosicion) {
                    capturadas.addLast(mesa[fila].get(i));
                    mesa[fila].remove(i);
                    primeraPosicion--;
                }
            }
        }
        return capturadas;
    }
    /**
     * Comprueba si hay alguna carta con la misma especie
     * 
     * @param fila
     * @param species: tipos de pájaro
     * @return true si hay alguna carta con la misma especie, false si no
     */
    public boolean hasMismaEspecie(int fila, TypeBird species) {
        for (Card card : mesa[fila]) {
            if (card.getTypeBird().equals(species)) {
                return true;
            }
        }
        return false;
    }

    public void colocarCartasIniciales(DeckOfCards baraja) {
        for (int i = 0; i < 4; i++) {
            while (mesa[i].size() < 3) {
                Card carta = baraja.extraerCarta();
                // no puede haber especies repetidas en la misma fila al inicio
                if (!hasMismaEspecie(i, carta.getTypeBird())) {
                    mesa[i].addFirst(carta);
                    ;
                } else {
                    baraja.getCartas().addLast(carta);
                }
            }
        }
    }

    /**
     * comprueba si todas las cartas de una fila son de la misma especie.
     * si la fila esta vacia, también devuelve true .
     */
    private boolean filaTieneEspecieUnica(int fila) {
        if (mesa[fila].isEmpty()) {
            return true;
        }

        // Cogemos la primera carta como referencia
        TypeBird primeraEspecie = mesa[fila].get(0).getTypeBird();

        // Comprobamos si hay alguna carta que sea DIFERENTE
        for (int i = 1; i < mesa[fila].size(); i++) {
            if (!mesa[fila].get(i).getTypeBird().equals(primeraEspecie)) {
                return false; // En cuanto vemos una especie distinta, la fila es válida
            }
        }

        return true;
    }

    /**
     * rellena la fila robando de la baraja hasta que aparezca una especie diferente
     * a la que solo queda en la fila.
     */
    private void rellenarFila(int fila, DeckOfCards baraja, DiscardedCards descartes) {
        if (mesa[fila].isEmpty()) {
            if (baraja.reabastecerSiVacia(descartes)) {
                mesa[fila].addLast(baraja.extraerCarta());
            } else {
                return;
            }
        }
        TypeBird especieSobrante = mesa[fila].get(0).getTypeBird(); //no me convence el nombre de Sobrante, pero ya no se que ponerle
        boolean especieDiferenteEncontrada = false;
        boolean quedanCartasDisponibles = true;
        while (!especieDiferenteEncontrada && quedanCartasDisponibles) {
            // comprobamos si podemos recuperar cartas
            if (!baraja.reabastecerSiVacia(descartes)) {
                quedanCartasDisponibles = false;
            } else {
                // si hay cartas ejecutamos el camino normal de forma segura
                Card nuevaCarta = baraja.extraerCarta();
                mesa[fila].addLast(nuevaCarta);

                if (!nuevaCarta.getTypeBird().equals(especieSobrante)) {
                    especieDiferenteEncontrada = true;
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
