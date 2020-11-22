package it.unicam.cs.asdl1920.mp2;

import java.sql.Blob;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Un RBTree, un albero rosso-nero, è un albero binario di ricerca con le
 * seguenti proprietà:
 * 
 * 1- Ciascun nodo è rosso o nero e la radice è sempre nera
 * 
 * 2- Ciascuna foglia NIL (cioè una foglia che non contiene una etichetta di
 * tipo E) è nera
 * 
 * 3- Se un nodo è rosso allora entrambi i figli sono neri
 * 
 * 4- Ogni cammino da un nodo ad una foglia sua discendente contiene lo stesso
 * numero di nodi neri (contando anche le foglie NIL)
 * 
 * Si può dimostrare che le operazioni di inserimento, ricerca e rimozione di un
 * valore in un RBTree hanno un costo O(lg n), dove n è il numeo di nodi
 * dell'albero. Ciò è dovuto al fatto che la cosiddetta altezza nera (cioè il
 * numero dei nodi neri incontrati in un cammino) viene mantenuta uguale per
 * tutti i cammini dalla radice alle foglie.
 * 
 * Per maggiori dettagli si veda il Cap. 13 di T.H. Cormen, C.E. Leiserson, R.L.
 * Rivest, C. Stein, Introduzione agli Algoritmi e Strutture Dati (terza
 * edizione), McGraw-Hill, 2010 -
 * https://www.mheducation.it/9788838665158-italy-introduzione-agli-algoritmi-e-strutture-dati-3ed
 * 
 * In questa implementazione degli RBTree è possibile inserire elementi
 * duplicati, ma non è possibile inserire elementi null.
 * 
 * @author NICO TRIONFETTI    nico.trionfetti@studenti.unicam.it
 *
 * @param <E>
 *                il tipo delle etichette dei nodi dell'albero. La classe E deve
 *                avere un ordinamento naturale implementato tramite il metodo
 *                compareTo. Tale ordinamento è quello usato nell'RBTree per
 *                confrontare le etichette dei nodi.
 * 
 * 
 */
public class RBTree<E extends Comparable<E>> {
    /*
     * Costanti e metodi static.
     */

    /*
     * E' un flag che rappresenta il colore rosso dei nodi negli RBTree.
     */
    protected static final boolean RED = true;

    /*
     * E' un flag che rappresenta il colore rosso dei nodi negli RBTree.
     */
    protected static final boolean BLACK = false;

    /**
     * Determina se un nodo di un albero RBTree è rosso.
     * 
     * @param x
     *              un nodo di un albero RBTree
     * 
     * @return true se il nodo passato è colorato di rosso, false altrimenti
     */
    protected static boolean isRed(
            @SuppressWarnings("rawtypes") RBTree.RBTreeNode x) {
        if (isNil(x))
            return false;
        return x.color == RED;
    }

    /**
     * Determina se un nodo di un albero RBTree è nero.
     * 
     * @param x
     *              un nodo di un albero RBTree
     * 
     * @return true se il nodo passato è colorato di nero oppure se è una foglia
     *         NIL (che è sempre considerata un nodo nero in un RBTree), false
     *         altrimenti
     */
    protected static boolean isBlack(
            @SuppressWarnings("rawtypes") RBTree.RBTreeNode x) {
        if (isNil(x))
            return true;
        return x.color == BLACK;
    }

    /**
     * Determina se un certo nodo è una foglia NIL.
     * 
     * E' possibile rappresentare le foglie NIL sia con il valore null sia con
     * un particolare nodo "sentinella". Questa API è indipendente dalla
     * particolare scelta implementativa poiché fa riferimento al concetto
     * astratto di foglia NIL negli alberi rosso-neri.
     * 
     * @param n
     *              un puntatore a un nodo di un RBTRee
     * 
     * @return true se il puntatore passato punta a una foglia NIL.
     */
    protected static boolean isNil(
            @SuppressWarnings("rawtypes") RBTree.RBTreeNode n) {
        // TODO implementare usando la sentinella o null (sentinella
        // consigliata)
        //utilizzo null per controllare se si tratta di una foglia
            return n == null;

    }

    /*
     * Variabili istanza e metodi non static.
     */



    /*
     * Il nodo radice di questo albero. Se null, allora l'albero è vuoto.
     */
    private RBTreeNode root;

    /*
     * Il numero di elementi in questo RBTree, diverso dal numero di nodi poiché
     * un elemento può essere inserito più di una volta e ciò non incrementa il
     * numero di nodi, ma solo un contatore di molteplicità nel nodo che
     * contiene l'elemento ripetuto.
     */
    private int size;

    /*
     * Il numero di nodi in questo RBTree.
     */
    private int numberOfNodes;

    /**
     * Costruisce un RBTree vuoto.
     */
    public RBTree() {
        // TODO implementare
        this.root=null;         // inizializzo un albero vuoto
        this.size=0;
        this.numberOfNodes=0;
    }

    /**
     * Costruisce un RBTree che consiste solo di un nodo radice.
     * 
     * @param rootElement
     *                        l'informazione associata al nodo radice
     * @throws NullPointerException
     *                                  se l'elemento passato è null
     */
    public RBTree(E element) {
        // TODO implementare
        if(element==null) throw new NullPointerException();       // creo un albero con un singolo elemento
        this.root = new RBTreeNode(element);
        this.root.color = BLACK;
        this.size=1;
        this.numberOfNodes=1;
    }

    /**
     * Determina se questo RBTree contiene un certo elemento.
     * 
     * @param el
     *               l'elemento da cercare
     * @return true se l'elemento è presente in questo RBTree, false altrimenti.
     * @throws NullPointerException
     *                                  se l'elemento {E el} è null
     */
    public boolean contains(E el) {
        // TODO implementare
        if(el==null) throw new NullPointerException();        // controllo se un determinato elemento e' contenuto dentro l'albero
        if(this.root==null) return false;
        return this.getNodeOf(el) != null;    // sfrutto il metodo getnodeof
    }

    /**
     * Restituisce il nodo che è etichettato con un certo elemento dato.
     * 
     * @param el
     *               l'elemento che etichetta il nodo cercato
     * 
     * @return il puntatore al nodo che è etichettato con l'elemento dato,
     *         oppure null se nell'albero non c'è nessun nodo etichettato con
     *         quell'elemento
     * 
     * @throws NullPointerException
     *                                  se l'elemento passato è null
     * 
     */
    protected RBTreeNode getNodeOf(E el) {
        // TODO implementare
        if(el == null) throw new NullPointerException();               // metodo che per prima cosa controlla se la root e' vuota
        RBTreeNode app = root;
        if(app==null) return null;
        while (true){                           //controllo se l'elemento e' nullo e in tal caso ritorno null
            if(app==null)
                return null;
            if(app.getEl().equals(el))                     // se trovo l'elemento che sto cercando ritorno il nodo corrispondente
                return app;
            app = app.search(el);                  // metodo per spostarmi a destra o sinistra
        }
    }

    /**
     * Determina il numero di occorrenze di un certo elemento in questo RBTree.
     * 
     * @param el
     *               l'elemento di cui determinare il numero di occorrenze
     * @return il numero di occorrenze dell'elemento {E el} in questo RBTree,
     *         zero se non è presente.
     * @throws NullPointerException
     *                                  se l'elemento {E el} è null
     */
    public int getCount(E el) {
        // TODO implementare
        if(el == null) throw new NullPointerException();   // ritorno il il nodo dell'elemento ricercato e de prendo il valore count
        if(this.getNodeOf(el)==null)
            return 0;
        return this.getNodeOf(el).getCount();
    }

    /**
     * Restituisce l'altezza nera di questo RBTree, cioè il numero di nodi
     * colorati di nero in un qualsiasi cammino dalla radice a una foglia NIL.
     * Se questo RBTree è vuoto viene restituito il valore -1. L'altezza nera è
     * sempre maggiore o uguale di 1 in un albero non vuoto.
     * 
     * @return l'altezza di questo RBTree, -1 se questo RBTree è vuoto.
     */
    public int getBlackHeight() {
        // TODO implementare
        if(this.root==null)             // richiamo il metodo getBlackHeight del nodo root se root e' diverso da null
            return -1;
        return this.root.getBlackHeight();
    }

    /**
     * Restituisce l'elemento minimo presente in questo RBTree.
     * 
     * @return l'elemento minimo in questo RBTree
     * @return null se questo RBTree è vuoto.
     */
    public E getMinimum() {
        // TODO implementare
        if(this.root == null) return null;    // se  questo albero contiene almeno un elemento richiamo la ricerca del minimo in questo albero dal root
        RBTreeNode app = root.getMinimum();
        return app.getEl();
    }

    /**
     * Restituisce l'elemento massimo presente in questo RBTree.
     * 
     * @return l'elemento massimo in questo RBTree
     * @return null se questo RBTree è vuoto.
     */
    public E getMaximum() {
        // TODO implementare
        if(this.root == null) return null;    // se  questo albero contiene almeno un elemento richiamo la ricerca del massimo in questo albero dal root
        RBTreeNode app = root.getMaximum();
        return app.getEl();
    }

    /**
     * Restituisce il numero di nodi in questo RBTree.
     * 
     * @return il numero di nodi in questo RBTree.
     */
    public int getNumberOfNodes() {
        return this.numberOfNodes;
    }

    /**
     * Restituisce l'elemento <b>strettamente</b> predecessore, in questo
     * RBTree, di un dato elemento. Si richiede che l'elemento passato sia
     * presente nell'albero.
     * 
     * @param el
     *               l'elemento di cui si chiede il predecessore
     * @return l'elemento <b>strettamente</b> predecessore rispetto
     *         all'ordinamento naturale della classe {@code E}, di {@code el} in
     *         questo RBTree, oppure {@code null} se {@code el} è l'elemento
     *         minimo.
     * @throws NullPointerException
     *                                      se l'elemento {@code el} è null
     * @throws IllegalArgumentException
     *                                      se l'elemento {@code el} non è
     *                                      presente in questo RBTree.
     */
    public E getPredecessor(E el) {
        // TODO implementare
        if(el == null) throw new NullPointerException();     //controllo le varie eccezzioni che posso avere
        RBTreeNode app = this.getNodeOf(el);
        if(app==null) throw new IllegalArgumentException();
        if(this.getMinimum()==app) return null;
        if(app.getLeft()!=null || app.getParent()!=null){             // inizio a scorrere l'albero alla ricerca dell'elemento precedente
            RBTreeNode app2 = app.getPredecessor();
            if(app2==null)
                return null;
            return app.getPredecessor().getEl();
        }
        return null;
    }

    /**
     * @return the root
     */
    protected RBTreeNode getRoot() {
        return this.root;
    }

    /**
     * Restituisce il numero di elementi contenuti in questo RBTree. In caso di
     * elementi ripetuti essi vengono contati più volte.
     * 
     * @return il numero di elementi di tipo {@code E} presenti in questo
     *         RBTree, zero se non è presente nessun elemento.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Restituisce l'elemento <b>strettamente</b> successore, in questo RBTree,
     * di un dato elemento. Si richiede che l'elemento passato sia presente
     * nell'albero.
     * 
     * @param el
     *               l'elemento di cui si chiede il successore
     * @return l'elemento <b>strettamente</b> successore, rispetto
     *         all'ordinamento naturale della classe {@code E}, di {@code el} in
     *         questo RBTree, oppure {@code null} se {@code el} è l'elemento
     *         massimo.
     * @throws NullPointerException
     *                                      se l'elemento {@code el} è null
     * @throws IllegalArgumentException
     *                                      se l'elemento {@code el} non è
     *                                      presente in questo RBTree.
     */
    public E getSuccessor(E el) {
        // TODO implementare
        if(el == null) throw new NullPointerException();       // per il successore mi sposto a destra e nel caso che esista un valore a sinistra del valore destro mi sposto ricorsivamente a sinistra per la ricerca dell'elemento successore
        RBTreeNode app = this.getNodeOf(el);
        if(app==null) throw new IllegalArgumentException();
        if(this.getMaximum()==app) return null;
        if(app.getRight()!=null || app.getParent()!=null){
            RBTreeNode app2 = app.getSuccessor();
            if(app2==null)
                return null;
            return app.getSuccessor().getEl();
        }
        return null;
    }

    /**
     * Restituisce la lista degli elementi contenuti in questo RBTree secondo
     * l'ordine determinato dalla visita in-order. Per le proprietà degli alberi
     * binari di ricerca la lista ottenuta conterrà gli elementi in ordine
     * crescente rispetto all'ordinamento naturale della classe {@code E}. Nel
     * caso di elementi ripetuti, essi appaiono più volte nella lista
     * consecutivamente.
     * 
     * @return la lista ordinata degli elementi contenuti in questo RBTree,
     *         tenendo conto della loro molteplicità.
     */
    public List<E> inOrderVisit() {               // creo un oggetto lista e partendo dall'ultimo valore a sinistra mi sposto pian pian a destra controlando tutti i valori di ogni singolo nodo in modo da posizionarli in ordine crescente
        // TODO implementare
        List<E> lista = new ArrayList<E>(){};
        int i = 0;
        if(getMinimum()==null) return lista;
        RBTreeNode app = getNodeOf(this.getMinimum());
        while (true){
           if(app==null)
               break;
           if(!lista.contains(app.getEl()))
               if(app.getLeft()!=null) {
                   if (lista.contains(app.getLeft().getEl()))
                       app.inOrderVisit(lista);
               }else
                 app.inOrderVisit(lista);
            if(app.getEl()==getMaximum()){
               break;
           }
           if(app.getLeft()!=null&&!lista.contains(app.getLeft().getEl())){
               app=app.getLeft();
           }else {
               if(app.getRight()!=null&&!lista.contains(app.getRight().getEl()))
                   app=app.getRight();
               else
                   app = app.getParent();
           }

        }
        return lista;
    }

    /**
     * Determina se questo RBTree è vuoto.
     * 
     * @return true se questo RBTree è vuoto, false altrimenti.
     */
    public boolean isEmpty() {
        return isNil(this.root);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String descr = "RBTree [root=" + root.el.toString() + ", size=" + size
                + ", numberOfNodes=" + numberOfNodes + "]\n";
        return descr + this.root.toString();
    }

    /**
     * Inserisce un nuovo elemento in questo RBTree. Se l'elemento è già
     * presente viene incrementato il suo numero di occorrenze.
     * 
     * @param el
     *               l'elemento da inserire.
     * @return il numero di confronti tra elementi della classe {@code E}
     *         effettuati durante l'inserimento (cioè il numero di chiamate al
     *         metodo compareTo della classe E)
     * @throws NullPointerException
     *                                  se l'elemento {@code el} è null
     */
    public int insert(E el) {
        // TODO implementare
        if(el == null) throw new NullPointerException();
        if(this.root==null){                                  // controllo se sto inserendo un elemento un un albero vuoto
            this.root = new RBTreeNode(el);
            this.root.color = BLACK;
            this.size++;
            this.numberOfNodes++;
            return 0;
        }
        RBTreeNode app = root;
        int confronti = 0;
        while (true) {                               // creo un loop
            int conf = app.getEl().compareTo(el);
            confronti++;
            if(app.getEl()==null)                  // controllo se sono arrivato ad una foglia
            {
                app.insert(el);
                this.numberOfNodes++;
                this.size++;
                return confronti;
            }
            if(conf == 0) {
                app.insert(el);
                this.size++;
                return confronti;
            }
            if(conf>0){

                if(app.getLeft()==null) {                     // controllo se a sinistra ho una foglia
                    app.insert(el);
                    this.size++;
                    this.numberOfNodes++;
                    return confronti;
                }
                app = app.getLeft();
            }else {
                if(app.getRight()==null) {                    // controllo se a destra ho una foglia
                    app.insert(el);
                    this.size++;
                    this.numberOfNodes++;
                    return confronti;
                }
                app = app.getRight();
            }
        }                                         // ogni volta che trovo un elemento vuoto o l'elemento che sto inserndo richiamo il metodo insert nel nodo
    }

    /**
     * Cancella da questo RBTree una occorrenza di un certo elemento.
     * 
     * @param element
     *                    l'elemento da cancellare
     * @return true, se l'elemento è stato cancellato, false se non era presente
     *         o l'albero è vuoto.
     * @throws NullPointerException
     *                                  se l'elemento passato è nullo
     */
    public boolean remove(E el) {
        // TODO implementare
        if(el == null) throw new NullPointerException();
        if(this.isEmpty() || this.getNodeOf(el)==null) return false;
        this.size--;
        return this.getNodeOf(el).remove(el);    // per la rimozione di un elemento controllo con il metodo ricerca nodo se e' presente e poi richiamo il metodo removo sul nodo
    }

    /**
     * Classe interna per i nodi di un RBTree. La classe definisce i nodi
     * ricorsivamente, cioè un nodo contiene puntatori a nodi della stessa
     * classe. Le operazioni esposte come public nelle API della classe
     * principale RBTree<E> sono "duplicate" nei nodi e tipicamente il meodo
     * pubblico della classe principale fa dei controlli e poi, se è il caso,
     * chiama il metodo "gemello" sul nodo radice dell'albero.
     */
    protected class RBTreeNode {

        /*
         * Etichetta del nodo. ATTENZIONE: non può cambiare! Nel caso di
         * removeFixUp, bisogna utilizzare un metodo opportuno per sostituire il
         * nodo da rimuovere con il suo successore. Infatti non è possibole
         * cambiare l'etichetta associata al nodo da rimuovere. Attenzione al
         * caso di sostituzione della radice e al caso di sostituzione di un
         * nodo sul suo genitore!
         */
        protected final E el;

        // sottoalbero sinistro
        protected RBTreeNode left;

        // sottoalbero destro
        protected RBTreeNode right;

        // genitore del nodo, null se questo nodo è la radice del BRTree
        protected RBTreeNode parent;

        // colore del nodo
        protected boolean color;

        // conta il numero di occorrenze dell'elemento (molteplicità)
        protected int count;

        /**
         * Crea un nodo di un RBTree con l'etichetta passata. Il nodo creato non
         * ha nessun collegamento con altri nodi, ha molteplicità pari a 1 e
         * colore rosso.
         * 
         * @param el
         *               l'etichetta da associare al nodo
         * 
         * @throws NullPointerException
         *                                  se l'etichetta passata è null
         */
        protected RBTreeNode(E el) {
            if(el == null) throw new NullPointerException();
            this.el = el;
            // TODO implementare
            this.count =1;         // creo un oggetto nell'albero RBTree di colore rosso
            this.color = RED;
            this.left = null;
            this.right = null;
        }

        /**
         * Metodo ricorsivo che implementa la visita in-order a partire dal nodo
         * this.
         * 
         * @param r
         *              la lista a cui aggiungere gli elementi visitati in-order
         */
        protected void inOrderVisit(List<E> r) {
            // TODO implementare
            int i =0;
            while (i<this.count){
                r.add(this.getEl());           // metodo richiamato dal metodo inOrderVisit della classe RBTree dove per ogni count del nodo inserisco l'oggetto dentro la lista
                i++;
            }
        }

        /**
         * Trova il nodo dell'albero che contiene l'elemento successore di
         * quello contenuto in questo nodo. Il metodo assume che il nodo con un
         * elemento successore esista nell'albero. Ciò deve essere controllato
         * dal metodo getSuccessor() della classe principale.
         * 
         * @return un puntatore al nodo che contiene l'elemento successore a
         *         quello contenuto in questo nodo, secondo l'ordinamento
         *         naturale della classe E.
         */
        protected RBTreeNode getSuccessor() {
            // TODO implementare
            if(this.getRight()!=null){
                if(this.getRight().getLeft()!=null)
                    if(this.getEl().compareTo(this.getRight().getLeft().getEl())<0) {           // controllo se per la ricerca del minore mi devo spostare a destra o a sinistra       es il caso     (10)
                        return this.getRight().getLeft();                                           //                                                                                                     \
                    }                                                                               //                                                                                                      (15)
                return this.getRight();                                                             //                                                                                                     /
                                                                                                    //                                                                                                  (12)
            }
            RBTreeNode app = this;
            while (app.getParent().getEl().compareTo(this.getEl())<0){
                app = app.getParent();
                if(app.getParent()==(null)||app.getParent().getEl().compareTo(this.getEl())<0) return null;
            }
            return app.getParent();
        }

        /**
         * Trova il nodo dell'albero che contiene l'elemento predecessore di
         * quello contenuto in questo nodo. Il metodo assume che il nodo con un
         * elemento predecessore esista nell'albero. Ciò deve essere controllato
         * dal metodo getPredecessor() della classe principale.
         * 
         * @return un puntatore al nodo che contiene l'elemento predecessore a
         *         quello contenuto in questo nodo, secondo l'ordinamento
         *         naturale della classe E.
         */
        protected RBTreeNode getPredecessor() {
            // TODO implementare
            if(this.getLeft()!=null){
                if(this.getLeft().getRight()!=null)                                                 // anche qui come il metodo getSuccessor della classe RBTreeNode
                    if(this.getEl().compareTo(this.getLeft().getRight().getEl())>0) {
                        return this.getLeft().getRight();
                    }
                return this.getLeft();

            }
            RBTreeNode app = this;
            while (app.getParent().getEl().compareTo(this.getEl())>0){
                app = app.getParent();
                if(app.getEl().compareTo(this.el)>0) return null;
            }
            return app.getParent();
        }

        /**
         * Cerca un elemento nel (sotto)albero di cui questo nodo è radice.
         * 
         * @param el
         *               l'elemento da cercare
         * 
         * @return il puntatore al nodo che contiene l'elemento cercato oppure
         *         null se l'elemento non esiste nel (sotto)albero di cui questo
         *         nodo è la radice.
         */
        protected RBTreeNode search(E el) {                              //  metodo che sfruttato dalla classe getnodeof per capire in queale ramo del sottoalbero mi devo spostare
            // TODO implementare
            RBTreeNode app = this;
            if(app.getEl().compareTo(el)<0)
                app=app.getRight();
            else
                app = app.getLeft();
            return app;
        }

        /**
         * Restituisce il nodo con elemento minimo nel (sotto)albero di cui
         * questo nodo è radice.
         * 
         * @return il nodo con elemento minimo nel (sotto)albero di cui questo
         *         nodo è radice
         */
        protected RBTreeNode getMinimum() {
            // TODO implementare
            RBTreeNode app= this;
            while (true) {
                if (app.getLeft() != null)    // metodo interno della classe RBTREENODE per la ricerca del minimo (richiamato dalla classe RBTREE)
                    app = app.getLeft();
                else
                    break;
            }
            return app;
        }

        /**
         * Restituisce il nodo con elemento massimo nel (sotto)albero di cui
         * questo nodo è radice.
         * 
         * @return il nodo con elemento massimo nel (sotto)albero di cui questo
         *         nodo è radice
         */
        protected RBTreeNode getMaximum() {
            // TODO implementare
            RBTreeNode app = this;
            while (true) {
                if (app.getRight() != null)
                    app = app.getRight();          // metodo interno della classe RBTREENODE per la ricerca del massimo (richiamato dalla classe RBTREE)
                else
                    break;
            }
            return app;
        }

        /**
         * @return the el
         */
        protected E getEl() {
            return el;
        }

        /**
         * @return the left
         */
        protected RBTreeNode getLeft() {
            return left;
        }

        /**
         * @return the right
         */
        protected RBTreeNode getRight() {
            return right;
        }

        /**
         * @return the parent
         */
        protected RBTreeNode getParent() {
            return parent;
        }

        /**
         * @return the color
         */
        protected boolean getColor() {
            return color;
        }

        /**
         * @return the count
         */
        protected int getCount() {
            return count;
        }

        /**
         * Determina se questo nodo è attualmente colorato di rosso.
         * 
         * @return true se questo nodo è attualmente colorato di rosso, false
         *         altrimenti
         */
        protected boolean isRed() {

            return this.color == RED;
        }

        /**
         * Determina se questo nodo è attualmente colorato di nero.
         * 
         * @return true se questo nodo è attualmente colorato di nero, false
         *         altrimenti
         */
        protected boolean isBlack() {

            return this.color == BLACK;
        }

        /**
         * Determina l'altezza nera di questo nodo, cioè il numero di nodi neri
         * che si incontrano in un qualsiasi cammino da qui ad una foglia NIL.
         * Non viene contata la colorazione di questo nodo.
         * 
         * @return l'altezza nera di questo nodo
         * 
         */
        protected int getBlackHeight() {
            // TODO implementare
            RBTreeNode app = this.getLeft();    // metodo richiamato per calcolre i nodi neri ... ciclo i i nodi dal root andando sempre a sinistra contando i nodi neri
            int b =0;
            if(app==null)
                return 1;
            while (true) {
                if (app.getColor() == false)
                    b++;
                if (app.getLeft() == null) {
                    b++;
                    break;
                }
                app = app.getLeft();
            }
            return b;
        }

        /**
         * Inserisce un elemento nel (sotto)albero di cui questo nodo è radice.
         * Se l'elemento è già presente viene semplicemente incrementata la sua
         * molteplicità.
         * 
         * @param el
         *               l'elemento da inserire
         * 
         * @return il numero di operazioni di comparazione (chiamate al metodo
         *         compareTo della classe E) effettuate per l'inserimento
         *         dell'elemento.
         */
        protected int insert(E el) {
            // TODO implementare
            if(this.getEl().equals(el)){
                this.count++;           // nel caso che l'elemento sia gia presente aumento il count
                return -1;
            }else {
                if(this.getEl().compareTo(el)>0) {    // mi sposto a destra o a sinistra in base al caso che ho
                    this.left = new RBTreeNode(el);
                    this.left.parent = this;
                    case1(this.left);    // metodo utilizzato per bilanciare l'albero dopo l'inserimento di un nuovo nodo
                }else {
                    this.right = new RBTreeNode(el);
                    this.right.parent = this;
                    case1(this.right);
                }
            }
            return 0;
        }

        /**
         * Effettua una rotazione a sinistra.
         * 
         * @param x
         *              il nodo su cui effettuare la rotazione, deve avere un
         *              figlio destro
         * @throws IllegalArgumentException
         *                                      se il nodo passato ha figlio
         *                                      destro NIL
         */
        protected void leftRotate(RBTreeNode x) {
            // TODO implementare
            RBTreeNode right = x.right;    // rotazione a sinistra
            if (right != null)           // controllo di avere il valore a destra per effettuare la rotazione
            {
                x.right = right.left;
                if (right.left != null)    // controllo i vari casi che posso avere
                {
                    right.left.parent = x;
                }
                right.parent = x.parent;
                if (x.parent == null )
                {
                   root = right;
                }
                else
                {
                    if (x.parent.left == x)
                    {
                        x.parent.left = right;
                    }
                    else
                    {
                        x.parent.right = right;
                    }
                }
                right.left= x;
                x.parent = right;
            }
        }

        /**
         * Effettua una rotazione a destra.
         * 
         * @param x
         *              il nodo su cui effettuare la rotazione, deve avere un
         *              figlio sinistro
         * 
         * @throws IllegalArgumentException
         *                                      se il nodo passato ha figlio
         *                                      sinistro NIL
         */
        protected void rightRotate(RBTreeNode x) {    // rotazione a destra
            // TODO implementare
            if (x.left == null) {          // controllo di avere il valore a sinistra per effettuare la rotazione
                throw new IllegalArgumentException();
            }
            RBTreeNode left = x.left;
            if (left != null)
            {
                x.left = left.right;
                if (left.right != null)
                {
                    left.right.parent = x;
                }
                left.parent = x.parent;
                if (x.parent == null )
                {
                    root = left;
                }
                else
                {
                    if (x.parent.right == x)
                    {
                        x.parent.right = left;
                    }
                    else
                    {
                        x.parent.left= left;
                    }
                }
                left.right = x;
                x.parent = left;
            }
        }

        /**
         * Rimuove un elemento dal (sotto)albero di cui questo nodo è radice. Se
         * l'elemento ha molteplicità maggiore di 1 allora viene semplicemente
         * decrementata la molteplicità.
         * 
         * @param el
         *               l'elemento da rimuovere
         * 
         * @return true se l'elemento è stato rimosso (anche solo semplicemente
         *         decrementando la molteplicità), false se non era presente.
         */
        protected boolean remove(E el) {
            // TODO implementare
            if(this.count>1){
                this.count--; // nel caso che il count dell'oggetto da rimuovere sia maggiore di uno decremento semplicemente il count di 1
                return true;
            }                                           // nel caso che l'elemento e' contenuto una solo volta procedo a identicarne il caso che andro ad utilizzare e svolgendo le adeguate operazioni di rimozione e di bilanciamento
            if(this==root) {
                if (this.left == null) {
                    root = this.right;
                    numberOfNodes--;
                    return true;
                }
                if(this.right==null){
                    root = this.left;
                    numberOfNodes--;
                    return true;
                }
            }
            if(this.parent==null){          // caso in cui rimuovo il root
                if(this.right!=null){
                    if(root.right.left!=null){
                        root.right.left.left = root.left;
                        root.right.left.right = root.right;
                        root.left.parent = root.right.left;
                        root.right.parent = root.right.left;
                        root=root.right.left;
                        root.right.left=null;
                        root.parent=null;
                        root.color = BLACK;
                        return true;
                    }
                    if(root.left.right!=null){                // vari casi di rimozioni possibili
                        root.left.right.left=root.left;
                        root.left.right.right=root.right;
                        root.left.parent = root.left.right;
                        root.right.parent = root.left.right;
                        root= root.left.right;
                        root.color = BLACK;
                        root.parent = null;
                        root.left.right = null;
                        return true;
                    }
                    root = this.right;
                    root.left=this.left;
                    root.parent=null;
                    this.left.parent=root;
                    numberOfNodes--;
                    root.left.color=RED;
                    delete_case1(this);
                    return true;
                }
                if(this.left!=null) {
                    root = this.left;
                    root.right = this.right;
                    root.parent=null;
                    this.right.parent=root;
                    numberOfNodes--;
                    root.right.color=BLACK;
                    delete_case1(this);
                    return true;
                }
                root = null;
                numberOfNodes--;
                return true;
            }
            if(this.parent.getEl().compareTo(el)>0)
            {
                if(this.right!=null){
                    this.parent.left = this.right;
                    this.right.parent = this.parent;
                    this.right.left = this.left;
                    this.left.parent = this.right;
                    if(this.right.left.left==null&&this.right.left.right==null)
                        this.right.left.color=RED;
                    if(this.right.left!=null||this.right.right!=null)
                        this.right.color=BLACK;
                }else {
                    if(this.left!=null){
                        this.parent.left = this.left;
                        this.left.parent = this.parent;
                    }else {
                        root.left = null;
                        if(root.right==null){
                            root.color=BLACK;
                            return true;
                        }
                        delete_case1(this.parent);

                    }
                }
            }else {
                if(this.right!=null){
                    if(this.left!=null) {     // caso in cui tutti e due i figli siano diversi da null
                        this.parent.right = this.right;
                        this.right.parent=this.parent;
                        this.right.left=this.left;
                        this.right.left.parent=this.right;
                        if(this.right.left.left==null&&this.right.left.right==null)
                            this.right.left.color=RED;
                        if(this.right.left!=null|this.right.right!=null)
                            this.right.color=BLACK;
                        numberOfNodes--;
                        return true;
                    }
                    else {
                        this.parent.right = this.right;
                    }
                }else {
                    if(this.left!=null){
                        this.parent.right = this.left;
                        this.left.parent = this.parent;
                    }else {
                        if(this.parent==root&&root.left!=null) {
                            rightRotate(this.parent);
                            root.right.right=null;
                            root.right.left.color=RED;
                            root.color=BLACK;
                            numberOfNodes--;
                            return true;
                        }
                        else
                            this.parent.right = null;
                    }
                }
            }
            numberOfNodes--;
            if(this.color==BLACK)
            {
                if(this.left!=null) {
                    if (this.left.color == RED){
                        this.left.color = BLACK;
                        return true;
                    }
                    else {
                        delete_case1(this);
                        return true;
                    }
                }else {
                    if(this.right==null){
                        this.color=RED;
                        return true;
                    }
                    if (this.right.color == RED)
                        this.right.color = BLACK;
                    else {
                        delete_case1(this);
                        return true;
                    }
                }
            }

            return true;


        }

        // TODO aggiungere eventuali metodi privati per l'implementazione di
        // insert e remove
        private RBTreeNode fratello(RBTreeNode n){   // ritorno il nodo fratello
            if(n==n.parent.left)
                return n.parent.right;
            else
                return n.parent.left;
        }
        private RBTreeNode nonno(RBTreeNode n) {
            return n.parent.parent;
        }     // metodo che mi ritorna il nodo.parent.parent
        private RBTreeNode zio(RBTreeNode n){
            if(n.parent == nonno(n).left)     // mi ritorna lo zio di un nodo
                return nonno(n).right;
            else
                return nonno(n).left;
        }
        private void case1(RBTreeNode n) {   // metodo di controllo per l'inserimento    mi permette di andare a ribilanciare l'albero dopo un inserimento in modo da avere lo stesso numeor di nodi neri per ogni ramo
            if (n.parent == null)   // se e' root lo imposto di colore nero
                n.color = BLACK;
            else
                case2(n);
        }
        private void case2(RBTreeNode n){
            if(n.parent.color == BLACK)
                return;
            else
                case3(n);
        }
        private void case3(RBTreeNode n){
            if(zio(n)!=null &&zio(n).color==RED) {  // metodi per il controllo del bilanciamento
                n.parent.color = BLACK;
                zio(n).color=BLACK;
                nonno(n).color=RED;
                case1(nonno(n));
            }
            else
                case4(n);
        }
        private void case4(RBTreeNode n){
            if(n == n.parent.right && n.parent == nonno(n).left){
                if(n.parent.parent.right==null)
                {
                    leftRotate(n.parent);
                    n=n.left;
                    rightRotate(n.parent.parent);
                    n = n.parent.right;
                    if(n.right==null && n.left ==null) {
                        n.color = RED;
                        n.parent.color = BLACK;
                    }
                }else {
                    leftRotate(n.parent);
                    n = n.left;
                }
            }else {
                if(n==n.parent.left && n.parent ==nonno(n).right){
                    rightRotate(n.parent);
                    n = n.right;
                }
                case5(n);
            }
        }
        private void case5(RBTreeNode n){
            n.parent.color = BLACK;
            nonno(n).color=RED;
            if(n == n.parent.left && n.parent == nonno(n).left){
                rightRotate(nonno(n));
            }else
                leftRotate(nonno(n));
        }
        private void delete_case1(RBTreeNode n){  // metodi per il ribilanciamento dopo una cancellazione   (stessa cosa del metodo case_1 solo per la cancellazione)
            if(n==root)
                if(n.left==null){
                    leftRotate(root);
                    root.color=BLACK;
                    if(root.left.right!=null)
                        root.left.right.color=RED;
                    return;
                }
            if(n.parent == null)
                return;
            else
                delete_case2(n);

        }
        private void delete_case2(RBTreeNode n){
            if(fratello(n).color==RED){
                n.parent.color = RED;
                fratello(n).color=BLACK;
                if(n==n.parent.left)
                    leftRotate(n.parent);
                else
                    rightRotate(n);
            }
            delete_case3(n);
        }
        private void delete_case3(RBTreeNode n){
            if(n.parent.color==BLACK&&fratello(n).color==BLACK&&fratello(n).left.color==BLACK&&fratello(n).right.color==BLACK){
                fratello(n).color = RED;
                delete_case1(n.parent);
            }else {
                delete_case4(n);
            }
        }
        private void delete_case4(RBTreeNode n){
            if(n.parent.color==RED&&fratello(n).color==BLACK&&fratello(n).left.color==BLACK&&fratello(n).right.color==BLACK){
                fratello(n).color= RED;
                n.parent.color=BLACK;
            }else {
                delete_case5(n);
            }
        }
        private void delete_case5(RBTreeNode n){
            if(n==n.parent.left&&fratello(n).color==BLACK&&fratello(n).left.color==RED&&fratello(n).right.color==BLACK){
                fratello(n).color=RED;
                fratello(n).left.color=BLACK;
                rightRotate(fratello(n));
            }else {
                if(n==n.parent.right&&fratello(n).color==BLACK&&fratello(n).right.color==RED&&fratello(n).left.color==BLACK){
                    fratello(n).color=RED;
                    fratello(n).right.color=BLACK;
                    leftRotate(fratello(n));
                }
            }
            delete_case6(n);
        }
        private void delete_case6(RBTreeNode n){
            fratello(n).color=n.parent.color;
            n.parent.color=BLACK;
            if(n==n.parent.left){
                fratello(n).right.color=BLACK;
                leftRotate(n.parent);
            }else {
                fratello(n).left.color=BLACK;
                rightRotate(n.parent);
            }
        }

    }
}
