package ru.sgu.csit.admissiondepartment.system;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showErrorMessage;

/**
 * Date: Jun 22, 2010
 * Time: 2:32:31 PM
 *
 * @author xx & hd
 */
@Component
public class DatabaseSettings {

    public static final String MY_SQL = "MySQL";
    public static final String POSTGRE_SQL = "PostgreSQL";

    private static final String HIBERNATE_PROPERTIES = "hibernate.properties";

    private static final String KEY_DRIVER = "hibernate.connection.driver_class";
    private static final String KEY_DIALECT = "hibernate.connection.dialect";
    private static final String KEY_URL = "hibernate.connection.url";

    private static Map<String, Database> supportedDatabasesMap = ImmutableSortedMap.of(
            MY_SQL, new Database("com.mysql.jdbc.Driver", "org.hibernate.dialect.MySQL5InnoDBDialect"),
            POSTGRE_SQL, new Database("org.postgresql.Driver", "org.hibernate.dialect.PostgreSQLDialect")
    );

    private Database database = supportedDatabasesMap.get(POSTGRE_SQL);
    private boolean configured = false;

    private String databaseType = POSTGRE_SQL;
    private String host = "localhost";
    private Integer port = 5432;
    private String databaseName = "AdmissionDepartment";

    @Autowired
    private BasicDataSource dataSource;

    public DatabaseSettings() {
        try {
            loadSettings();
        } catch (IOException e) {
            // settings not found
        } catch (WrongSettingsException e) {
            showErrorMessage("При загрузке настроек произошла ошибка: " + e.getMessage());
            System.err.println(e);
        }
    }

    public void loadSettings() throws IOException, WrongSettingsException {
        Properties dbProperties = new Properties();
        dbProperties.load(new FileInputStream(HIBERNATE_PROPERTIES));

        String url = dbProperties.getProperty(KEY_URL);

        if (Strings.isNullOrEmpty(url)) {
            throw new WrongSettingsException("In " + HIBERNATE_PROPERTIES + "  " + KEY_URL + " not found.");
        }

        Pattern urlPattern = Pattern.compile("jdbc:(\\w+)://([\\w\\.]+):(\\d+)/(\\w+)");
        Matcher matcher = urlPattern.matcher(url);

        if (matcher.matches()) {
            setConnectionUrl(matcher.group(1), matcher.group(2), Integer.valueOf(matcher.group(3)), matcher.group(4));
        } else {
            throw new WrongSettingsException("In " + HIBERNATE_PROPERTIES + " wrong " + KEY_URL + ".");
        }

        String driver = dbProperties.getProperty(KEY_DRIVER);

        if (Strings.isNullOrEmpty(driver)) {
            throw new WrongSettingsException("In " + HIBERNATE_PROPERTIES + "  " + KEY_DRIVER + " not found.");
        }

        String dialect = dbProperties.getProperty(KEY_DIALECT);

        if (Strings.isNullOrEmpty(dialect)) {
            throw new WrongSettingsException("In " + HIBERNATE_PROPERTIES + "  " + KEY_DIALECT + " not found.");
        }

        database = new Database(driver, dialect);
    }

    public void saveSettings() {
        Properties dbProperties = new Properties();
        dbProperties.setProperty(KEY_URL, makeDbUrlString(databaseType, host, port, databaseName));
        dbProperties.setProperty(KEY_DRIVER, database.driver);
        dbProperties.setProperty(KEY_DIALECT, database.dialect);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(HIBERNATE_PROPERTIES);
            try {
                dbProperties.store(fileOutputStream, "Hibernate settings");
            } finally {
                fileOutputStream.close();
            }
        } catch (IOException e) {
            showErrorMessage("При сохранении настроек в файл " + HIBERNATE_PROPERTIES + " возникли проблемы.");
            e.printStackTrace();
        }
    }

    public boolean tryLogin(String username, char[] password) {
        dataSource.setUsername(username);
        dataSource.setPassword(String.valueOf(password));
        dataSource.setUrl(makeDbUrlString(databaseType, host, port, databaseName));

        try {
            Connection connection = dataSource.getConnection();
            connection.close();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    public List<String> getSupportedDatabaseList() {
        return Lists.newArrayList(supportedDatabasesMap.keySet());
    }

    public void setConnectionUrl(String databaseType, String host, Integer port, String databaseName) {
        for (String dbType : supportedDatabasesMap.keySet()) {
            if (dbType.equalsIgnoreCase(databaseType)) {
                this.databaseType = dbType;
                database = supportedDatabasesMap.get(dbType);
                break;
            }
        }

        this.host = host;
        this.port = port;
        this.databaseName = databaseName;

        configured = true;
    }

    public boolean isConfigured() {
        return configured;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    private String makeDbUrlString(String selectedDatabaseType, String host, Integer port, String name) {
        return "jdbc:" + selectedDatabaseType.toLowerCase() + "://" + host + ":" + port + "/" + name;
    }

    private static class Database {
        private String driver;
        private String dialect;

        private Database(String driver, String dialect) {
            this.driver = driver;
            this.dialect = dialect;
        }
    }
}
