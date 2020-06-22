package com.mindera.school.mindgesment.http.models;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class PasswordReset {

    @NotEmpty
    private String password;

    @NotEmpty
    private String token;

    public PasswordReset() {
    }

    public PasswordReset(@NotEmpty String password, @NotEmpty String token) {
        this.password = password;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        PasswordReset that = (PasswordReset) o;
        return Objects.equals(password, that.password) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, token);
    }

    @Override
    public String toString() {
        return "PasswordReset{" +
                "password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
