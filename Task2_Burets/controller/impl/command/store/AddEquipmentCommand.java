package by.training.equipment_store.controller.impl.command.store;

import by.training.equipment_store.bean.Category;
import by.training.equipment_store.bean.Gender;
import by.training.equipment_store.bean.SportEquipment;
import by.training.equipment_store.controller.Command;
import by.training.equipment_store.service.StoreService;
import by.training.equipment_store.service.exception.ServiceException;
import by.training.equipment_store.service.factory.ServiceFactory;
import by.training.equipment_store.util.ArgumentParser;

import java.util.ArrayList;
import java.util.logging.Logger;

public class AddEquipmentCommand implements Command {

    private static Logger log = Logger.getLogger(AddEquipmentCommand.class.getName());

    @Override
    public String execute(String request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        StoreService storeService = factory.getStoreService();
        String response;

        ArrayList<String> arguments = ArgumentParser.parse(request);
        if (arguments.size() == 5) {
            try {
                String title = arguments.get(1);
                String type = arguments.get(2);
                Gender gender = Gender.valueOf(arguments.get(3));
                double price = Double.valueOf(arguments.get(4));

                Category category = new Category(type, gender);
                SportEquipment equipment = new SportEquipment(title, price, category);

                try {
                    storeService.addEquipment(equipment);
                    response = "Successfully added";
                } catch (ServiceException e) {
                    log.severe(e.getMessage());
                    response = "Something went wrong. Try again";
                }

            } catch (NullPointerException | ClassCastException e) {
                log.severe(e.getMessage());
                response = "Can't add new equipment. Wrong args";
            }

        } else {
            response = "Can't add new equipment. Wrong amount of args";
        }
        return response;
    }
}
