package com.bzaja.myjavafxlibrary.util;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

public class NumberUnaryOperator implements UnaryOperator<TextFormatter.Change> {

    private static final String NUMERIC_PATTERN = "^\\-?[0-9]*\\.?[0-9]*$";

    @Override
    public TextFormatter.Change apply(TextFormatter.Change t) {
        return t.getText().matches(NUMERIC_PATTERN) ? t : null;
    }

}
