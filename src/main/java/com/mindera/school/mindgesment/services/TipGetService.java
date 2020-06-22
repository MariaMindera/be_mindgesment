package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.http.models.PaginatedTips;

public interface TipGetService {

    PaginatedTips getAll(Integer page);
}
