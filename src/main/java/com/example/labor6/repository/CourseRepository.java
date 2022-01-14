package com.example.labor6.repository;

import com.example.labor6.model.Course;

import java.util.concurrent.Callable;

public class CourseRepository extends InMemoryRepository<Course>{

    public CourseRepository() {
        super();
    }

    /**
     * @param id das Id eines Objektes aus der Liste "repoList"
     * @return die Vorlesung mit der Id "id"
     */
    @Override
    public Course findOne(Long id) {
        Course coursetoFind = this.repoList.stream()
                .filter(course -> course.getCourseID() == id)
                .findFirst()
                .orElse(null);

        return coursetoFind;
    }

    /**
     *
     * @param entity ein Objekt von Typ Vorlesung
     * @return eine aktualisierte Version des Objektes
     */
    @Override
    public Course update(Course entity) {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> course.getCourseID() == entity.getCourseID())
                .findFirst()
                .orElseThrow();


        courseToUpdate.setName(entity.getName());
        courseToUpdate.setTeacherID(entity.getTeacherID());
        courseToUpdate.setMaxEnrollment(entity.getMaxEnrollment());
        courseToUpdate.setStudentsEnrolled(entity.getStudentsEnrolled());
        courseToUpdate.setCredits(entity.getCredits());

        return courseToUpdate;
    }
}
