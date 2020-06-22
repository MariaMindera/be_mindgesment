package com.mindera.school.mindgesment.http.models;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class EmailChange {

    @NotEmpty
    private String email;

    @NotEmpty
    private String token;

    public EmailChange() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailChange that = (EmailChange) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, token);
    }

    @Override
    public String toString() {
        return "PasswordReset{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
