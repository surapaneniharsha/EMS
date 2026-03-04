package com.infinira.ems.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.io.IOException;
import java.text.MessageFormat;

public class DBUtil {

    private static volatile DBUtil instance;

    private static final String PROPERTIES_FILE_NAME = "ems.properties";

    private static final String DB_URL_PROP = "db.url";
    private static final String DB_USER_PROP = "db.userId";
    private static final String DB_PASSWORD_PROP = "db.password";

    private String url;
    private String user;
    private String password;

    private DBUtil() {
        loadDBConfiguration();
    }

    public static DBUtil getInstance() {
        if (instance == null) {
            synchronized (DBUtil.class) {
                if (instance == null) {
                    instance = new DBUtil();
                }
            }
        }
        return instance;
    }


    private void loadDBConfiguration() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            if (inputStream == null) {
                throw new RuntimeException(MessageFormat.format(EMS_001, PROPERTIES_FILE_NAME));
            }

            Properties dbProperties = new Properties();
            dbProperties.load(inputStream);

            url = validate(dbProperties.getProperty(DB_URL_PROP), DB_URL_PROP);
            user = validate(dbProperties.getProperty(DB_USER_PROP), DB_USER_PROP);
            password = validate(dbProperties.getProperty(DB_PASSWORD_PROP), DB_PASSWORD_PROP);

        } catch (IOException ioe) {
            throw new RuntimeException(MessageFormat.format(EMS_002, PROPERTIES_FILE_NAME));
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Throwable th) {
            throw new RuntimeException(MessageFormat.format(EMS_004, url, user));
        }
    }

    public static void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable th) {
                // throw new RuntimeException("Error closing ResultSet");
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Throwable th) {
                // throw new RuntimeException("Error closing PreparedStatement");
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (Throwable th) {
                //throw new RuntimeException("Error closing Connection");
                
            }
        }
    }

    private static String validate(String value, String propertyName) {
        if (value == null || value.isEmpty()) {
            throw new RuntimeException(MessageFormat.format(EMS_003, propertyName));
        }
        return value;
    }


    private static final String EMS_001 = "{0} file is not found in the classpath, please check.";
    private static final String EMS_002 = "Error loading database configuration: {0}";
    private static final String EMS_003 = "Invalid value for the Property: {0}";
    private static final String EMS_004 = "Failed to establish database connection. Please check your configuration: URL={0}, USER={1}";
}