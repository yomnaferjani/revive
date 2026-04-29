package edu.urgence.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent examensView = FXMLLoader.load(getClass().getResource("/GestionExamens.fxml"));
        Parent resultatsView = FXMLLoader.load(getClass().getResource("/GestionResultats.fxml"));

        Tab tabExamens = new Tab("Examens Demandés");
        tabExamens.setContent(examensView);
        tabExamens.setClosable(false);

        Tab tabResultats = new Tab("Résultats");
        tabResultats.setContent(resultatsView);
        tabResultats.setClosable(false);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(tabExamens, tabResultats);

        Scene scene = new Scene(tabPane, 950, 680);

        primaryStage.setTitle("REVIVE - Module Laboratoire et Imagerie");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(MainClass.class, args);
    }
}