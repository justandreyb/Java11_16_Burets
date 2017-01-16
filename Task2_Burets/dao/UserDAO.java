package by.training.Task2_Burets.dao;

import by.training.Task2_Burets.bean.RentUnit;
import by.training.Task2_Burets.bean.User;
import by.training.Task2_Burets.dao.exception.DAOException;

public interface UserDAO {
    void registration(User user) throws DAOException;
    User signIn(String login, String password) throws DAOException;

    void rentEquipment(User user, int equipmentID) throws DAOException;
    void returnEquipment(User user, int equipmentID) throws DAOException;
    RentUnit getRentedEquipments(int userID) throws DAOException;
    int getUserID(User user) throws DAOException;
}
