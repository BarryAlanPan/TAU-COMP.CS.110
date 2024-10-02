package com.example.fitnesstracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.fitnesstracker.controller.FitnessController;
import com.example.fitnesstracker.model.FitnessModel;
import com.example.fitnesstracker.view.FitnessView;

public class FitnessTrackerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        FitnessModel model = new FitnessModel();
        FitnessView view = new FitnessView();
        FitnessController controller = new FitnessController(model, view);

        Scene scene = new Scene(view.getRoot(), 800, 600);
        primaryStage.setTitle("Fitness Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial data load
        controller.loadTodayData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}