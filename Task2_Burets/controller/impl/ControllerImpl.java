package by.training.equipment_store.controller.impl;

import by.training.equipment_store.controller.Command;
import by.training.equipment_store.controller.Controller;

import java.util.logging.Logger;

public class ControllerImpl implements Controller {

    private static Logger log = Logger.getLogger(ControllerImpl.class.getName());
    private final CommandProvider provider = new CommandProvider();
    private final char delimiter = ' ';

    @Override
    public String executeTask(String request) {
        String response;
        try {
            if (!request.isEmpty()) {
                String commandName;
                Command command;

                int index = getDelimiterIndex(request);
                if (index != -1) {
                    commandName = request.substring(0, index);
                    command = provider.getCommand(commandName);
                    response = command.execute(request);
                } else {
                    response = "Warning! Request doesn't contain desired options.";
                }
            } else {
                response = "Warning! Request is empty";
                log.severe(response);
            }
        } catch (IllegalArgumentException | NullPointerException ex) {
            log.severe(ex.getMessage());
            response = "Error during processing request. Please try again";
        }
        return response;
    }

    private int getDelimiterIndex(String request) {
        return request.indexOf(delimiter);
    }

}
