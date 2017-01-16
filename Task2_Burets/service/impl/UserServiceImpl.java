package by.training.Task2_Burets.service.impl;

import by.training.Task2_Burets.bean.RentUnit;
import by.training.Task2_Burets.bean.User;
import by.training.Task2_Burets.dao.UserDAO;
import by.training.Task2_Burets.dao.exception.DAOException;
import by.training.Task2_Burets.dao.factory.DAOFactory;
import by.training.Task2_Burets.service.UserService;
import by.training.Task2_Burets.service.exception.ServiceException;

public class UserServiceImpl implements UserService {
    @Override
    public void registration(User user) throws ServiceException {
        if (isValidUser(user)) {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getUserDAO();
            try {
                userDAO.registration(user);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("Args is not valid");
        }
    }

    @Override
    public User signIn(String login, String password) throws ServiceException {
        User currentUser = null;
        if (login != null && !login.isEmpty()) {
            if (password != null && !password.isEmpty()) {
                DAOFactory factory = DAOFactory.getInstance();
                UserDAO user = factory.getUserDAO();
                try {
                    currentUser = user.signIn(login, password);
                } catch (DAOException e) {
                    throw new ServiceException(e);
                }
            }
        }
        return currentUser;
    }

    @Override
    public RentUnit getRentedEquipments(User user) throws ServiceException {
        if (isValidUser(user)) {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getUserDAO();
            try {
                int userID = userDAO.getUserID(user);
                return userDAO.getRentedEquipments(userID);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("Args is not valid");
        }
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
