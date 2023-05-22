package org.city.list.model.request;

import org.apache.commons.lang3.StringUtils;

public record UpdateCityRequest(
        String name,
        String photo
) {
    public UpdateCityRequest {
        if (StringUtils.isBlank(name) && StringUtils.isBlank(photo)) {
            throw new IllegalArgumentException("'name' or 'photo' must specify");
        }
    }
}
