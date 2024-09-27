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
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private LineChart<Number, Number> lineChart;
    private XYChart.Series<Number, Number> stepsSeries;
    private XYChart.Series<Number, Number> caloriesSeries;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(PADDING));

        // Top section
        HBox topSection = createTopSection();
        root.setTop(topSection);

        // Center section (graph)
        lineChart = createLineChart();
        root.setCenter(lineChart);

        // Bottom section
        HBox bottomSection = createBottomSection();
        root.setBottom(bottomSection);

        // Left section (menu)
        VBox leftMenu = createLeftMenu(primaryStage);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Fitness Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial data load
        loadTodayData();
    }

    private HBox createTopSection() {
        HBox topSection = new HBox(PADDING);
        ComboBox<String> groupComboBox = new ComboBox<>();
        groupComboBox.getItems().addAll("Me", "Family", "Friends", "Work");
        groupComboBox.setValue("Me");
        Button manageGroupsButton = new Button("Manage groups");
        manageGroupsButton.setOnAction(e -> showManageGroupsDialog());
        topSection.getChildren().addAll(new Label("Group:"), groupComboBox, manageGroupsButton);
        return topSection;
    }

    private LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Daily Activity");
        chart.setCreateSymbols(false);

        stepsSeries = new XYChart.Series<>();
        stepsSeries.setName("Steps");
        caloriesSeries = new XYChart.Series<>();
        caloriesSeries.setName("Calories");

        chart.getData().addAll(stepsSeries, caloriesSeries);
        return chart;
    }

    private HBox createBottomSection() {
        HBox bottomSection = new HBox(PADDING);
        CheckBox stepsCheckBox = new CheckBox("Steps");
        CheckBox caloriesCheckBox = new CheckBox("Calories");
        stepsCheckBox.setSelected(true);
        caloriesCheckBox.setSelected(true);

        stepsCheckBox.setOnAction(e -> toggleSeries(stepsSeries, stepsCheckBox.isSelected()));
        caloriesCheckBox.setOnAction(e -> toggleSeries(caloriesSeries, caloriesCheckBox.isSelected()));

        ComboBox<String> timelineComboBox = new ComboBox<>();
        timelineComboBox.getItems().addAll("Today", "This week");
        timelineComboBox.setValue("Today");
        timelineComboBox.setOnAction(e -> {
            if (timelineComboBox.getValue().equals("Today")) {
                loadTodayData();
            } else {
                loadWeekData();
            }
        });

        bottomSection.getChildren().addAll(stepsCheckBox, caloriesCheckBox, new Label("Timeline:"), timelineComboBox);
        return bottomSection;
    }

    private VBox createLeftMenu(Stage primaryStage) {
        VBox leftMenu = new VBox(PADDING);
        leftMenu.setPadding(new Insets(PADDING));
        leftMenu.getChildren().addAll(
            new Button("My data"),
            new Button("Settings"),
            new Button("Log out")
        );

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> primaryStage.close());
        leftMenu.getChildren().add(quitButton);

        return leftMenu;
    }

    private void toggleSeries(XYChart.Series<Number, Number> series, boolean visible) {
        series.getNode().setVisible(visible);
        for (XYChart.Data<Number, Number> data : series.getData()) {
            if (data.getNode() != null) {
                data.getNode().setVisible(visible);
            }
        }
    }

    private void loadTodayData() {
        int[] steps = {0, 20, 80, 300, 411, 550, 790, 932, 1074};
        int[] calories = {75, 155, 300, 430, 511, 698, 785, 867, 915};
        int[] hours = {7, 8, 9, 10, 11, 12, 13, 14, 15};

        updateChartData(steps, calories, hours);
        lineChart.getXAxis().setLabel("Hour");
    }

    private void loadWeekData() {
        int[] steps = {6500, 11025, 7450, 7923, 10002, 4999, 1074};
        int[] calories = {2000, 3140, 2300, 2560, 2990, 1780, 470};
        int[] days = {1, 2, 3, 4, 5, 6, 7};

        updateChartData(steps, calories, days);
        lineChart.getXAxis().setLabel("Day of Week");
    }

    private void updateChartData(int[] steps, int[] calories, int[] xValues) {
        stepsSeries.getData().clear();
        caloriesSeries.getData().clear();

        for (int i = 0; i < xValues.length; i++) {
            stepsSeries.getData().add(new XYChart.Data<>(xValues[i], steps[i]));
            caloriesSeries.getData().add(new XYChart.Data<>(xValues[i], calories[i]));
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