package org.example;

import java.util.List;

public interface ProjectRepository<T> {

    void add(T object);

    List<T> getAll();

    boolean saveInFile();

    boolean removeItemByEmail(String line);

}

