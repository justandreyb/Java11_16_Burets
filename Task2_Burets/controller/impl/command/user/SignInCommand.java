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
