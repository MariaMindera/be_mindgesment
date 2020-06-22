package com.mindera.school.mindgesment.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * TransactionEntity class exists to save a transaction information.
 */
@Entity
@Table(name = "transactions", indexes = {@Index(name = "createdIn", columnList = "createdIn")})
public class TransactionEntity extends KeyEntity {

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, scale = 2)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 120)
    private String description;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CategoryEntity category;

    @Column(nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private TransactionTypeEntity type;

    @Column(nullable = false)
    private Instant createdIn = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    private WishEntity wish;

    public TransactionEntity() {
    }

    public TransactionEntity(String title, Double amount, LocalDate date, CategoryEntity category, TransactionTypeEntity type, UserEntity user) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.type = type;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public TransactionTypeEntity getType() {
        return type;
    }

    public void setType(TransactionTypeEntity type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Instant getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(Instant createdIn) {
        this.createdIn = createdIn;
    }

    public WishEntity getWish() {
        return wish;
    }

    public void setWish(WishEntity wish) {
        this.wish = wish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (! super.equals(o)) return false;
        TransactionEntity that = (TransactionEntity) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description) &&
                category == that.category &&
                type == that.type &&
                Objects.equals(createdIn, that.createdIn) &&
                Objects.equals(user, that.user) &&
                Objects.equals(wish, that.wish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTitle(), getAmount(), getDate(), getDescription(), getCategory(), getType(), getCreatedIn());
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "title='" + title + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", type=" + type +
                '}';
    }
}
