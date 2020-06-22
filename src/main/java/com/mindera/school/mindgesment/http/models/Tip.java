package com.mindera.school.mindgesment.http.models;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;

public class Tip {

    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    private LocalDate date;

    public Tip() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        Tip tip = (Tip) o;
        return Objects.equals(id, tip.id) &&
                Objects.equals(title, tip.title) &&
                Objects.equals(description, tip.description) &&
                Objects.equals(imageUrl, tip.imageUrl) &&
                Objects.equals(date, tip.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, imageUrl, date);
    }

    @Override
    public String toString() {
        return "Tip{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date=" + date +
                '}';
    }
}
