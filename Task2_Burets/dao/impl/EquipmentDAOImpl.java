package by.training.equipment_store.dao.impl;

import by.training.equipment_store.bean.Category;
import by.training.equipment_store.bean.Gender;
import by.training.equipment_store.bean.SportEquipment;
import by.training.equipment_store.dao.EquipmentDAO;
import by.training.equipment_store.dao.exception.DAOException;
import by.training.equipment_store.util.DBConnector;
import by.training.equipment_store.util.exception.UtilException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EquipmentDAOImpl implements EquipmentDAO {
    @Override
    public void addEquipment(SportEquipment equipment) throws DAOException {
        String query = "INSERT INTO equipment (title, category, gender, price) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnector.getConnection();
            statement = connection.prepareStatement(query);

            Category category = equipment.getCategory();

            statement.setString(1, equipment.getTitle());
            statement.setString(2, category.getType());
            statement.setString(3, category.getGender().name());
            statement.setBigDecimal(4, equipment.getPriceDecimal());

            if (statement.executeUpdate() < 1) {
                throw new DAOException("Error during adding new equipment");
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
    public void deleteEquipmentByID(int equipmentID) throws DAOException {
        if (equipmentID > 0) {
            String query = "DELETE FROM equipment WHERE id=?";

            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = DBConnector.getConnection();
                statement = connection.prepareStatement(query);

                statement.setInt(1, equipmentID);

                if (statement.executeUpdate() < 1) {
                    throw new DAOException("Error during deleting equipment with id = " + equipmentID);
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
    }

    @Override
    public HashMap<Integer, SportEquipment> getEquipments(String title) throws DAOException {
        HashMap<Integer, SportEquipment> equipments = new HashMap<>(0);
        if (!title.isEmpty()) {
            String query = "SELECT id, title, category, gender, price FROM equipment WHERE title=?";

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet set = null;

            try {
                connection = DBConnector.getConnection();
                statement = connection.prepareStatement(query);

                statement.setString(1, title);

                set = statement.executeQuery();

                while (set.next()) {
                    try {
                        int id = set.getInt(1);
                        String currentTitle = set.getString(2);
                        String currentType = set.getString(3);
                        Gender currentGender = Gender.valueOf(set.getString(4));
                        BigDecimal currentPrice = set.getBigDecimal(5);

                        Category category = new Category(currentType, currentGender);
                        SportEquipment equipment = new SportEquipment(currentTitle, currentPrice, category);

                        equipments.put(id, equipment);
                    } catch (NullPointerException e) {
                        throw new DAOException("Error during selecting equipment with title = " + title);
                    }
                }

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
        return equipments;
    }

    @Override
    public SportEquipment getEquipment(int id) throws DAOException {
        SportEquipment equipment = null;
        if (id > 0) {
            String query = "SELECT title, category, gender, price FROM equipment WHERE id=?";

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet set = null;

            try {
                connection = DBConnector.getConnection();
                statement = connection.prepareStatement(query);

                statement.setInt(1, id);

                set = statement.executeQuery();

                while (set.next()) {
                    try {
                        String currentTitle = set.getString(2);
                        String currentType = set.getString(3);
                        Gender currentGender = Gender.valueOf(set.getString(4));
                        BigDecimal currentPrice = set.getBigDecimal(5);

                        Category category = new Category(currentType, currentGender);
                        equipment = new SportEquipment(currentTitle, currentPrice, category);
                    } catch (NullPointerException e) {
                        throw new DAOException("Error during selecting equipment with id = " + id);
                    }
                }

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
        return equipment;
    }

    @Override
    public ArrayList<String> getReport() throws DAOException {
        String query = "SELECT id, title, category, gender, price FROM equipment";
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        ArrayList<String> result = null;

        try {
            connection = DBConnector.getConnection();
            statement = connection.createStatement();

            set = statement.executeQuery(query);

            result = new ArrayList<>(0);
            while (set.next()) {
                int equipmentID = set.getInt(1);
                String title = set.getString(2);
                String category = set.getString(3);
                Gender gender = Gender.valueOf(set.getString(4));
                double price = set.getBigDecimal(5).doubleValue();

                HashMap<Integer, Byte> history = getReportForEquipment(equipmentID);

                StringBuilder report = new StringBuilder(0);
                for (int currentID : history.keySet()) {
                    report.append(createReport(equipmentID, price, title, category, gender, history.get(currentID)));
                }
                result.add(report.toString());
            }

            return result;
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

    private HashMap<Integer, Byte> getReportForEquipment(int equipmentID) throws DAOException {
        String query = "SELECT id, is_active FROM user_to_equipment WHERE equipment=" + equipmentID;
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        HashMap<Integer, Byte> result = null;

        try {
            connection = DBConnector.getConnection();
            statement = connection.createStatement();

            set = statement.executeQuery(query);

            result = new HashMap<>(0);
            if (set.next()) {
                int id = set.getInt(1);
                byte isActive = set.getByte(2);
                result.put(id, isActive);
            }

            return result;
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

    private String createReport(int id, double price, String title, String category, Gender gender, byte status) {
        StringBuilder report = new StringBuilder(0);
        report.append("\tID: ");
        report.append(id);
        report.append("\tTitle: ");
        report.append(title);
        report.append(", price: ");
        report.append(price);
        report.append(", category: ");
        report.append(category);
        report.append(", gender: ");
        report.append(gender);
        report.append(", status: ");
        if (status == 1) {
            report.append("ACTIVE");
        } else {
            report.append("CLOSED");
        }
        report.append("\n");

        return report.toString();
    }
}
