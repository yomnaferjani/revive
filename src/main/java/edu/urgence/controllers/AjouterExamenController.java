package edu.urgence.controllers;

import edu.urgence.entities.Examens_demandes;
import edu.urgence.services.Examens_demandesService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;

public class AjouterExamenController {

    @FXML private TextField tfIdConsultation;
    @FXML private TextField tfTypeExamen;
    @FXML private ComboBox<String> cbStatut;
    @FXML private CheckBox cbUrgent;
    @FXML private Label lblMessage;

    private final Examens_demandesService service = new Examens_demandesService();

    @FXML
    public void initialize() {
        cbStatut.getItems().addAll("En attente", "Realise");
        cbStatut.setValue("En attente");
    }

    @FXML
    private void handleAjouter() {

        Examens_demandes e = new Examens_demandes();

        e.setIdConsultation(Integer.parseInt(tfIdConsultation.getText()));
        e.setTypeExamen(tfTypeExamen.getText());
        e.setDateDemande(new Date());
        e.setStatut(cbStatut.getValue());
        e.setUrgent(cbUrgent.isSelected());

        service.ajouterExamen(e);

        lblMessage.setText("Ajout réussi !");
    }

    @FXML
    private void handleVider() {
        tfIdConsultation.clear();
        tfTypeExamen.clear();
        cbStatut.setValue("En attente");
        cbUrgent.setSelected(false);
    }
}