package org.city.list.repository;

import org.city.list.IntegrationTest;
import org.city.list.exception.NotFoundException;
import org.city.list.model.domain.City;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CityCrudRepositoryTest extends IntegrationTest {

    @Autowired
    private CrudRepository<City> cityCrudRepository;

    @Nested
    class ModificationOperations {

        @Test
        void shouldUpdateCitySuccess() {
            //given
            City city = new City(1L, "Tokyo" , "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/500px-Skyscrapers_of_Shinjuku_2009_January.jpg");
            City expected = new City(1L, "newName", "newPhoto");

            //when
            City actual = cityCrudRepository.update(expected);
            Optional<City> actualFromDB = cityCrudRepository.findById(1L);

            //then
            assertEquals(expected, actual);
            assertNotEquals(city, actual);
            assertNotNull(actualFromDB);
            assertTrue(actualFromDB.isPresent());
            assertEquals(expected, actualFromDB.get());
        }

        @Test
        void shouldThrowIfNotFoundCity() {
            //given
            City city = new City((long) Integer.MAX_VALUE, "newName", "newPhoto");

            //then
            Exception ex = assertThrows(
                    NotFoundException.class,
                    () -> cityCrudRepository.update(city)
            );
            assertEquals("Not found CITY with id '2147483647'", ex.getMessage());
        }

        @Test
        void shouldThrowIfModelIsNull() {
            //then
            Exception ex = assertThrows(
                    NullPointerException.class,
                    () -> cityCrudRepository.update(null)
            );
        }
    }

    @Nested
    class CrudReadOperations {
        @Test
        void shouldFindByIdSuccess() {
            //given
            City expected = new City(2L, "Jakarta","https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Jakarta_Pictures-1.jpg/327px-Jakarta_Pictures-1.jpg");

            //when
            Optional<City> actual = cityCrudRepository.findById(2L);

            //then
            assertTrue(actual.isPresent());
            assertEquals(expected, actual.get());
        }

        @Test
        void shouldReturnNullFindById() {
            //when
            Optional<City> actual = cityCrudRepository.findById((long) Integer.MAX_VALUE);

            //then
            assertFalse(actual.isPresent());
        }

        @Test
        void shouldFindAllSuccess() {
            //when
            List<City> all = cityCrudRepository.findAll();

            //then
            assertEquals(1000, all.size());
        }
    }

}