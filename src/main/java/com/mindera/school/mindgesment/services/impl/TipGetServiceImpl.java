package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.repositories.TipRepository;
import com.mindera.school.mindgesment.http.models.PaginatedTips;
import com.mindera.school.mindgesment.http.models.Tip;
import com.mindera.school.mindgesment.services.TipGetService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TipGetServiceImpl implements TipGetService {

    private final TipRepository repository;

    private final MapperFacade mapper;

    @Autowired
    public TipGetServiceImpl(TipRepository repository, MapperFacade mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PaginatedTips getAll(Integer page) {
        var paginatedTips = new PaginatedTips();
        paginatedTips.setList(
                repository.findAll(PageRequest.of(page, 10, Sort.by("date").descending()))
                        .get()
                        .map(tipEntity -> mapper.map(tipEntity, Tip.class))
                        .collect(Collectors.toList())
        );
        paginatedTips.setTotal(repository.count());
        return paginatedTips;
    }
}
