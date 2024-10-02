package com.example.fitnesstracker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FitnessModel {
    private ObservableList<String> groups = FXCollections.observableArrayList("Me", "Family", "Friends", "Work");
    private int[] steps;
    private int[] calories;
    private int[] xValues;

    public ObservableList<String> getGroups() {
        return groups;
    }

    public void setDailyData(int[] steps, int[] calories, int[] xValues) {
        this.steps = steps;
        this.calories = calories;
        this.xValues = xValues;
    }

    public int[] getSteps() {
        return steps;
    }

    public int[] getCalories() {
        return calories;
    }

    public int[] getXValues() {
        return xValues;
    }

    // Add methods for data manipulation and business logic
}