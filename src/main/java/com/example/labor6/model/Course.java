package com.example.labor6.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private long teacherID;
    private long courseID;
    private int maxEnrollment;
    private List<Long> studentsEnrolled;
    private int credits;

    /**
     * wir erstellen ein neues Objekt von Typ Vorlesung
     * @param name ein String
     * @param teacherID eine "Long"-Zahl
     * @param courseID eine "Long"-Zahl
     * @param maxEnrollment eine Zahl
     * @param credits eine Zahl
     */
    public Course(String name, long teacherID, long courseID, int maxEnrollment, int credits)
    {
        this.name = name;
        this.teacherID = teacherID;
        this.courseID = courseID;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = new ArrayList<>();
        this.credits = credits;
    }

    public Course(String name, long teacherID, long courseID, int maxEnrollment, List<Long> studentsEnrolled, int credits)
    {
        this.name = name;
        this.teacherID = teacherID;
        this.courseID = courseID;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = studentsEnrolled;
        this.credits = credits;
    }


    public Course(String name, long courseID, int credits){
        this.name = name;
        this.courseID = courseID;
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Vorlesung{" +
                "name='" + name + '\'' +
                ", lehrer=" + teacherID +
                ", vorlesungID=" + courseID +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                '}';
    }

    /* Getters */
    public String getName() {
        return name;
    }

    public long getTeacherID() {
        return teacherID;
    }

    public long getCourseID() { return courseID; }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public int getCredits() {
        return credits;
    }

    /* Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setTeacherID(long teacherID) {
        this.teacherID = teacherID;
    }

    public void setCourseID(long courseID) { this.courseID = courseID; }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public void setStudentsEnrolled(List<Long> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}