package com.samesame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the SameSame Password Comparator
 * A secure desktop application for comparing password inputs
 * 
 * Compatible with Java 24.0.2 and higher
 */
public class PasswordComparatorApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PasswordComparatorApp.class.getResource("/fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        
        // Set application icon (if available)
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            // Icon not found, continue without it
        }
        
        stage.setTitle("SameSame - Password Comparator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}