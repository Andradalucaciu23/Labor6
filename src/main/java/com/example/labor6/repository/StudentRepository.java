package com.example.labor6.repository;

import com.example.labor6.model.Student;

public class StudentRepository extends InMemoryRepository<Student>{

    public StudentRepository()
    {
        super();
    }

    /**
     * @param id das Id eines Objektes aus der Liste "repoList"
     * @return der Student mit der Id "id"
     */
    @Override
    public Student findOne(Long id){
        Student studentToFind = this.repoList.stream()
                .filter(student -> student.getStudentID() == id)
                .findFirst()
                .orElse(null);

        return studentToFind;
    }

    /**
     *
     * @param entity ein Objekt von Typ Student
     * @return eine aktualisierte Version des Objektes
     */
    @Override
    public Student update(Student entity) {
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentID() == entity.getStudentID())
                .findFirst()
                .orElseThrow();

        studentToUpdate.setPersonID(entity.getPersonID());
        studentToUpdate.setVorname(entity.getVorname());
        studentToUpdate.setNachname(entity.getNachname());
        studentToUpdate.setTotalCredits(entity.getTotalCredits());
        studentToUpdate.setEnrolledCourses(entity.getEnrolledCourses());

        return studentToUpdate;
    }
}