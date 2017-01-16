package by.training.equipment_store.service;

import by.training.equipment_store.bean.SportEquipment;
import by.training.equipment_store.bean.User;
import by.training.equipment_store.service.exception.ServiceException;

import java.util.HashMap;

public interface StoreService {
    void addEquipment(SportEquipment sportEquipment) throws ServiceException;
    void deleteEquipmentByID(int equipmentID) throws ServiceException;

    HashMap<Integer, SportEquipment> searchEquipment(String title) throws ServiceException;

    void rentEquipment(int equipmentID, User user) throws ServiceException;
    void returnEquipment(int equipmentID, User user) throws ServiceException;

    String printReport() throws ServiceException;
}
