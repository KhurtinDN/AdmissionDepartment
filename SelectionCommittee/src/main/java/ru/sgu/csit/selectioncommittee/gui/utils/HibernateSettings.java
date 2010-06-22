package ru.sgu.csit.selectioncommittee.gui.utils;

import ru.sgu.csit.selectioncommittee.factory.LocalSessionFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.showErrorMessage;

/**
 * Date: Jun 22, 2010
 * Time: 2:32:31 PM
 *
 * @author xx & hd
 */
public class HibernateSettings {
    private final String HIBERNATE_PROPERTIES = "hibernate.properties";
    private static HibernateSettings settings;
    private Properties dbProperties;

    private String databaseType = "PostgreSql";
    private String host = "localhost";
    private Integer port = 5432;
    private String databaseName = "SelectionCommittee";

    private boolean configured = false;
    private boolean login = false;

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

        String url = dbProperties.getProperty("hibernate.connection.url");
        if (url != null) {  // jdbc:postgresql://localhost:5432/SelectionCommittee
            Pattern pattern = Pattern.compile("jdbc:(\\w+)://([\\w\\.]+):(\\d+)/(\\w+)");
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                databaseType = matcher.group(1);
                host = matcher.group(2);
                port = new Integer(matcher.group(3));
                databaseName = matcher.group(4);
            } else {
                throw new WrongSettingsException("In " + HIBERNATE_PROPERTIES + " wrong url.");
            }
        } else {
            throw new WrongSettingsException("In " + HIBERNATE_PROPERTIES + " hibernate.connection.url not found.");
        }
    }

    public void saveSettings() {
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
        login = LocalSessionFactory.createNewSessionFactory(dbProperties);
        return login;
    }

    public boolean isLogin() {
        return login;
    }

    public boolean isConfigured() {
        return configured;
    }

    public void setUserNameAndPassword(String username, char[] password) {
        dbProperties.put("hibernate.connection.username", username);
        dbProperties.put("hibernate.connection.password", new String(password));
    }

    public void setConnectionUrl(String databaseType, String host, Integer port, String databaseName) {
        Database database = supportedDatabasesMap.get(databaseType);
        dbProperties.put("hibernate.connection.driver_class", database.getDriver());
        dbProperties.put("hibernate.connection.dialect", database.getDialect());
        
        String url = createDatabaseUrl(databaseType, host, port, databaseName);
        dbProperties.put("hibernate.connection.url", url);

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
