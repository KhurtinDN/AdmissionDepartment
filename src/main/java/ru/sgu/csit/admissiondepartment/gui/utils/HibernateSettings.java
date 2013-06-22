package ru.sgu.csit.admissiondepartment.gui.utils;

import ru.sgu.csit.admissiondepartment.factory.LocalSessionFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class HibernateSettings {
    private final String HIBERNATE_PROPERTIES = "hibernate.properties";

    private final String KEY_USERNAME = "hibernate.connection.username";
    private final String KEY_PASSWORD = "hibernate.connection.password";
    private final String KEY_DRIVER = "hibernate.connection.driver_class";
    private final String KEY_DIALECT = "hibernate.connection.dialect";
    private final String KEY_URL = "hibernate.connection.url";

    private static HibernateSettings settings;
    private Properties dbProperties;

    private String databaseType = "PostgreSql";
    private String host = "localhost";
    private Integer port = 5432;
    private String databaseName = "SelectionCommittee";

    private boolean configured = false;

    private Map<String, Database> supportedDatabasesMap = createSupportedDatabasesMap();

    private HibernateSettings() {
        try {
            loadSettings();
            configured = true;
        } catch (IOException e) {
            // settings not found
        } catch (WrongSettingsException e) {
            showErrorMessage("При загрузке настроек произошла ошибка: " + e.getMessage());
            System.err.println(e);
        }
    }

    public void loadSettings() throws IOException, WrongSettingsException {
        dbProperties = new Properties();
        dbProperties.load(new FileInputStream(HIBERNATE_PROPERTIES));

        String url = dbProperties.getProperty(KEY_URL);
        if (url != null) {  // jdbc:postgresql://localhost:5432/SelectionCommittee
            Pattern pattern = Pattern.compile("jdbc:(\\w+)://([\\w\\.]+):(\\d+)/(\\w+)");
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                databaseType = matcher.group(1);
                host = matcher.group(2);
                port = new Integer(matcher.group(3));
                databaseName = matcher.group(4);
            } else {
                throw new WrongSettingsException("In " + HIBERNATE_PROPERTIES + " wrong " + KEY_URL + ".");
            }
        } else {
            throw new WrongSettingsException("In " + HIBERNATE_PROPERTIES + "  " + KEY_URL + " not found.");
        }
    }

    public void saveSettings() {
        dbProperties.remove(KEY_USERNAME);
        dbProperties.remove(KEY_PASSWORD);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(HIBERNATE_PROPERTIES);
            dbProperties.store(fileOutputStream, "Hibernate settings");
            fileOutputStream.close();
        } catch (IOException e) {
            showErrorMessage("При создании файла настроек " + HIBERNATE_PROPERTIES + " возникли проблемы.");
            System.err.println(e);
        }
    }

    public static HibernateSettings getSettings() {
        if (settings == null) {
            settings = new HibernateSettings();
        }
        return settings;
    }

    public boolean tryLogin() {
        return LocalSessionFactory.createNewSessionFactory(dbProperties);
    }

    public boolean isConfigured() {
        return configured;
    }

    public void setUserNameAndPassword(String username, char[] password) {
        dbProperties.put(KEY_USERNAME, username);
        dbProperties.put(KEY_PASSWORD, new String(password));
    }

    public void setConnectionUrl(String databaseType, String host, Integer port, String databaseName) {
        Database database = supportedDatabasesMap.get(databaseType);
        dbProperties.put(KEY_DRIVER, database.getDriver());
        dbProperties.put(KEY_DIALECT, database.getDialect());
        
        String url = createDatabaseUrl(databaseType, host, port, databaseName);
        dbProperties.put(KEY_URL, url);

        this.databaseType = databaseType;
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;

        configured = true;
    }

    private String createDatabaseUrl(String selectedDatabaseType, String host, Integer port, String name) {
        StringBuilder urlStringBuilder = new StringBuilder("jdbc:");
        urlStringBuilder.append(selectedDatabaseType.toLowerCase()).append("://");
        urlStringBuilder.append(host).append(":");
        urlStringBuilder.append(port).append("/");
        urlStringBuilder.append(name);
        return urlStringBuilder.toString();
    }

    private Map<String, Database> createSupportedDatabasesMap() {
        Map<String, Database> supportedDatabasesMap = new HashMap<String, Database>();

        supportedDatabasesMap.put("MySql",
                new Database("com.mysql.jdbc.Driver", "org.hibernate.dialect.MySQL5InnoDBDialect") );

        supportedDatabasesMap.put("PostgreSql",
                new Database("org.postgresql.Driver", "org.hibernate.dialect.PostgreSQLDialect") );

        return supportedDatabasesMap;
    }

    public List<String> getSupportedDatabaseList() {
        return new ArrayList<String>(supportedDatabasesMap.keySet());
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

    private class Database {
        private String driver;
        private String dialect;

        private Database(String driver, String dialect) {
            this.driver = driver;
            this.dialect = dialect;
        }

        public String getDriver() {
            return driver;
        }

        public String getDialect() {
            return dialect;
        }
    }
}
