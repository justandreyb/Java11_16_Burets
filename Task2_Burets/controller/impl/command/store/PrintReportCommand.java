package by.training.equipment_store.controller.impl.command.store;

import by.training.equipment_store.controller.Command;
import by.training.equipment_store.service.StoreService;
import by.training.equipment_store.service.exception.ServiceException;
import by.training.equipment_store.service.factory.ServiceFactory;

import java.util.logging.Logger;

public class PrintReportCommand implements Command {

    private static Logger log = Logger.getLogger(PrintReportCommand.class.getName());

    @Override
    public String execute(String request) {
        ServiceFactory factory = ServiceFactory.getInstance();
        StoreService storeService = factory.getStoreService();
        String response;

        try {
            response = storeService.printReport();
        } catch (ServiceException e) {
            log.severe(e.getMessage());
            response = "Something went wrong. Try again";
        }

        return response;
    }
}
