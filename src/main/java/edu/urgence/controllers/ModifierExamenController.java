package edu.urgence.controllers;

import edu.urgence.entities.Examens_demandes;
import edu.urgence.services.Examens_demandesService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierExamenController {

    @FXML private TextField tfIdConsultation;
    @FXML private TextField tfTypeExamen;
    @FXML private ComboBox<String> cbStatut;
    @FXML private CheckBox cbUrgent;
    @FXML private Label lblMessage;

    private final Examens_demandesService service = new Examens_demandesService();

    private Examens_demandes examen;

    @FXML
    public void initialize() {

        // Remplir ComboBox
        cbStatut.getItems().addAll("En attente", "Realise");

        // Récupérer l'examen sélectionné
        examen = GestionExamensController.getExamenSelectionne();

        if (examen != null) {
            tfIdConsultation.setText(String.valueOf(examen.getIdConsultation()));
            tfTypeExamen.setText(examen.getTypeExamen());
            cbStatut.setValue(examen.getStatut());
            cbUrgent.setSelected(examen.isUrgent());
        }
    }

    @FXML
    private void handleModifier() {

        if (examen == null) {
            lblMessage.setText("Aucun examen sélectionné.");
            return;
        }

        try {
            examen.setIdConsultation(Integer.parseInt(tfIdConsultation.getText().trim()));
            examen.setTypeExamen(tfTypeExamen.getText().trim());
            examen.setStatut(cbStatut.getValue());
            examen.setUrgent(cbUrgent.isSelected());

            service.modifierExamen(examen);

            lblMessage.setText("Examen modifié avec succès.");

            fermerFenetre();

        } catch (Exception e) {
            lblMessage.setText("Erreur : " + e.getMessage());
        }
    }

    private void fermerFenetre() {
        Stage stage = (Stage) tfIdConsultation.getScene().getWindow();
        stage.close();
    }
}