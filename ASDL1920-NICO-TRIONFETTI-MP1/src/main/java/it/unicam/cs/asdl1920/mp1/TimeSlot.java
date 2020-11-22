/**
 * 
 */
package it.unicam.cs.asdl1920.mp1;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Un time slot è un intervallo di tempo continuo che può essere associato ad
 * una prenotazione o a una facility. Gli oggetti della classe sono immutabili.
 * Non sono ammessi time slot che iniziano e finiscono nello stesso istante.
 *
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 *
 */
public class TimeSlot implements Comparable<TimeSlot> {

    /**
     * Rappresenta la soglia di tolleranza da considerare nella sovrapposizione
     * di due Time Slot. Se si sovrappongono per un numero di minuti minore o
     * uguale a questa soglia allora NON vengono considerati sovrapposti.
     */
    public static final int MINUTES_OF_TOLERANCE_FOR_OVERLAPPING = 5;

    private final GregorianCalendar start;

    private final GregorianCalendar stop;

    /**
     * Crea un time slot tra due istanti di inizio e fine
     * 
     * @param start
     *                  inizio del time slot
     * @param stop
     *                  fine del time slot
     * @throws NullPointerException
     *                                      se uno dei due istanti, start o
     *                                      stop, è null
     * @throws IllegalArgumentException
     *                                      se start è uguale o successivo a
     *                                      stop
     */
    public TimeSlot(GregorianCalendar start, GregorianCalendar stop) {
        // TODO implementare
        if(start == null || stop == null) // controllo che i valori non siano nulli e nel caso lancio una eccezzione
            throw new NullPointerException();
        if(start.getTimeInMillis()>=stop.getTimeInMillis()) // controllo che il valore di start sia minore di stop e nel caso lancio una eccezzione del tipo IllegalArgumentException
            throw new IllegalArgumentException();
        this.start = start;
        this.stop = stop;
    }

    /**
     * @return the start
     */
    public GregorianCalendar getStart() {
        // TODO implementare
        return this.start;
    }

    /**
     * @return the stop
     */
    public GregorianCalendar getStop() {
        // TODO implementare
        return this.stop;
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
        // TODO implementare
        return Objects.hash(getStart(),getStop());   // creo un codice identificativo in base allo start e allo stop
    }

    /*
     * Un time slot è uguale a un altro se rappresenta esattamente lo stesso
     * intervallo di tempo, cioè se inizia nello stesso istante e termina nello
     * stesso istante.
     */
    @Override
    public boolean equals(Object obj) {
        // TODO implementare
        if(obj==null||this.getClass() != obj.getClass()) // controllo che obj non sia null e che sia della classe timeslot
            return false;
        return this.hashCode()==obj.hashCode();  // controllo che abbia lo stesso hashcode
    }

    /*
     * Un time slot precede un altro se inizia prima. Se due time slot iniziano
     * nello stesso momento quello che finisce prima precede l'altro. Se hanno
     * stesso inizio e stessa fine sono uguali, in compatibilità con equals.
     */
    @Override
    public int compareTo(TimeSlot o) {
        // TODO implementare
        if(getStart().compareTo(o.getStart())>0)   // se lo start del oggetto e' maggiore di quello passato
            return 1;
        if (this.equals(o))  // se questa condizione e' soddisfatte gli oggetti sono uguali
            return 0;
        if(getStart().getTime().equals(o.getStart().getTime()))
            if (getStop().compareTo(o.getStop())>0) // se hanno lo stesso inizio e il primo finisce prima del secondo ritorna uno
                return 1;
            return -1; // altrimenti se lo start del oggetto passato e' maggiore del primo oggetto o se hanno lo stesso start ma lo stop del oggetto passato finisce prima
    }

    /**
     * Determina se questo time slot si sovrappone a un altro time slot dato,
     * considerando la soglia di tolleranza.
     * 
     * @param o
     *              il time slot che viene passato per il controllo di
     *              sovrapposizione
     * @return true se questo time slot si sovrappone per più di
     *         MINUTES_OF_TOLERANCE_FOR_OVERLAPPING minuti a quello passato
     * @throws NullPointerException
     *                                  se il time slot passato è nullo
     */
    public boolean overlapsWith(TimeSlot o) {
        // TODO implementare
        if(o.equals(null))  // se l'oggetto passato e' nullo
            throw new NullPointerException();
        if((getStop().getTimeInMillis()-getStart().getTimeInMillis())<=MINUTES_OF_TOLERANCE_FOR_OVERLAPPING*60000||(o.getStop().getTimeInMillis()-o.getStart().getTimeInMillis())<=MINUTES_OF_TOLERANCE_FOR_OVERLAPPING*60000) // se uno dei due intevalli e' minore / uguale a 5 minuti va bene perche ci sono 5 minuti di tolleranza per le prenotazioni
            return false;
        if(getStart().compareTo(o.getStart())<0)  // controllo le varie sovrapposizioni possibili dei intervalli tenendo conto dei 5 minuti di tolleranza
        {
            if((getStop().getTimeInMillis()-o.getStart().getTimeInMillis())<=MINUTES_OF_TOLERANCE_FOR_OVERLAPPING*60000)
                return false;
        }
        else
        {
            if((o.getStop().getTimeInMillis()-getStart().getTimeInMillis())<=MINUTES_OF_TOLERANCE_FOR_OVERLAPPING*60000)
                return false;
        }
        return true;
    }

    /*
     * Esempio di stringa: [4/11/2019 11.0 - 4/11/2019 13.0] Esempio di stringa:
     * [10/11/2019 11.15 - 10/11/2019 23.45]
     */
    @Override
    public String toString() {
        // TODO implementare
        // sfrutto la classe Calendar per prendere i singoli valori della data che mi servono facendo attenzione al mese che parte da 0 (aggiungendo 1)
        return "["+this.getStart().get(Calendar.DATE)+"/"+((int)this.getStart().get(Calendar.MONTH)+1)+"/"+this.getStart().get(Calendar.YEAR)+' '+getStart().get(Calendar.HOUR_OF_DAY)+'.'+this.getStart().get(Calendar.MINUTE)+" - "+this.getStop().get(Calendar.DATE)+"/"+((int)this.getStop().get(Calendar.MONTH)+1)+"/"+this.getStop().get(Calendar.YEAR)+' '+getStop().get(Calendar.HOUR_OF_DAY)+'.'+this.getStop().get(Calendar.MINUTE)+"]";
    }

}
