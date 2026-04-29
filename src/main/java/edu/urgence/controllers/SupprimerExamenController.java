package edu.urgence.controllers;

import edu.urgence.entities.Examens_demandes;
import edu.urgence.services.Examens_demandesService;
import javafx.fxml.FXML;

public class SupprimerExamenController {

    private final Examens_demandesService service = new Examens_demandesService();

    private Examens_demandes examen = GestionExamensController.getExamenSelectionne();

    @FXML
    private void handleSupprimer() {
        service.supprimerExamen(examen.getIdDemande());
    }
}