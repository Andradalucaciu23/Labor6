package com.example.labor6.repository;

import com.example.labor6.exception.*;
import com.example.labor6.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistrationSystem {
    private final CourseRepository courseRepository;
    private final PersonRepository personRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final EnrolledRepository enrolledRepository;

    /**
     * wir erstellen ein neues Objekt von Typ RegistrationSystem
     */
    public RegistrationSystem() {
        this.courseRepository = new CourseRepository();
        this.personRepository = new PersonRepository();
        this.studentRepository = new StudentRepository();
        this.teacherRepository = new TeacherRepository();
        this.enrolledRepository = new EnrolledRepository();
    }

    /**
     * @return alle Elementen aus der "vorlesungRepository"
     */
    public CourseRepository getCourseRepository(){
        return this.courseRepository;
    }

    /**
     * @return alle Elementen aus der "personRepository"
     */
    public PersonRepository getPersonRepository() {
        return this.personRepository;
    }

    /**
     * @return alle Elementen aus der "studentRepository"
     */
    public StudentRepository getStudentRepository(){
        return this.studentRepository;
    }

    /**
     * @return alle Elementen aus der "lehrerRepository"
     */
    public TeacherRepository getTeacherRepository(){
        return this.teacherRepository;
    }

    /**
     * @return alle Elementen aus der "enrolledRepository"
     */
    public EnrolledRepository getEnrolledRepository() {
        return enrolledRepository;
    }

    /**
     *
     * @param CourseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param StudentID eine "Long" Zahl, die ein "Student" Id entspricht
     * @return der Student meldet sich fur die gegebene Vorlesung an
     * @throws RegisterException falls der Student oder die Vorlesung nicht in der Repository Liste sind
     *                     falls es gar kein verfugbar Platz dur der Vorlesung gibt
     *                     falls der Anzahl der Credits des Studenten grosser als 30
     *
     * wir aktualisieren die Listen aus der Student-, Vorlesung- und LehrerRepository
     */
    public boolean register(long CourseID, long StudentID) throws RegisterException {
        String message = "Unerfüllte Bedingungen: ";
        Course course = this.courseRepository.findOne(CourseID);
        Student student = this.studentRepository.findOne(StudentID);

        if (course == null || student == null)
            throw new RegisterException(message + "Der Student und/oder die Vorlesung sind/ist nicht in der Liste.");

        if (course.getStudentsEnrolled().size() > course.getMaxEnrollment())
            throw new RegisterException(message + "Keine freie Plätze.");
        else if (student.getTotalCredits() + course.getCredits() > 30)
            throw new RegisterException(message + "Anzahl von Credits übersprungen.");
        else if (this.enrolledRepository.findOne(course.getCourseID(), student.getStudentID()))
            throw new RegisterException(message + "Der Student nimmt an dieser Vorlesung teil.");
        else {
            this.enrolledRepository.save(course.getCourseID(), student.getStudentID());
        }

        return true;
    }

    /**
     *
     * @param CourseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param StudentID eine "Long" Zahl, die ein "Student" Id entspricht
     *
     * wir loschen die Vorlesung aus der "Vorlesung" Liste des Studenten
     * wir loschen der Student aus der "Studenten" Liste der Vorlesung
     * wir aktualisieren die Listen aus der Student- und VorlesungRepository
     *
     * @throws RegisterException falls Der Student und/oder die Vorlesung sind/ist nicht in der Liste
     */
    public void unregister(long CourseID, long StudentID) throws RegisterException {
        String message = "Unerfüllte Bedingungen: ";
        Course course = this.getCourseRepository().findOne(CourseID);
        Student student = this.getStudentRepository().findOne(StudentID);

        if (course == null || student == null)
            throw new RegisterException(message + "Der Student und/oder die Vorlesung sind/ist nicht in der Liste.");

        this.getEnrolledRepository().delete(CourseID, StudentID);
    }

    /**
     * @return ein HashMap mit der Vorlesungen, die freie Platze haben und deren Anzahl

     */
     public HashMap<Integer, Course> retrieveCoursesWithFreePlaces() {
        HashMap<Integer, Course> map = new HashMap<Integer, Course>();
        int freePlatz = 0;
        for (Course v: courseRepository.findAll()) {
            if (v.getMaxEnrollment() > v.getStudentsEnrolled().size()) {
                freePlatz = v.getMaxEnrollment() - v.getStudentsEnrolled().size();
                map.put(freePlatz, v);
            }

        }

        return map;
    }

    /**
     * @param CourseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @return eine Liste von Studenten, die an der gegebenen Vorlesung teilnehmen
     * @throws ExistException falls die Vorlesung nicht in der Liste ist

     */
    public List<Long> retrieveStudentsEnrolledForACourse(long CourseID){
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < this.courseRepository.repoList.size(); i++) {
            if (this.courseRepository.repoList.get(i).getCourseID() == CourseID){
                list.addAll(this.courseRepository.repoList.get(i).getStudentsEnrolled());
                break;
            }
        }
        return list;
    }
    /**
     * @return alle Elemente aus der "vorlesungRepository"
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * @return alle Elemente aus der "personRepository"
     */
    public List<Person> getAllPersons(){return personRepository.findAll();}

    /**
     * @return alle Elemente aus der "studentRepository"
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * @return alle Elemente aus der "lehrerRepository"
     */
    public List<Teacher> getAllLehrer() {
        List<Long> courseList;
        Teacher teacher, newTeacher;
        Course course;
        for (int i = 0; i < this.teacherRepository.findAll().size(); i++) {
            courseList = new ArrayList<>();
            teacher = this.teacherRepository.repoList.get(i);
            for (int j = 0; j < this.teacherRepository.findAll().size(); j++) {
                course = this.courseRepository.repoList.get(j);
                if (teacher.getTeacherID() == course.getTeacherID())
                    courseList.add(course.getCourseID());
            }

            newTeacher = new Teacher(
                    new Person(teacher.getPersonID(),
                            teacher.getVorname(),
                            teacher.getNachname()),
                    teacher.getTeacherID(),
                    courseList
            );

            this.teacherRepository.update(newTeacher);
        }

        return this.teacherRepository.findAll();
    }

    /**
     *
     * @param CourseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param newCredit die neue Anzahl von Credits
     * @return wir andern die Anzahl der Credits der Vorlesung
     *         wir "unregister" alle Studenten aus der Vorlesung
     *         wir aktualisieren die Vorlesung
     *         wir "register" die alte Studenten zu der Vorlesung
     * @throws RegisterException falls die alte Studenten nicht mehr an der Vorlesung teilnehmen konnten
     * @throws ExistException falls die Vorlesung existiert nicht
     */
    public void changeCreditFromACourse(long CourseID, int newCredit) throws RegisterException {
        String message = "Unerfüllte Bedingungen: ";
        Course course = this.courseRepository.findOne(CourseID);
        if (course == null)
            throw new RegisterException(message + "Die Vorlesung ist nicht in der Liste.");

        int oldCredit = course.getCredits();
        Course newCourse = new Course(course.getName(),
                course.getTeacherID(),
                course.getCourseID(),
                course.getMaxEnrollment(),
                newCredit);

        Student student, newStudent;
        for (Long studentID: course.getStudentsEnrolled()) {
            student = this.studentRepository.findOne(studentID);
            if (student.getTotalCredits() - oldCredit + newCredit > 30)
                unregister(CourseID, student.getStudentID());
            else {
                newStudent = new Student(new Person(student.getPersonID(),
                        student.getVorname(),
                        student.getNachname()),
                        student.getStudentID(),
                        student.getTotalCredits() - oldCredit + newCredit,
                        student.getEnrolledCourses());
                this.studentRepository.update(newStudent);
            }

        }

        this.courseRepository.update(newCourse);
    }

    /**
     *
     * @param TeacherID eine "Long" Zahl, die ein "Lehrer" Id entspricht
     * @param CourseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @return die Loschung der Vorlesung von dem Lehrer
     *         wir "unregister" alle Studenten aus der Vorlesung
     *         wir aktualisieren die "Vorlesung" Liste des Lehrers
     */
    public boolean deleteVorlesungFromLehrer(long TeacherID, long CourseID) throws DeleteCourseFromTeacherException {
        String message = "Unerfüllte Bedingungen: ";
        Teacher teacher = this.teacherRepository.findOne(TeacherID);
        Course course = this.courseRepository.findOne(CourseID);

        if (teacher == null || course == null)
            throw new DeleteCourseFromTeacherException(message + "Der Lehrer und/oder die Vorlesung sind/ist nicht in der Liste.");

        if (course.getTeacherID() != teacher.getTeacherID())
            throw new DeleteCourseFromTeacherException(message + "Der Lehrer unterrichtet nicht diese Vorlesung.");

        for (Long student: course.getStudentsEnrolled()) {
            try {
                unregister(course.getCourseID(), student);
            } catch (RegisterException e) {
                System.out.println(message + e.getMessage());
            }
        }

        List<Long> courseList = this.teacherRepository.findOne(TeacherID).getCourses();
        courseList.remove(course);
        Teacher newLehrer = new Teacher(new Person(teacher.getPersonID(),
                teacher.getVorname(),
                teacher.getNachname()),
                teacher.getTeacherID(),
                courseList);

        this.teacherRepository.update(newLehrer);
        this.courseRepository.delete(course);

        return true;
    }

}