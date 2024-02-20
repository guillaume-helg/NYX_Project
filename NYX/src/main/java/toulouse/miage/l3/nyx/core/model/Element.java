package toulouse.miage.l3.nyx.core.model;

import java.util.*;

/**
 * Represents a element (Element)
 * @author Lucas Godard
 * @version 1.0
 */
public class Element {

    /* ===========================================
     * DECLARATION OF ATTRIBUTES
     * =========================================== */
    /** code of the element */
    private String code;
    /** name of the element */
    private String nom;
    /** quantity of the element */
    private Double quantite;
    /** unit of the element */
    private Unite uniteMesure;
    /** purchase price of the element */
    private Double prixAchat;
    /** selling price of the element */
    private Double prixVente;

    private Double deliveryDuration;

    /* ===========================================
     * CONSTRUCTOR AND NATIVE FUNCTIONS
     * =========================================== */
    /**
     * Constructor for element
     * @param code : code of an element
     * @param nom : name of an element
     * @param quantite : quantities of an element
     * @param uniteMesure : unit of an element
     * @param prixAchat : purchase price of an element
     * @param prixVente : selling price of an element
     */
    public Element(String code, String nom, Double quantite, Unite uniteMesure, Double prixAchat, Double prixVente) {
        this.code = code;
        this.nom = nom;
        this.quantite = quantite;
        this.uniteMesure = uniteMesure;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
    }

    /* ===========================================
     * GETTER
     * =========================================== */
    /**
     * Get the element's code
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Get the element's name
     * @return nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Get the element's quantity
     * @return quantite
     */
    public Double getQuantite() {
        return this.quantite;
    }

    /**
     * Get the element's unit
     * @return uniteMesure
     */
    public Unite getUniteMesure() {
        return this.uniteMesure;
    }

    /**
     * Get the element's purchase price
     * @return prixAchat
     */
    public Double getPrixAchat() {
        return this.prixAchat;
    }

    /**
     * Get the element's selling price
     * @return prixVente
     */
    public Double getPrixVente() {
        return this.prixVente;
    }

    public Double getDeliveryDuration() {
        return this.deliveryDuration;
    }


    /* ===========================================
     * SETTER
     * =========================================== */
    /**
     * Set the element's purchase price
     * @param prixAchat : purchase price of an element
     */
    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    /**
     * Set the element's selling price
     * @param prixVente : selling price of an element
     */
    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    /**
     * Set the element's unit
     * @param quantite : quantities of an element
     */
    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getProductionTime() {
        return this.deliveryDuration;
    }

    public boolean isAvailable(Double quantity, int multiplicator) {
        return this.quantite >= quantity * multiplicator;
    }
}
