/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.control;

import java.time.LocalDateTime;
import javafx.scene.layout.HBox;

/**
 *
 * @author Borna
 */
public class DateTimePicker extends HBox {

    private final ClearableDatePicker clearableDatePicker;
    private final ClockSpinner clockSpinner;

    private static final String DEFAULT_STYLE = "date-time-picker";
    
    public DateTimePicker() {
        this.getStyleClass().add(DEFAULT_STYLE);
        this.setSpacing(10);

        clearableDatePicker = new ClearableDatePicker();
        clearableDatePicker.setPrefWidth(150);

        clockSpinner = new ClockSpinner();

        this.getChildren().addAll(clearableDatePicker, clockSpinner);
    }

    public ClearableDatePicker getClearableDatePicker() {
        return clearableDatePicker;
    }

    public ClockSpinner getClockSpinner() {
        return clockSpinner;
    }

    public LocalDateTime getLocalDateTime() {
        return clearableDatePicker.getDatePicker().getValue() != null
                ? LocalDateTime.parse(String.format("%sT%s", clearableDatePicker.getDatePicker().getValue(), clockSpinner.getHoursAndMinutes())) : null;
    }
}
