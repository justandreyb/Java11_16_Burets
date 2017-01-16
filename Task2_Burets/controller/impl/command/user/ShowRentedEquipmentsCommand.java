package by.training.Task2_Burets.controller.impl.command.user;

import by.training.Task2_Burets.bean.RentUnit;
import by.training.Task2_Burets.bean.SportEquipment;
import by.training.Task2_Burets.bean.User;
import by.training.Task2_Burets.controller.Command;
import by.training.Task2_Burets.service.UserService;
import by.training.Task2_Burets.service.exception.ServiceException;
import by.training.Task2_Burets.service.factory.ServiceFactory;
import by.training.Task2_Burets.util.ArgumentParser;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ShowRentedEquipmentsCommand implements Command {

    private static Logger log = Logger.getLogger(ShowRentedEquipmentsCommand.class.getName());

    @Override
    public String execute(String request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();

        String response;

        ArrayList<String> arguments = ArgumentParser.parse(request);
        if (arguments.size() == 3) {
            try {
                String login = arguments.get(1);
                String password = arguments.get(2);

                try {
                    User user = userService.signIn(login, password);
                    if (user != null) {
                        RentUnit unit = userService.getRentedEquipments(user);
                        if (unit != null) {
                            if (unit.getUnits().length > 0) {
                                StringBuilder equipments = new StringBuilder(0);
                                for (SportEquipment equipment : unit.getUnits()) {
                                    equipments.append("\tTitle: ");
                                    equipments.append(equipment.getTitle());
                                    equipments.append(", category: ");
                                    equipments.append(equipment.getCategory().getType());
                                    equipments.append(", price: ");
                                    equipments.append(equipment.getPrice());
                                    equipments.append("\n");
                                }
                                response = equipments.toString();
                            } else {
                                response = "You don't have any rented equipment";
                            }
                        } else {
                            response = "Something went wrong. Try again";
                        }
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
