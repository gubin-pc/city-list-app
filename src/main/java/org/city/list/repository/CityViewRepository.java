package org.city.list.repository;

import jakarta.annotation.Nonnull;
import org.city.list.model.view.CityView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityViewRepository {
    Page<CityView> findAll(@Nonnull Pageable pageable, String name);
}
