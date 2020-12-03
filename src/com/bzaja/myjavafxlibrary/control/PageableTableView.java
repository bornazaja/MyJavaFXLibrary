package com.bzaja.myjavafxlibrary.control;

import java.util.Arrays;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PageableTableView<T> extends VBox {

    private Label titleLabel;
    private MenuButton menuButton;
    private TableView<T> tableView;
    private Label itemsPerPageLabel;
    private ComboBox<Integer> itemsPerPageComboBox;
    private Label currentNumberOfPageLabel;
    private Button previousButton;
    private Button nextButton;

    private static final String DEFAULT_STYLE_CLASS = "pageable-table-view";

    public PageableTableView() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.setSpacing(10);
        this.setPadding(new Insets(10));

        addFirstContent();
        addSecondContent();
        addThirdContent();
    }

    private void addFirstContent() {
        titleLabel = new Label("Title");
        titleLabel.getStyleClass().add("title-text");

        menuButton = new MenuButton("Actions");

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().add(new HBox(titleLabel, spacer, menuButton));
    }

    private void addSecondContent() {
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.prefHeightProperty().bind(this.prefHeightProperty());
        VBox.setVgrow(tableView, Priority.ALWAYS);
        this.getChildren().add(tableView);
    }

    private void addThirdContent() {
        itemsPerPageLabel = new Label("Items per page");

        itemsPerPageComboBox = new ComboBox<>();
        itemsPerPageComboBox.setItems(FXCollections.observableList(Arrays.asList(5, 10, 25, 50, 100)));
        itemsPerPageComboBox.getSelectionModel().select(1);
        itemsPerPageComboBox.setPrefWidth(100);

        currentNumberOfPageLabel = new Label("1/...");

        previousButton = new Button("<");
        previousButton.setContentDisplay(ContentDisplay.CENTER);

        nextButton = new Button(">");
        nextButton.setContentDisplay(ContentDisplay.CENTER);

        HBox hBox = new HBox(itemsPerPageLabel, itemsPerPageComboBox, currentNumberOfPageLabel, previousButton, nextButton);
        hBox.setSpacing(10);
        hBox.setPrefWidth(500);

        this.getChildren().add(hBox);
    }

    public TableView<T> getTableView() {
        return tableView;
    }

    public ComboBox<Integer> getItemsPerPageComboBox() {
        return itemsPerPageComboBox;
    }

    public Label getCurrentNumberOfPageLabel() {
        return currentNumberOfPageLabel;
    }

    public void addMenuItem(String text, EventHandler eventHandler) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(eventHandler);
        menuButton.getItems().add(menuItem);
    }

    public void setOnItemsPerPageChange(EventHandler eventHandler) {
        itemsPerPageComboBox.setOnAction((e) -> eventHandler.handle(e));
    }

    public void setOnPreviousClick(EventHandler eventHandler) {
        previousButton.setOnAction((e) -> eventHandler.handle(e));
    }

    public void setOnNextClick(EventHandler eventHandler) {
        nextButton.setOnAction((e) -> eventHandler.handle(e));
    }

    public StringProperty titleProperty() {
        return titleLabel.textProperty();
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public void setTitle(String text) {
        titleProperty().set(text);
    }

    public StringProperty menuButtonTextProperty() {
        return menuButton.textProperty();
    }

    public String getMenuButtonText() {
        return menuButtonTextProperty().get();
    }

    public void setMenuButtonText(String menuButtonText) {
        menuButtonTextProperty().set(menuButtonText);
    }

    public StringProperty itemsPerPageTextProperty() {
        return itemsPerPageLabel.textProperty();
    }

    public String getItemsPerPageText() {
        return itemsPerPageTextProperty().get();
    }

    public void setItemsPerPageText(String itemsPerPageText) {
        itemsPerPageTextProperty().set(itemsPerPageText);
    }
}
