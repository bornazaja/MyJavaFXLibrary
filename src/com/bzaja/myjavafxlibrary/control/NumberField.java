package com.bzaja.myjavafxlibrary.control;

import com.bzaja.myjavafxlibrary.util.NumberUnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class NumberField extends TextField {

    private static final String DEFALUT_STYLE_CLASS = "number-field";

    public NumberField() {
        this.getStyleClass().add(DEFALUT_STYLE_CLASS);
        this.setTextFormatter(new TextFormatter(new NumberUnaryOperator()));
    }
}
