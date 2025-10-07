package ru.otus.hw.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2000)) {
                return Health.up()
                        .withDetail("databaseConnect", "Successful connection to the database")
                        .build();
            } else {
                return Health.down()
                        .withDetail("databaseConnect", "Failed to connect to the database")
                        .build();
            }
        } catch (SQLException e) {
            return Health.down()
                    .withDetail("databaseConnect", "Error connecting to the database")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
