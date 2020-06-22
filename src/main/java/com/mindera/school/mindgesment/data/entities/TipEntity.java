package com.mindera.school.mindgesment.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

/**
 * TipEntity class exists to save a tip information.
 */
@Entity
@Table(name = "tips")
public class TipEntity extends KeyEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 120000)
    private String description;

    @Column(nullable = false, length = 500)
    private String imageUrl;

    @Column
    private LocalDate date = LocalDate.now();

    public TipEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (! super.equals(o)) return false;
        TipEntity tipEntity = (TipEntity) o;
        return Objects.equals(title, tipEntity.title) &&
                Objects.equals(description, tipEntity.description) &&
                Objects.equals(imageUrl, tipEntity.imageUrl) &&
                Objects.equals(date, tipEntity.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, description, imageUrl, date);
    }

    @Override
    public String toString() {
        return "TipEntity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date=" + date +
                '}';
    }
}
