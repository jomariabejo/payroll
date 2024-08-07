package com.jomariabejo.motorph.controller;

import com.jomariabejo.motorph.entity.User;
import com.jomariabejo.motorph.repository.UserRepository;
import com.jomariabejo.motorph.service.LoginManager;
import com.jomariabejo.motorph.utility.AlertUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn, registerBtn;
    private final UserRepository userRepository = new UserRepository();
    private final Validator usernameValidator = new Validator();
    private final Validator passwordValidator = new Validator();

    public void initialize() { /* Validator for username field. */
        usernameValidator.createCheck().dependsOn("username", usernameField.textProperty()).withMethod(c -> {
            String userName = c.get("username");
            if (userName.isEmpty()) {
                c.error("Please provide username.");
                loginBtn.setDisable(true);
            }/* enable the log in button when both fields is not empty */ else if (!userName.isEmpty() && !passwordField.getText().isEmpty())
                loginBtn.setDisable(false);
        }).decorates(usernameField).immediate(); /* Validator for password field. */
        passwordValidator.createCheck().dependsOn("password", passwordField.textProperty()).withMethod(c -> {
            String password = c.get("password");
            if (password.isEmpty()) {
                c.error("Please provide password.");
                loginBtn.setDisable(true);
            }/* enable the log in button when both fields is not empty */ else if (!usernameField.getText().isEmpty() && !password.isEmpty())
                loginBtn.setDisable(false);
        }).decorates(passwordField).immediate();
    }

    public void initManager(final LoginManager loginManager) {
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<User> verification = authorize();
                if (verification.isPresent()) {
                    User user = verification.get();
                    int userId = user.getUserID();
                    loginManager.authenticated(userId);
                } else
                    AlertUtility.showErrorAlert("Invalid Credentials", "Login Failed", "We couldn't find an account that matches what you entered. Please verify your credentials and try again.");
            }
        });
    }

    private Optional<User> authorize() {
        try {
            return userRepository.getUserByUsernameANDPassword(usernameField.getText(), passwordField.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void forgotPasswordClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/jomariabejo/motorph/center/forgot_password.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Enter username to get verification code.");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
