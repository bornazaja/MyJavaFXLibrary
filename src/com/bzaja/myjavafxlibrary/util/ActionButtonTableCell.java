package com.bzaja.myjavafxlibrary.util;

import java.util.List;
import java.util.function.Function;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ActionButtonTableCell<T> extends TableCell<T, MenuButton> {

    private final MenuButton menuButton;

    private ActionButtonTableCell(Function<T, List<MenuItem>> function) {
        this.menuButton = new MenuButton(". . .");
        this.menuButton.setOnShowing((e) -> this.menuButton.getItems().setAll(function.apply(getCurrentItem())));
    }

    public T getCurrentItem() {
        return getTableView().getItems().get(getIndex());
    }

    public static <T> Callback<TableColumn<T, MenuButton>, TableCell<T, MenuButton>> forTableColumn(Function<T, List<MenuItem>> function) {
        return param -> new ActionButtonTableCell<>(function);
    }

    @Override
    public void updateItem(MenuButton item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(menuButton);
        }
    }
}
