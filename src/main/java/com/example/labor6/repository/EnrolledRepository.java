package com.example.labor6.repository;

import com.example.labor6.model.*;
import com.example.labor6.repository.*;
import java.util.List;

public class EnrolledRepository {
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    public EnrolledRepository() {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    /**
     *
     * @param courseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param studentID eine "Long" Zahl, die ein "Student" Id entspricht
     * @return "true", falls der Student an der Vorlesung teilnimmt, "false" sonst
     */
    public boolean findOne(Long courseID, Long studentID){
        Long existRegister = this.courseRepository.findOne(courseID).getStudentsEnrolled()
                .stream()
                .filter(id -> id.equals(studentID))
                .findFirst()
                .orElse(null);

        if (existRegister == null)
            return false;
        else
            return true;
    }

    /**
     * //wir registrieren der Student an der Vorlesung
     * @param courseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param studentID eine "Long" Zahl, die ein "Student" Id entspricht
     */
    public void save(Long courseID, Long studentID){
        List<Long> studentList = this.courseRepository.findOne(courseID).getStudentsEnrolled();
        studentList.add(studentID);
        Course course = new Course(this.courseRepository.findOne(courseID).getName(),
                this.courseRepository.findOne(courseID).getTeacherID(),
                this.courseRepository.findOne(courseID).getCourseID(),
                this.courseRepository.findOne(courseID).getMaxEnrollment(),
                studentList,
                this.courseRepository.findOne(courseID).getCredits());

        this.courseRepository.update(course);

        List<Long> courseList = this.studentRepository.findOne(studentID).getEnrolledCourses();
        courseList.add(courseID);
        Student student = new Student(new Person(this.studentRepository.findOne(studentID).getPersonID(),
                this.studentRepository.findOne(studentID).getVorname(),
                this.studentRepository.findOne(studentID).getNachname()),
                this.studentRepository.findOne(studentID).getStudentID(),
                this.studentRepository.findOne(studentID).getTotalCredits() + this.courseRepository.findOne(courseID).getCredits(),
                courseList);

        this.studentRepository.update(student);
    }

    /**
     * //wir löschen der Student aus der Vorlesung
     * @param courseID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param studentID eine "Long" Zahl, die ein "Student" Id entspricht
     */
    public void delete(Long courseID, Long studentID){
        List<Long> studentList = this.courseRepository.findOne(courseID).getStudentsEnrolled();
        studentList.remove(studentID);

        Course course = new Course(this.courseRepository.findOne(courseID).getName(),
                this.courseRepository.findOne(courseID).getTeacherID(),
                this.courseRepository.findOne(courseID).getCourseID(),
                this.courseRepository.findOne(courseID).getMaxEnrollment(),
                studentList,
                this.courseRepository.findOne(courseID).getCredits());

        this.courseRepository.update(course);

        List<Long> courseList = this.studentRepository.findOne(studentID).getEnrolledCourses();
        courseList.remove(courseID);

        Student student = new Student(new Person(this.studentRepository.findOne(studentID).getPersonID(),
                this.studentRepository.findOne(studentID).getVorname(),
                this.studentRepository.findOne(studentID).getNachname()),
                this.studentRepository.findOne(studentID).getStudentID(),
                this.studentRepository.findOne(studentID).getTotalCredits() - this.courseRepository.findOne(courseID).getCredits(),
                courseList);

        this.studentRepository.update(student);
    }
}
