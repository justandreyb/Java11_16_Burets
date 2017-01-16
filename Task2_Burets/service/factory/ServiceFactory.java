package by.training.Task2_Burets.service.factory;

import by.training.Task2_Burets.service.StoreService;
import by.training.Task2_Burets.service.UserService;
import by.training.Task2_Burets.service.impl.StoreServiceImpl;
import by.training.Task2_Burets.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory factory = new ServiceFactory();

    private final StoreService storeServiceImpl = new StoreServiceImpl();
    private final UserService userServiceImpl = new UserServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return factory;
    }

    public StoreService getStoreService() {
        return storeServiceImpl;
    }

    public UserService getUserService() {
        return userServiceImpl;
    }
}
