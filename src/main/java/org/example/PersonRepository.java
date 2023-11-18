package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository implements ProjectRepository<Person> {

    @Value("${app.contacts.added-file.path}")
    private String path;

    public static final List<Person> persons = new ArrayList<Person>();

    @Override
    public void add(Person person) {
        persons.add(person);
    }

    @Override
    public List<Person> getAll() {
        return new ArrayList<>(persons);
    }

    @Override
    public boolean saveInFile() {
        if (!persons.isEmpty()) {
            try {
                PrintWriter writer = new PrintWriter(path);
                persons.forEach(p -> {
                    writer.println(p.fullName + ";" + p.phoneNumber + ";" + p.email);
                });
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeItemByEmail(String email) {
        List<Person> personsForDelete = new ArrayList<>();
        persons.forEach(p -> {
            if (p.email.equals(email)) {
                personsForDelete.add(p);
            }
        });
        if (!personsForDelete.isEmpty()) {
            persons.removeAll(personsForDelete);
            return true;
        } else {
            return false;
        }
    }

}
