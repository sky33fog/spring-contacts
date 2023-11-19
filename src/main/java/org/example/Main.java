package org.example;

import org.example.config.DefaultAppConfig;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {

        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(DefaultAppConfig.class)) {
            CommandHandler handler = context.getBean(CommandHandler.class);
            handler.mainHandler();
        }
    }
}