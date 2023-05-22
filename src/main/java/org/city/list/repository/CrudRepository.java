package org.city.list.repository;

import jakarta.annotation.Nonnull;
import org.city.list.exception.NotFoundException;
import org.city.list.model.domain.DomainModel;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends DomainModel<?>> {
    Optional<T> findById(@Nonnull Long id);
    T getById(@Nonnull Long id);
    T update(@Nonnull T model) throws NotFoundException;
    List<T> findAll();
}
