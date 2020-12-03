/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.control;

import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;

/**
 *
 * @author Borna
 */
public class ClockSpinner extends HBox {

    private final Spinner<String> hoursSpinner;
    private final Label separatorLabel;
    private final Spinner<String> minutesSpinner;

    private static final String DEFAULT_STYLE = "clock-spinner";

    public ClockSpinner() {
        this.getStyleClass().add(DEFAULT_STYLE);
        this.setSpacing(10);

        SpinnerValueFactory<String> hoursSpinnerValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(getFormatedNumbersInRange(0, 23));
        hoursSpinnerValueFactory.setValue("00");

        hoursSpinner = new Spinner<>();
        hoursSpinner.setValueFactory(hoursSpinnerValueFactory);
        hoursSpinner.setPrefWidth(60);

        separatorLabel = new Label(":");
        separatorLabel.setMaxHeight(Integer.MAX_VALUE);
        separatorLabel.setStyle("-fx-font-size: 14px;");

        SpinnerValueFactory<String> minutesSpinnerValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(getFormatedNumbersInRange(0, 59));
        minutesSpinnerValueFactory.setValue("00");

        minutesSpinner = new Spinner<>();
        minutesSpinner.setValueFactory(minutesSpinnerValueFactory);
        minutesSpinner.setPrefWidth(60);

        this.getChildren().addAll(hoursSpinner, separatorLabel, minutesSpinner);
    }

    private ObservableList<String> getFormatedNumbersInRange(int min, int max) {
        ObservableList<String> formatedNumbers = FXCollections.observableArrayList();

        for (int i = min; i <= max; i++) {
            if (i >= 0 && i <= 9) {
                formatedNumbers.add(String.format("%02d", i));
            } else {
                formatedNumbers.add(String.valueOf(i));
            }
        }

        return formatedNumbers;
    }

    public String getHours() {
        return hoursSpinner.getValue();
    }

    public String getMinutes() {
        return minutesSpinner.getValue();
    }

    public String getHoursAndMinutes() {
        return String.format("%s:%s", getHours(), getMinutes());
    }

    public LocalTime getLocalTime() {
        return LocalTime.parse(getHoursAndMinutes());
    }
}
