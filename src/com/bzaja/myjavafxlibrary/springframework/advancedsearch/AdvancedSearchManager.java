/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.springframework.advancedsearch;

import com.bzaja.myjavafxlibrary.util.ControlUtils;
import com.bzaja.myjavafxlibrary.util.HTMLPathable;
import com.bzaja.myjavafxlibrary.util.ItemDto;
import com.bzaja.myjavafxlibrary.util.NodeUtils;
import com.bzaja.myjavafxlibrary.util.StageResult;
import com.bzaja.myjavalibrary.springframework.data.query.Operator;
import com.bzaja.myjavalibrary.springframework.data.query.QueryCriteriaDto;
import com.bzaja.myjavalibrary.springframework.data.query.SearchCriteriaDto;
import com.bzaja.myjavalibrary.springframework.data.query.SearchCriteriaType;
import com.bzaja.myjavalibrary.springframework.data.query.SearchOperation;
import com.bzaja.myjavalibrary.springframework.data.query.SearchOperationUtils;
import com.bzaja.myjavalibrary.util.LanguageFormat;
import com.bzaja.myjavalibrary.util.MapUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Borna
 */
public class AdvancedSearchManager {

    private static final String CRITERIA_TYPES_COMBOBOX_ID = "criteriaTypesComboBox";
    private static final String COLUMNS_FOR_SEARCHING_COMBOBOX_ID = "columnsForSearchingComboBox";
    private static final String OPERATIONS_FOR_SEARCHING_COMBOBOX_ID = "operationsForSearchingComboBox";
    private static final String VALUE_TEXT_FIELD_ID = "valueTextField";
    private static final int MAX_NUMBER_OF_FILTERS = 10;

    private AdvancedSearchDto advancedSearchDto;

    private final Node rootNode;
    private final ComboBox<ItemDto> operatorsComboBox;
    private final VBox filtersVBox;
    private final Button addFilterButton;
    private final Label currentNumberOfFiltersLabel;
    private final Button removeAllFiltersButton;
    private final ComboBox<ItemDto> columnsForSortingComboBox;
    private final ComboBox<ItemDto> sortDirectionsComboBox;
    private final Button applyButton;
    private final Button cancelButton;
    private final Button resetButton;

    private final WebView instructionsWebView;

    private StageResult stageResult;

    public AdvancedSearchManager(AdvancedSearchHolder advancedSearchHolder) {
        stageResult = StageResult.CANCEL;

        rootNode = advancedSearchHolder.getRootNode();
        operatorsComboBox = advancedSearchHolder.getOperatorsComboBox();
        filtersVBox = advancedSearchHolder.getFiltersVBox();
        addFilterButton = advancedSearchHolder.getAddFilterButton();
        currentNumberOfFiltersLabel = advancedSearchHolder.getCurrentNumberOfFiltersLabel();
        removeAllFiltersButton = advancedSearchHolder.getRemoveAllFiltersButton();
        columnsForSortingComboBox = advancedSearchHolder.getColumnsForSortingComboBox();
        sortDirectionsComboBox = advancedSearchHolder.getSortDirectionsComboBox();
        applyButton = advancedSearchHolder.getApplyButton();
        cancelButton = advancedSearchHolder.getCancelButton();
        resetButton = advancedSearchHolder.getResetButton();

        instructionsWebView = advancedSearchHolder.getInstructionsWebView();

        setupFiltersVBoxChildrenListener();
        setupButtonsEvents();
    }

    private void setupFiltersVBoxChildrenListener() {
        filtersVBox.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Node> change) {
                if (filtersVBox.getChildren().size() == MAX_NUMBER_OF_FILTERS) {
                    addFilterButton.setDisable(true);
                } else {
                    addFilterButton.setDisable(false);
                }

                if (filtersVBox.getChildren().isEmpty()) {
                    removeAllFiltersButton.setDisable(true);
                } else {
                    removeAllFiltersButton.setDisable(false);
                }
            }
        });
    }

    private void setupButtonsEvents() {
        addFilterButton.setOnAction((e) -> onAddFilterClick());
        removeAllFiltersButton.setOnAction((e) -> onRemoveAllFiltersClick());
        applyButton.setOnAction((e) -> onApplyClick());
        cancelButton.setOnAction((e) -> onCancelClick());
        resetButton.setOnAction((e) -> onResetClick());
    }

    private void onAddFilterClick() {
        if (advancedSearchDto != null) {
            filtersVBox.getChildren().add(createHBoxFilter(null, null, null, null));
            refreshCurrentNumberOfFiltersLabel();
        }
    }

    private void onRemoveAllFiltersClick() {
        filtersVBox.getChildren().clear();
        refreshCurrentNumberOfFiltersLabel();
    }

    private void onApplyClick() {
        if (advancedSearchDto != null) {
            List<SearchCriteriaDto> searchCriterias = new ArrayList<>();

            for (Node node : filtersVBox.getChildren()) {
                HBox hBox = (HBox) node;
                List<Node> hBoxNodes = hBox.getChildren();

                ComboBox<ItemDto> vrsteKriterijaComboBox = (ComboBox<ItemDto>) getDynamiclyCreatedControl(hBoxNodes, CRITERIA_TYPES_COMBOBOX_ID);
                SearchCriteriaType searchCriteriaType = !ControlUtils.hasComboBoxSelectedDefaultValue(vrsteKriterijaComboBox)
                        ? SearchCriteriaType.valueOf(ControlUtils.getSelectedItemKeyFromComboBoxToString(vrsteKriterijaComboBox))
                        : null;

                ComboBox<ItemDto> stupciZaPretrazivanjeComboBox = (ComboBox<ItemDto>) getDynamiclyCreatedControl(hBoxNodes, COLUMNS_FOR_SEARCHING_COMBOBOX_ID);
                String column = !ControlUtils.hasComboBoxSelectedDefaultValue(stupciZaPretrazivanjeComboBox)
                        ? ControlUtils.getSelectedItemKeyFromComboBoxToString(stupciZaPretrazivanjeComboBox)
                        : null;

                ComboBox<ItemDto> operacijeZaPretrazivanjeComboBox = (ComboBox<ItemDto>) getDynamiclyCreatedControl(hBoxNodes, OPERATIONS_FOR_SEARCHING_COMBOBOX_ID);
                SearchOperation searchOperation = !ControlUtils.hasComboBoxSelectedDefaultValue(operacijeZaPretrazivanjeComboBox)
                        ? SearchOperation.valueOf(ControlUtils.getSelectedItemKeyFromComboBoxToString(operacijeZaPretrazivanjeComboBox))
                        : null;

                TextField vrijednostTextField = (TextField) getDynamiclyCreatedControl(hBoxNodes, VALUE_TEXT_FIELD_ID);
                String value = ControlUtils.hasTextFieldValue(vrijednostTextField) ? vrijednostTextField.getText() : null;

                searchCriterias.add(new SearchCriteriaDto(searchCriteriaType, column, searchOperation, value));
            }

            Operator operator = Operator.valueOf(operatorsComboBox.getValue().getKey());
            Integer pageNumber = advancedSearchDto.getQueryCriteria().getPageable().getPageNumber();
            Integer pageSize = advancedSearchDto.getQueryCriteria().getPageable().getPageSize();
            Sort.Direction direction = Sort.Direction.valueOf(sortDirectionsComboBox.getValue().getKey());
            String property = columnsForSortingComboBox.getValue().getKey();

            advancedSearchDto.getQueryCriteria().setOperator(operator);
            advancedSearchDto.getQueryCriteria().setSearchCriterias(searchCriterias);
            advancedSearchDto.getQueryCriteria().setPageable(PageRequest.of(pageNumber, pageSize, direction, property));

            stageResult = StageResult.OK;
            NodeUtils.closeCurrentStageByNode(rootNode);
        }
    }

    private Node getDynamiclyCreatedControl(List<Node> nodes, String nodeId) {
        return nodes.stream().filter(x -> x.getId().equals(nodeId)).findFirst().orElse(null);
    }

    private void onCancelClick() {
        NodeUtils.closeCurrentStageByNode(rootNode);
    }

    private void onResetClick() {
        for (Node node : filtersVBox.getChildren()) {
            HBox hBox = (HBox) node;
            for (Node hboxNode : hBox.getChildren()) {
                if (hboxNode instanceof ComboBox) {
                    ComboBox comboBox = (ComboBox) hboxNode;
                    comboBox.getSelectionModel().select(0);
                }

                if (hboxNode instanceof TextField) {
                    TextField textField = (TextField) hboxNode;
                    textField.setText("");
                }
            }
        }
        operatorsComboBox.getSelectionModel().select(0);
        columnsForSortingComboBox.getSelectionModel().select(0);
        sortDirectionsComboBox.getSelectionModel().select(0);
    }

    public void initData(AdvancedSearchDto advancedSearchDto) {
        this.advancedSearchDto = advancedSearchDto;

        initOperatorsComboBox(advancedSearchDto.getQueryCriteria().getOperator());

        Sort.Order order = advancedSearchDto.getQueryCriteria().getPageable().getSort().get().findFirst().orElse(null);
        initColumnsForSortingComboBox(order);
        initSortDirectionComboBox(order);

        initSearchFilters(advancedSearchDto);
        refreshCurrentNumberOfFiltersLabel();
    }

    private void initOperatorsComboBox(Operator operator) {
        Map<String, String> map = MapUtils.toStringString(MapUtils.toEnumMap(Operator.values()));
        ControlUtils.fillComboBox(operatorsComboBox, map, 0, false);
        ControlUtils.setSelectedComboBoxItem(operatorsComboBox, operator.toString());
    }

    private void initColumnsForSortingComboBox(Sort.Order order) {
        columnsForSortingComboBox.setPrefWidth(Integer.MAX_VALUE);
        ControlUtils.fillComboBox(columnsForSortingComboBox, advancedSearchDto.getPropertiesInfo(), "propertyName", "displayName", 0, false, LanguageFormat.HR);
        ControlUtils.setSelectedComboBoxItem(columnsForSortingComboBox, order.getProperty());
    }

    private void initSortDirectionComboBox(Sort.Order order) {
        sortDirectionsComboBox.setPrefWidth(Integer.MAX_VALUE);
        List<String> keys = Arrays.asList(Sort.Direction.ASC.toString(), Sort.Direction.DESC.toString());
        List<String> values = Arrays.asList("ASC [A-Z]", "DESC [Z-A]");
        ControlUtils.fillComboBox(sortDirectionsComboBox, keys, values, 0, false);
        ControlUtils.setSelectedComboBoxItem(sortDirectionsComboBox, order.getDirection().toString());
    }

    private void initSearchFilters(AdvancedSearchDto advancedSearchDto) {
        List<SearchCriteriaDto> searchCriterias = advancedSearchDto.getQueryCriteria().getSearchCriterias();

        filtersVBox.getChildren().clear();

        if (!searchCriterias.isEmpty()) {
            searchCriterias.forEach((searchCriteria) -> {
                filtersVBox.getChildren().add(createHBoxFilter(searchCriteria.getSearchCriteriaType(), searchCriteria.getColumn(), searchCriteria.getSearchOperation(), searchCriteria.getValue()));
            });
        } else {
            removeAllFiltersButton.setDisable(true);
        }
    }

    private void refreshCurrentNumberOfFiltersLabel() {
        int trenutniBrojFiltera = filtersVBox.getChildren().size();
        currentNumberOfFiltersLabel.setText(String.format("%d/%d", trenutniBrojFiltera, MAX_NUMBER_OF_FILTERS));
    }

    private HBox createHBoxFilter(SearchCriteriaType searchCriteriaType, String column, SearchOperation searchOperation, Object value) {
        HBox filterHBox = new HBox(20);
        filterHBox.setPadding(new Insets(20));
        filterHBox.setSpacing(10);

        ComboBox<ItemDto> criteriaTypesComboBox = createCriteriaTypesComboBox(searchCriteriaType);

        ComboBox<ItemDto> columnForSearchingComboBox = createColumnForSearchingComboBox(column);

        ComboBox<ItemDto> operationsForSearchingComboBox = createOperationsForSearchingComboBox(column, searchOperation);

        setOnColumnsForSearchingChanged(columnForSearchingComboBox, operationsForSearchingComboBox);

        TextField valueForSearchingTextField = createValueTextField(value);

        Button removeButton = createRemoveButton(filterHBox);

        List<Node> nodes = Arrays.asList(criteriaTypesComboBox, columnForSearchingComboBox, operationsForSearchingComboBox, valueForSearchingTextField, removeButton);

        filterHBox.getChildren().addAll(nodes);

        return filterHBox;
    }

    private ComboBox<ItemDto> createCriteriaTypesComboBox(SearchCriteriaType searchCriteriaType) {
        ComboBox<ItemDto> criteriaTypesComboBox = createComboBox(CRITERIA_TYPES_COMBOBOX_ID);

        Map<String, String> map = MapUtils.toStringString(MapUtils.toEnumMap(SearchCriteriaType.values()));
        ControlUtils.fillComboBox(criteriaTypesComboBox, map, 0, true);

        if (searchCriteriaType != null) {
            criteriaTypesComboBox.getSelectionModel().select(new ItemDto(searchCriteriaType.toString()));
        }

        return criteriaTypesComboBox;
    }

    private ComboBox<ItemDto> createColumnForSearchingComboBox(String column) {
        ComboBox<ItemDto> columnForSearchingComboBox = createComboBox(COLUMNS_FOR_SEARCHING_COMBOBOX_ID);
        ControlUtils.fillComboBox(columnForSearchingComboBox, advancedSearchDto.getPropertiesInfo(), "propertyName", "displayName", 0, true, LanguageFormat.HR);

        if (column != null) {
            columnForSearchingComboBox.getSelectionModel().select(new ItemDto(column));
        }

        return columnForSearchingComboBox;
    }

    private ComboBox<ItemDto> createOperationsForSearchingComboBox(String column, SearchOperation searchOperation) {
        ComboBox<ItemDto> operationsForSearchingComboBox = createComboBox(OPERATIONS_FOR_SEARCHING_COMBOBOX_ID);

        if (column != null && searchOperation != null) {
            Map<String, String> map = MapUtils.toStringString(MapUtils.toEnumMap(SearchOperationUtils.getSearchOperationsByClassAndPropertyName(advancedSearchDto.getBeanClassInterface().getFullClassName(), column)));
            ControlUtils.fillComboBox(operationsForSearchingComboBox, map, 0, true);
            operationsForSearchingComboBox.getSelectionModel().select(new ItemDto(searchOperation.toString()));
        } else {
            ControlUtils.fillComboBoxWithOnlyAChooseOption(operationsForSearchingComboBox);
        }

        return operationsForSearchingComboBox;
    }

    private void setOnColumnsForSearchingChanged(ComboBox<ItemDto> columnsForSearchingComboBox, ComboBox<ItemDto> operationsForSearchingComboBox) {
        columnsForSearchingComboBox.setOnAction((e) -> {
            if (!ControlUtils.hasComboBoxSelectedDefaultValue(columnsForSearchingComboBox)) {
                Map<String, String> map = MapUtils.toStringString(MapUtils.toEnumMap(SearchOperationUtils.getSearchOperationsByClassAndPropertyName(advancedSearchDto.getBeanClassInterface().getFullClassName(), columnsForSearchingComboBox.getValue().getKey())));
                ControlUtils.fillComboBox(operationsForSearchingComboBox, map, 0, true);
            } else {
                ControlUtils.fillComboBoxWithOnlyAChooseOption(operationsForSearchingComboBox);
            }
        });
    }

    private ComboBox<ItemDto> createComboBox(String id) {
        ComboBox<ItemDto> comboBox = new ComboBox<>();
        comboBox.setId(id);
        comboBox.setPrefWidth(Integer.MAX_VALUE);
        comboBox.setMaxWidth(Integer.MAX_VALUE);
        HBox.setHgrow(comboBox, Priority.ALWAYS);
        return comboBox;
    }

    private TextField createValueTextField(Object value) {
        TextField vrijednostTextField = createTextField(VALUE_TEXT_FIELD_ID);

        if (value != null) {
            vrijednostTextField.setText(value.toString());
        }

        return vrijednostTextField;
    }

    private TextField createTextField(String id) {
        TextField textField = new TextField();
        textField.setId(id);
        textField.setPrefWidth(Integer.MAX_VALUE);
        HBox.setHgrow(textField, Priority.ALWAYS);
        return textField;
    }

    private Button createRemoveButton(HBox hBox) {
        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("remove-filter-button");
        removeButton.setOnAction((e) -> {
            filtersVBox.getChildren().remove(hBox);
            refreshCurrentNumberOfFiltersLabel();
        });
        return removeButton;
    }

    public StageResult getStageResult() {
        return stageResult;
    }

    public QueryCriteriaDto getQueryCriteria() {
        return advancedSearchDto.getQueryCriteria();
    }

    public void loadInstructionsHtmlFileInWebView(HTMLPathable hTMLPathableInstructions) {
        URL url = getClass().getResource(hTMLPathableInstructions.getPath());
        instructionsWebView.getEngine().load(url.toString());
    }
}
