/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Un oggetto di questa classe è un risolutore del problema di trovare
 * <b>tutte</b> le più lunghe sottosequenze comuni tra due stringhe date.
 * 
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 */
public class AllLCSsSolver {

    private final String x;

    private final String y;

    // TODO inserire le altre variabili istanza che servono

    private boolean risolto = false;   // variabile utilizzata per controllare se LCS e' stato svolto

    private int[][] l ;  // matrice utilizzata per determinare le possibili combinzioni delle parole x e y
    private char[][] spostamenti ; // matrice utilizzata per salvare gli spostamenti per trovare LCS

    private int m;  // dimensione di x
    private int n;  // dimensione di y
    private int lMassimo; // dimensione del LCS

    /**
     * Costruisce un risolutore All LCS fra due stringhe date.
     * 
     * @param x
     *              la prima stringa
     * @param y
     *              la seconda stringa
     * @throws NullPointerException
     *                                  se almeno una delle due stringhe passate
     *                                  è nulla
     */
    public AllLCSsSolver(String x, String y) {
        if(x==null||y==null)
            throw new NullPointerException();  // controllo che i parametri passati non siano nulli
        this.x = x;  // assegnazione dei valori alle varibili
        this.y = y;
        // TODO completare
        m=x.length();
        n=y.length();
        l = new int[m+1][n+1];  // inizializzazione delle matrici   (un lato rappresenta x l'altro y )
        spostamenti = new char[m+1][n+1];

    }

    /**
     * @return the string x
     */
    public String getX() {
        return x;
    }

    /**
     * @return the string y
     */
    public String getY() {
        return y;
    }

    // TODO reimplementare i metodi equals ed hashcode facendo in modo che due
    // solver siano uguali se e solo se hanno le stesse stringhe, in un
    // qualsiasi ordine esse siano date. Ad esempio, se in questo risolutore x =
    // "pippo" e y = "pluto allora un risolutore in cui x =
    // "pippo" e y = "pluto e un altro risolutore in cui x = "pluto" e y =
    // "pippo" sono entrambi da considerarsi uguale a questo risolutore.


    @Override
    public boolean equals(Object o) {  //metodo equals della classe che contalle che x = x o x=y e viceversa
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if((this.x==((AllLCSsSolver) o).x&&this.y==((AllLCSsSolver) o).y)||(this.x==((AllLCSsSolver) o).y&&this.y==((AllLCSsSolver) o).x))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Risolve il problema di trovare tutte le LCSs delle due stringhe di questo
     * solver, se non è stato già risolto precedentemente. Dopo l'esecuzione di
     * questo metodo il problema verrà considerato risolto.
     */
    public void solve() {
        // TODO implementare
        if(risolto)   // controllo se il seguento oggetto sia stato 'risolto' e se non lo e' lo risolvo
            return;
        lcs(m,n);
        risolto=true;
        return;
    }

    /**
     * Determina se questo solver ha già risolto il problema.
     * 
     * @return true se il problema LCS di questo solver è già stato risolto
     *         precedentemente, false altrimenti
     */
    public boolean isSolved() {
        // TODO implementare
        return risolto; // ritorno la variabile che mi dice se e' risolto ( ogni oggetto appena creato ha la variabile risolto impostata a false)
    }

    /**
     * Determina la lunghezza massima delle sottosequenze comuni.
     * 
     * @return la massima lunghezza delle sottosequenze comuni di this.x e
     *         this.y.
     * @throws IllegalStateException
     *                                   se il solver non ha ancora risolto il
     *                                   problema LCS
     */
    public int getLengthOfSolutions() {
        // TODO implementare
        if(!risolto)     // controllo se LCS e' stato risolto
            throw new IllegalStateException();  // creo un eccezzione se non e' stato risolto
        return lMassimo;    // ritorno il valore della lunghezza del LCS (viene calcolata quando viene 'risolto')
    }

    /**
     * Restituisce la soluzione del problema di tutte le LCSs.
     * 
     * @return un insieme che contiene tutte le sottosequenze di this.x e this.y
     *         di lunghezza massima
     * @throws IllegalStateException
     *                                   se il solver non ha ancora risolto il
     *                                   problema All LCSs
     */
    public Set<String> getSolutions() {
        // TODO implementare
        if(!risolto)  // controllo se sia stato 'risolto' e nel caso che non lo e' creo un eccezzione
            throw new IllegalStateException();
        return findLCS(x,y,m,n);    // metodo che mi ritorna tutte le LCS
    }

    /**
     * Determina se una certa stringa è una sottosequenza comune delle due
     * stringhe di questo solver.
     * 
     * @param z
     *              la string da controllare
     * @return true se z è sottosequenza di this.x e di this.y, false altrimenti
     * @throws NullPointerException
     *                                  se z è null
     */
    public boolean isCommonSubsequence(String z) {  // per controllare se una determinata parola sia una sottosequenza di x e y creo due nuovi oggetti AllLCS con x , z e z , y come valori
        // TODO implementare
        if (z == null)
            throw new NullPointerException();
        if (z == "")
            return true;
        AllLCSsSolver app1 = new AllLCSsSolver(x,z);
        app1.lcs(x.length(),z.length());
        if(app1.lMassimo != z.length())  // controllo se la lunghezza del LCS sia uguale alla lunghezza di z
            return false;
        AllLCSsSolver app2 = new AllLCSsSolver(z,y); // controllo se la lunghezza del LCS sia uguale alla lunghezza di z
        app2.lcs(z.length(),y.length());
        if(app2.lMassimo!=z.length())
            return false;
        return true;  // nel caso che nessuna delle precedeni eccezzioni e' presente vuol dire che x e y hanno come sottosequenza z e ritorno true
    }

    /**
     * Determina se tutte le stringhe di un certo insieme hanno tutte la stessa
     * lunghezza e sono sottosequenze di entrambe le stringhe di questo solver.
     * 
     * @param sequences
     *                      l'insieme di stringhe da testare
     * @return true se tutte le stringe in sequences hanno la stessa lunghezza e
     *         sono sottosequenze di this.x e this.y, false altrimenti
     * @throws NullPointerException
     *                                  se l'insieme passato è nullo.
     */
    public boolean checkLCSs(Set<String> sequences) {
        // TODO implementare
        if (sequences == null)  // controllo che la sequanza non sia nulla
            throw new NullPointerException();
        int lApp = -1;
        for(String app : sequences) {    // foreach per eseguire il metodo isCommonSubsequence su ogni elemento della sequenza
            AllLCSsSolver app2 = new AllLCSsSolver(x,y);
            if(!app2.isCommonSubsequence(app))
                return false;
            if(lApp==-1)  // controlli per vedere che tutte le sequenza abbiano la stessa lunghezza
                lApp=app.length();
            else
                if (lApp != app.length())
                    return false;
        }
        return true;
    }

    // TODO aggiungere eventuali metodi privati accessori che servono per
    // l'implementazione dei metodi pubblici

    private Set<String> findLCS(String X, String Y, int m, int n)  // metodo ricorsivo per la ricerca di tutte le LCS
    {
        Set<String> lista = new HashSet<>(); // variabile per inserire tutte le LCS

        if (m == 0 || n == 0)   // inserisco il campo "" nella lista se non ci sono altri LCS
        {
            lista.add("");
            return lista;
        }

        if (X.charAt(m - 1) == Y.charAt(n - 1))  // controllo se l'ultimo questi due caratteri delle stringhe coincidano
        {
            Set<String> tmp = findLCS(X, Y, m - 1, n - 1);  // richiamo il metodo findlcs su una porzione sempre piu piccola
            for (String str : tmp)  //ciclo i valori di tmp e li inserisco all'interno della lista
                lista.add(str + X.charAt(m - 1));
        }
        else
        {
            if (l[m - 1][n] >= l[m][n - 1])
                lista = findLCS(X, Y, m - 1, n);
            if (l[m][n - 1] >= l[m - 1][n])
            {
                Set<String> tmp = findLCS(X, Y, m, n - 1);
                lista.addAll(tmp);
            }
        }
        return lista;
    }

    private void lcs(int m, int n)  // metodo per la creazioni delle matrici l e spostamenti
    {
        for(int i = 0 ; i<m;i++) {        // inizializzi la matrice l con tutti valori 0
            for (int j = 0; j < n; j++) {
                l[i][j] = 0;
            }
        }
        for (int i =0;i<=m;i++){   //ciclo la matrice per tutto il suo volumo con questi due for
            for (int j =0;j<=n;j++){
                if(i!=0&&j!=0) {   // se i = 0 e j = 0 non viene eseguita nessuna azione
                    if (x.charAt(i-1) == y.charAt(j-1)) {  // controllo se i caratteri della stringa x e y in una determina cella ( ricordiamo che un lato rappresenta x e l'altro rappresenta y)
                        l[i][j] = l[i - 1][j - 1] + 1;   // aumento il valore della cella di 1 rispetto alla cella che si trova nell'angolo in altro a sinistra di questa cella
                        spostamenti[i][j] = 'c';         // inserisco il carattere c nella ominima cella della matrice spostamenti
                    } else {
                        if (l[i - 1][j] >= l[i][j - 1]) {  // analizzo gli atri due casi possibili ( u = under  e l = left ) in base a il valore delle celle vicine
                            l[i][j] = l[i - 1][j];
                            spostamenti[i][j] = 'u';
                        } else {
                            l[i][j] = l[i][j - 1];
                            spostamenti[i][j] = 'l';
                        }
                    }
                }
                if(j==n&&i==m)       // una volta completata la matrice memorizzo il valore della cella in posizione [m][n] che rappresenta la dimensione del LCS
                    lMassimo=l[i][j];
            }
        }
        return;
    }
}
