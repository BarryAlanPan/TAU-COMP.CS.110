package com.example.fitnesstracker.view;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FitnessView {
    private static final int PADDING = 10;

    private BorderPane root;
    private ComboBox<String> groupComboBox;
    private Button manageGroupsButton;
    private LineChart<Number, Number> lineChart;
    private XYChart.Series<Number, Number> stepsSeries;
    private XYChart.Series<Number, Number> caloriesSeries;
    private CheckBox stepsCheckBox;
    private CheckBox caloriesCheckBox;
    private ComboBox<String> timelineComboBox;
    private VBox leftMenu;

    public FitnessView() {
        createView();
    }

    private void createView() {
        root = new BorderPane();
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
        leftMenu = createLeftMenu();
        root.setLeft(leftMenu);
    }

    private HBox createTopSection() {
        HBox topSection = new HBox(10);
        topSection.setPadding(new Insets(0, 0, PADDING, 0));

        groupComboBox = new ComboBox<>();
        groupComboBox.setPromptText("Select Group");

        manageGroupsButton = new Button("Manage Groups");

        topSection.getChildren().addAll(groupComboBox, manageGroupsButton);
        return topSection;
    }

    private LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Fitness Data");

        stepsSeries = new XYChart.Series<>();
        stepsSeries.setName("Steps");

        caloriesSeries = new XYChart.Series<>();
        caloriesSeries.setName("Calories");

        chart.getData().addAll(stepsSeries, caloriesSeries);
        return chart;
    }

    private HBox createBottomSection() {
        HBox bottomSection = new HBox(10);
        bottomSection.setPadding(new Insets(PADDING, 0, 0, 0));

        stepsCheckBox = new CheckBox("Steps");
        stepsCheckBox.setSelected(true);

        caloriesCheckBox = new CheckBox("Calories");
        caloriesCheckBox.setSelected(true);

        timelineComboBox = new ComboBox<>();
        timelineComboBox.getItems().addAll("Today", "This Week");
        timelineComboBox.setValue("Today");

        bottomSection.getChildren().addAll(stepsCheckBox, caloriesCheckBox, timelineComboBox);
        return bottomSection;
    }

    private VBox createLeftMenu() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(0, PADDING, 0, 0));

        Button homeButton = new Button("Home");
        Button profileButton = new Button("Profile");
        Button settingsButton = new Button("Settings");

        menu.getChildren().addAll(homeButton, profileButton, settingsButton);
        return menu;
    }

    // Getters for all UI components
    public BorderPane getRoot() { return root; }
    public ComboBox<String> getGroupComboBox() { return groupComboBox; }
    public Button getManageGroupsButton() { return manageGroupsButton; }
    public LineChart<Number, Number> getLineChart() { return lineChart; }
    public XYChart.Series<Number, Number> getStepsSeries() { return stepsSeries; }
    public XYChart.Series<Number, Number> getCaloriesSeries() { return caloriesSeries; }
    public CheckBox getStepsCheckBox() { return stepsCheckBox; }
    public CheckBox getCaloriesCheckBox() { return caloriesCheckBox; }
    public ComboBox<String> getTimelineComboBox() { return timelineComboBox; }
    public VBox getLeftMenu() { return leftMenu; }
}