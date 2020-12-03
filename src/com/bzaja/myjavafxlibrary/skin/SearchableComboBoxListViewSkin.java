package com.bzaja.myjavafxlibrary.skin;

import com.bzaja.myjavafxlibrary.control.SearchableComboBox;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SearchableComboBoxListViewSkin<T> extends ComboBoxListViewSkin<T> {

    private final TextField searchTextField;
    private final ListView<T> listView;
    private final VBox vBoxMenu;

    private final SearchableComboBox<T> searchableComboBox;
    private int currentNumberOfItemsInListView = -1;

    private boolean clickSelection = false;

    public SearchableComboBoxListViewSkin(SearchableComboBox<T> searchableComboBox) {
        super(searchableComboBox);

        this.searchableComboBox = searchableComboBox;

        searchTextField = new TextField();

        listView = new ListView<>();
        listView.setItems(searchableComboBox.getItems());

        listView.getSelectionModel().selectedItemProperty().addListener((p, o, item) -> {
            if (item != null) {
                searchableComboBox.getSelectionModel().select(item);
                listView.refresh();

                if (clickSelection) {
                    searchableComboBox.hide();
                }
            }
        });

        listView.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.SPACE || t.getCode() == KeyCode.ESCAPE) {
                searchableComboBox.hide();
            }
        });

        listView.addEventFilter(MouseEvent.ANY, me -> clickSelection = me.getEventType().equals(MouseEvent.MOUSE_PRESSED));

        vBoxMenu = new VBox(searchTextField, listView);
        vBoxMenu.setStyle("-fx-background-color: white; -fx-effect: dropshadow(gaussian, rgb(0.0,0.0,0.0,0.15),10.0, 0.7,0.0,1.5);");
        vBoxMenu.setSpacing(10);
        vBoxMenu.setPadding(new Insets(10));
        vBoxMenu.prefWidthProperty().bind(this.getSkinnable().widthProperty());
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    @Override
    public Node getPopupContent() {
        if (currentNumberOfItemsInListView != listView.getItems().size()) {
            listView.setItems(searchableComboBox.getItems());
            currentNumberOfItemsInListView = listView.getItems().size();
        }

        return vBoxMenu;
    }
}
