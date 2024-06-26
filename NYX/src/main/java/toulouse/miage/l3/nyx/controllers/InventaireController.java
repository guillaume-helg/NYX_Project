package toulouse.miage.l3.nyx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.model.Unite;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import static toulouse.miage.l3.nyx.core.model.Usine.*;
import static toulouse.miage.l3.nyx.core.utils.UtilsElement.*;

/**
 * Controller class for managing Element in a GUI application
 * @author Lucas GODARD
 * @version 1.0
 */
public class InventaireController implements Initializable {

    /* ===========================================
     * DECLARATION OF ATTRIBUTES
     * =========================================== */
    /** tableView of elements */
    @FXML
    private TableView<Element> elementTableView;
    /** column for element's code */
    @FXML
    private TableColumn<Element, Integer> elementCode;
    /** column for element's name */
    @FXML
    private TableColumn<Element, String> elementNom;
    /** column for element's purchase price */
    @FXML
    private TableColumn<Element, Double> elementPrixA;
    /** column for element's selling price */
    @FXML
    private TableColumn<Element, Double> elementPrixV;
    /** column for element's quantities */
    @FXML
    private TableColumn<Element, Double> elementQuantite;
    /** column for element's unit */
    @FXML
    private TableColumn<Element, String> elementUnite;
    /** text field for element's code */
    @FXML
    private TextField ajoutcode;
    /** text field for element's name */
    @FXML
    private TextField ajoutnom;
    /** text field for element's purchase price */
    @FXML
    private TextField ajoutprixa;
    /** text field for element's selling price */
    @FXML
    private TextField ajoutprixv;
    /** text field for element's quantities */
    @FXML
    private TextField ajoutqte;
    /** text field for element's unit */
    @FXML
    private ComboBox<Unite> ajoutunite;
    /** information message */
    @FXML
    private Label message;


    /* ===========================================
     * TABLEVIEW INITIALIZATION
     * =========================================== */
    /**
     * The application will load this function at the start when it's called
     * The code will display a table view, with custom columns
     * It also listens to clicks on table view rows in order to fill text fields
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Create the tableview cells
        elementCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        elementNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        elementPrixA.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
        elementPrixV.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        elementQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        elementUnite.setCellValueFactory(new PropertyValueFactory<>("uniteMesure"));

        //Add Element in cells
        elementTableView.setItems(getElements());

        //Set text fields with data of the selected Element in the tableview
        elementTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ajoutcode.setText(newSelection.getCode());
                ajoutnom.setText(newSelection.getNom());
                ajoutqte.setText(String.valueOf(newSelection.getQuantite()));
                ajoutprixa.setText(String.valueOf(newSelection.getPrixAchat()));
                ajoutprixv.setText(String.valueOf(newSelection.getPrixVente()));
                ajoutunite.setValue(newSelection.getUniteMesure());
            }
        });

        //Add Unite values in the comboxbox and set 'pieces' by default
        ajoutunite.getItems().addAll(Unite.values());
        ajoutunite.setValue(Unite.pieces);
    }


    /* ===========================================
     * NAVIGATION
     * =========================================== */
    /**
     * Enable to change the scene from inventaire to accueil
     * @param actionEvent - click on tab
     * @throws IOException if the scene do not exist
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml", actionEvent);
    }

    /**
     * Enable to change the scene from inventaire to chaine de production
     * @param actionEvent : click on tab
     * @throws IOException if the scene do not exist
     */
    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/chaineproduction-view.fxml", actionEvent);
    }

    /**
     * Enable to change the scene from inventaire to inventaire (refresh)
     * @param actionEvent : click on tab
     * @throws IOException if the scene do not exist
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml", actionEvent);
    }


    /* ===========================================
     * UTILS FOR CONTROLLER
     * =========================================== */
    /**
     * Print a message whose content and color are given
     * @param style : a css style in String format
     * @param text : content of message
     */
    public void printLabel(String style, String text){
        message.setStyle(style);
        message.setText(text);
    }

    /**
     * Creates a new element from the information provided in the text fields
     * @return Element created through the GUI of inventaire
     */
    public Element textfieldsToElement(){
        return new Element(ajoutcode.getText(),ajoutnom.getText(),
                Double.parseDouble(ajoutqte.getText()), ajoutunite.getValue(),
                Double.parseDouble(ajoutprixa.getText()), Double.parseDouble(ajoutprixv.getText()));
    }

    /**
     * Creates a new item from a selected row in the tableview
     * @return Element selected in the GUI
     */
    public Element selecteditemToElement(){
        return elementTableView.getSelectionModel().getSelectedItem();
    }


    /* ===========================================
     * CHECK FORMAT OF INPUTS
     * =========================================== */
    /**
     * Checks if the text fields are empty
     * @return a boolean
     */
    public boolean checkEmptyField(){
        return ajoutcode.getText().isEmpty() ||
                ajoutnom.getText().isEmpty() ||
                ajoutqte.getText().isEmpty() ||
                ajoutprixa.getText().isEmpty() ||
                ajoutprixv.getText().isEmpty();
    }

    /**
     * Checks if the element's code is take without affecting quantity addition
     * @return a boolean
     */
    public boolean checkCodeIsTake(){
        Element e = getElementByCode(ajoutcode.getText());
        if (e != null) {
            return !Objects.equals(e.getNom(), ajoutnom.getText()) ||
                    !e.getPrixAchat().toString().equals(ajoutprixa.getText()) ||
                    !e.getPrixVente().toString().equals(ajoutprixv.getText()) ||
                    e.getUniteMesure() != ajoutunite.getValue()
                    ;
        }
        return false;
    }

    /**
     * Checks if the element's code exists according to the name entered in the text field
     * @return a boolean
     */
    public boolean checkElementExist() {
        Element e = getElementByName(ajoutnom.getText());
        if (e != null) {
            return !Objects.equals(e.getCode(), ajoutcode.getText());
        }
        return false;
    }

    /**
     * Check all conditions for modify (format and number check)
     * print the correct associated error message
     * @return a boolean
     */
    public boolean checkAllModif(){
        if(checkEmptyField()) {
            printLabel("-fx-text-fill: red", "Un des champs est vide");
            return false;
        }
        if (!checkFormatCode(ajoutcode.getText())) {
            printLabel("-fx-text-fill: red", "Code pas au bon format\nFormat : 'E000' - 'E999'");
            return false;
        }
        if (!checkQuantite(Double.parseDouble(ajoutqte.getText()))) {
            printLabel("-fx-text-fill: red", "Qte doit être > 0");
            return false;
        }
        if (!checkPurchasePrice(Double.parseDouble(ajoutprixa.getText()))) {
            printLabel("-fx-text-fill: red", "Prix achat doit être > 0");
            return false;
        }
        if (!checkSellingPrice(Double.parseDouble(ajoutprixv.getText()))) {
            printLabel("-fx-text-fill: red", "Prix vente doit être > 0");
            return false;
        }
        return true;
    }

    /**
     * Check all conditions for add (format, number and existence check)
     * print the correct associated error message
     * @return a boolean
     */
    public boolean checkAllAdd(){
        if (!checkAllModif()){
            return false;
        }
        if (checkCodeIsTake()) {
            printLabel("-fx-text-fill: red","Code déjà pris pour un autre\nnom, prix et/ou unite");
            return false;
        }
        if (checkElementExist()) {
            printLabel("-fx-text-fill: red","Element déjà présent\navec le code : " +
                    getElementByName(ajoutnom.getText()).getCode());
            return false;
        }
        return true;
    }


    /* ===========================================
     * ACTION ON BUTTON
     * =========================================== */
    /**
     * Create an element and add it in the table view or adjust his quantities
     */
    public void addElement(){
        if (checkAllAdd()){
            Element e = textfieldsToElement();
            if (elementsContains(e)) {
                Double postqte = addQuantitiesOfElement(e);
                printLabel("-fx-text-fill: green", "Quantité mise à jour");
                elementTableView.refresh();
                ajoutqte.setText(String.valueOf(postqte));
            }
            else {
                addToElements(e);
                printLabel("-fx-text-fill: green", "Element ajouté");
            }
        }
    }

    /**
     * Remove the selected element from the table view
     * @return an element
     */
    public Element delElement(){
        Element e = selecteditemToElement();
        if (!checkEmptyField()){
            removeToElement(e);
            printLabel("-fx-text-fill: red", "Element supprimé");
            return e;
        }
        else {
            printLabel("-fx-text-fill: red", "Aucun élément sélectionné");
            return null;
        }
    }

    /**
     * Modify the selected element from the table view
     */
    public void modifyElement() {
        if (checkAllModif()){
            Element eltextf = textfieldsToElement();
            Element elselect = selecteditemToElement();
            if (elementsContains(elselect)){
                modifyToElement(elselect,eltextf);
                elementTableView.refresh();
                printLabel("-fx-text-fill: green", "Element modifié");
            }
            else{
                printLabel("-fx-text-fill: red", "Element non modifiable");
            }
        }
    }

    /**
     * Clear all text fields
     */
    public void clearTextField() {
        ajoutcode.setText("");
        ajoutnom.setText("");
        ajoutqte.setText("");
        ajoutprixa.setText("");
        ajoutprixv.setText("");
        ajoutunite.setValue(Unite.pieces);
        message.setText("");
    }
}
