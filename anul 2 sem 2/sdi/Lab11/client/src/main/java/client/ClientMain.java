package client;

import client.UI.UI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("client.config");
         UI ui = annotationConfigApplicationContext.getBean(UI.class);

         ui.startUI();
    }
}
