package com.hatal.ToDoListBackend.SaveToFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DbBackupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Run automatically on startup — restores from data-only.sql if it exists
     */
    @PostConstruct
    public void onStartup() {
        try {
            jdbcTemplate.execute("RUNSCRIPT FROM 'data-only.sql'");
            System.out.println("✅ Data restored from data-only.sql");
        } catch (Exception e) {
            System.out.println("⚠️ No backup file found. Starting fresh.");
        }
    }

    /**
     * Run automatically on shutdown — exports to data-only.sql
     */
    @PreDestroy
    public void exportDataOnly() throws Exception {
        String[] tables = {"users", "tasks", "users_tasks"};
        try (PrintWriter out = new PrintWriter(new FileWriter("data-only.sql"))) {
            for (String table : tables) {
                List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM " + table);
                for (Map<String, Object> row : rows) {
                    // Skip the first field
                    List<String> keys = new ArrayList<>(row.keySet()).subList(table.equals("users_tasks") ? 0 : 1, row.size());
                    List<Object> valuesList = new ArrayList<>(row.values()).subList(table.equals("users_tasks") ? 0 : 1, row.size());

                    StringBuilder sql = new StringBuilder("INSERT INTO " + table + " (");
                    sql.append(String.join(", ", keys));
                    sql.append(") VALUES (");

                    String values = valuesList.stream()
                            .map(val -> val == null ? "NULL" : "'" + val.toString().replace("'", "''") + "'")
                            .reduce((a, b) -> a + ", " + b).orElse("");

                    sql.append(values).append(");");
                    out.println(sql.toString());
                }
            }
        }
        System.out.println("✅ Data export complete: data-only.sql");
    }
}

