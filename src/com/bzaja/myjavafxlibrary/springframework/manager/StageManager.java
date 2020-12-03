package com.bzaja.myjavafxlibrary.springframework.manager;

import com.bzaja.myjavafxlibrary.springframework.advancedsearch.AdvancedSearchControllerInterface;
import com.bzaja.myjavafxlibrary.springframework.advancedsearch.AdvancedSearchDto;
import com.bzaja.myjavafxlibrary.util.ControllerInterface;
import com.bzaja.myjavafxlibrary.util.CssPathable;
import com.bzaja.myjavafxlibrary.util.FXMLPathable;
import com.bzaja.myjavalibrary.util.PathableUtils;
import com.bzaja.myjavafxlibrary.util.StageResult;
import com.bzaja.myjavalibrary.springframework.data.query.QueryCriteriaDto;
import com.bzaja.myjavalibrary.util.BeanClassInterface;
import com.bzaja.myjavalibrary.util.PropertyInfoDto;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StageManager {

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    public <T> void showPrimaryStage(Stage currentStage, FXMLPathable fXMLPathable, T t, CssPathable[] cssPathables) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fXMLPathable.getPath()));
            loader.setControllerFactory(configurableApplicationContext::getBean);

            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            scene.getStylesheets().addAll(PathableUtils.pathablesToPaths(cssPathables));
            scene.getRoot().requestFocus();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            currentStage.close();

            if (t != null) {
                ControllerInterface<T> controller = loader.getController();
                controller.initData(t);
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showPrimaryStage(Stage currentStage, FXMLPathable fXMLPathable, CssPathable[] cssPathables) {
        showPrimaryStage(currentStage, fXMLPathable, null, cssPathables);
    }

    public <TController, TDto> TController showSecondaryStage(FXMLPathable fXMLPathable, TDto tdto, CssPathable[] cssPathables) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fXMLPathable.getPath()));
            loader.setControllerFactory(configurableApplicationContext::getBean);

            Parent parent = loader.load();

            TController tController = loader.getController();

            if (tdto != null) {
                if (tController instanceof ControllerInterface) {
                    ControllerInterface<TDto> controllerInterface = (ControllerInterface<TDto>) tController;
                    controllerInterface.initData(tdto);
                }

                if (tController instanceof AdvancedSearchControllerInterface) {
                    AdvancedSearchControllerInterface advancedSearchControllerInterface = (AdvancedSearchControllerInterface) tController;
                    advancedSearchControllerInterface.loadInstructionsHtmlFileInWebView();
                    advancedSearchControllerInterface.initData((AdvancedSearchDto) tdto);
                }
            }

            Scene scene = new Scene(parent);
            scene.getStylesheets().addAll(PathableUtils.pathablesToPaths(cssPathables));
            scene.getRoot().requestFocus();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            return tController;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public <TController> TController showSecondaryStage(FXMLPathable fXMLPathable, CssPathable[] cssPathables) {
        return showSecondaryStage(fXMLPathable, null, cssPathables);
    }

    public void showAdvancedSearchStage(FXMLPathable fXMLPathable, CssPathable[] cssPathables, BeanClassInterface beanClassInterface, List<PropertyInfoDto> propertiesInfo, QueryCriteriaDto queryCriteriaDto, Function<QueryCriteriaDto, Runnable> function) {
        AdvancedSearchDto advancedSearchDto = new AdvancedSearchDto(beanClassInterface, propertiesInfo, queryCriteriaDto);

        AdvancedSearchControllerInterface advancedSearchControllerInterface = showSecondaryStage(fXMLPathable, advancedSearchDto, cssPathables);

        if (advancedSearchControllerInterface.getStageResult().equals(StageResult.OK)) {
            function.apply(advancedSearchControllerInterface.getQueryCriteria()).run();
        }
    }
}
