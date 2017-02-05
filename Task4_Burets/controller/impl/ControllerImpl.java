package by.training.xml_validator.controller.impl;

import by.training.xml_validator.controller.Command;
import by.training.xml_validator.controller.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerImpl implements Controller {
    private static final Logger log = Logger.getLogger(ControllerImpl.class.getName());
    private final CommandProvider provider = CommandProvider.getInstance();
    private static final char delimiter = ' ';

    @Override
    public String executeTask(String request) {
        String response;
        //noinspection Duplicates
        try {
            if (!request.isEmpty()) {
                String commandName;
                Command command;

                int index = getCommandLength(request);
                if (isWord(index)) {
                    commandName = request.substring(0, index);
                    command = provider.getCommand(commandName);
                    response = command.validate(request);
                } else {
                    response = "Warning! Command doesn't contain desired options.";
                }
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

    private boolean isWord(int length) {
        return length != -1;
    }
}
