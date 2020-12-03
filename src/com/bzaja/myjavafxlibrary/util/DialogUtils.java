package com.bzaja.myjavafxlibrary.util;

import com.bzaja.myjavalibrary.util.BeanUtils;
import com.bzaja.myjavalibrary.util.LanguageFormat;
import com.bzaja.myjavalibrary.util.PropertyInfoDto;
import java.util.List;
import java.util.Optional;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

public final class DialogUtils {

    private DialogUtils() {

    }

    public static void showDialog(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static <T> void showDialog(Alert.AlertType alertType, String headerText, T bean, List<PropertyInfoDto> propertiesInfo, LanguageFormat languageFormat) {
        showDialog(alertType, headerText, getBeanToString(bean, propertiesInfo, languageFormat));
    }

    public static void showConfirmationDialog(String headerText, String contentText, Runnable runnable, String successMessage, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                runnable.run();
                showDialog(Alert.AlertType.INFORMATION, "Info", successMessage);
            } catch (Exception e) {
                showDialog(Alert.AlertType.ERROR, "Error", errorMessage);
            } finally {
                closeAlert(alert);
            }
        }
    }

    public static <T> void showConfirmationDialog(String headerText, T bean, List<PropertyInfoDto> propertiesInfo, Runnable runnable, String successMessage, String errorMeesage, LanguageFormat languageFormat) {
        showConfirmationDialog(headerText, getBeanToString(bean, propertiesInfo, languageFormat), runnable, successMessage, errorMeesage);
    }

    public static void showLoadingDialog(Runnable runnable, String errorMessage) {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        Label loadingLabel = new Label("Loading...");
        loadingLabel.setStyle("-fx-font-size: 14px;");

        VBox bodyContent = new VBox(progressIndicator, loadingLabel);
        bodyContent.setAlignment(Pos.CENTER);
        bodyContent.setSpacing(20);
        bodyContent.setPrefWidth(150);
        bodyContent.setPrefHeight(150);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> e.consume());
        alert.getDialogPane().setContent(bodyContent);
        alert.show();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    runnable.run();
                    Thread.sleep(500);
                } catch (Exception e) {
                    cancel();
                }

                return null;
            }

            @Override
            protected void succeeded() {
                closeAlert(alert);
            }

            @Override
            protected void cancelled() {
                closeAlert(alert);
                showDialog(Alert.AlertType.ERROR, "Error", errorMessage);
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private static void closeAlert(Alert alert) {
        alert.setResult(ButtonType.CLOSE);
        alert.close();
    }

    public static void tryShowErrorDialog(Runnable runnable, String errorMessage) {
        try {
            runnable.run();
        } catch (Exception e) {
            DialogUtils.showDialog(Alert.AlertType.ERROR, "Greška", errorMessage);
        }
    }

    private static <T> String getBeanToString(T bean, List<PropertyInfoDto> propertiesInfo, LanguageFormat languageFormat) {
        StringBuilder stringBuilder = new StringBuilder();

        propertiesInfo.forEach((propertyInfo) -> {
            stringBuilder.append(propertyInfo.getDisplayName()).append(": ").append(BeanUtils.getFormattedPropertyValue(bean, propertyInfo.getPropertyName(), languageFormat)).append(System.lineSeparator());
        });

        return stringBuilder.toString();
    }
}
