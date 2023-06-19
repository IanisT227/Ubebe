package ro.ubb.bookstore.client;

import ro.ubb.bookstore.client.UI.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ClientApp {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ro.ubb.bookstore.client.config");
        Console console = context.getBean(Console.class);
        console.runConsole();
        System.out.println("bye ");
    }
}
