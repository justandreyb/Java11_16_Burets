package by.training.equipment_store.controller.impl.command.store;

import by.training.equipment_store.bean.SportEquipment;
import by.training.equipment_store.controller.Command;
import by.training.equipment_store.service.StoreService;
import by.training.equipment_store.service.exception.ServiceException;
import by.training.equipment_store.service.factory.ServiceFactory;
import by.training.equipment_store.util.ArgumentParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class SearchEquipmentCommand implements Command {

    private static Logger log = Logger.getLogger(SearchEquipmentCommand.class.getName());

    @Override
    public String execute(String request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        StoreService storeService = factory.getStoreService();
        String response;

        ArrayList<String> arguments = ArgumentParser.parse(request);
        if (arguments.size() == 2) {
            try {
                String title = arguments.get(1);
                try {
                    HashMap<Integer, SportEquipment> equipments = storeService.searchEquipment(title);
                    response = getReportMessage(equipments);
                } catch (ServiceException e) {
                    log.severe(e.getMessage());
                    response = "Something went wrong. Try again";
                }

            } catch (NullPointerException | ClassCastException e) {
                log.severe(e.getMessage());
                response = "Can't find equipment. Wrong args";
            }

        } else {
            response = "Can't find equipment. Wrong amount of args";
        }
        return response;
    }

    private String getReportMessage(HashMap<Integer, SportEquipment> equipments) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int id : equipments.keySet()) {
            stringBuilder.append("ID: ");
            stringBuilder.append(id);
            stringBuilder.append("\tcategory: ");
            stringBuilder.append(equipments.get(id).getCategory().getType());
            stringBuilder.append(", gender: ");
            stringBuilder.append(equipments.get(id).getCategory().getGender());
            stringBuilder.append(", price: ");
            stringBuilder.append(equipments.get(id).getPriceDecimal());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
