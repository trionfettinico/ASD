/**
 * 
 */
package it.unicam.cs.asdl1920.mp1;

/**
 * Una Presence Facility è una facility che può essere presente oppure no. Ad
 * esempio la presenza di un proiettore HDMI oppure la presenza dell'aria
 * condizionata.
 *
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 *
 */
public class PresenceFacility extends Facility {

    /**
     * Costruisce una presence facility.
     * 
     * @param codice
     * @param descrizione
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla.
     */
    public PresenceFacility(String codice, String descrizione) {
        super(codice, descrizione);
        if(codice == null || descrizione == null)  // controllo che i valori non siano nulli altrimenti lancio una eccezzione
            throw new NullPointerException();
        // TODO implementare
    }

    /*
     * Una Presence Facility soddisfa una facility solo se la facility passata è
     * una Presence Facility ed ha lo stesso codice.
     * 
     */
    @Override
    public boolean satisfies(Facility o) {
        // TODO implementare
        return this.getClass() == o.getClass() && this.hashCode()==o.hashCode(); // controllo che siano della stessa classe e che icodici coincidino
    }

}
