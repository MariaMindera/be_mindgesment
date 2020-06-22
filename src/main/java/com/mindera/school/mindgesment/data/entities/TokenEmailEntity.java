package com.mindera.school.mindgesment.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * TokenEmailEntity class exists to save an email token.
 */
@Entity
@Table(name = "tokenEmail")
public class TokenEmailEntity extends KeyEntity {

    private String userId;

    private String newEmail;

    private LocalDateTime createdAt;

    public TokenEmailEntity() {
    }

    public TokenEmailEntity(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
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
        TokenEmailEntity that = (TokenEmailEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(newEmail, that.newEmail) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, newEmail, createdAt);
    }

    @Override
    public String toString() {
        return "TokenEmailEntity{" +
                "userId='" + userId + '\'' +
                ", newEmail='" + newEmail + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
