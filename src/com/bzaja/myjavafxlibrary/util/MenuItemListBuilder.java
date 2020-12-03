/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.util;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.MenuItem;

public class MenuItemListBuilder {

    private final List<MenuItem> menuItems;

    public MenuItemListBuilder() {
        menuItems = new ArrayList<>();
    }

    public MenuItemListBuilder addMenuItem(String text, Runnable runnable) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction((e) -> runnable.run());
        menuItems.add(menuItem);
        return this;
    }

    public List<MenuItem> build() {
        return menuItems;
    }
}
