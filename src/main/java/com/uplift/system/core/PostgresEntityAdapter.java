package com.uplift.system.interop.adapters;

import com.uplift.system.interop.InteroperabilityManager.LanguageAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class PostgresEntityAdapter implements LanguageAdapter<PostgresEntity> {
    private final DataSource dataSource;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public PostgresEntityAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Map<String, Object> toNormalizedForm(PostgresEntity entity) {
        Map<String, Object> normalized = new HashMap<>();
        normalized.put("id", entity.getId());
        normalized.put("name", entity.getName());
        normalized.put("data", entity.getData());
        return normalized;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <R> R fromNormalizedForm(Map<String, Object> normalized) {
        PostgresEntity entity = new PostgresEntity();
        entity.setId(((Number)normalized.get("id")).longValue());
        entity.setName((String)normalized.get("name"));
        entity.setData((Map<String, Object>)normalized.get("data"));
        return (R)entity;
    }
    
    @Override
    public boolean validateEntity(PostgresEntity entity) {
        return entity != null && entity.getName() != null && !entity.getName().isEmpty();
    }
    
    @Override
    public Map<String, Object> getCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        capabilities.put("language", "postgres");
        capabilities.put("supportsTransactions", true);
        capabilities.put("supportsConcurrentAccess", true);
        return capabilities;
    }
    
    // Additional methods for database operations
    public PostgresEntity saveToDatabase(PostgresEntity entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO entities (name, data) VALUES (?, ?::jsonb) RETURNING id", 
                 PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, entity.getName());
            stmt.setString(2, objectMapper.writeValueAsString(entity.getData()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating entity failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating entity failed, no ID obtained.");
                }
            }
            
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error saving entity to database", e);
        }
    }
}