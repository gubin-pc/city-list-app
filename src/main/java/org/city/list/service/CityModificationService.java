package org.city.list.service;

import org.city.list.model.domain.City;
import org.city.list.model.view.CityView;
import org.city.list.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class CityModificationService {

    private final CrudRepository<City> crudRepository;

    public CityModificationService(CrudRepository<City> crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Transactional
    public CityView update(Long id, String name, String photo) {
        validateURL(photo);
        City city = crudRepository.getById(id).withName(name).withPhoto(photo);
        City model = crudRepository.update(city);
        return CityView.fromDomain(model);
    }

    private void validateURL(String url) {
        if (url == null) return;
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Invalid photo url");
        }
    }

}
