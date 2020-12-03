package com.bzaja.myjavafxlibrary.control;

import com.bzaja.myjavafxlibrary.skin.SearchableComboBoxListViewSkin;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;

public class SearchableComboBox<T> extends ComboBox<T> {

    private final SearchableComboBoxListViewSkin<T> searchableJFXComboBoxSkin;

    private static final String DEFAULT_STYLE_CLASS = "searchable-combo-box";

    public SearchableComboBox() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);

        searchableJFXComboBoxSkin = new SearchableComboBoxListViewSkin<>(this);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return searchableJFXComboBoxSkin;
    }

    public TextField getSearchTextField() {
        return searchableJFXComboBoxSkin.getSearchTextField();
    }

    public StringProperty searchTextFieldPromptTextProperty() {
        return searchableJFXComboBoxSkin.getSearchTextField().promptTextProperty();
    }

    public String getSearchTextFieldPromptText() {
        return searchTextFieldPromptTextProperty().get();
    }

    public void setSearchTextFieldPromptText(String searchTextFieldPromptText) {
        searchTextFieldPromptTextProperty().set(searchTextFieldPromptText);
    }
}
