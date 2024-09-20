package com.example.fitnesstracker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FitnessTrackerApp extends Application {

    private static final int PADDING = 10;
    private static final int CHART_MIN_X = 5;
    private static final int CHART_MAX_X = 15;
    private static final int CHART_TICK_UNIT = 2;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(PADDING));

        // Top section
        HBox topSection = new HBox(PADDING);
        ComboBox<String> groupComboBox = new ComboBox<>();
        groupComboBox.getItems().addAll("Me", "Family", "Friends", "Work");
        groupComboBox.setValue("Me");
        Button manageGroupsButton = new Button("Manage groups");
        topSection.getChildren().addAll(new Label("Group:"), groupComboBox, manageGroupsButton);
        root.setTop(topSection);

        // Center section (graph)
        NumberAxis xAxis = new NumberAxis(CHART_MIN_X, CHART_MAX_X, CHART_TICK_UNIT);
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Daily Activity");
        XYChart.Series<Number, Number> stepsSeries = new XYChart.Series<>();
        stepsSeries.setName("Steps");
        XYChart.Series<Number, Number> caloriesSeries = new XYChart.Series<>();
        caloriesSeries.setName("Calories");

        // Add some dummy data
        // addDummyData(stepsSeries, caloriesSeries);

        // lineChart.getData().addAll(stepsSeries, caloriesSeries);
        // root.setCenter(lineChart);

        // Bottom section
        HBox bottomSection = new HBox(PADDING);
        CheckBox stepsCheckBox = new CheckBox("Steps");
        CheckBox caloriesCheckBox = new CheckBox("Calories");
        stepsCheckBox.setSelected(true);
        caloriesCheckBox.setSelected(true);
        ComboBox<String> timelineComboBox = new ComboBox<>();
        timelineComboBox.getItems().addAll("Today", "This week", "Last week", "This month");
        timelineComboBox.setValue("Today");
        bottomSection.getChildren().addAll(stepsCheckBox, caloriesCheckBox, new Label("Timeline:"), timelineComboBox);
        root.setBottom(bottomSection);

        // Left section (menu)
        VBox leftMenu = new VBox(PADDING);
        leftMenu.setPadding(new Insets(PADDING));
        leftMenu.getChildren().addAll(
            new Button("My data"),
            new Button("Settings"),
            new Button("Log out")
        );
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Fitness Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Quit button functionality
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> primaryStage.close());
        leftMenu.getChildren().add(quitButton);

        // Manage groups button functionality
        manageGroupsButton.setOnAction(e -> showManageGroupsDialog());
    }

    private void addDummyData(XYChart.Series<Number, Number> stepsSeries, XYChart.Series<Number, Number> caloriesSeries) {
        int[] hours = {5, 7, 9, 11, 13, 15};
        int[] steps = {0, 500, 1000, 3000, 5000, 6112};
        int[] calories = {0, 20, 40, 60, 80, 101};

        for (int i = 0; i < hours.length; i++) {
            stepsSeries.getData().add(new XYChart.Data<>(hours[i], steps[i]));
            caloriesSeries.getData().add(new XYChart.Data<>(hours[i], calories[i]));
        }
    }

    private void showManageGroupsDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Manage Groups");
        dialog.setHeaderText("My groups");

        VBox content = new VBox(PADDING);
        content.setPadding(new Insets(20));

        String[] groups = {"Family", "Friends", "Work"};
        for (String group : groups) {
            HBox groupBox = new HBox(PADDING);
            groupBox.getChildren().addAll(new Label(group), new Button("+"));
            content.getChildren().add(groupBox);
        }

        TextField newGroupField = new TextField();
        newGroupField.setPromptText("New group");
        Button addNewGroupButton = new Button("+");
        HBox newGroupBox = new HBox(PADDING, newGroupField, addNewGroupButton);
        content.getChildren().add(newGroupBox);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}