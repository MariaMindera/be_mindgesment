package com.mindera.school.mindgesment.data.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * KeyEntity class exists to be extended by other class.
 * <p>
 * This project uses a String id system for the database and
 * this class generates this id to be implemented by other classes.
 */
@MappedSuperclass
public abstract class KeyEntity {

    @Id
    @Column(name = "id", unique = true, length = 50)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    public KeyEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyEntity)) return false;
        KeyEntity keyEntity = (KeyEntity) o;
        return Objects.equals(id, keyEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "KeyEntity{" +
                "id='" + id + '\'' +
                '}';
    }
}
