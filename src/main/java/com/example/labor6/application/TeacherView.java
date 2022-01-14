
package com.example.labor6.application;

import com.example.labor6.controller.RegistrationSystemController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TeacherView extends Application {
    private final RegistrationSystemController registrationSystemController;
    private Stage stage;
    private Long teacherID;
    ToolBar toolBar = new ToolBar();
    final Button buttonHome = new Button("Sign out");
    final Button buttonExit = new Button("Exit");
    final Button enrolledStudentsButton = new Button("Enrolled Students");
    final TextArea courseID = new TextArea();
    final ListView courseList = new ListView();
    final ListView enrolledStudents = new ListView();

    final Label courses = new Label("Courses");
    final Label students = new Label("Enrolled Students");


    /**
     * wir erstellen ein neues Obj von Typ "TeacherView"
     */

    public TeacherView(){
        this.registrationSystemController = new RegistrationSystemController();
    }



    public TeacherView(RegistrationSystemController registrationSystemController){
        this.registrationSystemController = registrationSystemController;
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("TEACHER");
        setStage(stage);

        setToolBar();
        courses.setFont(Font.font("Arial", 10));
        students.setFont(Font.font("Arial", 10));

        courseID.setPromptText("Enter Course Id: \n");
        coursesDisplay();

        HBox texts = new HBox();
        texts.getChildren().addAll(courseID);
        texts.setPrefSize(1,1);
        texts.setAlignment(Pos.BOTTOM_CENTER);
        HBox buttons = new HBox();

        buttons.setSpacing(36);


        VBox vBox = new VBox(toolBar,courses,courseList, students);
        vBox.setSpacing(30);

        StackPane root = new StackPane();
        root.getChildren().addAll(vBox);
        Scene scene = new Scene(root, 1000, 500);

        stage.setScene(scene);
        stage.show();

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    /**
     * die "setTollBar" Methode erstellt ein neus Tollbar Obj
     */
    public void setToolBar(){
        toolBar.getItems().addAll(buttonHome, buttonExit);
        buttonHome.setOnAction(actionEvent -> getStage().close());
        buttonExit.setOnAction(actionEvent ->
        {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setContentText("Do you want to close the app?");

            alert.showAndWait().ifPresent(buttonType ->
            {
                if(buttonType == ButtonType.OK){
                    Platform.exit();
                }
            });
        });
    }

    public Long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Long teacherID) {
        this.teacherID = teacherID;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    /**
     * die courseDisplay Methode zeigt lle courses, die Teacher mit der Id =  TeacherID unterrichtet
     */
    private void coursesDisplay(){
        List<String> toStringCourse = new ArrayList<>();

        registrationSystemController.controller_getAllCourses()
                .forEach(course ->
                {if (course.getTeacherID() == this.teacherID)
                    toStringCourse.add("Course ID: " + course.getCourseID() + "\t\tCourse Name: " +  course.getName()  + "\t\tVorlesung Credits: " + course.getCredits());});

        ObservableList<String> courses = FXCollections.observableArrayList(toStringCourse);
        courseList.setPrefSize(50, 100);
        courseList.setItems(courses);
    }




    }

