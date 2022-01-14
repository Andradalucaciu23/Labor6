package com.example.labor6.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends com.example.labor6.model.Person {
    private long teacherID;
    private List<Long> courses;

    /**
     * //wir erstellen ein neues Objekt von Typ "Lehrer"
     * @param person ein Objekt von Typ "Person"
     * @param teacherID eine "long" Zahl
     */
    public Teacher(Person person, long teacherID) {
        super(person.getPersonID(), person.getVorname(), person.getNachname());
        this.teacherID = teacherID;
        this.courses = new ArrayList<>();
    }

    public Teacher(Person person, long teacherID, List<Long> courses) {
        super(person.getPersonID(), person.getVorname(), person.getNachname());
        this.teacherID = teacherID;
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Lehrer{" +
                "lehrerID=" + teacherID +
                ", vorlesungen=" + courses +
                "} " + super.toString();
    }

    /* Getters */
    @Override
    public Long getPersonID() {
        return super.getPersonID();
    }

    @Override
    public String getVorname() {
        return super.getVorname();
    }

    @Override
    public String getNachname() {
        return super.getNachname();
    }

    public long getTeacherID() { return teacherID; }

    public List<Long> getCourses() { return courses; }

    /* Setters */
    @Override
    public void setPersonID(Long personID) {
        super.setPersonID(personID);
    }

    @Override
    public void setVorname(String vorname) {
        super.setVorname(vorname);
    }

    @Override
    public void setNachname(String nachname) {
        super.setNachname(nachname);
    }

    public void setTeacherID(long lehrerID) { this.teacherID = teacherID; }

    public void setCourses(List<Long> courses) { this.courses = courses; }
}