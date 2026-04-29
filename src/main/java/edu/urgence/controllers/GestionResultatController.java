package edu.urgence.controllers;

import edu.urgence.entities.Resultats;
import edu.urgence.services.ResultatService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.util.List;

public class GestionResultatController {

    @FXML private VBox containerResultats;
    @FXML private Label lblMessage;

    private final ResultatService service = new ResultatService();

    private static Resultats resultatSelectionne;

    public static Resultats getResultatSelectionne() {
        return resultatSelectionne;
    }

    @FXML
    public void initialize() {
        chargerResultats();
    }

    private void chargerResultats() {
        containerResultats.getChildren().clear();

        List<Resultats> resultats = service.afficher();

        if (resultats == null || resultats.isEmpty()) {
            Label emptyLabel = new Label("Aucun résultat pour le moment.");
            emptyLabel.setStyle("-fx-text-fill: #8A94A6; -fx-font-size: 14px;");
            containerResultats.getChildren().add(emptyLabel);
            return;
        }

        for (Resultats r : resultats) {
            containerResultats.getChildren().add(creerCarteResultat(r));
        }
    }

    private VBox creerCarteResultat(Resultats r) {
        VBox card = new VBox(8);

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-padding: 15;" +
                        "-fx-border-color: #2563EB;" +
                        "-fx-border-width: 0 0 0 5;" +
                        "-fx-border-radius: 16;"
        );

        Label title = new Label("Résultat #" + r.getIdResultat());
        title.setStyle("-fx-text-fill: #0F172A; -fx-font-size: 17px; -fx-font-weight: bold;");

        Label demande = new Label("ID Demande : " + r.getIdDemande());
        Label compteRendu = new Label("Compte rendu : " + r.getCompteRendu());
        Label fichier = new Label("Fichier : " + r.getFichierJoint());
        Label date = new Label("Date : " + r.getDateResultat());

        demande.setStyle("-fx-text-fill: #64748B;");
        compteRendu.setStyle("-fx-text-fill: #64748B;");
        fichier.setStyle("-fx-text-fill: #64748B;");
        date.setStyle("-fx-text-fill: #64748B;");

        HBox badge = new HBox();
        Label label = new Label("Résultat enregistré");
        label.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #10B981; -fx-font-weight: bold; -fx-padding: 5 10; -fx-background-radius: 10;");
        badge.getChildren().add(label);

        card.getChildren().addAll(title, demande, compteRendu, fichier, date, badge);

        card.setOnMouseClicked(event -> {
            resultatSelectionne = r;
            lblMessage.setText("Résultat sélectionné : #" + r.getIdResultat());
        });

        return card;
    }

    @FXML
    private void ouvrirAjouter() {
        resultatSelectionne = null;
        ouvrirFenetre("/AjouterResultat.fxml", "Ajouter un résultat");
    }

    @FXML
    private void ouvrirModifier() {
        if (resultatSelectionne == null) {
            afficherMessage("Veuillez sélectionner un résultat à modifier.");
            return;
        }

        ouvrirFenetre("/ModifierResultat.fxml", "Modifier un résultat");
    }

    @FXML
    private void ouvrirSupprimer() {
        if (resultatSelectionne == null) {
            afficherMessage("Veuillez sélectionner un résultat à supprimer.");
            return;
        }

        ouvrirFenetre("/SupprimerResultat.fxml", "Supprimer un résultat");
    }

    @FXML
    private void handleActualiser() {
        lblMessage.setText("");
        chargerResultats();
    }

    private void ouvrirFenetre(String fxml, String titre) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));

            Stage stage = new Stage();
            stage.setTitle(titre);
            stage.setScene(new Scene(root, 900, 620));
            stage.setResizable(true);

            stage.setOnHidden(event -> chargerResultats());

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            afficherMessage("Erreur ouverture : " + fxml);
        }
    }

    private void afficherMessage(String message) {
        lblMessage.setText(message);
        lblMessage.setStyle("-fx-text-fill: #EF4444; -fx-font-weight: bold;");
    }

    // ═══════════════  NAVIGATION SIDEBAR  ═══════════════

    @FXML
    void showExamens() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionExamens.fxml"));
            Stage stage = (Stage) containerResultats.getScene().getWindow();
            double w = stage.getWidth();
            double h = stage.getHeight();
            stage.setScene(new Scene(root, w, h));
        } catch (Exception e) {
            e.printStackTrace();
            afficherMessage("Erreur navigation vers Examens.");
        }
    }

    @FXML
    void showResultats() {
        // Déjà sur la page Résultats — on rafraîchit simplement
        chargerResultats();
    }
}