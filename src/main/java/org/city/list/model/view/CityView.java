package org.city.list.model.view;

import jakarta.annotation.Nonnull;
import org.city.list.model.domain.City;
import org.city.list.model.domain.DomainModel;

import static java.util.Objects.requireNonNull;

public record CityView(
        @Nonnull Long id,
        @Nonnull String name,
        @Nonnull String photo
) implements DomainModel<Long> {

    public CityView {
        requireNonNull(id);
        requireNonNull(name);
        requireNonNull(photo);
    }

    @Override
    public Long id() {
        return id;
    }

    public static CityView fromDomain(City model) {
        return new CityView(model.id(), model.name(), model.photo());
    }
}

