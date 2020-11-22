package it.unicam.cs.asdl1920.mp1;

import java.util.*;

/**
 * Un oggetto della classe aula rappresenta una certa aula con le sue facilities
 * e le sue prenotazioni.
 *
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 *
 */
public class Aula implements Comparable<Aula> {
    // Identificativo unico di un'aula
    private final String nome;

    // Location dell'aula
    private final String location;

    // Insieme delle facilities di quest'aula
    private final Set<Facility> facilities;

    // Insieme delle prenotazioni per quest'aula, segue l'ordinamento naturale
    // delle prenotazioni
    private final SortedSet<Prenotazione> prenotazioni;

    /**
     * Costruisce una certa aula con nome e location. Il set delle facilities è
     * vuoto. L'aula non ha inizialmente nessuna prenotazione.
     * 
     * @param nome
     *                     il nome dell'aula
     * @param location
     *                     la location dell'aula
     * 
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla
     */
    public Aula(String nome, String location) {
        // TODO implementare
        // controllo che i parametri passati non siano nulli
        if(nome==(null)||location==(null))
            throw new NullPointerException(); // nel caso che uno dei due sia nullo , mando un eccezzione del tipo NullPointerException
        // nel caso che i parametri non siano nulli , assegni questi valore alle variabili della classe
        this.location = location;
        this.nome = nome;
        this.prenotazioni = new TreeSet<>();  // per prenotazioni e facilities creo due strutture vuote
        this.facilities = new HashSet<>();
    }

    /**
     * Costruisce una certa aula con nome, location e insieme delle facilities.
     * L'aula non ha inizialmente nessuna prenotazione.
     * 
     * @param nome
     *                       il nome dell'aula
     * @param location
     *                       la location dell'aula
     * @param facilities
     *                       l'insieme delle facilities dell'aula
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla
     */
    public Aula(String nome, String location, Set<Facility> facilities) {
        // TODO implementare
        if(nome == null || location == null ||facilities==(null))   // stessa cosa del metodoro costruttore precedente ma in piu controllo anche facilities
            throw new NullPointerException();
        this.location = location;
        this.nome = nome;
        this.prenotazioni = new TreeSet<>();
        this.facilities = facilities;
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
        // TODO implementare
        return this.nome.hashCode();   // creo il metodo hashcode che mi identifica questa classe con un codice univoco (il nome)
    }

    /* Due aule sono uguali se e solo se hanno lo stesso nome */
    @Override
    public boolean equals(Object obj) {
        // TODO implementare
        if(obj instanceof Aula)
            return this.hashCode()==((Aula)obj).hashCode();     // due oggetti sono uguali se sono della stessa instanza e se hanno lo stesso hashcode
        return false;
    }

    /* L'ordinamento naturale si basa sul nome dell'aula */
    @Override
    public int compareTo(Aula o) {
        // TODO implementare
        return this.nome.compareTo(o.getNome());      // per il compareto di questa classe dobbiamo ordinare in base a nome , il quindi andremo ad utilizzare il compareto della classe string(di nome)
    }

    /**
     * @return the facilities
     */
    public Set<Facility> getFacilities() {
        // TODO implementare
        return this.facilities;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        // TODO implementare
        return this.nome;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        // TODO implementare
        return this.location;
    }

    /**
     * @return the prenotazioni
     */
    public SortedSet<Prenotazione> getPrenotazioni() {
        // TODO implementare
        return this.prenotazioni;
    }

    /**
     * Aggiunge una faciltity a questa aula.
     * 
     * @param f
     *              la facility da aggiungere
     * @return true se la facility non era già presente e quindi è stata
     *         aggiunta, false altrimenti
     * @throws NullPointerException
     *                                  se la facility passata è nulla
     */
    public boolean addFacility(Facility f) {
        // TODO implementare
        if(f==null)  // nel caso che la Facility f sia vuota si lancia l'eccezione NullPointerException
            throw new NullPointerException();
        for(Facility fa : getFacilities()) // utlizzo il foreach per ciclare tutto la struttura della facility (il foreach sfrutta i Iteratori)
        {
            if(fa.equals(f))  // se "fa"(oggetto d'appoggio delle facility della classe) e' uguale d f (return false) --> sfrutto l' equals della classe Facility
                return false;
        }
        return getFacilities().add(f);  // se la facility non e' presente , aggiungo f alla lista delle facility
    }

    /**
     * Determina se l'aula è libera in un certo time slot.
     * 
     * 
     * @param ts
     *               il time slot da controllare
     * 
     * @return true se l'aula risulta libera per tutto il periodo del time slot
     *         specificato
     * @throws NullPointerException
     *                                  se il time slot passato è nullo
     */
    public boolean isFree(TimeSlot ts) {
        // TODO implementare - sfruttare l'ordinamento tra le prenotazioni per
        // rispondere in maniera efficiente
        if(ts == null)   // creo l'eccezzione se il timeslot ts e' nullo
            throw new NullPointerException();
        GregorianCalendar g = new GregorianCalendar();    //
        g.setTime(new Date(Long.MAX_VALUE));              //  creo un nuovo timeslot che parte dalla fine del time slot ts e finisce al valore piu' grande del valore long
        TimeSlot time = new TimeSlot(ts.getStop(), g);    //
        TreeSet<Prenotazione> appP = new TreeSet<>(prenotazioni.headSet(new Prenotazione(this, time, "", "")));  // sfrutto l'headset per creare un nuovo treeset di dimensioni minore (con ultimo valore la prenotazione prima del timeSlot creato in precedenza)
        appP = new TreeSet<>(appP.descendingSet());  // giro al contrario l'albero con il comando descendingset()
        for (Prenotazione app : appP) {  // ciclo con il foreach il nuovo albero generato
            if (app.getTimeSlot().overlapsWith(ts))  // se trovo una sovrapposizione ritorno false (l'aula non e' libera)
                return false;
            if (app.getTimeSlot().getStop().compareTo(ts.getStart()) < 0) // nel caso che non ci siano sovraposizioni utilizzo questo controllo per terminare il foreach appena supero il timeslot che mi interessa
                break;
        }
        return true; // il timeslot da me ricercato e' libero
    }

    /**
     * Determina se questa aula soddisfa tutte le facilities richieste
     * rappresentate da un certo insieme dato.
     * 
     * @param requestedFacilities
     *                                l'insieme di facilities richieste da
     *                                soddisfare
     * @return true se e solo se tutte le facilities di
     *         {@code requestedFacilities} sono soddisfatte da questa aula.
     * @throws NullPointerException
     *                                  se il set di facility richieste è nullo
     */
    public boolean satisfiesFacilities(Set<Facility> requestedFacilities) {
        // TODO implementare
        if(requestedFacilities == null)  // nel caso che sia nullo creo l"eccezzione
            throw new NullPointerException();
        int i = 0; // variabile di controllo
        int y = 0; // variabile di controllo
        for (Facility f : requestedFacilities){  // ciclo per tutte le faciliti date
            y++;
            for (Facility fa : getFacilities() ) { // per ogni facility data controllo che sia presente nelle faciliti della classe
                if (fa.satisfies(f))  // controllo che sia presente
                {
                    i++;  // variabile di controllo
                    break; // se e' presente interompo il secondo foreach
                }
            }
            if(i!=y)  // sed una delle facilioty non e' soddisfatta ritono subito false
                return false;
        }
        return true; // ritorno true se e' soddisfatta
    }

    /**
     * Prenota l'aula controllando eventuali sovrapposizioni.
     * 
     * @param ts
     * @param docente
     * @param motivo
     * @throws IllegalArgumentException
     *                                      se la prenotazione comporta una
     *                                      sovrapposizione con un'altra
     *                                      prenotazione nella stessa aula.
     * @throws NullPointerException
     *                                      se una qualsiasi delle informazioni
     *                                      richieste è nulla.
     */
    public void addPrenotazione(TimeSlot ts, String docente, String motivo) {
        // TODO implementare
        int i = 0;
        if(ts==null||docente==null||motivo==null)  // controllo che i parametri non siano nulli
            throw new NullPointerException();
        for (Prenotazione p : this.getPrenotazioni()) // cilco per ogni prenotazione
        {
            if(p.getTimeSlot().overlapsWith(ts))  // controllo che non cu sia una sovraposizione
                throw new IllegalArgumentException();  // altrimenti lancio l'eccezzione IllegalArgumentException
        }
        this.getPrenotazioni().add(new Prenotazione(this,ts,docente,motivo));  // nel caso che tutte le condizioni precedenti siano soddisfatte aggiungo la nuova prenotazione
    }

    /**
     * Cancella una prenotazione di questa aula.
     * 
     * @param p
     *              la prenotazione da cancellare
     * @return true se la prenotazione è stata cancellata, false se non era
     *         presente.
     * @throws NullPointerException
     *                                  se la prenotazione passata è null
     */
    public boolean removePrenotazione(Prenotazione p) {
        // TODO implementare
        if(p==null) // controllo che non sia null
            throw new NullPointerException();
        GregorianCalendar g = new GregorianCalendar();                //
        g.setTime(new Date(Long.MAX_VALUE));                          // creo un  nuovo timeslot che parte dallo stop della prenotazione p e finisce al massimo valore di long
        TimeSlot time = new TimeSlot(p.getTimeSlot().getStop(), g);   //
        TreeSet<Prenotazione> appP = new TreeSet<>(getPrenotazioni().headSet(new Prenotazione(this, time, "", "")));  // creo una nuovostruttua che inizia all'inizio dell'albero prenotazione e finisce prima del nuovo time slot generato
        appP = new TreeSet<>(appP.descendingSet());  // giro al contrario l'albero con il comando descendingset()
        for (Prenotazione app : appP) // ciclo la nuova struttura
        {
            if(app.equals(p))  // se le due prenotazioni sono uguali la rimuovo e termino
                return getPrenotazioni().remove(p);
            if(p.getTimeSlot().getStop().compareTo(app.getTimeSlot().getStart())<0) // se supero l'intervallo d tempo che mi interesa termino e ritorno false
                return false;
        }
        return false;

    }

    /**
     * Rimuove tutte le prenotazioni di questa aula che iniziano prima (o
     * esattamente in) di un punto nel tempo specificato.
     * 
     * @param timePoint
     *                      un certo punto nel tempo
     * @return true se almeno una prenotazione è stata cancellata, false
     *         altrimenti.
     * @throws NullPointerException
     *                                  se il punto nel tempo passato è nullo.
     */
    public boolean removePrenotazioniBefore(GregorianCalendar timePoint) {
        // TODO implementare - sfruttare l'ordinamento tra le prenotazioni per
        // eseguire in maniera efficiente
        TreeSet<Prenotazione> app = new TreeSet<>();    // uguale al removo prenotazione solo che vafo ad aggiungere i valori che devo rimuovere in una struttura d'appoggio
        Date d = new Date(Long.MAX_VALUE);
        GregorianCalendar g = new GregorianCalendar();
        g.setTime(d);
        TimeSlot time = new TimeSlot(timePoint, g);
        TreeSet<Prenotazione> appP = new TreeSet<>(getPrenotazioni().headSet(new Prenotazione(this, time, "", "")));
        appP = new TreeSet<>(appP.descendingSet());
        for (Prenotazione p : appP) {
            if (p.getTimeSlot().getStart().compareTo(timePoint) <= 0)  // controllo che mi trovo nell'intervallo che mi interessa
                app.add(p);
            else
                break;  // altrimenti termino il foreach
        }
        return getPrenotazioni().removeAll(app);  // rimuovo tutte le prenotazione contenuto nelle variabile d'appoggio in una sola volta grazie ad removeall


    }
}
