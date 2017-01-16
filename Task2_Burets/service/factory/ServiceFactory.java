package by.training.equipment_store.service.factory;

import by.training.equipment_store.service.StoreService;
import by.training.equipment_store.service.UserService;
import by.training.equipment_store.service.impl.StoreServiceImpl;
import by.training.equipment_store.service.impl.UserServiceImpl;

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
