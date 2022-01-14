package com.example.labor6;

import com.example.labor6.application.TeacherView;
import com.example.labor6.application.StudentView;
import com.example.labor6.controller.RegistrationSystemController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UIController {
    final RegistrationSystemController registrationSystemController = new RegistrationSystemController();
    @FXML
    private TextField textField;

    /**
     * der Button "Student" registriert der Benutzer als einen Student
     */
    @FXML
    protected void onStudentButtonClick(){
        if (textField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Error");
            alert.setContentText("No Id was given");
            alert.showAndWait();
        }
        else if (registrationSystemController.controller_studentRepository().findOne(Long.valueOf(textField.getText())) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Error");
            alert.setContentText("There is no Student with ID: " + textField.getText());
            alert.showAndWait();
            textField.clear();
        }
        else {
            try {
                StudentView studentView = new StudentView(registrationSystemController);
                studentView.setStudentID(Long.valueOf(textField.getText()));
                studentView.start(new Stage());
                textField.clear();
            } catch (Exception e) {
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Exception Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

    }

    /**
     * der Button "Lehrer" registriert der Benutzer als einen Lehrer
     */
    @FXML

    protected void onTeacherButtonClick(){
        if (textField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Error");
            alert.setContentText("No Id was given");
            alert.showAndWait();
        }
        else if (registrationSystemController.controller_teacherRepository().findOne(Long.valueOf(textField.getText())) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Error");
            alert.setContentText("There is no Lehrer with ID: " + textField.getText());
            alert.showAndWait();
            textField.clear();
        }
        else {
            try {
                TeacherView lehrerView = new TeacherView(registrationSystemController);
                lehrerView.setTeacherID(Long.valueOf(textField.getText()));
                lehrerView.start(new Stage());
                textField.clear();
            } catch (Exception e) {
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Exception Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    /**
     * der Button "Home" zeigt, dass man an der "Home"-Seite ist
     */

    @FXML

    protected void onHomeButtonClick(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Home Page");
        alert.setContentText("You are on the Home Page");
        alert.showAndWait();
    }
    /**
     * der Button "Exit" schließt die Application
     **/
    @FXML
    protected void onExitButtonClick(){
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Do you want to leave the application?");

        alert.showAndWait().ifPresent(buttonType ->
        {
            if (buttonType == ButtonType.OK) {
                Platform.exit();
            }
        });
    }
}