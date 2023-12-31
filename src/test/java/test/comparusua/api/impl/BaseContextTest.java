package test.comparusua.api.impl;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@ActiveProfiles({"test"})
public class BaseContextTest {

    public static final String POSTGRES_LATEST_VERSION = "postgres:latest";
    public static final int DEFAULT_POSTGRES_PORT = 5432;

    private static final String dbName1 = "data-base-1-test";
    private static final String table1 = "users";
    private static final String user1 = "testuser";
    private static final String pass1 = "testpass";

    private static final String dbName2 = "data-base-2-test";
    private static final String table2 = "user_table";
    private static final String user2 = "testuser";
    private static final String pass2 = "testpass";

    @Container
    private static final PostgreSQLContainer<?> postgresContainer1 = new PostgreSQLContainer<>(POSTGRES_LATEST_VERSION)
            .withDatabaseName(dbName1)
            .withUsername(user1)
            .withPassword(pass1)
            .withEnv("POSTGRES_PASSWORD", pass1)
            .withExposedPorts(DEFAULT_POSTGRES_PORT)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5433), new ExposedPort(DEFAULT_POSTGRES_PORT)))
            ));

    @Container
    private static final PostgreSQLContainer<?> postgresContainer2 = new PostgreSQLContainer<>(POSTGRES_LATEST_VERSION)
            .withDatabaseName(dbName2)
            .withUsername(user2)
            .withPassword(pass2)
            .withEnv("POSTGRES_PASSWORD", pass2)
            .withExposedPorts(DEFAULT_POSTGRES_PORT)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5434), new ExposedPort(DEFAULT_POSTGRES_PORT)))
            ));

    @BeforeAll
    static void setup() {
        postgresContainer1.start();
        postgresContainer2.start();
        initializeDatabase();
    }

    @AfterAll
    static void teardown() {
        postgresContainer1.stop();
        postgresContainer2.stop();
    }

    private static void initializeDatabase() {
        try {
            String url1 = postgresContainer1.getJdbcUrl();
            String url2 = postgresContainer2.getJdbcUrl();

            try (Connection connection1 = DriverManager.getConnection(url1, user1, pass1); Connection connection2 = DriverManager.getConnection(url2, user2, pass2)) {

                createTable(connection1, table1);
                createTable(connection2, table2);

                insertData(connection1, table1,
                        List.of("user_id", "login", "first_name", "last_name"),
                        List.of("id1", "userName1", "name1", "surname1")
                );
                insertData(connection2, table2,
                        List.of("ldap_login", "name", "surname"),
                        List.of("userName2", "name2", "surname2")
                );
            }
        } catch (SQLException e) {
            log.error("Error during initializing database", e);
        }
    }

    private static void createTable(Connection connection, String tableName) throws SQLException {
        String sqlTemplate = prepareSqlTemplateForCreatingTable(tableName);

        try (PreparedStatement statement = connection.prepareStatement(sqlTemplate)) {
            statement.executeUpdate();
            log.info("Successfully created table: {}", tableName);
        }
    }

    private static String prepareSqlTemplateForCreatingTable(String tableName) {
        String columns;

        if (table1.equals(tableName)) {
            columns = "user_id VARCHAR(255) PRIMARY KEY NOT NULL," +
                    "login VARCHAR(255) NOT NULL," +
                    "first_name VARCHAR(255) NOT NULL," +
                    "last_name VARCHAR(255) NOT NULL";
        } else if (table2.equals(tableName)) {
            columns = "ldap_login VARCHAR(255) PRIMARY KEY NOT NULL," +
                    "name VARCHAR(255) NOT NULL," +
                    "surname VARCHAR(255) NOT NULL";
        } else {
            throw new IllegalArgumentException("Unknown table name: " + tableName);
        }
        return String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, columns);
    }

    private static void insertData(Connection connection, String tableName, List<String> columnNames, List<String> values) throws SQLException {
        String sqlTemplate = prepareSqlTemplateForInsertingDataInTable(tableName, columnNames, values);

        try (PreparedStatement statement = connection.prepareStatement(sqlTemplate)) {
            for (int i = 0; i < values.size(); i++) {
                statement.setString(i + 1, values.get(i));
            }
            statement.executeUpdate();
            log.info("Successfully inserted data in table: {}", tableName);
        }
    }

    private static String prepareSqlTemplateForInsertingDataInTable(String tableName, List<String> columnNames, List<String> values) {
        StringBuilder sqlValues = new StringBuilder("VALUES (");
        StringBuilder columns = new StringBuilder("(");

        for (int i = 0; i < values.size(); i++) {
            if (i != values.size() - 1) {
                sqlValues.append("?, ");
                columns.append(columnNames.get(i)).append(", ");
            } else {
                sqlValues.append("?)");
                columns.append(columnNames.get(i)).append(")");
            }
        }
        return String.format("INSERT INTO %s %s %s", tableName, columns, sqlValues);
    }
}