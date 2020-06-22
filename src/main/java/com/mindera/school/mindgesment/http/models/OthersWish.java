package com.mindera.school.mindgesment.http.models;

import java.util.List;
import java.util.Objects;

public class OthersWish {

    private Wish wish;

    private List<Wish> othersWishes;

    public OthersWish() {
    }

    public Wish getWish() {
        return wish;
    }

    public void setWish(Wish wish) {
        this.wish = wish;
    }

    public List<Wish> getOthersWishes() {
        return othersWishes;
    }

    public void setOthersWishes(List<Wish> othersWishes) {
        this.othersWishes = othersWishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OthersWish that = (OthersWish) o;
        return Objects.equals(wish, that.wish) &&
                Objects.equals(othersWishes, that.othersWishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wish, othersWishes);
    }

    @Override
    public String toString() {
        return "OthersWish{" +
                "wish=" + wish +
                ", othersWishes=" + othersWishes +
                '}';
    }
}
