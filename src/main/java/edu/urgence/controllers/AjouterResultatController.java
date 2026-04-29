package edu.urgence.controllers;

import edu.urgence.entities.Resultats;
import edu.urgence.services.ResultatService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Date;

public class AjouterResultatController {

    @FXML private TextField tfIdDemande;
    @FXML private TextArea taCompteRendu;
    @FXML private TextField tfFichierJoint;
    @FXML private Label lblMessage;

    private final ResultatService service = new ResultatService();

    @FXML
    private void handleAjouter() {
        if (tfIdDemande.getText().trim().isEmpty() || taCompteRendu.getText().trim().isEmpty()) {
            lblMessage.setText("Veuillez remplir les champs obligatoires.");
            return;
        }

        Resultats r = new Resultats();

        r.setIdDemande(Integer.parseInt(tfIdDemande.getText().trim()));
        r.setCompteRendu(taCompteRendu.getText().trim());
        r.setFichierJoint(tfFichierJoint.getText().trim());
        r.setDateResultat(new Date());

        service.ajouter(r);

        lblMessage.setText("Résultat ajouté avec succès.");
        handleVider();
    }

    @FXML
    private void handleVider() {
        tfIdDemande.clear();
        taCompteRendu.clear();
        tfFichierJoint.clear();
    }
}