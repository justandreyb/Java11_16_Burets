package by.training.Task2_Burets.service;

import by.training.Task2_Burets.bean.RentUnit;
import by.training.Task2_Burets.bean.User;
import by.training.Task2_Burets.service.exception.ServiceException;

public interface UserService {
    void registration(User user) throws ServiceException;
    User signIn(String login, String password) throws ServiceException;
    RentUnit getRentedEquipments(User user) throws ServiceException;
}
