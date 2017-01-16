package by.training.Task2_Burets.controller.impl;

import by.training.Task2_Burets.bean.CommandName;
import by.training.Task2_Burets.controller.Command;
import by.training.Task2_Burets.controller.impl.command.WrongCommand;
import by.training.Task2_Burets.controller.impl.command.store.*;
import by.training.Task2_Burets.controller.impl.command.user.RegistrationUserCommand;
import by.training.Task2_Burets.controller.impl.command.user.ShowRentedEquipmentsCommand;
import by.training.Task2_Burets.controller.impl.command.user.SignInCommand;

import java.util.HashMap;

public class CommandProvider {
    private static final CommandProvider factory = new CommandProvider();

    private HashMap<CommandName, Command> repository = new HashMap<>(0);

    CommandProvider() {
        repository.put(CommandName.ADD_EQUIPMENT, new AddEquipmentCommand());
        repository.put(CommandName.DELETE_EQUIPMENT, new DeleteEquipmentCommand());

        repository.put(CommandName.SEARCH_EQUIPMENT, new SearchEquipmentCommand());
        repository.put(CommandName.RENT_EQUIPMENT, new RentEquipmentCommand());
        repository.put(CommandName.RETURN_EQUIPMENT, new ReturnEquipmentCommand());

        repository.put(CommandName.PRINT_REPORT, new PrintReportCommand());

        repository.put(CommandName.REGISTRATION_USER, new RegistrationUserCommand());
        repository.put(CommandName.SIGN_IN, new SignInCommand());
        repository.put(CommandName.SHOW_RENTED_EQUIPMENTS, new ShowRentedEquipmentsCommand());

        repository.put(CommandName.WRONG_COMMAND, new WrongCommand());
    }

    public Command getCommand(String request) {
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(request.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException ex) {
            command = repository.get(CommandName.WRONG_COMMAND);
        }

        return command;
    }
}
