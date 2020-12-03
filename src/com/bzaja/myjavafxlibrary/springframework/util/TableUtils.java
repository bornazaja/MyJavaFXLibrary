package com.bzaja.myjavafxlibrary.springframework.util;

import com.bzaja.myjavalibrary.util.BeanUtils;
import com.bzaja.myjavalibrary.util.PropertyInfoDto;
import com.bzaja.myjavafxlibrary.control.PageableTableView;
import com.bzaja.myjavafxlibrary.util.ActionButtonTableCell;
import com.bzaja.myjavalibrary.util.LanguageFormat;
import java.util.List;
import java.util.function.Function;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import org.springframework.data.domain.Page;

public final class TableUtils {

    private TableUtils() {

    }

    public static <T> void refresh(PageableTableView<T> pageableTableView, Page<T> page, int pageIndex) {
        pageableTableView.getCurrentNumberOfPageLabel().setText(String.format("%d/%d", pageIndex + 1, page.getTotalPages()));
        pageableTableView.getTableView().setItems(FXCollections.observableList(page.getContent()));
        pageableTableView.getTableView().refresh();
    }

    public static <T> void doPreviousPage(Page<T> page, SimpleIntegerProperty pageIndex, Runnable runnable) {
        if (page.hasPrevious()) {
            pageIndex.set(pageIndex.subtract(1).get());
            runnable.run();
        }
    }

    public static <T> void doNextPage(Page<T> page, SimpleIntegerProperty pageIndex, Runnable runnable) {
        if (page.hasNext()) {
            pageIndex.set(pageIndex.add(1).get());
            runnable.run();
        }
    }

    public static <T> void setupPropertyColumn(TableColumn<T, String> tableColumn, String propertyName, LanguageFormat languageFormat) {
        tableColumn.setCellValueFactory(x -> new SimpleStringProperty(BeanUtils.getFormattedPropertyValue(x.getValue(), propertyName, languageFormat)));
    }

    public static <T> void addColumns(PageableTableView<T> pageableTableView, List<PropertyInfoDto> propertiesInfo, Function<T, List<MenuItem>> function, LanguageFormat languageFormat) {
        propertiesInfo.forEach((propertyInfoDto) -> {
            pageableTableView.getTableView().getColumns().add(createPropertyColumn(propertyInfoDto.getDisplayName(), propertyInfoDto.getPropertyName(), languageFormat));
        });
        pageableTableView.getTableView().getColumns().add(createActionColumn(function));
    }

    private static <T> TableColumn<T, String> createPropertyColumn(String displayName, String propertyName, LanguageFormat languageFormat) {
        TableColumn<T, String> tableColumn = new TableColumn<>(displayName);
        tableColumn.setEditable(false);
        tableColumn.setSortable(false);
        tableColumn.setCellValueFactory(x -> new SimpleStringProperty(BeanUtils.getFormattedPropertyValue(x.getValue(), propertyName, languageFormat)));
        return tableColumn;
    }

    private static <T> TableColumn<T, MenuButton> createActionColumn(Function<T, List<MenuItem>> function) {
        TableColumn<T, MenuButton> tableColumn = new TableColumn<>();
        tableColumn.setEditable(false);
        tableColumn.setSortable(false);
        tableColumn.setCellFactory(ActionButtonTableCell.forTableColumn(function));
        return tableColumn;
    }
}
