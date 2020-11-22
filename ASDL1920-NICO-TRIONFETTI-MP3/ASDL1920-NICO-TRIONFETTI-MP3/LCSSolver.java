/**
 * Miniprogetto 3 di Algoritmi e Strutture Dati, Laboratorio Anno Accademico 2019/2020
 */
package it.unicam.cs.asdl1920.mp3;

import java.util.Objects;

/**
 * Un oggetto di questa classe è un risolutore del problema della più lunga
 * sottosequenza comune tra due stringhe date.
 * 
 * @author NICO TRIONFETTI - nico.trionfetti@studenti.unicam.it
 *
 */
public class LCSSolver {

    private final String x;

    private final String y;

    // TODO inserire le altre variabili istanza che servono

    private boolean risolto = false;  // variabile utilizzata per controllare se LCS e' stato svolto

    private int[][] l ;   // matrice utilizzata per determinare le possibili combinzioni delle parole x e y
    private char[][] spostamenti ; // matrice utilizzata per salvare gli spostamenti per trovare LCS

    private int m;  // dimensione di x
    private int n;  // dimensione di y
    private int lMassimo; // dimensione del LCS

    /**
     * Costruisce un risolutore LCS fra due stringhe date.
     * 
     * @param x
     *              la prima stringa
     * @param y
     *              la seconda stringa
     * @throws NullPointerException
     *                                  se almeno una delle due stringhe passate
     *                                  è nulla
     */
    public LCSSolver(String x, String y) {
        if(x==null||y==null)                  // controllo che i parametri passati non siano nulli
            throw new NullPointerException();
        this.x = x;         // assegnazione dei valori alle varibili
        this.y = y;
        // TODO completare

        this.m=x.length();
        this.n=y.length();
        l= new int[m + 1][n + 1];    // inizializzazione delle matrici   (un lato rappresenta x l'altro y )
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
    public boolean equals(Object o) {    //metodo equals della classe che contalle che x = x o x=y e viceversa
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if((this.x==((LCSSolver) o).x&&this.y==((LCSSolver) o).y)||(this.x==((LCSSolver) o).y&&this.y==((LCSSolver) o).x))
            return true;
        return false;
    }

    @Override
    public int hashCode() {    // creo un hashcode per l'oggetto calcolando il fatto che l'hashcode di x e y sia uguale a l'hashcode di y e x
        if(x==y)
            return Objects.hash(x, y);
        int i = x.length();
        int j = y.length();
        if(i==0) // controllo se uno dei due valori sia "" e in tal caso nel generare l'hashcode lo inserisco come primo valore
        {
            return Objects.hash(x,y);
        }else {
            if(j==0)
            {
                return Objects.hash(y,x);
            }
        }
        if(i>j){      // controllo se x sia alfabeticamente maggiore di y e in base al caso che ho creo un opportuno hashcode inserendo x o y per primo se alfabeticamente maggiore
            for (int k=0;k<i;k++){
                if(x.charAt(k)>y.charAt(k)){
                    return Objects.hash(x,y);
                }else {
                    if(x.charAt(k)<y.charAt(k)){
                        return Objects.hash(y,x);
                    }
                }
            }
            return Objects.hash(x,y);
        }else {
            for (int k=0;k<j;k++) {
                if (x.charAt(k) > y.charAt(k)) {
                    return Objects.hash(x, y);
                } else {
                    if (x.charAt(k) < y.charAt(k)) {
                        return Objects.hash(y, x);
                    }
                }
            }
            return Objects.hash(x,y);
        }
    }

    /**
     * Risolve il problema LCS delle due stringhe di questo solver, se non è
     * stato già risolto precedentemente. Dopo l'esecuzione di questo metodo il
     * problema verrà considerato risolto.
     */
    public void solve() {
        // TODO implementare
        if(risolto)     // controllo se il seguento oggetto sia stato 'risolto' e se non lo e' lo risolvo
            return;
        lcs(m,n);
        risolto = true;
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
        return risolto;   // ritorno la variabile che mi dice se e' risolto ( ogni oggetto appena creato ha la variabile risolto impostata a false)
    }

    /**
     * Determina la lunghezza massima delle sottosequenze comuni.
     * 
     * @return la massima lunghezza delle sottosequenze comuni di x e y.
     * @throws IllegalStateException
     *                                   se il solver non ha ancora risolto il
     *                                   problema LCS
     */
    public int getLengthOfSolution() {
        // TODO implementare
        if(!risolto)     // controllo se LCS e' stato risolto
            throw new IllegalStateException();  // creo un eccezzione se non e' stato risolto
        return lMassimo;    // ritorno il valore della lunghezza del LCS (viene calcolata quando viene 'risolto')
    }

    /**
     * Restituisce una soluzione del problema LCS.
     * 
     * @return una sottosequenza di this.x e this.y di lunghezza massima
     * @throws IllegalStateException
     *                                   se il solver non ha ancora risolto il
     *                                   problema LCS
     */
    public String getOneSolution() {   // metodo che ripercorre la matrice creata nella 'risoluzione' del LCS per ritornare la parola LCS
        // TODO implementare
        int i =m;
        int j =n;
        String soluzioneInversa="";
        String soluzione="";
        while (l[i][j]!=0){
            if(spostamenti[i][j]=='c'){
                soluzioneInversa=soluzioneInversa+x.charAt(i-1);
                i=i-1;
                j=j-1;
            }else {
                if(spostamenti[i][j]=='l'){
                    j=j-1;
                }else {
                    i=i-1;
                }
            }
        }
        for (i = soluzioneInversa.length() - 1; i >= 0; i--) {    // ripercorendo la matrice , troviamo la frase ma al contrario quindi giro la stringa
            soluzione += soluzioneInversa.charAt(i);
        }
        return soluzione;
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
    public boolean isCommonSubsequence(String z) {  // per controllare se una determinata parola sia una sottosequenza di x e y creo due nuovi oggetti LCS con x , z e z , y come valori
        // TODO implementare
        if (z == null)
            throw new NullPointerException();
        if (z == "")
            return true;
        LCSSolver app1 = new LCSSolver(x,z);
        app1.lcs(x.length(),z.length());
        if(app1.lMassimo != z.length())  // controllo se la lunghezza del LCS sia uguale alla lunghezza di z
            return false;
        LCSSolver app2 = new LCSSolver(z,y); // controllo se la lunghezza del LCS sia uguale alla lunghezza di z
        app2.lcs(z.length(),y.length());
        if(app2.lMassimo!=z.length())
            return false;
        return true;  // nel caso che nessuna delle precedeni eccezzioni e' presente vuol dire che x e y hanno come sottosequenza z e ritorno true
    }

    // TODO aggiungere eventuali metodi privati accessori che servono per
    // l'implementazione dei metodi pubblici
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
