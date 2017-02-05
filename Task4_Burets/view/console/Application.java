package by.training.xml_validator.view.console;

import by.training.xml_validator.controller.Controller;
import by.training.xml_validator.controller.impl.ControllerImpl;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        boolean stop = false;
        Controller controller = new ControllerImpl();

        System.out.println("Enter commands here. To exit type 'STOP'");

        Scanner reader = new Scanner(System.in);
        while (!stop) {
            String request = reader.nextLine();

            if ("STOP".equals(request.toUpperCase())) {
                stop = true;
            } else {
                String response = controller.executeTask(request);
                System.out.println(response);
            }
        }
    }
}
