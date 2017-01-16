package by.training.equipment_store.dao;

import by.training.equipment_store.bean.RentUnit;
import by.training.equipment_store.bean.User;
import by.training.equipment_store.dao.exception.DAOException;

public interface UserDAO {
    void registration(User user) throws DAOException;
    User signIn(String login, String password) throws DAOException;

    void rentEquipment(User user, int equipmentID) throws DAOException;
    void returnEquipment(User user, int equipmentID) throws DAOException;
    RentUnit getRentedEquipments(int userID) throws DAOException;
    int getUserID(User user) throws DAOException;
}
