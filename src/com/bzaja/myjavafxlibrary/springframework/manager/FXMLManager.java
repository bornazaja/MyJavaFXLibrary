package com.bzaja.myjavafxlibrary.springframework.manager;

import com.bzaja.myjavafxlibrary.util.ControllerInterface;
import com.bzaja.myjavafxlibrary.util.FXMLPathable;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FXMLManager {

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    public <T> Parent load(FXMLPathable fXMLPathable, T t) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fXMLPathable.getPath()));
            loader.setControllerFactory(configurableApplicationContext::getBean);

            Parent parent = loader.load();

            if (t != null) {
                ControllerInterface<T> dataController = loader.getController();
                dataController.initData(t);
            }

            return parent;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Parent load(FXMLPathable fXMLPathable) {
        return load(fXMLPathable, null);
    }
}
