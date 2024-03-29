package toulouse.miage.l3.nyx.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

import static toulouse.miage.l3.nyx.core.utils.UtilsChaine.readChaine;
import static toulouse.miage.l3.nyx.core.utils.UtilsElement.readElement;

public class Usine {

    // Design pattern in case we had to manage several Usine to stock Chaine, Commande and Element
    private static Map<Integer,Usine> instance = new HashMap<>();

    public static Usine getInstance(Integer usineId) {
        if (instance.get(usineId) == null) {
            instance.put(usineId,new Usine());
        }
        return instance.get(usineId);
    }


    private Usine(){}

    /**
     * List which register every chaine object of the Usine from the text files
     */
    private static ObservableList<Chaine> chaines = FXCollections.observableArrayList();
    /**
     * List which register every commandes object of the Usine from the text files
     */
    private static ObservableList<Commande> commandes = FXCollections.observableArrayList();
    /**
     * List which register every element object of the Usine from the text files
     */
    private static ObservableList<Element> elements = FXCollections.observableArrayList();


    /**
     * add every chaine to the list
     */
    public void chargerChaines() {
        chaines.addAll(readChaine());
    }

    /**
     * add every element to the liste
     */
    public void chargerElements() {
        elements.addAll(readElement());
    }

    /* ===========================================
     * FUNCTIONS FOR CHAINES
     * These functions have been created to counter every bad manipulations with the list
     * and give access only to some methods
     * =========================================== */

    public static ObservableList<Chaine> getChaine() {
        return FXCollections.unmodifiableObservableList(chaines);
    }
    public static void addToChaine(Chaine c) {chaines.add(c);}
    public static boolean chainesContains(Chaine c) {
        return chaines.contains(c);
    }
    public static int chainesIndexOf(Chaine c) {
        return chaines.indexOf(c);
    }
    public static void modifyToChaine(Chaine chpre, Chaine chpost){chaines.set(chainesIndexOf(chpre),chpost);}

    public static void removeToChaine(Chaine c) {
        chaines.remove(c);
    }


    /* ===========================================
     * FUNCTIONS FOR COMMANDES
     * These functions have been created to counter every bad manipulations with the list
     * and give access only to some methods
     * =========================================== */

    public static void clearChainesCommandes() {
        commandes.clear();
    }

    public static void addAllInChainesCommandes(Commande commande) {
        commandes.addAll(commande);
    }

    public static void addToCommandes(Commande commande) {
        commandes.add(commande);
    }

    public static ObservableList<Commande> getCommandes() {
        return FXCollections.unmodifiableObservableList(commandes);
    }

    public static int getSizeChainesCommande() {
        return commandes.size();
    }

    public static void removeToCommande(Commande c) {
        commandes.remove(c);
    }

    public static void removeToCommandeByChaine(Chaine c) {
        for(Commande commande : commandes) {
            if (commande.getChaine().equals(c)) {
                removeToCommande(commande);
            }
        }
    }

    /* ===========================================
     * FUNCTIONS FOR ELEMENTS
     * These functions have been created to counter every bad manipulations with the list
     * and give access only to some methods
     * =========================================== */

    public static ObservableList<Element> getElements() {
        return FXCollections.unmodifiableObservableList(elements);
    }

    public static void addToElements(Element e) {
        elements.add(e);
    }

    public static void removeToElement(Element e) {
        elements.remove(e);
    }

    public static void modifyToElement(Element elpre, Element elpost) {
        elpre.setCode(elpost.getCode());
        elpre.setNom(elpost.getNom());
        elpre.setUniteMesure(elpost.getUniteMesure());
        elpre.setQuantite(elpost.getQuantite());
        elpre.setPrixAchat(elpost.getPrixAchat());
        elpre.setPrixVente(elpost.getPrixVente());
    }

    public static Double addQuantitiesOfElement(Element e) {
        Double totqte = elements.get(elementIndexOf(e)).getQuantite() + e.getQuantite();
        elements.get(elementIndexOf(e)).setQuantite(totqte);
        return totqte;
    }

    public static boolean elementsContains(Element e) {
        return elements.contains(e);
    }

    public static int elementIndexOf(Element e) {
        return elements.indexOf(e);
    }

    public static Element getElements(int index) {
        return elements.get(index);
    }

    public static Element getElementByCode(String code){
        for(Element element : elements){
            if (Objects.equals(element.getCode(), code)) return element;
        }
        return null;
    }

    public static Element getElementByName(String name){
        for(Element element : elements){
            if (Objects.equals(element.getNom(), name)) return element;
        }
        return null;
    }

    public static void addAllElement(List<Element> elementss) {
        elements.addAll(elementss);
    }

    public static ObservableList<String> getNomElement(){
        ObservableList<String> codeElements = FXCollections.observableArrayList();
        for(Element element : elements){
            codeElements.add(element.getNom());
        }
        return codeElements;
    }
}