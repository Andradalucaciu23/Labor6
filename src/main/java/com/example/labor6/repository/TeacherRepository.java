package com.example.labor6.repository;
import com.example.labor6.model.Teacher;

public class TeacherRepository extends InMemoryRepository<Teacher>{

    public TeacherRepository()
    {
        super();
    }

    /**
     * @param id das Id eines Objektes aus der Liste "repoList"
     * @return der Lehrer mit der Id "id"
     */
    @Override
    public Teacher findOne(Long id) {
        Teacher lehrerToFind = this.repoList.stream()
                .filter(lehrer -> lehrer.getTeacherID() == id)
                .findFirst()
                .orElse(null);

        return lehrerToFind;
    }

    /**
     *
     * @param entity ein Objekt von Typ Lehrer
     * @return eine aktualisierte Version des Objektes
     */
    @Override
    public Teacher update(Teacher entity) {
        Teacher teacherToUpdate = this.repoList.stream()
                .filter(lehrer -> lehrer.getTeacherID() == entity.getTeacherID())
                .findFirst()
                .orElseThrow();

        teacherToUpdate.setPersonID(entity.getPersonID());
        teacherToUpdate.setVorname(entity.getVorname());
        teacherToUpdate.setNachname(entity.getNachname());
        teacherToUpdate.setCourses(entity.getCourses());

        return teacherToUpdate;
    }
}
