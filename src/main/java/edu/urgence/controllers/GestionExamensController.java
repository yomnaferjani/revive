package edu.urgence.controllers;

import edu.urgence.entities.Examens_demandes;
import edu.urgence.services.Examens_demandesService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.util.List;

public class GestionExamensController {

    @FXML
    private VBox containerExamens;

    @FXML
    private Label lblMessage;

    private final Examens_demandesService service = new Examens_demandesService();

    private static Examens_demandes examenSelectionne;

    public static Examens_demandes getExamenSelectionne() {
        return examenSelectionne;
    }

    @FXML
    public void initialize() {
        chargerExamens();
    }

    private void chargerExamens() {
        containerExamens.getChildren().clear();

        List<Examens_demandes> examens = service.getAllExamens();

        if (examens == null || examens.isEmpty()) {
            Label emptyLabel = new Label("Aucun examen demandé pour le moment.");
            emptyLabel.setStyle("-fx-text-fill: #8A94A6; -fx-font-size: 14px;");
            containerExamens.getChildren().add(emptyLabel);
            return;
        }

        for (Examens_demandes examen : examens) {
            containerExamens.getChildren().add(creerCarteExamen(examen));
        }
    }

    private VBox creerCarteExamen(Examens_demandes examen) {
        VBox card = new VBox(8);

        String borderColor = examen.isUrgent() ? "#EF4444" : "#2563EB";

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-padding: 15;" +
                        "-fx-border-color: " + borderColor + ";" +
                        "-fx-border-width: 0 0 0 5;" +
                        "-fx-border-radius: 16;" +
                        "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.12), 10, 0, 0, 3);"
        );

        Label title = new Label(examen.getTypeExamen());
        title.setStyle("-fx-text-fill: #0F172A; -fx-font-size: 17px; -fx-font-weight: bold;");

        Label consultation = new Label("ID Consultation : " + examen.getIdConsultation());
        consultation.setStyle("-fx-text-fill: #64748B; -fx-font-size: 13px;");

        Label date = new Label("Date : " + examen.getDateDemande());
        date.setStyle("-fx-text-fill: #64748B; -fx-font-size: 13px;");

        Label statut = new Label("Statut : " + examen.getStatut());
        statut.setStyle("-fx-text-fill: #2563EB; -fx-font-size: 13px; -fx-font-weight: bold;");

        Label urgent = new Label(examen.isUrgent() ? "URGENT" : "Non urgent");
        urgent.setStyle(
                examen.isUrgent()
                        ? "-fx-background-color: #FEE2E2; -fx-text-fill: #EF4444; -fx-font-weight: bold; -fx-padding: 5 10; -fx-background-radius: 10;"
                        : "-fx-background-color: #DCFCE7; -fx-text-fill: #10B981; -fx-font-weight: bold; -fx-padding: 5 10; -fx-background-radius: 10;"
        );

        HBox bottom = new HBox(10);
        bottom.getChildren().addAll(statut, urgent);

        card.getChildren().addAll(title, consultation, date, bottom);

        card.setOnMouseClicked(event -> {
            examenSelectionne = examen;
            lblMessage.setText("Examen sélectionné : " + examen.getTypeExamen());
        });

        return card;
    }

    @FXML
    private void ouvrirAjouter() {
        examenSelectionne = null;
        ouvrirFenetre("/AjouterExamen.fxml", "Ajouter un examen");
    }

    @FXML
    private void ouvrirModifier() {
        if (examenSelectionne == null) {
            afficherMessage("Veuillez sélectionner un examen à modifier.");
            return;
        }

        ouvrirFenetre("/ModifierExamen.fxml", "Modifier un examen");
    }

    @FXML
    private void ouvrirSupprimer() {
        if (examenSelectionne == null) {
            afficherMessage("Veuillez sélectionner un examen à supprimer.");
            return;
        }

        ouvrirFenetre("/SupprimerExamen.fxml", "Supprimer un examen");
    }

    @FXML
    private void handleActualiser() {
        lblMessage.setText("");
        chargerExamens();
    }

    private void ouvrirFenetre(String cheminFXML, String titre) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(cheminFXML));

            Stage stage = new Stage();
            stage.setTitle(titre);
            stage.setScene(new Scene(root, 900, 620));
            stage.setResizable(true);

            stage.setOnHidden(event -> chargerExamens());

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            afficherMessage("Erreur lors de l'ouverture : " + cheminFXML);
        }
    }

    private void afficherMessage(String message) {
        lblMessage.setText(message);
        lblMessage.setStyle("-fx-text-fill: #EF4444; -fx-font-weight: bold;");
    }

    @FXML
    void showExamens() {
        // Déjà sur la page Examens — on rafraîchit simplement
        chargerExamens();
    }

    @FXML
    void showResultats() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionResultats.fxml"));
            Stage stage = (Stage) containerExamens.getScene().getWindow();
            double w = stage.getWidth();
            double h = stage.getHeight();
            stage.setScene(new Scene(root, w, h));
        } catch (Exception e) {
            e.printStackTrace();
            afficherMessage("Erreur navigation vers Résultats.");
        }
    }
}