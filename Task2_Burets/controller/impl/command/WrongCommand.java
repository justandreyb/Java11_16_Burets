package by.training.Task2_Burets.controller.impl.command;

import by.training.Task2_Burets.controller.Command;

public class WrongCommand implements Command {
    @Override
    public String execute(String request) {
        return "Wrong command. Try again.";
    }
}
