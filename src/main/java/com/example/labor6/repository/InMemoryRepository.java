package com.example.labor6.repository;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryRepository<T> implements CrudRepository<T> {

    protected List<T> repoList;

    /**
     * wir erstellen ein neues Objekt von Typ InMemoryRepository
     */
    public InMemoryRepository()
    {
        this.repoList = new ArrayList<>();
    }

    /**
     * @param id das Id eines Objektes aus der Liste "repoList"
     * @return das Objekt aus der Liste "repoList"
     */
    @Override
    public T findOne(Long id) {
        return null;
    }

    /**
     * @return alle Elemente aus der List "repoList"
     */
    @Override
    public List<T> findAll() {
        return this.repoList;
    }

    /**
     * @param entity ein Objekt von Typ "T"
     * @return das neue gespeicherte Element aus der Liste "repoList"
     */
    @Override
    public T save(T entity) {
        this.repoList.add(entity);
        return entity;
    }

    /**
     * @param entity das Element aus der Liste "repoList", die man loschen will
     */
    @Override
    public void delete(T entity) {
        this.repoList.remove(entity);
    }
}