/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary;

import com.bzaja.myjavafxlibrary.control.ClearableDatePicker;
import com.bzaja.myjavafxlibrary.control.ClockSpinner;
import com.bzaja.myjavafxlibrary.control.DashboardTile;
import com.bzaja.myjavafxlibrary.control.DateTimePicker;
import com.bzaja.myjavafxlibrary.control.PageableTableView;
import com.bzaja.myjavafxlibrary.control.SearchableComboBox;
import com.bzaja.myjavafxlibrary.control.SearchableListSelectionView;
import com.bzaja.myjavafxlibrary.springframework.advancedsearch.AdvancedSearchCtrl;
import com.bzaja.myjavafxlibrary.springframework.advancedsearch.AdvancedSearchDto;
import com.bzaja.myjavafxlibrary.test.bean.Person;
import com.bzaja.myjavalibrary.springframework.data.query.Operator;
import com.bzaja.myjavalibrary.springframework.data.query.QueryCriteriaDto;
import com.bzaja.myjavalibrary.util.BeanClassInterface;
import com.bzaja.myjavalibrary.util.PropertyInfoDto;
import com.bzaja.myjavalibrary.util.PropertyInfoUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Borna
 */
public class MyJavaFXLibrary extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        ClearableDatePicker clearableDatePicker = createClearableDataPicker();
        SearchableComboBox<String> searchableComboBox = createSearchableComboBox();
        SearchableListSelectionView<String> searchableListSelectionView = createSearchableListSelectionView();
        PageableTableView<String> pageableTableView = createPageableTableView();
        DashboardTile dashboardTile = createDashboardTile();
        AdvancedSearchCtrl advancedSearchCtrl = createAdvancedSearchCtrl();
        ClockSpinner clockSpinner = createClockSpinner();
        DateTimePicker dateTimePicker = createDateTimePicker();

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(clearableDatePicker, searchableComboBox, searchableListSelectionView, pageableTableView, dashboardTile,
                advancedSearchCtrl, clockSpinner, dateTimePicker);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ClearableDatePicker createClearableDataPicker() {
        ClearableDatePicker clearableDatePicker = new ClearableDatePicker();
        clearableDatePicker.setMaxWidth(Integer.MAX_VALUE);
        return clearableDatePicker;
    }

    private SearchableComboBox<String> createSearchableComboBox() {
        SearchableComboBox<String> searchableComboBox = new SearchableComboBox<>();
        searchableComboBox.setSearchTextFieldPromptText("Search...");
        searchableComboBox.setMaxWidth(Integer.MAX_VALUE);
        searchableComboBox.setItems(FXCollections.observableList(Arrays.asList("John", "Sarah", "Luke")));
        return searchableComboBox;
    }

    private SearchableListSelectionView<String> createSearchableListSelectionView() {
        SearchableListSelectionView<String> searchableListSelectionView = new SearchableListSelectionView<>();
        searchableListSelectionView.setSearchTextFieldPromptText("Search...");
        searchableListSelectionView.setPrefHeight(500);
        searchableListSelectionView.getSearchableComboBox().getItems().addAll("John", "Sarah", "Luke");
        return searchableListSelectionView;
    }

    private PageableTableView<String> createPageableTableView() {
        PageableTableView<String> pageableTableView = new PageableTableView<>();
        pageableTableView.setPrefHeight(1000);
        pageableTableView.addMenuItem("Add", (e) -> printText("Add clicked"));
        pageableTableView.addMenuItem("Refresh", (e) -> printText("Refresh clicked"));
        pageableTableView.addMenuItem("Advcanced search", (e) -> printText("Advanced search clicked"));
        pageableTableView.addMenuItem("Export PDF", (e) -> printText("Export PDF clicked"));
        pageableTableView.setOnItemsPerPageChange((e) -> printText("Items per page changed"));
        pageableTableView.setOnPreviousClick((e) -> printText("Previous clicked"));
        pageableTableView.setOnNextClick((e) -> printText("Next clicked"));
        return pageableTableView;
    }

    public void printText(String text) {
        System.out.println(text);
    }

    private DashboardTile createDashboardTile() {
        DashboardTile dashboardTile = new DashboardTile();
        dashboardTile.setText("Salary");
        dashboardTile.setValue("$8000");
        return dashboardTile;
    }

    private AdvancedSearchCtrl createAdvancedSearchCtrl() {
        BeanClassInterface beanClassInterface = new BeanClassInterface() {
            @Override
            public String getFullClassName() {
                return Person.class.getName();
            }
        };

        List<String> displayNames = Arrays.asList("First Name", "Last Name", "E-mail");
        List<String> propertyNames = Arrays.asList("firstName", "lastName", "email");
        List<PropertyInfoDto> propertyInfoDtoList = PropertyInfoUtils.toPropertiesInfo(displayNames, propertyNames);

        QueryCriteriaDto queryCriteriaDto = new QueryCriteriaDto(Operator.AND, new ArrayList<>(), PageRequest.of(0, 10, Sort.Direction.ASC, "firstName"));

        AdvancedSearchDto advancedSearchDto = new AdvancedSearchDto(beanClassInterface, propertyInfoDtoList, queryCriteriaDto);

        AdvancedSearchCtrl advancedSearchCtrl = new AdvancedSearchCtrl();
        advancedSearchCtrl.getAdvancedSearchManager().initData(advancedSearchDto);

        return advancedSearchCtrl;
    }

    private ClockSpinner createClockSpinner() {
        return new ClockSpinner();
    }

    private DateTimePicker createDateTimePicker() {
        return new DateTimePicker();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
