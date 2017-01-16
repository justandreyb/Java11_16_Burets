package by.training.Task2_Burets.controller.impl.command.store;

import by.training.Task2_Burets.controller.Command;
import by.training.Task2_Burets.service.StoreService;
import by.training.Task2_Burets.service.exception.ServiceException;
import by.training.Task2_Burets.service.factory.ServiceFactory;

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
