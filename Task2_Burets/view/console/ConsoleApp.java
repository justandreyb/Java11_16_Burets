package by.training.Task2_Burets.view.console;

import by.training.Task2_Burets.controller.Controller;
import by.training.Task2_Burets.controller.impl.ControllerImpl;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        boolean continueLoop = true;
        Controller controller = new ControllerImpl();
        System.out.println("Enter commands here. To exit type 'STOP'");

        Scanner reader = new Scanner(System.in);
        while (continueLoop) {
            String request = reader.nextLine();

            if ("STOP".equals(request.toUpperCase())) {
                continueLoop = false;
            } else {
                String response = controller.executeTask(request);
                System.out.println(response);
            }
        }
    }
}
