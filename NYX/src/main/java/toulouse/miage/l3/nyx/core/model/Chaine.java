/*
 * Chaine.java 06/02/2024
 * Licence MIAGE, Université Paul Sabatier, pas de copyright, pas de droit d'auteur
 */
package toulouse.miage.l3.nyx.core.model;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static toulouse.miage.l3.nyx.core.model.Usine.elementsContains;
import static toulouse.miage.l3.nyx.core.model.Usine.getElements;


/**
 * Represents a production chain (Chaine)
 * @author Guillaume Helg
 * @version 1.0
 */
public class Chaine {

    /** code of chaine */
    private String code;
    /** name of the chain */
    private String nom;
    /** list of each element and his quantity needed by the chaine to create something */
    protected HashMap<Element, Double> listeElementEntreeH;
    /** list of each element we make by using this chaine */
    private HashMap<Element, Double> listeElementSortieH;
    /** toString of the Hashmap listeElementEntree */
    private String listeElementEntrees;
    /** toString of the Hashmap listeElementSortie */
    private String listeElementSorties;

    /**
     * Constructor for the Chaine class
     * @param code The code of the chain
     * @param nom The name of the chain
     * @param listeElementEntree The list of input elements and their quantities
     * @param listeElementSortie The list of output elements and their quantities
     */
    public Chaine(String code, String nom, HashMap<Element, Double> listeElementEntree, HashMap<Element, Double> listeElementSortie) {
        this.code = code;
        this.nom = nom;
        this.listeElementEntreeH = listeElementEntree;
        this.listeElementSortieH = listeElementSortie;
        this.listeElementEntrees = getFormattedListeEntree();
        this.listeElementSorties = getFormattedListeSortie();
    }

    /**
     * Returns the code of the chain
     * @return String containing the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the chain
     * @param code The new code of the chain
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the name of the chain
     * @return String containing the name
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets the name of the chain
     * @param nom The new name of the chain
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * format in a StringBuilder the content of the Hashmap listeElementEntreeH
     * @return a StringBuilder with the list of Element in entry
     */
    public String getFormattedListeEntree() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> entry : listeElementEntreeH.entrySet()) {
            str.append(entry.getKey().getCode());
            str.append(" * ");
            str.append(entry.getValue());
            str.append(", ");
        }
        str.setLength(str.length() - 2);
        return str.toString();
    }

    /**
     * format in a StringBuilder the content of the Hashmap listeElementSortieH
     * @return a StringBuilder with the list of Element in exit
     */
    public String getFormattedListeSortie() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> entry : listeElementSortieH.entrySet()) {
            str.append(entry.getKey().getCode());
            str.append(" * ");
            str.append(entry.getValue());
            str.append(", ");
        }
        str.setLength(str.length() - 2);
        return str.toString();
    }

    /**
     * Create a StringBuilder to write listEntree in the correct format for the csv file
     * @return StringBuilder of Liste Element Entree
     */
    public String getListeEntreeCSVType(){
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> entry : listeElementEntreeH.entrySet()) {
            if(!str.isEmpty()){
                str.append(",");
            }
            str.append("(");
            str.append(entry.getKey().getCode());
            str.append(",");
            str.append(entry.getValue());
            str.append(")");


        }
        return str.toString();

    }

    /**
     * Create a StringBuilder to write listSortie in the correct format for the csv file
     * @return StringBuilder of Liste Element Sortie
     */
    public String getListeSortieCSVType(){
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> entry : listeElementSortieH.entrySet()) {
            str.append("(");
            str.append(entry.getKey().getCode());
            str.append(",");
            str.append(entry.getValue());
            str.append(")");

        }
        return str.toString();

    }

    /**
     * Getter
     * @return
     */
    public String getListeElementEntree() {
        return getFormattedListeEntree();
    }

    /**
     *
     * @return
     */
    public String getListeElementSortie() {
        return getFormattedListeSortie();
    }

    /**
     *
     * @return
     */
    public String toString() {
        String str = this.code + "\n" + this.nom + "\n" + this.getFormattedListeEntree() + "\n" + this.getFormattedListeSortie();
        return str;
    }

    /**
     * Determines whether the production of the specified quantity is feasible
     * @param qtt The desired production quantity
     * @return true if production is feasible, false otherwise
     */
    public boolean isFeasible(int qtt) {
        boolean feasible = false;

        for (Map.Entry<Element, Double> currentElement : this.listeElementEntreeH.entrySet()) {
            Element element = currentElement.getKey();
            if (getElements().contains(element)) {
                int index = getElements().indexOf(element);
                if (getElements().get(index).getQuantite()-(currentElement.getValue() * qtt) >= 0) {
                    feasible = true;
                } else {
                    System.out.println("Pas quantité suffisante");
                    return false;
                }
            } else {
                System.out.println("Erreur, element inexistant");
            }
        }
        return feasible;
    }

    public HashMap<Element, Double> getListeElementEntreeH() {
        return listeElementEntreeH;
    }

    public HashMap<Element, Double> getListeElementSortieH() {
        return listeElementSortieH;
    }
}
