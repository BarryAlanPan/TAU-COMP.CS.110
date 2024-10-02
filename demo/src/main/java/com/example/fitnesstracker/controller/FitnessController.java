package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.model.FitnessModel;
import com.example.fitnesstracker.view.FitnessView;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class FitnessController {
    private FitnessModel model;
    private FitnessView view;

    public FitnessController(FitnessModel model, FitnessView view) {
        this.model = model;
        this.view = view;
        initializeViewHandlers();
        initializeGroupComboBox();
    }

    private void initializeViewHandlers() {
        view.getManageGroupsButton().setOnAction(e -> showManageGroupsDialog());
        view.getStepsCheckBox().setOnAction(e -> toggleSeries(view.getStepsSeries(), view.getStepsCheckBox().isSelected()));
        view.getCaloriesCheckBox().setOnAction(e -> toggleSeries(view.getCaloriesSeries(), view.getCaloriesCheckBox().isSelected()));
        view.getTimelineComboBox().setOnAction(e -> {
            if (view.getTimelineComboBox().getValue().equals("Today")) {
                loadTodayData();
            } else {
                loadWeekData();
            }
        });
    }

    private void initializeGroupComboBox() {
        view.getGroupComboBox().setItems(model.getGroups());
        view.getGroupComboBox().getSelectionModel().selectFirst();
    }

    public void loadTodayData() {
        int[] steps = {0, 20, 80, 300, 411, 550, 790, 932, 1074};
        int[] calories = {75, 155, 300, 430, 511, 698, 785, 867, 915};
        int[] hours = {7, 8, 9, 10, 11, 12, 13, 14, 15};

        model.setDailyData(steps, calories, hours);
        updateChartData();
        view.getLineChart().getXAxis().setLabel("Hour");
    }

    public void loadWeekData() {
        int[] steps = {6500, 11025, 7450, 7923, 10002, 4999, 1074};
        int[] calories = {2000, 3140, 2300, 2560, 2990, 1780, 470};
        int[] days = {1, 2, 3, 4, 5, 6, 7};

        model.setDailyData(steps, calories, days);
        updateChartData();
        view.getLineChart().getXAxis().setLabel("Day of Week");
    }

    private void updateChartData() {
        view.getStepsSeries().getData().clear();
        view.getCaloriesSeries().getData().clear();

        int[] steps = model.getSteps();
        int[] calories = model.getCalories();
        int[] xValues = model.getXValues();

        for (int i = 0; i < xValues.length; i++) {
            view.getStepsSeries().getData().add(new XYChart.Data<>(xValues[i], steps[i]));
            view.getCaloriesSeries().getData().add(new XYChart.Data<>(xValues[i], calories[i]));
        }
    }

    private void toggleSeries(XYChart.Series<Number, Number> series, boolean visible) {
        series.getNode().setVisible(visible);
        for (XYChart.Data<Number, Number> data : series.getData()) {
            if (data.getNode() != null) {
                data.getNode().setVisible(visible);
            }
        }
    }

    private void showManageGroupsDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Manage Groups");
        dialog.setHeaderText("Add or Remove Groups");
        dialog.setContentText("Enter group name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(groupName -> {
            if (!model.getGroups().contains(groupName)) {
                model.getGroups().add(groupName);
                showAlert("Group Added", groupName + " has been added to the groups.");
            } else {
                showAlert("Group Exists", groupName + " already exists in the groups.");
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}