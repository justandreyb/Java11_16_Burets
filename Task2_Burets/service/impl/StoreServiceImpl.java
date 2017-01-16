package by.training.Task2_Burets.service.impl;

import by.training.Task2_Burets.bean.Category;
import by.training.Task2_Burets.bean.SportEquipment;
import by.training.Task2_Burets.bean.User;
import by.training.Task2_Burets.dao.EquipmentDAO;
import by.training.Task2_Burets.dao.UserDAO;
import by.training.Task2_Burets.dao.exception.DAOException;
import by.training.Task2_Burets.dao.factory.DAOFactory;
import by.training.Task2_Burets.service.StoreService;
import by.training.Task2_Burets.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreServiceImpl implements StoreService {

    @Override
    public void addEquipment(SportEquipment sportEquipment) throws ServiceException {
        if (isValidEquipment(sportEquipment)) {
            DAOFactory factory = DAOFactory.getInstance();
            EquipmentDAO equipmentDAO = factory.getEquipmentDAO();
            try {
                equipmentDAO.addEquipment(sportEquipment);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("Can't add new equipment. Wrong args");
        }
    }

    @Override
    public void deleteEquipmentByID(int equipmentID) throws ServiceException {
        if (equipmentID > 0) {
            DAOFactory factory = DAOFactory.getInstance();
            EquipmentDAO equipmentDAO = factory.getEquipmentDAO();
            try {
                equipmentDAO.deleteEquipmentByID(equipmentID);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("Can't delete equipment. Wrong args");
        }
    }


    @Override
    public HashMap<Integer, SportEquipment> searchEquipment(String title) throws ServiceException {
        HashMap<Integer, SportEquipment> equipments = new HashMap<Integer, SportEquipment>(0);
        if (!title.isEmpty()) {
            DAOFactory factory = DAOFactory.getInstance();
            EquipmentDAO equipmentDAO = factory.getEquipmentDAO();
            try {
                equipments = equipmentDAO.getEquipments(title);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("Can't search equipment. Wrong args");
        }
        return equipments;
    }


    @Override
    public void rentEquipment(int equipmentID, User user) throws ServiceException {
        if (equipmentID > 0 && isValidUser(user)) {
            int equipmentsAmount = user.getRentedEquipments().getUnits().length;
            int userLimit = user.getRentedEquipments().getLimit();

            if (equipmentsAmount < userLimit) {
                DAOFactory factory = DAOFactory.getInstance();
                UserDAO userDAO = factory.getUserDAO();

                try {
                    userDAO.rentEquipment(user, equipmentID);
                } catch (DAOException e) {
                    throw new ServiceException(e);
                }
            } else {
                throw new ServiceException("Sorry, you can't rent more equipments. Maximal amount for you is " + userLimit);
            }
        } else {
            throw new ServiceException("Can't rent equipment. Wrong args");
        }
    }

    @Override
    public void returnEquipment(int equipmentID, User user) throws ServiceException {
        if (equipmentID > 0 && isValidUser(user)) {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getUserDAO();

            try {
                userDAO.returnEquipment(user, equipmentID);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("Can't return equipment. Wrong args");
        }
    }

    @Override
    public String printReport() throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        EquipmentDAO equipmentDAO = factory.getEquipmentDAO();
        ArrayList<String> reportStruct = null;
        try {
            reportStruct = equipmentDAO.getReport();

            StringBuilder report = new StringBuilder(0);
            for (String currentReport : reportStruct) {
                report.append(currentReport);
            }
            return report.toString();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private boolean isValidEquipment(SportEquipment equipment) {
        Category category;
        boolean result = false;

        if (equipment != null) {
            if (equipment.getCategory() != null) {
                category = equipment.getCategory();
                if (equipment.getTitle() != null && category.getType() != null && category.getGender() != null) {
                    if ((!equipment.getTitle().isEmpty()) && (!category.getType().isEmpty())) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    private boolean isValidUser(User user) {
        boolean result = false;

        if (user != null) {
            if (user.getRentedEquipments() != null) {
                if (user.getEmail() != null && user.getPassword() != null) {
                    if (!user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
