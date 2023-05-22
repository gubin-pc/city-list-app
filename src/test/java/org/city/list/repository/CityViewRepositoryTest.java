package org.city.list.repository;

import org.city.list.IntegrationTest;
import org.city.list.model.view.CityView;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityViewRepositoryTest extends IntegrationTest {

    @Autowired
    private CityViewRepository cityViewRepository;

    @ParameterizedTest
    @CsvSource({"Belgaum,1", "Ga,10", "Ma,33"})
    void shouldFindAllByNameFilterSuccess(
            String name,
            int size
    ) {
        //given
        PageRequest pageable = PageRequest.of(0, 100);

        //when
        Page<CityView> cities = cityViewRepository.findAll(pageable, name);

        //then
        assertEquals(size, cities.getContent().size());
        cities.forEach(cityView -> assertTrue(cityView.name().startsWith(name)));
    }
}