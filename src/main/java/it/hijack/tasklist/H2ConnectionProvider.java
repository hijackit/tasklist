package it.hijack.tasklist;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class H2ConnectionProvider implements ConnectionProvider {

    @Value("${h2.url}")
    String url;

    public H2ConnectionProvider() {

    }

    public H2ConnectionProvider(String url) {
        this.url = url;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, "sa", null);
    }
}
