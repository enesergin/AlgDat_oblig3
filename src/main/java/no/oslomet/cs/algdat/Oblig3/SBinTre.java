package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {

    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }


    //Oppgave 1
    public boolean leggInn(T verdi){
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot; //p er roten
        Node<T> q = null;

        int temp = 0; //hjelpevariabel

        while (p != null){ //kjører gjennom treet til p er ute
            q = p;                                 //p har q som forelder
            temp = comp.compare(verdi,p.verdi);     //bruker komparatoren
            p = temp < 0 ? p.venstre : p.høyre;     //flytter p
        }

        //p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi,q); //oppretter en ny node, med foreldernode

        if (q == null){
            rot = p; //p blir rotnode
        }
        else if (temp < 0){
            q.venstre = p; //venstre barn til q
        }
        else{
            q.høyre = p; //høyre barn til q
        }

        antall++;
        return true;
    }
    //

    //Oppgave 2
    public int antall(T verdi) {

        if (tom()){ //Treet er tomt
            throw new NoSuchElementException("Treet er tomt!");
        }
        Node<T> p = rot; //p er roten

        int antall = 0;

        while (p != null) //kjører gjennom treet til p er ute
        {
            int cmp = comp.compare(verdi,p.verdi); //bruker komparator
            if (cmp < 0){ //verdien som er gitt er mindre enn p.verdi
                p = p.venstre;
            }
            else { //verdien som er gitt er større eller lik p.verdi
                if (cmp == 0) { //verdien som er gitt er lik p.verdi
                    antall++;
                }
                p = p.høyre; //går til høyre
            }
        }
        return antall;
    }
    //

    //Oppgave 3
    private static <T> Node<T> førstePostorden(Node<T> p) {
        while (true) {
            if (p.venstre != null){//går inn i venstre node i hver runde i løkken til det ikke finnes fler
                p = p.venstre;
            }
            else if (p.høyre != null){
                p = p.høyre;
            }
            else{
                return p; //returner første node postorden
            }
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        if (p.forelder == null){ //hvis p er siste i postorden, returneres det null
            return null;
        }
        Node<T> q = p.forelder;

        if (q.venstre == p){
            if (q.høyre == null){
                return q;
            }
            else{
                return førstePostorden(q.høyre);
            }
        }
        return q;
    }
    //

    //Oppgave 4
    public void postorden(Oppgave<? super T> oppgave) {
        if (rot == null){
            return;
        }
        Node<T> p = førstePostorden(rot);

        while (p != null){
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p.venstre != null){
            postordenRecursive(p.venstre, oppgave);
        }
        if(p.høyre != null){
            postordenRecursive(p.høyre, oppgave);
        }

        oppgave.utførOppgave(p.verdi);
    }
    //

    //Oppgave 5
    public ArrayList<T> serialize() {
        if (tom()){
            return null;
        }

        ArrayList<T> liste = new ArrayList<>(); //lager liste som skal returneres
        Queue<Node<T>> kø = new ArrayDeque<>(); //lager kø
        kø.add(rot); //køen starter med roten

        while (!kø.isEmpty()){ //legger til node verdiene i array i nivå orden
            Node<T> p = kø.remove();
            liste.add(p.verdi);

            if (p.venstre != null){
                kø.add(p.venstre);
            }
            if (p.høyre != null){
                kø.add(p.høyre);
            }
        }
        return liste;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> nyeTreet = new SBinTre<>(c);
        data.forEach(nyeTreet::leggInn);
        return nyeTreet;
    }
    //

    //Oppgave 6
    public boolean fjern(T verdi) {

        if (verdi == null){
            return false;  // Treet har ingen verdier som er null
        }

        Node<T> p = rot; //starter fra roten
        Node<T> q = null; //q skal være forelderet til p

        while (p != null) { //prøver å finne verdien
            int cmp = comp.compare(verdi,p.verdi); //Bruker komparatør
            if (cmp < 0) { //gitt verdi er mindre enn p verdi
                q = p; p = p.venstre; //går til venstre
            }
            else{ //gitt verdi er større eller lik p verdi
                if (cmp == 0){//den gitte verdien er funnet
                    break;
                }
                q = p; p = p.høyre; //går til høyre
            }
        }
        if (p == null){ //gitt verdi er ikke funnet
            return false;
        }

        //2 forskjellige tilfeller nå. 1. Noden har 0 eller 1 barn. 2. Noden har 2 barn
        if (p.venstre == null || p.høyre == null){ //Noden som skal fjernes har 0 eller 1 barn

            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  //barnet til noden p blir definert som b
            if (p == rot) {
                rot = b;
                if(b != null){
                    b.forelder = null;
                }

            }
            else if (p == q.venstre){
                q.venstre = b;
                if(b != null){
                    b.forelder = q;
                }
            }
            else {
                q.høyre = b;
                if(b != null){
                    b.forelder = q;
                }
            }
        }
        else { //Noden som skal fjernes har 0 eller 1 barn
            Node<T> s = p;
            Node <T> r = p.høyre;

            while (r.venstre != null) {
                s = r; //s skal være forelderet til r
                r = r.venstre;
            }

            p.verdi = r.verdi;

            if (s != p) {
                s.venstre = r.høyre;
                if(r.høyre != null){
                    r.høyre.forelder = s;
                }

            }
            else {
                s.høyre = r.høyre;
                if(r.høyre != null){
                    r.høyre.forelder = s;
                }
            }
        }

        antall--;
        endringer++;
        return true;
    }

    public int fjernAlle(T verdi) {
        int i = 0;
        boolean fjernet = true;
        while(fjernet){
            if(fjern(verdi))
                i++;
            else
                fjernet = false;
        }
        return i;
    }

    public void nullstill() {
        if (!tom()) {
            Node<T> p = førstePostorden(rot);
            while (p != null) {
                p.venstre = null;
                p.høyre = null;
                if (p == rot) {
                    rot = null;
                }
                antall--;
                p = nestePostorden(p);
            }
        }
    }
    //

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }


} // ObligSBinTre
