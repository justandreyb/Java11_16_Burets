package by.training.equipment_store.controller.impl.command;

import by.training.equipment_store.controller.Command;

public class WrongCommand implements Command {
    @Override
    public String execute(String request) {
        return "Wrong command. Try again.";
    }
}
