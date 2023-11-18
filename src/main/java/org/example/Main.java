package org.example;

import org.example.config.DefaultAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        String helpText = """
                                
                \t add    - added new contact;
                \t output - show a list of all  contacts;
                \t remove - remove contact;
                \t save   - save contacts in file;
                \t help   - call for help
                \t exit   - exit program""";

        String pattern = "Firstname;+899999999;example@example.example";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an action:" + helpText);

        ApplicationContext context = new AnnotationConfigApplicationContext(DefaultAppConfig.class);
        PersonRepository personRepo = context.getBean(PersonRepository.class);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            switch (input) {
                case ("add") -> {
                    System.out.print("Enter a new entry in the format: " + pattern + "\n\t>> ");
                    String entry = scanner.nextLine();
                    String[] entryArray = entry.split(";");
                    if (checkData(entryArray)) {
                        personRepo.add(new Person(entryArray[0], entryArray[1], entryArray[2]));
                        System.out.println("Entry added successfully.");
                    }
                    else {
                        System.out.println("Input error.");
                    }
                }
                case ("output") -> {
                    if (!personRepo.getAll().isEmpty()) {
                        personRepo.getAll().forEach(System.out::println);
                    } else {
                        System.out.println("No data to output.");
                    }
                }
                case ("remove") -> {
                    System.out.print("Enter email:\n\t>> ");
                    String email = scanner.nextLine();
                    if(personRepo.removeItemByEmail(email)) {
                        System.out.println("Removed successfully.");
                    } else {
                        System.out.println("Contact not found.");
                    }
                }
                case ("save") -> {
                    if(personRepo.saveInFile()) {
                        System.out.println("Successfully saved to file. ");
                    } else {
                        System.out.println("No ou to saving.");
                    }
                }
                case ("help") -> {
                    System.out.println(helpText);
                }
                case ("exit") -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("Incorrect command. Enter \"help\".");
                }
            }
        }
    }

    private static boolean checkData(String[] inputArray) {
        return nameValidation(inputArray[0]) && phoneNumberValidation(inputArray[1]) && emailValidation(inputArray[2]);
    }

    private static boolean nameValidation(String name) {
        String nameRegex = "[a-zA-Zа-яА-ЯёЁ\\s]{5,50}";
        return name.trim().matches(nameRegex);
    }

    private static boolean phoneNumberValidation(String number) {
        String numberRegex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        return number.trim().matches(numberRegex);
    }

    private static boolean emailValidation(String email) {
        String emailRegex = "^([a-z\\d-_]+[.])*[a-z\\d-_]+@[a-z\\d-_]+([.][a-z\\d-_]+)*[.][a-z]+$";
        return email.trim().matches(emailRegex);
    }
}