package lk.ijse.pos.util;

import lk.ijse.pos.listner.ConnectionPoolManager;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    private BasicDataSource pool;

    public CrudUtil() {
        this.pool = ConnectionPoolManager.getInstance().getDataSource();
        if (pool == null) {
            throw new RuntimeException("Connection pool is null. Check your ConnectionPoolManager setup.");
        }
    }

    public <T> T execute(String sql, Object... args) throws SQLException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            if (sql.trim().startsWith("SELECT", 0) || sql.trim().startsWith("select", 0)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                return (T) resultSet;
            } else {
                int affectedRows = preparedStatement.executeUpdate();
                return (T) (Boolean) (affectedRows > 0);
            }
        } catch (SQLException e) {
            throw new SQLException("Error executing SQL: " + sql, e);
        }
    }
}
