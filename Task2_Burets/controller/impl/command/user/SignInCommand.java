package by.training.equipment_store.controller.impl.command.user;

import by.training.equipment_store.bean.User;
import by.training.equipment_store.controller.Command;
import by.training.equipment_store.service.UserService;
import by.training.equipment_store.service.exception.ServiceException;
import by.training.equipment_store.service.factory.ServiceFactory;
import by.training.equipment_store.util.ArgumentParser;
import by.training.equipment_store.util.Coder;
import by.training.equipment_store.util.exception.UtilException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class SignInCommand implements Command {

    private static Logger log = Logger.getLogger(SignInCommand.class.getName());

    @Override
    public String execute(String request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        String response;

        ArrayList<String> arguments = ArgumentParser.parse(request);
        if (arguments.size() == 3) {
            try {
                String login = arguments.get(1);
                String password;
                try {
                    password = Coder.encrypt(arguments.get(2));

                    User user = userService.signIn(login, password);
                    if (user != null) {
                        response = "You are signed in";
                    } else {
                        response = "You are not registered";
                    }
                } catch (ServiceException | UtilException e) {
                    log.severe(e.getMessage());
                    response = "Error with sign in. Check args";
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
