package by.training.xml_validator.controller.impl;

import by.training.xml_validator.bean.CommandName;
import by.training.xml_validator.controller.ParseCommand;
import by.training.xml_validator.controller.ValidateCommand;
import by.training.xml_validator.controller.impl.command.parse.ParseViaDOM;
import by.training.xml_validator.controller.impl.command.parse.ParseViaSAX;
import by.training.xml_validator.controller.impl.command.parse.ParseViaStAX;
import by.training.xml_validator.controller.impl.command.parse.WrongParseCommand;
import by.training.xml_validator.controller.impl.command.validate.ValidateViaParser;
import by.training.xml_validator.controller.impl.command.validate.WrongValidateCommand;
import by.training.xml_validator.util.ArgumentParser;
import by.training.xml_validator.util.exception.UtilException;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandProvider {
    private static final Logger log = Logger.getLogger(CommandProvider.class.getName());
    private static final CommandProvider factory = new CommandProvider();

    private HashMap<CommandName, ValidateCommand> validateCommandRepository = new HashMap<CommandName, ValidateCommand>(0);
    private HashMap<CommandName, ParseCommand> parseCommandRepository = new HashMap<CommandName, ParseCommand>(0);

    private CommandProvider() {
        fillValidateCommandRepo();
        fillParseCommandRepo();
    }

    public static CommandProvider getInstance() {
        return factory;
    }

    ValidateCommand getValidateCommand(String request) {
        CommandName commandName;
        ValidateCommand validateCommand;

        try {
            request = request.toUpperCase();
            commandName = CommandName.valueOf(request);
            validateCommand = validateCommandRepository.get(commandName);

            if (commandName.name().contains("VALIDATE_VIA")) {
                validateCommand.setParserName(ArgumentParser.getParserName(commandName));
            }

        } catch (UtilException | IllegalArgumentException ex) {
            validateCommand = validateCommandRepository.get(CommandName.WRONG_COMMAND);
            log.log(Level.WARNING, "Wrong validate command", ex);
        }

        return validateCommand;
    }

    ParseCommand getParseCommand(String request) {
        CommandName commandName;
        ParseCommand command;
        try {
            request = request.toUpperCase();
            commandName = CommandName.valueOf(request);
            command = parseCommandRepository.get(commandName);
        } catch (IllegalArgumentException ex) {
            command = parseCommandRepository.get(CommandName.WRONG_COMMAND);
            log.log(Level.WARNING, "Wrong command", ex);
        }

        return command;
    }

    private void fillParseCommandRepo() {
        parseCommandRepository.put(CommandName.PARSE_VIA_DOM, new ParseViaDOM());
        parseCommandRepository.put(CommandName.PARSE_VIA_SAX, new ParseViaSAX());
        parseCommandRepository.put(CommandName.PARSE_VIA_STAX, new ParseViaStAX());

        parseCommandRepository.put(CommandName.WRONG_COMMAND, new WrongParseCommand());
    }

    private void fillValidateCommandRepo() {
        validateCommandRepository.put(CommandName.VALIDATE_VIA_DOM, new ValidateViaParser());
        validateCommandRepository.put(CommandName.VALIDATE_VIA_SAX, new ValidateViaParser());
        validateCommandRepository.put(CommandName.VALIDATE_VIA_STAX, new ValidateViaParser());

        validateCommandRepository.put(CommandName.WRONG_COMMAND, new WrongValidateCommand());
    }
}
