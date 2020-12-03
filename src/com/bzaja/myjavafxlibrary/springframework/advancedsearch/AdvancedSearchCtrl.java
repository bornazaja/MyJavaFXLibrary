/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.springframework.advancedsearch;

import com.bzaja.myjavafxlibrary.util.FXUtils;
import com.bzaja.myjavafxlibrary.util.ItemDto;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

/**
 *
 * @author Borna
 */
public class AdvancedSearchCtrl extends TabPane {

    private Tab advancedSearchTab;
    private Label advancedSearchTitleLabel;
    private Label searchTitleLabel;
    private Label operatorLabel;
    private ComboBox<ItemDto> operatorsComboBox;
    private Label criteriaTypeLabel;
    private Label searchColumnLabel;
    private Label operationLabel;
    private Label valueLabel;
    private ScrollPane filtersHolderScrollPane;
    private VBox filtersVBox;
    private Button addFilterButton;
    private Label currentNumberOfFiltersLabel;
    private Button removeAllFiltersButton;
    private Label sortTitleLabel;
    private Label sortColumnLabel;
    private ComboBox<ItemDto> columnsForSortingComboBox;
    private Label directionLabel;
    private ComboBox<ItemDto> sortDirectionsComboBox;
    private Button applyButton;
    private Button cancelButton;
    private Button resetButton;

    private Tab instructionsTab;
    private WebView instructionsWebView;

    private AdvancedSearchHolder advancedSearchHolder;
    private AdvancedSearchManager advancedSearchManager;

    public AdvancedSearchCtrl() {
        setupAdvancedSearchTab();
        setupInstructionsTab();
        initAdvancedSearchHolder();
    }

    private void setupAdvancedSearchTab() {
        initControlsForAdvancedSearchTab();
        addContentToAdvancedSearchTab();
        this.getTabs().add(advancedSearchTab);
    }

    private void initControlsForAdvancedSearchTab() {
        advancedSearchTab = new Tab("Advanced search");
        advancedSearchTab.setClosable(false);

        advancedSearchTitleLabel = new Label("Advanced search");
        advancedSearchTitleLabel.getStyleClass().add("advanced-search-title");

        searchTitleLabel = new Label("Search");
        searchTitleLabel.getStyleClass().add("search-title");

        operatorLabel = new Label("Operator:");
        operatorsComboBox = createComboBox();

        criteriaTypeLabel = createLabelForSearchCriteriasHBox("Criteria type");
        searchColumnLabel = createLabelForSearchCriteriasHBox("Column");
        operationLabel = createLabelForSearchCriteriasHBox("Operation");
        valueLabel = createLabelForSearchCriteriasHBox("Value");

        filtersHolderScrollPane = new ScrollPane();
        filtersHolderScrollPane.setFitToWidth(true);
        filtersHolderScrollPane.setFitToHeight(true);
        filtersHolderScrollPane.setMinHeight(200);

        VBox.setVgrow(filtersHolderScrollPane, Priority.ALWAYS);

        filtersVBox = new VBox();
        filtersVBox.setFillWidth(true);

        filtersHolderScrollPane.setContent(filtersVBox);

        addFilterButton = new Button("Add filter");
        currentNumberOfFiltersLabel = new Label("1/...");
        removeAllFiltersButton = new Button("Remove all filters");
        removeAllFiltersButton.getStyleClass().add("remove-all-filters-button");

        sortTitleLabel = new Label("Sort");
        sortTitleLabel.getStyleClass().add("sort-title");

        sortColumnLabel = new Label("Column:");
        columnsForSortingComboBox = createComboBox();

        directionLabel = new Label("Direction:");
        sortDirectionsComboBox = createComboBox();

        applyButton = new Button("Apply");
        cancelButton = new Button("Cancel");
        resetButton = new Button("Reset");
    }

    private ComboBox<ItemDto> createComboBox() {
        ComboBox<ItemDto> comboBox = new ComboBox<>();

        Platform.runLater(() -> {
            comboBox.setPrefWidth(Integer.MAX_VALUE);
            comboBox.setMaxWidth(Integer.MAX_VALUE);
        });

        return comboBox;
    }

    private Label createLabelForSearchCriteriasHBox(String text) {
        Label label = new Label(text);

        Platform.runLater(() -> {
            label.setMaxWidth(Integer.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);
        });

        return label;
    }

    private void addContentToAdvancedSearchTab() {
        GridPane operatorsGridPane = new GridPane();
        operatorsGridPane.addRow(0, operatorLabel, operatorsComboBox);
        applyColumnConstraintsToGridPane(operatorsGridPane, 2);

        HBox labelsForSearchCriteriasHBox = new HBox(criteriaTypeLabel, searchColumnLabel, operationLabel, valueLabel);
        labelsForSearchCriteriasHBox.setSpacing(100);
        labelsForSearchCriteriasHBox.setFillHeight(true);

        HBox nestedButtonsHbox = new HBox(addFilterButton, currentNumberOfFiltersLabel, removeAllFiltersButton);
        nestedButtonsHbox.setAlignment(Pos.CENTER);
        nestedButtonsHbox.setSpacing(10);

        GridPane sortGridPane = new GridPane();
        sortGridPane.addRow(0, sortColumnLabel, columnsForSortingComboBox);
        sortGridPane.addRow(1, directionLabel, sortDirectionsComboBox);
        applyColumnConstraintsToGridPane(sortGridPane, 2);
        applyRowConstraintsToGridPane(sortGridPane, 2);

        HBox mainButtonsHbox = new HBox(applyButton, cancelButton, resetButton);
        mainButtonsHbox.setSpacing(10);

        VBox tabContentVBox = new VBox(advancedSearchTitleLabel, searchTitleLabel, operatorsGridPane, labelsForSearchCriteriasHBox, filtersHolderScrollPane,
                nestedButtonsHbox, sortTitleLabel, sortGridPane, mainButtonsHbox);

        tabContentVBox.setPadding(new Insets(20));
        tabContentVBox.setSpacing(20);
        tabContentVBox.setFillWidth(true);

        advancedSearchTab.setContent(tabContentVBox);
    }

    private void applyColumnConstraintsToGridPane(GridPane gridPane, int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setFillWidth(true);
            columnConstraints.setMinWidth(100);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
    }

    private void applyRowConstraintsToGridPane(GridPane gridPane, int numberOfRows) {
        for (int i = 0; i < numberOfRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setFillHeight(true);
            rowConstraints.setMinHeight(30);
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }

    private void setupInstructionsTab() {
        instructionsTab = new Tab("Instructions");
        instructionsTab.setClosable(false);
        FXUtils.runSafe(() -> initInstructionsWebView());
        this.getTabs().add(instructionsTab);
    }

    private void initInstructionsWebView() {
        instructionsWebView = new WebView();
        instructionsTab.setContent(instructionsWebView);
    }

    private void initAdvancedSearchHolder() {
        advancedSearchHolder = new AdvancedSearchHolder(this, operatorsComboBox, filtersVBox, addFilterButton,
                currentNumberOfFiltersLabel, removeAllFiltersButton, columnsForSortingComboBox, sortDirectionsComboBox, applyButton, cancelButton,
                resetButton, instructionsWebView);
    }

    public AdvancedSearchManager getAdvancedSearchManager() {
        if (advancedSearchManager == null) {
            advancedSearchManager = new AdvancedSearchManager(advancedSearchHolder);
        }

        return advancedSearchManager;
    }

    public StringProperty advancedSearchTabTextProperty() {
        return advancedSearchTab.textProperty();
    }

    public String getAdvancedSearchTabText() {
        return advancedSearchTabTextProperty().get();
    }

    public void setAdvancedSearchTabText(String advancedSearchTabTex) {
        advancedSearchTabTextProperty().set(advancedSearchTabTex);
    }

    public StringProperty advancedSearchTitleProperty() {
        return advancedSearchTitleLabel.textProperty();
    }

    public String getAdvancedSearchTitle() {
        return advancedSearchTitleProperty().get();
    }

    public void setAdvancedSearchTitle(String mainAdvancedSearchTitle) {
        advancedSearchTitleProperty().set(mainAdvancedSearchTitle);
    }

    public StringProperty searchTitleProperty() {
        return searchTitleLabel.textProperty();
    }

    public String getSearchTitle() {
        return searchTitleProperty().get();
    }

    public void setSearchTitle(String searchTitle) {
        searchTitleProperty().set(searchTitle);
    }

    public StringProperty operatorTextProperty() {
        return operatorLabel.textProperty();
    }

    public String getOperatorText() {
        return operatorTextProperty().get();
    }

    public void setOperatorText(String operatorText) {
        operatorTextProperty().set(operatorText);
    }

    public StringProperty criteriaTypeTextProperty() {
        return criteriaTypeLabel.textProperty();
    }

    public String getCriteriaTypeText() {
        return criteriaTypeTextProperty().get();
    }

    public void setCriteriaTypeText(String criteriaTypeText) {
        criteriaTypeTextProperty().set(criteriaTypeText);
    }

    public StringProperty searchColumnTextProperty() {
        return searchColumnLabel.textProperty();
    }

    public String getSearchColumnText() {
        return searchColumnTextProperty().get();
    }

    public void setSearchColumnText(String searchColumnText) {
        searchColumnTextProperty().set(searchColumnText);
    }

    public StringProperty operationTextProperty() {
        return operationLabel.textProperty();
    }

    public String getOperationText() {
        return operationTextProperty().get();
    }

    public void setOperationText(String operationText) {
        operationTextProperty().set(operationText);
    }

    public StringProperty valueTextProperty() {
        return valueLabel.textProperty();
    }

    public String getValueText() {
        return valueTextProperty().get();
    }

    public void setValueText(String valueText) {
        valueTextProperty().set(valueText);
    }

    public StringProperty addFilterButtonTextProperty() {
        return addFilterButton.textProperty();
    }

    public String getAddFilterButtonText() {
        return addFilterButtonTextProperty().get();
    }

    public void setAddFilterButtonText(String addFilterText) {
        addFilterButtonTextProperty().set(addFilterText);
    }

    public StringProperty removeAllFiltersButtonTextProperty() {
        return removeAllFiltersButton.textProperty();
    }

    public String getRemoveAllFiltersButtonText() {
        return removeAllFiltersButtonTextProperty().get();
    }

    public void setRemoveAllFiltersButtonText(String removeAllFiltersText) {
        removeAllFiltersButtonTextProperty().set(removeAllFiltersText);
    }

    public StringProperty sortTitleTextProperty() {
        return sortTitleLabel.textProperty();
    }

    public String getSortTitleText() {
        return sortTitleTextProperty().get();
    }

    public void setSortTitleText(String sortTitleText) {
        sortTitleTextProperty().set(sortTitleText);
    }

    public StringProperty sortColumnTextProperty() {
        return sortColumnLabel.textProperty();
    }

    public String getSortColumnText() {
        return sortColumnTextProperty().get();
    }

    public void setSortColumnText(String sortColumnText) {
        sortColumnTextProperty().set(sortColumnText);
    }

    public StringProperty directionTextProperty() {
        return directionLabel.textProperty();
    }

    public String getDirectionText() {
        return directionTextProperty().get();
    }

    public void setDirectionText(String directionText) {
        directionTextProperty().set(directionText);
    }

    public StringProperty applyButtonTextProperty() {
        return applyButton.textProperty();
    }

    public String getApplyButtonText() {
        return applyButtonTextProperty().get();
    }

    public void setApplyButtonText(String applyText) {
        applyButtonTextProperty().set(applyText);
    }

    public StringProperty cancelButtonTextProperty() {
        return cancelButton.textProperty();
    }

    public String getCancelButtonText() {
        return cancelButtonTextProperty().get();
    }

    public void setCancelButtonText(String cancelButtonText) {
        cancelButtonTextProperty().set(cancelButtonText);
    }

    public StringProperty resetButtonTextProperty() {
        return resetButton.textProperty();
    }

    public String getResetButtonText() {
        return resetButtonTextProperty().get();
    }

    public void setResetButtonText(String resetButtonText) {
        resetButtonTextProperty().set(resetButtonText);
    }

    public StringProperty instructionsTabTextProperty() {
        return instructionsTab.textProperty();
    }

    public String getInstructionsTabText() {
        return instructionsTabTextProperty().get();
    }

    public void setInstructionsTabText(String instructionsTabText) {
        instructionsTabTextProperty().set(instructionsTabText);
    }
}
