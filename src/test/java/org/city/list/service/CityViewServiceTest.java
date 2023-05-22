package org.city.list.service;

import org.city.list.IntegrationTest;
import org.city.list.model.view.CityView;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CityViewServiceTest extends IntegrationTest {

    @Autowired
    private CityViewService cityViewService;

    @ParameterizedTest
    @CsvSource({"Belgaum,1", "Ga,10", "Ma,33"})
    void shouldFindAllByNameFilterSuccess(
            String name,
            int size
    ) {
        //given
        PageRequest pageable = PageRequest.of(0, 100);

        //when
        Page<CityView> cities = cityViewService.getCities(pageable, name);

        //then
        assertEquals(size, cities.getContent().size());
        cities.forEach(cityView -> {
            assertTrue(cityView.name().startsWith(name));
        });
    }

}