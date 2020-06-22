package com.mindera.school.mindgesment.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * TokenPasswordEntity class exists to save a password token.
 */
@Entity
@Table(name = "tokenPassword")
public class TokenPasswordEntity extends KeyEntity {

    private String userId;

    private LocalDateTime createdAt;

    public TokenPasswordEntity() {
    }

    public TokenPasswordEntity(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (! super.equals(o)) return false;
        TokenPasswordEntity that = (TokenPasswordEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, createdAt);
    }

    @Override
    public String toString() {
        return "TokenPasswordEntity{" +
                "userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
