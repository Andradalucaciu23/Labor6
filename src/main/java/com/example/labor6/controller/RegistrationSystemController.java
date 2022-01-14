package com.example.labor6.controller;

import com.example.labor6.exception.*;
import com.example.labor6.model.*;
import com.example.labor6.repository.TeacherRepository;
import com.example.labor6.repository.RegistrationSystem;
import com.example.labor6.repository.StudentRepository;
import com.example.labor6.repository.CourseRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationSystemController {
    private final RegistrationSystem registrationSystem;

    public RegistrationSystemController(){
        this.registrationSystem = new RegistrationSystem();
        this.controller_addPerson(1L, "Andrada", "Lucaciu");
        this.controller_addPerson(2L, "Maria", "Lucaciu");
        this.controller_addPerson(3L, "Denis", "Lucaciu");
        this.controller_addPerson(4L, "Lucian", "Popescu");
        this.controller_addPerson(5L, "Iuliana", "Mitroi");

        this.controller_addStudent(1L, 10L);
        this.controller_addStudent(2L, 11L);

        this.controller_addTeacher(4L, 13L);
        this.controller_addTeacher(5L, 12L);

        this.controller_addCourse("BD", 11L, 100L, 30, 5);
        this.controller_addCourse("MAP", 12L, 101L, 31, 6);
        this.controller_addCourse("RC", 11L, 102L, 32, 7);
    }

    /**
     * @param CourseID eine "long" Zahl
     * @param StudentID eine "long" Zahl
     * @throws RegisterException falls der Student mit der Id "StudentID" nicht an der Vorlesung mit der Id "VorlesungID" registriert wurde
     */
    public void controller_register(long CourseID, long StudentID) throws RegisterException {
        registrationSystem.register(CourseID, StudentID);
    }



    public StudentRepository controller_studentRepository(){
        return this.registrationSystem.getStudentRepository();
    }
    public TeacherRepository controller_teacherRepository(){return this.registrationSystem.getTeacherRepository();}

    public CourseRepository controller_courseRepository(){
        return this.registrationSystem.getCourseRepository();
    }

    /**
     * @return die Liste aller Vorlesungen
     */
    public List<Course> controller_getAllCourses(){
        return registrationSystem.getAllCourses();
    }

    /**
     * @return die Liste aller Personen
     */
    public List<Person> controller_getAllPersons() {
        return registrationSystem.getAllPersons();
    }

    /**
     * @return die Liste aller Studenten
     */
    public List<Student> controller_getAllStudents() {
        return registrationSystem.getAllStudents();
    }

    /**
     * @return die Liste aller Lehrer
     */
    public List<Teacher> controller_getAllTeacher() {
        return registrationSystem.getAllLehrer();
    }

    /**
     * @return ein HashMap mit der Vorlesungen, die freie Platze haben und deren Anzahl
     */
    public HashMap<Integer, Course> controller_retrieveCoursesWithFreePlaces() {
        return registrationSystem.retrieveCoursesWithFreePlaces();
    }

    /**
     * @param CourseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @return eine Liste von Studenten, die an der gegebenen Vorlesung teilnehmen
     * @throws ExistException falls die Vorlesung nicht in der Liste ist
     */
    public List<Long> controller_retrieveStudentsEnrolledForACourse(long CourseID) throws ExistException {
        return registrationSystem.retrieveStudentsEnrolledForACourse(CourseID);
    }

    /**
     * @param CourseIDID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param newCredit die neue Anzahl von Credits
     * @throws RegisterException falls die alte Studenten nicht mehr an der Vorlesung teilnehmen konnten
     * @throws ExistException falls die Vorlesung existiert nicht
     */
    public void controller_changeCreditFromACourse(long CourseIDID, int newCredit) throws RegisterException, ExistException{
        registrationSystem.changeCreditFromACourse(CourseIDID, newCredit);
    }

    /**
     * @param LehrerID eine "Long" Zahl, die ein "Lehrer" Id entspricht
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @throws DeleteCourseFromTeacherException falls der Lehrer nich die Vorlesung löschen kann
     * @throws RegisterException falls die Vorlesung existiert nicht
     */
    public void controller_deleteCourseFromLehrer(long LehrerID, long VorlesungID) throws DeleteCourseFromTeacherException, RegisterException {
        registrationSystem.deleteVorlesungFromLehrer(LehrerID, VorlesungID);
    }

    /**
     * @return die sortierte Liste der Studenten
     */
    public List<Student> controller_sortListeStudenten() {
        Collections.sort(registrationSystem.getAllStudents(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getPersonID().compareTo(o2.getPersonID());
            }
        });

        return registrationSystem.getAllStudents();
    }

    /**
     * @return die sortierte Liste der Vorlesungen
     */
    public List<Course> controller_sortListeCourses(){
        Collections.sort(registrationSystem.getAllCourses(), new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return registrationSystem.getAllCourses();
    }

    /**
     * @return die filtrierte Liste der Studenten
     */
    public List<Student> controller_filterListeStudenten(){
        List<Student> studentList = this.controller_getAllStudents().stream()
                .filter(student -> student.getEnrolledCourses().size() >=2).collect(Collectors.toList());

        return studentList;
    }

    /**
     * @return die filtrierte Liste der Vorlesungen
     */
    public List<Course> controller_filterListeCourses(){
        List<Course> courseList = this.controller_getAllCourses().stream()
                .filter(course -> course.getMaxEnrollment() > 30).collect(Collectors.toList());

        return courseList;
    }

    /**
     * @param personID eine "Long"-Zahl
     * @param Vorname ein String
     * @param Nachname ein String
     */
    public void controller_addPerson(Long personID, String Vorname, String Nachname){
        this.registrationSystem.getPersonRepository()
                .save(new Person(personID, Vorname, Nachname));
    }

    /**
     * @param PersonID eine "Long"-Zahl
     * @param StudentID eine "Long"-Zahl
     */
    public void controller_addStudent(Long PersonID, Long StudentID){
        if (registrationSystem.getPersonRepository().findOne(PersonID) != null) {
            registrationSystem.getStudentRepository()
                    .save(new Student(registrationSystem.getPersonRepository().findOne(PersonID), StudentID));
        }
    }

    /**
     * @param PersonID eine "Long"-Zahl
     * @param LehrerID eine "Long"-Zahl
     */
    public void controller_addTeacher(Long PersonID, Long LehrerID) {
        if (registrationSystem.getPersonRepository().findOne(PersonID) != null){
            registrationSystem.getTeacherRepository()
                    .save(new Teacher(registrationSystem.getPersonRepository().findOne(PersonID), LehrerID));
        }

    }

    /**
     * @param Name ein String
     * @param IdTeacher ein "Long"-Zahl
     * @param IdCourse ein "Long"-Zahl
     * @param MaxEnrollment ein Zahl
     * @param Credits ein Zahl
     */
    public void controller_addCourse(String Name, Long IdTeacher, Long IdCourse, int MaxEnrollment, int Credits){
        if (registrationSystem.getTeacherRepository().findOne(IdTeacher) != null){
            registrationSystem.getCourseRepository()
                    .save(new Course(Name, IdTeacher, IdCourse, MaxEnrollment, Credits));
        }
    }
}
