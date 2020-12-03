package com.bzaja.myjavafxlibrary.control;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ClearableDatePicker extends HBox {

    private final DatePicker datePicker;
    private final Button button;

    private static final String DEFAULT_STYLE_CLASS = "clearable-date-picker";

    public ClearableDatePicker() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);

        datePicker = new DatePicker();
        datePicker.setEditable(false);
        datePicker.setMaxWidth(Integer.MAX_VALUE);
        HBox.setHgrow(datePicker, Priority.ALWAYS);

        button = new Button("X");
        button.setMaxHeight(Integer.MAX_VALUE);
        button.setOnAction((e) -> datePicker.setValue(null));

        this.getChildren().addAll(datePicker, button);
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public StringProperty promptTextProperty() {
        return datePicker.promptTextProperty();
    }

    public String getPromptText() {
        return promptTextProperty().get();
    }

    public void setPromptText(String promptText) {
        promptTextProperty().set(promptText);
    }
}
