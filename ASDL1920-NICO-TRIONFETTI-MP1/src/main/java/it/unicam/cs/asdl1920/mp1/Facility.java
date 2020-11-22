package it.unicam.cs.asdl1920.mp1;

/**
 * Una facility generica è una caratteristica o delle dotazioni che una certa
 * aula può avere. La classe va specificata ulteriormente per definire i diversi
 * tipi di facilities.
 *
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 *
 */
public abstract class Facility {

    private final String codice;

    private final String descrizione;

    /**
     * Costruisce una certa facility generica.
     * 
     * @param codice
     *                        identifica la facility univocamente
     * @param descrizione
     *                        descrizione della facility
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla.
     */
    public Facility(String codice, String descrizione) {
        // TODO implementare
        if(codice.equals(null)||descrizione.equals(null))  // controllo che non siano nulli i valori
            throw new NullPointerException();
        this.codice = codice;
        this.descrizione = descrizione;
    }

    /**
     * @return the codice
     */
    public String getCodice() {
        // TODO implementare
        return this.codice;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        // TODO implementare
        return this.descrizione;
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
        // TODO implementare
        return this.codice.hashCode();  // creo un codice che identifica l'oggetto in base al codice
    }

    /*
     * L'uguaglianza di due facilities è basata unicamente sul codice
     */
    @Override
    public boolean equals(Object obj) {
        // TODO implementare
        if(obj != null && obj instanceof Facility)   // controllo che non sia nulla e che obj sia un instanza di Facility
            return this.hashCode()==obj.hashCode(); // controllo che i hashcode coincidano
        return false;
    }

    @Override
    public String toString() {
        return "Facility [codice=" + codice + ", descrizione=" + descrizione
                + "]";
    }

    /**
     * Determina se questa facility soddisfa un'altra facility data. Ad esempio
     * se questa facility indica che è presente un proiettore HDMI, allora essa
     * soddisfa la facility "presenza di un proiettore HDMI". Un altro esempio:
     * se questa facility indica un numero di posti a sedere pari a 30, allora
     * essa soddisfa ogni altra facility che indica che ci sono un numero di
     * posti minore o uguale a 30. Il metodo dipende dal tipo di facility, per
     * questo è astratto e va definito nelle varie sottoclassi.
     * 
     * @param o
     *              l'altra facility con cui determinare la compatibilità
     * @return true se questa facility soddisfa la facility passata, false
     *         altrimenti
     * @throws NullPointerException
     *                                  se la facility passata è nulla.
     */
    public abstract boolean satisfies(Facility o);

}
