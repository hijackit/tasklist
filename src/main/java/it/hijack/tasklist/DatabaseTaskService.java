package it.hijack.tasklist;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.sql.*;
import java.util.*;

@Singleton
public class DatabaseTaskService implements TaskService {

    @Inject
    ConnectionProvider connectionProvider;

    @Override
    public Collection<Task> getAll() {
        Collection<Task> tasks = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            String sql = """
                SELECT ID, TITLE, COMPLETED
                FROM TASKS 
                ORDER BY ID ASC
                """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                boolean completed = rs.getBoolean(3);
                Task task = new Task();
                task.setId(id);
                task.setTitle(title);
                task.setCompleted(completed);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }

        return tasks;
    }

    @Override
    public Optional<Task> get(int id) {
        try (Connection connection = connectionProvider.getConnection()) {
            String sql = """
                    SELECT TITLE, COMPLETED
                    FROM TASKS
                    WHERE ID = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title = rs.getString(1);
                boolean completed = rs.getBoolean(2);
                Task task = new Task();
                task.setId(id);
                task.setTitle(title);
                task.setCompleted(completed);
                return Optional.of(task);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }

    @Override
    public Task add(Task task) {
        try (Connection connection = connectionProvider.getConnection()) {
            String sql = """
                    INSERT INTO TASKS (TITLE) VALUES (?)
                    """;
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTitle());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            task.setId(id);
            return task;
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }

    @Override
    public Optional<Task> patch(Integer id, Task patch) {
        Optional<Task> optionalTask = get(id);
        if (optionalTask.isEmpty())
            return Optional.empty();

        Task task = optionalTask.get();
        if (patch.getTitle() != null)
            task.setTitle(patch.getTitle());

        if (patch.isCompleted() != null)
            task.setCompleted(patch.isCompleted());

        try (Connection connection = connectionProvider.getConnection()) {
            String sql = """
                    UPDATE TASKS SET TITLE = ?, COMPLETED = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, task.getTitle());
            ps.setBoolean(2, task.isCompleted());
            ps.executeUpdate();
            return Optional.of(task);
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = connectionProvider.getConnection()) {
            String sql = """
                    DELETE FROM TASKS WHERE ID = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int updatedRows = ps.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }
}

