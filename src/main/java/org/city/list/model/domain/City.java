package org.city.list.model.domain;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

import static java.util.Objects.requireNonNull;

public record City(
        Long id,
        @Nonnull String name,
        @Nonnull String photo
) implements DomainModel<Long> {
    public City {
        requireNonNull(name);
        requireNonNull(photo);
    }

    @Override
    public Long id() {
        return id;
    }

    public City copy(Long id, String name, String photo) {
        return new City(
                id == null ? this.id : id,
                name == null ? this.name : name,
                photo == null ? this.photo : photo
        );
    }

    public City withName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return copy(null, name, null);
        }
        return this;
    }

    public City withPhoto(String photo) {
        if (StringUtils.isNotBlank(name)) {
            return copy(null, null, photo);
        }
        return this;
    }
}

