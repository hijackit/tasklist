package it.hijack.tasklist;

import io.micronaut.runtime.Micronaut;
import org.flywaydb.core.Flyway;

public class Application {

    public static void main(String[] args) {

        Flyway.configure()
                .dataSource("jdbc:h2:file:./tasks", "SA", null)
                .load()
                .migrate();

        Micronaut.run(Application.class, args);
    }
}
