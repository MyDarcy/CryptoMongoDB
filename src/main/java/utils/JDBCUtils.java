package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Author by darcy
 * Date on 17-4-22 下午2:32.
 * Description:
 */
public class JDBCUtils {

    private static final String jdbcConfig = "jdbcConfig.properties";
    private static Properties properties = new Properties();

    static {
        try {
            InputStream stream = Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(jdbcConfig);
            properties.load(stream);
            Class.forName(properties.getProperty("driverClassName"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getProperty("jdbcUrl"),
                    properties.getProperty("user"), properties.getProperty("passwd"));
        } catch (SQLException e) {
            throw new RuntimeException("can not get connection");
        }
    }

}
