package by.training.equipment_store.dao;

import by.training.equipment_store.bean.SportEquipment;
import by.training.equipment_store.dao.exception.DAOException;

import java.util.ArrayList;
import java.util.HashMap;

public interface EquipmentDAO {
    void addEquipment(SportEquipment sportEquipment) throws DAOException;
    void deleteEquipmentByID(int equipmentID) throws DAOException;

    HashMap<Integer, SportEquipment> getEquipments(String title) throws DAOException;
    SportEquipment getEquipment(int id) throws DAOException;

    ArrayList<String> getReport() throws DAOException;
}
