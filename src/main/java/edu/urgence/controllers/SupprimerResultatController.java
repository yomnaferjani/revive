package edu.urgence.controllers;

import edu.urgence.entities.Resultats;
import edu.urgence.services.ResultatService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SupprimerResultatController {

    @FXML private Label lblInfo;
    @FXML private Label lblMessage;

    private final ResultatService service = new ResultatService();

    private Resultats resultat;

    @FXML
    public void initialize() {
        resultat = GestionResultatController.getResultatSelectionne();

        if (resultat != null) {
            lblInfo.setText("Voulez-vous supprimer le résultat #" + resultat.getIdResultat() + " ?");
        }
    }

    @FXML
    private void handleSupprimer() {
        if (resultat == null) {
            lblMessage.setText("Aucun résultat sélectionné.");
            return;
        }

        service.supprimer(resultat.getIdResultat());
        fermerFenetre();
    }

    @FXML
    private void handleAnnuler() {
        fermerFenetre();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) lblInfo.getScene().getWindow();
        stage.close();
    }
}