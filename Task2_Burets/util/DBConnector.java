package by.training.equipment_store.util;

import by.training.equipment_store.util.exception.UtilException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnector {
    private static Logger log = Logger.getLogger(DBConnector.class.getName());
    private static final String url = "jdbc:mysql://localhost:3306/equipment_store?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "123456";

    public static Connection getConnection() throws UtilException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException sqlEx) {
                log.severe(sqlEx.getMessage());
                throw new UtilException(sqlEx.getMessage());
            }
        } catch (ClassNotFoundException e) {
            log.severe(e.getMessage());
            throw new UtilException(e.getMessage());
        }
    }

    public static void closeConnection(Connection connection) throws UtilException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.severe(e.getMessage());
            throw new UtilException(e.getMessage());
        }
    }

}
