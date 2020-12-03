package com.bzaja.myjavafxlibrary.control;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardTile extends VBox {
    
    private final Label textLabel;
    private final Label valueLabel;

    private static final String DEFAULT_STYLE_CLASS = "dashboard-tile";
    
    public DashboardTile() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15));
        this.setSpacing(10);
        textLabel = new Label("<None>");

        valueLabel = new Label("<None>");
        valueLabel.setStyle("-fx-font-weight:bold;");

        this.getChildren().addAll(textLabel, valueLabel);
    }

    public StringProperty textProperty() {
        return textLabel.textProperty();
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String text) {
        textProperty().set(text);
    }

    public StringProperty valueProperty() {
        return valueLabel.textProperty();
    }

    public String getValue() {
        return valueProperty().get();
    }

    public void setValue(String value) {
        valueProperty().set(value);
    }
}
