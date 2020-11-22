package it.unicam.cs.asdl1920.mp1;


import java.util.*;

/**
 * Un gestore di aule gestisce un insieme di aule e permette di cercare aule
 * libere con certe caratteristiche fra quelle che gestisce.
 *
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 *
 */
public class GestoreAule {

    private final Set<Aula> aule;

    /**
     * Crea un gestore vuoto.
     */
    public GestoreAule() {
        // TODO implementare
        aule =new TreeSet<>();
    }

    /**
     * Aggiunge un'aula al gestore.
     * 
     * @param a
     *              una nuova aula
     * @return true se l'aula è stata aggiunta, false se era già presente.
     * @throws NullPointerException
     *                                  se l'aula passata è nulla
     */
    public boolean addAula(Aula a) {
        // TODO implementare
        if(a==null)  // controllo che non sia nulla
            throw new NullPointerException();
        if(getAule().size()==0)  // se e' vuota aggiungo subito l'oggetto
            return getAule().add(a);
        for (Aula i : this.getAule())  // ciclo aula in cerca dell'aula da inserire
        {
            if(i.equals(a))  // se e' presente ritorno false
                return false;
        }
        return getAule().add(a);  // altrimenti aggiungo l'aula
    }

    /**
     * @return the aule
     */
    public Set<Aula> getAule() {
        // TODO implementare
        return this.aule;
    }

    /**
     * Cerca tutte le aule che soddisfano un certo insieme di facilities e che
     * siano libere in un time slot specificato.
     * 
     * @param requestedFacilities
     *                                insieme di facilities richieste che
     *                                un'aula deve soddisfare
     * @param ts
     *                                il time slot in cui un'aula deve essere
     *                                libera
     * 
     * @return l'insieme di tutte le aule gestite da questo gestore che
     *         soddisfano tutte le facilities richieste e sono libere nel time
     *         slot indicato. Se non ci sono aule che soddisfano i requisiti
     *         viene restituito un insieme vuoto.
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  passate è nulla
     */
    public Set<Aula> cercaAuleLibere(Set<Facility> requestedFacilities,
            TimeSlot ts) {
        // TODO implementare
        if(requestedFacilities == null || ts == null)
            throw new NullPointerException();
        TreeSet<Aula> app = new TreeSet<>();  // creo una struttura dio appoggio
        for (Aula a : this.getAule()) // ciclo le aule per cercare quelle che sia libere in un determinitato timeslot(ts) e che soddisfino determinate Facility(requestedfacilities)
            {
            if (a.isFree(ts) && a.satisfiesFacilities(requestedFacilities))
                app. add(a);// se entrambe le condizioni sono soddisfatte , aggiungo l'aula alla variabile d'appoggio
        }
        return app; // ritorno l'insieme delle aule che soddisfano le condizioni richieste
    }

}
