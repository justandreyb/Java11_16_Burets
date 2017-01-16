package by.training.equipment_store.dao.impl;

import by.training.equipment_store.bean.*;
import by.training.equipment_store.dao.UserDAO;
import by.training.equipment_store.dao.exception.DAOException;
import by.training.equipment_store.util.Coder;
import by.training.equipment_store.util.DBConnector;
import by.training.equipment_store.util.exception.UtilException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public void registration(User user) throws DAOException {
        String query = "INSERT INTO user (email, password, name) VALUES (?, ?, ?)";
        String queryWithoutName = "INSERT INTO user (email, password) VALUES (?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnector.getConnection();

            if (user.getName() == null) {
                statement = connection.prepareStatement(queryWithoutName);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
            } else {
                statement = connection.prepareStatement(query);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getName());
            }

            if (statement.executeUpdate() < 1) {
                throw new DAOException("Error during adding new user");
            }

        } catch (UtilException | SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                DBConnector.closeConnection(connection);
            } catch (UtilException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }

    @Override
    public User signIn(String login, String password) throws DAOException {
        String query = "SELECT user.id, user.email, user.password, user.name FROM user WHERE user.email=? AND user.password=?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        User user = null;

        try {
            connection = DBConnector.getConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, login);
            statement.setString(2, Coder.encrypt(password));

            set = statement.executeQuery();

            while (set.next()) {
                try {
                    String name = set.getString(4);
                    if (name == null) {
                        user = new User(set.getString(2), set.getString(3));
                    } else {
                        user = new User(set.getString(2), set.getString(3), name);
                    }

                    RentUnit currentUnit = getRentedEquipments(set.getInt(1));
                    user.setRentedEquipments(currentUnit);

                } catch (NullPointerException e) {
                    throw new DAOException("Error during signing in with login = " + login);
                }
            }
            return user;

        } catch (UtilException | SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                DBConnector.closeConnection(connection);
            } catch (UtilException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }

    @Override
    public void rentEquipment(User user, int equipmentID) throws DAOException {
        if (hasAbleToBeRented(equipmentID)) {
            String queryToUpdate = "INSERT INTO user_to_equipment (user, equipment) VALUES ((SELECT user.id FROM user WHERE user.email=? AND user.password=?), ?)";

            Connection connection = null;
            PreparedStatement statement = null;

            try {
                connection = DBConnector.getConnection();
                statement = connection.prepareStatement(queryToUpdate);

                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                statement.setInt(3, equipmentID);

                statement.executeUpdate();

            } catch (UtilException | SQLException e) {
                throw new DAOException(e.getMessage());
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
                try {
                    DBConnector.closeConnection(connection);
                } catch (UtilException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        } else {
            throw new DAOException("This equipment has been already rented");
        }
    }

    @Override
    public void returnEquipment(User user, int equipmentID) throws DAOException {
        if (!hasAbleToBeRented(equipmentID)) {
            String query = "UPDATE user_to_equipment SET is_active=0 WHERE user=(SELECT user.id FROM user WHERE user.email=? AND user.password=?) AND equipment=?";

            Connection connection = null;
            PreparedStatement statement = null;

            try {
                connection = DBConnector.getConnection();
                statement = connection.prepareStatement(query);

                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                statement.setInt(3, equipmentID);

                statement.executeUpdate();

            } catch (UtilException | SQLException e) {
                throw new DAOException(e.getMessage());
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
                try {
                    DBConnector.closeConnection(connection);
                } catch (UtilException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        } else {
            throw new DAOException("This equipment hasn't been rented");
        }
    }

    @Override
    public RentUnit getRentedEquipments(int userID) throws DAOException {
        String query = "SELECT title, category, gender, price FROM equipment WHERE equipment.id IN (SELECT equipment FROM user_to_equipment WHERE user=? AND is_active=1)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        RentUnit rentedEquipments = null;

        try {
            connection = DBConnector.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, userID);

            set = statement.executeQuery();

            rentedEquipments = new RentUnit();
            while (set.next()) {
                try {
                    String currentTitle = set.getString(1);
                    String currentType = set.getString(2);
                    Gender currentGender = Gender.valueOf(set.getString(3));
                    BigDecimal currentPrice = set.getBigDecimal(4);

                    Category category = new Category(currentType, currentGender);
                    SportEquipment equipment = new SportEquipment(currentTitle, currentPrice, category);

                    rentedEquipments.addUnit(equipment);
                } catch (NullPointerException e) {
                    throw new DAOException("Error during getting rented equipments for user = " + userID);
                }
            }

            return rentedEquipments;
        } catch (UtilException | SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                DBConnector.closeConnection(connection);
            } catch (UtilException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }

    private boolean hasAbleToBeRented(int equipmentID) throws DAOException {
        String query = "SELECT id FROM user_to_equipment WHERE equipment=? AND is_active=1";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        boolean hasAble = true;
        try {
            connection = DBConnector.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, equipmentID);

            set = statement.executeQuery();

            if (set.next()) {
                hasAble = false;
            }

            return hasAble;
        } catch (UtilException | SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                DBConnector.closeConnection(connection);
            } catch (UtilException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }

    public int getUserID(User user) throws DAOException {
        String queryWithoutName = "SELECT id FROM user WHERE email=? AND password=?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        int userID = 0;
        try {
            connection = DBConnector.getConnection();

            statement = connection.prepareStatement(queryWithoutName);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());

            set = statement.executeQuery();

            if (set.next()) {
                userID = set.getInt(1);
            }

            return userID;

        } catch (UtilException | SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
            try {
                DBConnector.closeConnection(connection);
            } catch (UtilException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }
}
