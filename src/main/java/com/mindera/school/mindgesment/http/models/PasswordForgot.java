package com.mindera.school.mindgesment.http.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class PasswordForgot {

    @Email
    @NotBlank
    private String email;

    public PasswordForgot() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordForgot that = (PasswordForgot) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "PasswordForgot{" +
                "email='" + email + '\'' +
                '}';
    }
}
