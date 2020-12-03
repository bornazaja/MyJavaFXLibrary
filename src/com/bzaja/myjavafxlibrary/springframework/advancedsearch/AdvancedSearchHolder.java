/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.springframework.advancedsearch;

import com.bzaja.myjavafxlibrary.util.ItemDto;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Borna
 */
@AllArgsConstructor
@Getter
public class AdvancedSearchHolder {

    private Node rootNode;
    private ComboBox<ItemDto> operatorsComboBox;
    private VBox filtersVBox;
    private Button addFilterButton;
    private Label currentNumberOfFiltersLabel;
    private Button removeAllFiltersButton;
    private ComboBox<ItemDto> columnsForSortingComboBox;
    private ComboBox<ItemDto> sortDirectionsComboBox;
    private Button applyButton;
    private Button cancelButton;
    private Button resetButton;
    private WebView instructionsWebView;
}
