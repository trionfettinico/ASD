/**
 * 
 */
package it.unicam.cs.asdl1920.mp1;

/**
 * Una Quantitative Facility è una facility che contiene una informazione
 * quantitativa. Ad esempio la presenza di 50 posti a sedere oppure la presenza
 * di 20 thin clients.
 * 
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 *
 */
public class QuantitativeFacility extends Facility {

    private final int quantity;

    /**
     * Costruisce una facility quantitativa.
     * 
     * @param codice
     *                        codice identificativo della facility
     * @param descrizione
     *                        descrizione testuale della facility
     * @param quantity
     *                        quantita' associata alla facility
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  {@code codice} e {@code descrizione} è
     *                                  nulla.
     */
    public QuantitativeFacility(String codice, String descrizione,
            int quantity) {
        super(codice, descrizione);
        // TODO implementare
        if(codice == null || descrizione == null) // controllo che i valori non siano nulli e nel caso lancio una eccezione
            throw new NullPointerException();
        this.quantity = quantity;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        // TODO implementare
        return this.quantity;
    }

    /*
     * Questa quantitative facility soddisfa una facility data se e solo se la
     * facility data è una quantitative facility, ha lo stesso codice e la
     * quantità associata a questa facility è maggiore o uguale alla quantità
     * della facility data.
     */
    @Override
    public boolean satisfies(Facility o) {
        // TODO implementare
        return this.getClass().equals((o).getClass()) && this.hashCode()==o.hashCode() && this.getQuantity() >= ((QuantitativeFacility) o).getQuantity(); // controllo che siano della stessa classe e abbiano lo stesso codice e che la quantita assocciata sia maggiore/uguale alla quantita' richiesta
    }

}
