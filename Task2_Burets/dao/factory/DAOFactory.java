package by.training.equipment_store.dao.factory;

import by.training.equipment_store.dao.EquipmentDAO;
import by.training.equipment_store.dao.UserDAO;
import by.training.equipment_store.dao.impl.EquipmentDAOImpl;
import by.training.equipment_store.dao.impl.UserDAOImpl;

public class DAOFactory {
    private static final DAOFactory factory = new DAOFactory();

    private final EquipmentDAO equipmentDAOImpl = new EquipmentDAOImpl();
    private final UserDAO userDAOImpl = new UserDAOImpl();

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return factory;
    }

    public EquipmentDAO getEquipmentDAO() {
        return this.equipmentDAOImpl;
    }

    public UserDAO getUserDAO() {
        return this.userDAOImpl;
    }
}
