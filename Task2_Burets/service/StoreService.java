package by.training.Task2_Burets.service;

import by.training.Task2_Burets.bean.SportEquipment;
import by.training.Task2_Burets.bean.User;
import by.training.Task2_Burets.service.exception.ServiceException;

import java.util.HashMap;

public interface StoreService {
    void addEquipment(SportEquipment sportEquipment) throws ServiceException;
    void deleteEquipmentByID(int equipmentID) throws ServiceException;

    HashMap<Integer, SportEquipment> searchEquipment(String title) throws ServiceException;

    void rentEquipment(int equipmentID, User user) throws ServiceException;
    void returnEquipment(int equipmentID, User user) throws ServiceException;

    String printReport() throws ServiceException;
}
