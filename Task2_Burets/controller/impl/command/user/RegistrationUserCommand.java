package by.training.Task2_Burets.controller.impl.command.user;

import by.training.Task2_Burets.bean.User;
import by.training.Task2_Burets.controller.Command;
import by.training.Task2_Burets.service.UserService;
import by.training.Task2_Burets.service.exception.ServiceException;
import by.training.Task2_Burets.service.factory.ServiceFactory;
import by.training.Task2_Burets.util.ArgumentParser;
import by.training.Task2_Burets.util.Coder;
import by.training.Task2_Burets.util.exception.UtilException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RegistrationUserCommand implements Command {

    private static Logger log = Logger.getLogger(RegistrationUserCommand.class.getName());

    @Override
    public String execute(String request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        String response;
        User user;

        ArrayList<String> arguments = ArgumentParser.parse(request);
        if (arguments.size() == 3 || arguments.size() == 4) {
            try {
                try {
                    if (arguments.size() == 3) {
                        String login = arguments.get(1);
                        String password = Coder.encrypt(arguments.get(2));
                        user = new User(login, password);
                    } else {
                        String login = arguments.get(1);
                        String password = Coder.encrypt(arguments.get(2));
                        String name = arguments.get(3);
                        user = new User(login, password, name);
                    }

                    userService.registration(user);
                    response = "Registration complete.";
                } catch (ServiceException | UtilException e) {
                    log.severe(e.getMessage());
                    response = "Registration error. Check args";
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
