package edu.urgence.controllers;

import edu.urgence.entities.Resultats;
import edu.urgence.services.ResultatService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ModifierResultatController {

    @FXML private TextField tfIdDemande;
    @FXML private TextArea taCompteRendu;
    @FXML private TextField tfFichierJoint;
    @FXML private Label lblMessage;

    private final ResultatService service = new ResultatService();

    private Resultats resultat;

    @FXML
    public void initialize() {
        resultat = GestionResultatController.getResultatSelectionne();

        if (resultat != null) {
            tfIdDemande.setText(String.valueOf(resultat.getIdDemande()));
            taCompteRendu.setText(resultat.getCompteRendu());
            tfFichierJoint.setText(resultat.getFichierJoint());
        }
    }

    @FXML
    private void handleModifier() {
        if (resultat == null) {
            lblMessage.setText("Aucun résultat sélectionné.");
            return;
        }

        resultat.setIdDemande(Integer.parseInt(tfIdDemande.getText().trim()));
        resultat.setCompteRendu(taCompteRendu.getText().trim());
        resultat.setFichierJoint(tfFichierJoint.getText().trim());

        service.modifier(resultat);

        lblMessage.setText("Résultat modifié avec succès.");
    }
}