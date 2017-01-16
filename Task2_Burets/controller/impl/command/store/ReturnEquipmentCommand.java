package by.training.Task2_Burets.controller.impl.command.store;

import by.training.Task2_Burets.bean.User;
import by.training.Task2_Burets.controller.Command;
import by.training.Task2_Burets.service.StoreService;
import by.training.Task2_Burets.service.UserService;
import by.training.Task2_Burets.service.exception.ServiceException;
import by.training.Task2_Burets.service.factory.ServiceFactory;
import by.training.Task2_Burets.util.ArgumentParser;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ReturnEquipmentCommand implements Command {

    private static Logger log = Logger.getLogger(ReturnEquipmentCommand.class.getName());

    @Override
    public String execute(String request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        StoreService storeService = factory.getStoreService();
        UserService userService = factory.getUserService();

        String response;

        ArrayList<String> arguments = ArgumentParser.parse(request);
        if (arguments.size() == 4) {
            try {
                String login = arguments.get(1);
                String password = arguments.get(2);

                int id = Integer.valueOf(arguments.get(3));

                try {
                    User user = userService.signIn(login, password);
                    if (user != null) {
                        storeService.returnEquipment(id, user);
                        response = "Complete";
                    } else {
                        response = "You are not registered";
                    }
                } catch (ServiceException e) {
                    log.severe(e.getMessage());
                    response = "Something went wrong. Try again";
                }
            } catch (NullPointerException | ClassCastException e) {
                log.severe(e.getMessage());
                response = "Error. Wrong args";
            }

        } else {
            response = "Error. Wrong amount of args";
        }
        return response;
    }
}
