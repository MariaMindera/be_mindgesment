package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.http.models.Wish;

import java.util.List;
import java.util.Map;

public interface WishOthersService {

    Map<String, Object> getProposalAmount(String username);

    void addWish(String username, Wish newWish, List<Wish> othersWishes);
}
