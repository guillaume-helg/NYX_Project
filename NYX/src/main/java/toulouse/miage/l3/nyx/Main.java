package toulouse.miage.l3.nyx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Usine;
import toulouse.miage.l3.nyx.core.utils.UtilsChaine;
import toulouse.miage.l3.nyx.core.utils.UtilsElement;

import java.io.IOException;
import java.util.Objects;

import static toulouse.miage.l3.nyx.core.model.Usine.getChaine;
import static toulouse.miage.l3.nyx.core.model.Usine.getElements;
import static toulouse.miage.l3.nyx.core.utils.UtilsCommande.getUsedElement;

/**
 * Main class to launch the GUI Application
 * @author Guillaume Helg, Lucas Godard, Hugues Ansoborlo
 * @version 1.0
 */
public class Main extends Application {
    public Usine usine;

    /**
     * Enable to set the frame of the GUI of the application
     * @param stage take the stage
     */
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml")));
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("NYX");
            stage.setScene(scene);
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.show();

            usine = Usine.getInstance(0);
            usine.chargerElements();
            usine.chargerChaines();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    UtilsElement.writeElement(getElements());
                    UtilsChaine.writeChaine(getChaine().toArray(new Chaine[0]));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to lauch the app
     * @param args : string pararm
     */
    public static void main(String[] args) {
        launch();
    }
}