package com.example.labor6.application;


import com.example.labor6.controller.RegistrationSystemController;
import com.example.labor6.exception.RegisterException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class StudentView<e2> extends Application{
    private final RegistrationSystemController registrationSystemController;
    private Stage stage;
    private Long studentID;
    private Scene scene;
    final ToolBar toolBar = new ToolBar();
    final Button buttonHome = new Button("Sign Out");
    final Button buttonExit = new Button("Exit");

    final Button registerButton = new Button("Register");
    final ListView courseList = new ListView();
    final ListView studentListView = new ListView();
    final TextField courseID = new TextField();


    final Button coursesButton = new Button("Courses");

    /**
     * wir erstellen ein neues Objekt von Typ "StudentView"
     */
    public StudentView() {
        this.registrationSystemController = new RegistrationSystemController();
    }

    public StudentView(RegistrationSystemController registrationSystemController){
        this.registrationSystemController = registrationSystemController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("STUDENT");
        setStage(stage);
        setToolBar();

        registerButton.setAlignment(Pos.TOP_LEFT);
        coursesButton.setAlignment(Pos.CENTER_RIGHT);

        setRegisterButton();
        setCourseButton();

        HBox texts = new HBox();
        texts.getChildren().addAll(courseID);
        texts.setAlignment(Pos.CENTER);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(registerButton,coursesButton);
        buttons.setSpacing(36);
        buttons.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(toolBar, courseList, texts, studentListView, buttons);
        vBox.setSpacing(30);

        StackPane root = new StackPane();
        root.getChildren().addAll(vBox);
        scene = new Scene(root, 1000, 500);

        stage.setScene(scene);
        stage.show();
    }

    /* Getters und Setters */
    public void setStudentID(Long studentID) { this.studentID = studentID; }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * die "setToolBar"-Methode erstellt ein neues ToolBar-Objekt
     */
    private void setToolBar(){
        toolBar.getItems().addAll(buttonHome, buttonExit);
        buttonHome.setOnAction(actionEvent -> getStage().close());
        buttonExit.setOnAction(actionEvent ->
                {   final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Exit");
                    alert.setContentText("Do you want to exit the app?");

                    alert.showAndWait().ifPresent(buttonType ->
                    {
                        if (buttonType == ButtonType.OK) {
                            Platform.exit();
                        }
                    });
                }
        );
    }

    /**
     * die "setRegisterButton"-Methode fügt einen neuen Student an der gegebenen Course ein
     */

    private void setRegisterButton(){
        List<String> toStringCourse = new ArrayList<>();
        registrationSystemController.controller_getAllCourses()
                .forEach(course -> {
                    final boolean add = toStringCourse.add("Course ID: " + course.getCourseID() + "\t\tCourse Name: " +  course.getName()  + "\t\tCourse Credits: " + course.getCredits());
                });
        ObservableList<String> courses = FXCollections.observableArrayList(toStringCourse);
        courseList.setPrefSize(50, 100);
        courseList.setItems(courses);

        courseID.setPromptText("Enter Course Id:\n");
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (courseID.getText().trim().isEmpty() || registrationSystemController.controller_courseRepository().findOne(Long.valueOf(courseID.getText())) == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Register Error");
                    alert.setContentText("Please ENTER an ID");
                    alert.showAndWait();
                }
                else {
                    try {
                        registrationSystemController.controller_register(Long.parseLong(courseID.getText()), studentID);
                    } catch (RegisterException e) {
                        final Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Exception Error");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
                courseID.clear();
            }
        });

    }


    /**
     * die "setCourseButton"-Methode zeigt alle Vorlesungen, an deren der Student teilnimmt
     */


    private void setCourseButton(){
        coursesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<Long> studentList = FXCollections.observableArrayList(registrationSystemController.controller_studentRepository().findOne(studentID).getEnrolledCourses());
                studentListView.setItems(studentList);
            }
        });
    }


}
