package com.bzaja.myjavafxlibrary.control;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SearchableListSelectionView<T> extends VBox {

    private SearchableComboBox<T> searchableComboBox;
    private ListView<T> listView;
    private final Button removeButton;
    private final Button removeAllButton;

    private static final String DEFAULT_STYLE_CLASS = "searchable-list-selection-view";

    public SearchableListSelectionView() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.setSpacing(10);

        searchableComboBox = new SearchableComboBox<>();
        searchableComboBox.setMaxWidth(Integer.MAX_VALUE);
        searchableComboBox.setOnAction((e) -> {
            T item = searchableComboBox.getSelectionModel().getSelectedItem();
            if (item != null) {
                listView.getItems().add(item);
                listView.scrollTo(listView.getItems().size() - 1);
            }
        });

        listView = new ListView<>();
        listView.prefHeightProperty().bind(this.prefHeightProperty());

        removeButton = new Button("Remove");
        removeButton.setMaxWidth(Integer.MAX_VALUE);
        removeButton.setOnAction((e) -> {
            if (!listView.getSelectionModel().isEmpty()) {
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            }
        });

        removeAllButton = new Button("Remove all");
        removeAllButton.setMaxWidth(Integer.MAX_VALUE);
        removeAllButton.setOnAction((e) -> listView.getItems().clear());

        HBox buttonsHBox = new HBox(removeButton, removeAllButton);
        buttonsHBox.setSpacing(10);

        HBox.setHgrow(removeButton, Priority.ALWAYS);
        HBox.setHgrow(removeAllButton, Priority.ALWAYS);

        this.getChildren().addAll(searchableComboBox, listView, buttonsHBox);
    }

    public SearchableComboBox<T> getSearchableComboBox() {
        return searchableComboBox;
    }

    public ListView<T> getListView() {
        return listView;
    }

    public StringProperty searchTextFieldPromptTextProperty() {
        return searchableComboBox.searchTextFieldPromptTextProperty();
    }

    public String getSearchTextFieldPromptText() {
        return searchTextFieldPromptTextProperty().get();
    }

    public void setSearchTextFieldPromptText(String prompText) {
        searchTextFieldPromptTextProperty().set(prompText);
    }

    public StringProperty removeButtonTextProperty() {
        return removeButton.textProperty();
    }

    public String getRemoveButtonText() {
        return removeButtonTextProperty().get();
    }

    public void setRemoveButtonText(String removeButtonText) {
        removeButtonTextProperty().set(removeButtonText);
    }

    public StringProperty removeAllButtonTextProperty() {
        return removeAllButton.textProperty();
    }

    public String getRemoveAllButtonText() {
        return removeAllButtonTextProperty().get();
    }

    public void setRemoveAllButtonText(String removeAllButtonText) {
        removeAllButtonTextProperty().set(removeAllButtonText);
    }
}
