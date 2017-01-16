package by.training.equipment_store.service;

import by.training.equipment_store.bean.RentUnit;
import by.training.equipment_store.bean.User;
import by.training.equipment_store.service.exception.ServiceException;

public interface UserService {
    void registration(User user) throws ServiceException;
    User signIn(String login, String password) throws ServiceException;
    RentUnit getRentedEquipments(User user) throws ServiceException;
}
