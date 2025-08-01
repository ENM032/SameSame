package com.samesame.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import com.samesame.service.PasswordComparator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main password comparison interface
 */
public class MainController implements Initializable {

    @FXML
    private VBox rootContainer;
    
    @FXML
    private PasswordField password1Field;
    
    @FXML
    private PasswordField password2Field;
    
    @FXML
    private CheckBox showPasswordsCheckBox;
    
    @FXML
    private TextField visiblePassword1Field;
    
    @FXML
    private TextField visiblePassword2Field;
    
    @FXML
    private Label resultLabel;
    
    @FXML
    private Button compareButton;
    
    @FXML
    private Button clearButton;
    
    @FXML
    private Label strengthLabel;
    
    private final PasswordComparator passwordComparator = new PasswordComparator();
    private PauseTransition realTimeComparison;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupPasswordFields();
        setupRealTimeComparison();
        setupUI();
    }

    private void setupPasswordFields() {
        // Bind visible text fields to password fields
        visiblePassword1Field.textProperty().bindBidirectional(password1Field.textProperty());
        visiblePassword2Field.textProperty().bindBidirectional(password2Field.textProperty());
        
        // Initially hide visible text fields
        visiblePassword1Field.setVisible(false);
        visiblePassword2Field.setVisible(false);
        
        // Toggle password visibility
        showPasswordsCheckBox.setOnAction(e -> togglePasswordVisibility());
    }

    private void setupRealTimeComparison() {
        // Create a pause transition for real-time comparison (debounced)
        realTimeComparison = new PauseTransition(Duration.millis(300));
        realTimeComparison.setOnFinished(e -> performComparison());
        
        // Add listeners to password fields for real-time comparison
        password1Field.textProperty().addListener((obs, oldText, newText) -> {
            realTimeComparison.stop();
            realTimeComparison.play();
            updatePasswordStrength(newText);
        });
        
        password2Field.textProperty().addListener((obs, oldText, newText) -> {
            realTimeComparison.stop();
            realTimeComparison.play();
        });
    }

    private void setupUI() {
        // Set initial state
        resultLabel.setText("");
        strengthLabel.setText("");
        
        // Style the result label
        resultLabel.getStyleClass().add("result-label");
        strengthLabel.getStyleClass().add("strength-label");
    }

    @FXML
    private void handleCompareAction() {
        performComparison();
    }

    @FXML
    private void handleClearAction() {
        password1Field.clear();
        password2Field.clear();
        resultLabel.setText("");
        strengthLabel.setText("");
        resultLabel.getStyleClass().removeAll("match", "no-match");
    }

    private void togglePasswordVisibility() {
        boolean showPasswords = showPasswordsCheckBox.isSelected();
        
        password1Field.setVisible(!showPasswords);
        password2Field.setVisible(!showPasswords);
        visiblePassword1Field.setVisible(showPasswords);
        visiblePassword2Field.setVisible(showPasswords);
    }

    private void performComparison() {
        String password1 = password1Field.getText();
        String password2 = password2Field.getText();
        
        if (password1.isEmpty() && password2.isEmpty()) {
            resultLabel.setText("");
            resultLabel.getStyleClass().removeAll("match", "no-match");
            return;
        }
        
        if (password1.isEmpty() || password2.isEmpty()) {
            resultLabel.setText("Please enter both passwords");
            resultLabel.getStyleClass().removeAll("match", "no-match");
            return;
        }
        
        boolean isMatch = passwordComparator.comparePasswords(password1, password2);
        
        if (isMatch) {
            resultLabel.setText("✓ Passwords match!");
            resultLabel.getStyleClass().removeAll("no-match");
            resultLabel.getStyleClass().add("match");
        } else {
            resultLabel.setText("✗ Passwords do not match");
            resultLabel.getStyleClass().removeAll("match");
            resultLabel.getStyleClass().add("no-match");
        }
    }

    private void updatePasswordStrength(String password) {
        if (password.isEmpty()) {
            strengthLabel.setText("");
            return;
        }
        
        String strength = passwordComparator.evaluatePasswordStrength(password);
        strengthLabel.setText("Strength: " + strength);
        
        // Update style based on strength
        strengthLabel.getStyleClass().removeAll("weak", "medium", "strong", "very-strong");
        strengthLabel.getStyleClass().add(strength.toLowerCase().replace(" ", "-"));
    }
}