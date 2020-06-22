package com.mindera.school.mindgesment.http.models;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction {

    private String id;

    @NotBlank
    @Size(max = 30)
    private String title;

    @Positive
    @NotNull
    @Digits(integer = 9, fraction = 2)
    private Double amount;

    @PastOrPresent
    @NotNull
    private LocalDate date;

    @NotNull
    private String category;

    @NotNull
    private String type;

    @Size(max = 120)
    private String description;

    public Transaction() {
    }

    public Transaction(@NotBlank @Size(max = 30) String title, @Positive @NotNull @Digits(integer = 9, fraction = 2) Double amount, @PastOrPresent @NotNull LocalDate date, @NotNull String category, @NotNull String type) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                getCategory().equals(that.getCategory()) &&
                getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getAmount(), getDate(), getDescription(), getCategory(), getType());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", type=" + type +
                '}';
    }
}
