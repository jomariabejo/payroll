package com.jomariabejo.motorph.controller.systemadmin;

import com.jomariabejo.motorph.database.DatabaseConnectionUtility;
import com.jomariabejo.motorph.entity.User;
import com.jomariabejo.motorph.enums.AccessType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemAdministratorController {

    @FXML
    private Button btn_search;

    @FXML
    private TextField tf_searchField;

    @FXML
    private TableView<User> tv_users;

    @FXML
    private TableColumn<Integer, User> userId;

    @FXML
    void searchBtnClicked(ActionEvent event) {

    }

    @FXML
    void searchiTextField(ActionEvent event) {

    }

    @FXML
    private void initialize() {
        try {
            setUsersTableView();
            populateTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateTableView() {
        String query = "SELECT user_id, username, password FROM user";

        try (
                Connection connection = DatabaseConnectionUtility.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                tv_users.getItems().add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setUsersTableView() throws SQLException {

        // Define the custom cell for the actions column
        TableColumn<User, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(150);
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            final Button editButton = new Button();
            final Button viewButton = new Button();
            final Button deleteButton = new Button();

            final HBox actionsBox = new HBox(editButton, viewButton, deleteButton);

            {
                actionsBox.setAlignment(Pos.CENTER); // Align HBox content to center
                actionsBox.setSpacing(5); // Set spacing between buttons

                editButton.setOnAction(event -> {
                    // Handle edit action here ⚠️
                    {
                        User user = getTableView().getItems().get(getIndex());

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/jomariabejo/motorph/create/add-new-user.fxml"));

                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                            stage.setTitle("Editing " + user.getUsername() + " record");
                            stage.setScene(new Scene(root));
                            stage.show();

                            UserController userController = fxmlLoader.getController();
                            userController.initUserIdAndAccessType(user.getUserID(), AccessType.UPDATE);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                viewButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/jomariabejo/motorph/create/add-new-user.fxml"));

                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Viewing " + user.getUsername() + " record");
                        stage.setScene(new Scene(root));
                        stage.show();

                        UserController addNewUserController = fxmlLoader.getController();
                        addNewUserController.initUserIdAndAccessType(user.getUserID(), AccessType.VIEW);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


                // Load the edit and view icon images
                Image editIcon = new Image(getClass().getResourceAsStream("/img/modify-icon.png"));
                Image viewIcon = new Image(getClass().getResourceAsStream("/img/view-icon.png"));

                ImageView editIconView = new ImageView(editIcon);
                editIconView.setFitWidth(24);
                editIconView.setFitHeight(24);

                ImageView viewIconView = new ImageView(viewIcon);
                viewIconView.setFitWidth(36);
                viewIconView.setFitHeight(24);


                // Set the icon images as graphics for the edit and view buttons
                deleteButton.setOnAction(event -> {
//                    User user = getTableView().getItems().get(getIndex());
//                    // Handle delete action here
//                    String customHeader = "Confirm Deletion";
//                    // Ask for confirmation before deleting an user
//                    String customContent = "Are you sure you want to delete user " + user.getUsername() + " ?";

                    User user = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/jomariabejo/motorph/create/add-new-user.fxml"));

                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Delete " + user.getUsername() + " record");
                        stage.setScene(new Scene(root));
                        stage.show();

                        UserController userController = fxmlLoader.getController();
                        userController.initUserIdAndAccessType(user.getUserID(), AccessType.DELETE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Load the delete icon image
                Image deleteIcon = new Image(getClass().getResourceAsStream("/img/delete-icon.png"));
                ImageView deleteIconView = new ImageView(deleteIcon);
                deleteIconView.setFitWidth(24);
                deleteIconView.setFitHeight(24);

                // Set the delete icon as the graphic for the delete button
                editButton.setGraphic(editIconView);
                viewButton.setGraphic(viewIconView);
                deleteButton.setGraphic(deleteIconView);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionsBox);
                }
            }
        });
        this.tv_users.getColumns().add(actionsColumn);
    }

    @FXML
    public void addNewUserClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/jomariabejo/motorph/create/add-new-user.fxml"));

        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Adding new user");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void searching(ActionEvent event) {

    }
}
