package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileReader;
import java.io.IOException;

public class ContactsInitializer implements InitializingBean {

    @Value("${app.contacts.default-file.path}")
    private String path;

    PersonRepository personRepo;

    public ContactsInitializer() {
    }

    @Autowired
    public void setPersonRepo(PersonRepository personRepository) {
        this.personRepo = personRepository;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                personRepo.add(createPerson(values[0]));
            }
        } catch (IOException| CsvValidationException ex) {
            ex.printStackTrace();
        }
    }

    private Person createPerson(String values) {
        String[] personFields = values.split(";");
        return new Person(personFields[0], personFields[1], personFields[2]);
    }

}
