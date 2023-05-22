package org.city.list.service;

import jakarta.annotation.Nonnull;
import org.city.list.model.view.CityView;
import org.city.list.repository.CityViewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

@Service
public class CityViewService {

    private final CityViewRepository cityRepository;

    public CityViewService(CityViewRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<CityView> getCities(@Nonnull Pageable pageable, String name) {
        return cityRepository.findAll(pageable, defaultIfBlank(name, null));
    }
}
