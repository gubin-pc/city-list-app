package org.city.list.service;

import org.city.list.IntegrationTest;
import org.city.list.model.domain.City;
import org.city.list.model.view.CityView;
import org.city.list.repository.CrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CityModificationServiceTest extends IntegrationTest {

    @Autowired
    private CityModificationService cityModificationService;

    @Autowired
    private CrudRepository<City> cityCrudRepository;

    @Test
    void shouldUpdateModel() {
        //given
        CityView original = cityCrudRepository.findById(1L).map(CityView::fromDomain).get();
        CityView expected = new CityView(1L, "cityModificationService", "http://test.example");

        //when
        CityView actual = cityModificationService.update(expected.id(), expected.name(), expected.photo());
        CityView actualFromDB = cityCrudRepository.findById(1L).map(CityView::fromDomain).get();

        //then
        assertEquals(expected, actual);
        assertNotEquals(original, actual);
        assertNotNull(actualFromDB);
        assertEquals(expected, actualFromDB);
    }

    @Test
    void shouldThrowIfUrlInvalid() {
        //given
        CityView expected = new CityView(1L, "cityModificationService", "invalid");

        //then
        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> cityModificationService.update(expected.id(), expected.name(), expected.photo())
        );

        assertEquals("Invalid photo url", ex.getMessage());
    }
}