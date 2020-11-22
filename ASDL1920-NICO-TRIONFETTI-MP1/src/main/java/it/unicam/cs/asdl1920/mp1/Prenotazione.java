package it.unicam.cs.asdl1920.mp1;

import java.util.Objects;

/**
 * Una prenotazione riguarda una certa aula per un certo time slot.
 *
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 *
 */
public class Prenotazione implements Comparable<Prenotazione> {

    private final Aula aula;

    private final TimeSlot timeSlot;

    private final String docente;

    private final String motivo;

    /**
     * Costruisce una prenotazione.
     * 
     * @param aula
     *                     l'aula a cui la prenotazione si riferisce
     * @param timeSlot
     *                     il time slot della prenotazione
     * @param docente
     *                     il nome del docente che ha prenotato l'aula
     * @param motivo
     *                     il motivo della prenotazione
     * @throws NullPointerException
     *                                  se uno qualsiasi degli oggetti passati è
     *                                  null
     */
    public Prenotazione(Aula aula, TimeSlot timeSlot, String docente,
            String motivo) {
        // TODO implementare
        if(aula == null || timeSlot == null || docente == null || motivo == null) // controllo che non siano nulle
            throw new NullPointerException();
        this.aula = aula;
        this.timeSlot = timeSlot;
        this.docente = docente;
        this.motivo = motivo;
    }

    /**
     * @return the aula
     */
    public Aula getAula() {
        // TODO implementare
        return this.aula;
    }

    /**
     * @return the timeSlot
     */
    public TimeSlot getTimeSlot() {
     // TODO implementare
        return this.timeSlot;
    }

    /**
     * @return the docente
     */
    public String getDocente() {
     // TODO implementare
        return this.docente;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
     // TODO implementare
        return this.motivo;
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
     // TODO implementare
        return this.timeSlot.hashCode();
    }

    /*
     * L'uguaglianza è data solo da stessa aula e stesso time slot. Non sono
     * ammesse prenotazioni diverse con stessa aula e stesso time slot.
     */
    @Override
    public boolean equals(Object obj) {
     // TODO implementare
        if(obj != null && obj instanceof Prenotazione) // controllo che obj sia diverso da null e che sia un instanza di Prenotazione
            return this.getAula().hashCode()==((Prenotazione)obj).getAula().hashCode()&&this.hashCode()==obj.hashCode(); // controllo che l'hashcode del timeslot e di aula corrisponda all'oggetto passato
        return false;

    }

    /*
     * Una prenotazione precede un altra in base all'ordine dei time slot. Se
     * due prenotazioni hanno lo stesso time slot allora una precede l'altra in
     * base all'ordine tra le aule.
     */
    @Override
    public int compareTo(Prenotazione o) {
     // TODO implementare
        if(this.getTimeSlot().getStart().getTimeInMillis()<o.getTimeSlot().getStart().getTimeInMillis()) // controllo se la prima prenotazione inizia prima dell'altra , in tal caso ritono -1
                return -1;
        if(this.getTimeSlot().equals(o.getTimeSlot())) // nel caso che i due time slot siano uguali
            return  this.getAula().compareTo(o.getAula());// ritono il valore del compareto delle due aule
        return 1; // altimenti ritorno 1
    }

    @Override
    public String toString() {
        return "Prenotazione [aula = " + aula + ", time slot =" + timeSlot
                + ", docente=" + docente + ", motivo=" + motivo + "]";
    }

}
