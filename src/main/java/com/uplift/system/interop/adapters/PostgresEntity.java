package com.uplift.system.interop.adapters;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * PostgresEntity:
 * A simple entity representing a record in Postgres. This class is used by
 * the PostgresEntityAdapter to normalize and recreate entity objects.
 *
 * Key properties:
 *   - id: Unique identifier (using Long for auto-generated IDs)
 *   - name: Entity name
 *   - data: A map holding additional arbitrary data
 */
public class PostgresEntity {
    private Long id;
    private String name;
    private Map<String, Object> data;

    /**
     * Default constructor: Initializes the entity with an empty data map.
     */
    public PostgresEntity() {
        this.data = new HashMap<>();
    }

    /**
     * Parameterized constructor: Creates an entity with the given id, name, and data map.
     *
     * @param id    the unique identifier
     * @param name  the entity's name
     * @param data  a map of additional entity data (if null, an empty map is used)
     */
    public PostgresEntity(Long id, String name, Map<String, Object> data) {
        this.id = id;
        this.name = name;
        this.data = (data != null) ? data : new HashMap<>();
    }

    // Getters and setters

    /**
     * Gets the entity ID.
     *
     * @return Long id of the entity
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the entity ID.
     *
     * @param id Long id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the entity name.
     *
     * @return String name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the entity name.
     *
     * @param name String name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the additional data map.
     *
     * @return Map<String, Object> containing additional data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Sets the additional data map.
     *
     * @param data Map<String, Object> to set as the entity's data
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Provides a string representation of the PostgresEntity.
     *
     * @return String representation of the entity
     */
    @Override
    public String toString() {
        return "PostgresEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * Evaluates equality based on id, name, and data.
     *
     * @param o the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostgresEntity)) return false;
        PostgresEntity that = (PostgresEntity) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(name, that.name) &&
               Objects.equals(data, that.data);
    }

    /**
     * Generates a hash code consistent with equals.
     *
     * @return int hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, data);
    }
} 