package by.training.xml_validator.controller.impl;

import by.training.xml_validator.bean.WebXML;
import by.training.xml_validator.controller.Controller;
import by.training.xml_validator.controller.ParseCommand;
import by.training.xml_validator.controller.ValidateCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerImpl implements Controller {
    private static final Logger log = Logger.getLogger(ControllerImpl.class.getName());

    private final CommandProvider provider = CommandProvider.getInstance();
    private static final char delimiter = ' ';
    private static final String VALIDATE_COMMAND = "VALIDATE_VIA";
    private static final String PARSE_COMMAND = "PARSE_VIA";

    @Override
    public String executeTask(String request) {
        String response;
        try {
            if (!request.isEmpty()) {
                int index = getCommandLength(request);
                response = performTask(request, index);
            } else {
                response = "Warning! Command is empty";
                log.log(Level.WARNING, response);
            }
        } catch (IllegalArgumentException ex) {
            response = "Error during processing command. Please try again";
            log.log(Level.WARNING, response, ex);
        }
        return response;
    }

    private int getCommandLength(String request) {
        return request.indexOf(delimiter);
    }

    private String performTask(String request, int commandLength) {
        String response;
        String commandName;

        if (isWord(commandLength)) {
            commandName = request.substring(0, commandLength);

            response = processCommand(commandName, request);
        } else {
            response = "Warning! Command doesn't contain desired options.";
            log.warning(response);
        }
        return response;
    }

    private boolean isWord(int length) {
        return length != -1;
    }

    private boolean isCommand(String commandName, String targetCommand) {
        return commandName.contains(targetCommand);
    }

    private String processCommand(String commandName, String request) {
        String response;

        if (isCommand(commandName, VALIDATE_COMMAND)) {
            ValidateCommand validateCommand = provider.getValidateCommand(commandName);

            response = validateCommand.validate(request);
        } else if (isCommand(commandName, PARSE_COMMAND)) {
            ParseCommand parseCommand = provider.getParseCommand(commandName);

            WebXML webXML = parseCommand.parse(request);

            response = getResult(webXML);

        } else {
            response = "Wrong command !";
            log.warning(response);
        }
        return response;
    }

    private String getResult(WebXML webXML) {
        String response;
        if (webXML != null) {
            response = webXML.toString();
        } else {
            response = "Something went wrong";
        }
        return response;
    }
}
