package by.training.Task2_Burets.controller.impl.command.store;

import by.training.Task2_Burets.controller.Command;
import by.training.Task2_Burets.service.StoreService;
import by.training.Task2_Burets.service.exception.ServiceException;
import by.training.Task2_Burets.service.factory.ServiceFactory;
import by.training.Task2_Burets.util.ArgumentParser;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DeleteEquipmentCommand implements Command {

    private static Logger log = Logger.getLogger(DeleteEquipmentCommand.class.getName());

    @Override
    public String execute(String request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        StoreService storeService = factory.getStoreService();
        String response;

        ArrayList<String> arguments = ArgumentParser.parse(request);
        if (arguments.size() == 2) {
            try {
                int id = Integer.valueOf(arguments.get(1));

                try {
                    storeService.deleteEquipmentByID(id);
                    response = "Successfully deleted";
                } catch (ServiceException e) {
                    log.severe(e.getMessage());
                    response = "Something went wrong. Try again";
                }

            } catch (NullPointerException | ClassCastException e) {
                log.severe(e.getMessage());
                response = "Can't delete equipment. Wrong args";
            }

        } else {
            response = "Can't delete equipment. Wrong amount of args";
        }
        return response;
    }
}
