package by.training.xml_validator.controller.impl;

import by.training.xml_validator.bean.CommandName;
import by.training.xml_validator.controller.Command;
import by.training.xml_validator.controller.impl.command.*;
import by.training.xml_validator.util.ArgumentParser;
import by.training.xml_validator.util.exception.UtilException;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandProvider {
    private static final Logger log = Logger.getLogger(CommandProvider.class.getName());
    private static final CommandProvider factory = new CommandProvider();

    private HashMap<CommandName, Command> repository = new HashMap<>(0);

    private CommandProvider() {
        repository.put(CommandName.VALIDATE_VIA_DOM, new ValidateViaParser());
        repository.put(CommandName.VALIDATE_VIA_SAX, new ValidateViaParser());
        repository.put(CommandName.VALIDATE_VIA_STAX, new ValidateViaParser());

        repository.put(CommandName.WRONG_COMMAND, new WrongCommand());
    }

    public static CommandProvider getInstance() {
        return factory;
    }

    public Command getCommand(String request) {
        CommandName commandName;
        Command command;

        try {
            request = request.toUpperCase();
            commandName = CommandName.valueOf(request);
            command = repository.get(commandName);

            if (commandName.name().contains("VALIDATE_VIA")) {
                command.setParserName(ArgumentParser.getParserName(commandName));
            }

        } catch (UtilException | IllegalArgumentException ex) {
            command = repository.get(CommandName.WRONG_COMMAND);
            log.log(Level.INFO, "Wrong command", ex);
        }

        return command;
    }
}
