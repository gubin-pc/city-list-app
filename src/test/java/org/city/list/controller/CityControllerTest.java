package org.city.list.controller;

import org.city.list.IntegrationTest;
import org.city.list.model.domain.City;
import org.city.list.model.request.UpdateCityRequest;
import org.city.list.model.view.CityView;
import org.city.list.repository.CrudRepository;
import org.city.list.util.TestPage;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class CityControllerTest extends IntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CrudRepository<City> cityCrudRepository;

    @Nested
    class Get {
        @Test
        void shouldGetCitiesSuccess() {
            //given
            List<CityView> cityView = List.of(
                    new CityView(997L, "Belgaum", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Kittur_Chenamma.jpg/500px-Kittur_Chenamma.jpg")
            );

            //when
            Page<CityView> responseBody = webTestClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/cities")
                                    .queryParam("name", "Belgaum")
                                    .queryParam("page", 0)
                                    .queryParam("size", 100)
                                    .build()
                    )
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(new ParameterizedTypeReference<TestPage<CityView>>() {
                    })
                    .returnResult()
                    .getResponseBody();

            //then
            assertIterableEquals(cityView, responseBody.getContent());
        }

        @Test
        void shouldReturn400IfRequestWithNegatePageable() {
            //when
            webTestClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/cities")
                                    .queryParam("name", "Belgaum")
                                    .queryParam("page", 0)
                                    .queryParam("size", -100)
                                    .build()
                    )
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody()
                    .json("{\"error\":\"Page size must not be less than one\"}");
        }
    }

    @Nested
    class Update {
        @Test
        void shouldUpdateCitySuccess() {
            //given
            UpdateCityRequest updateCityRequest = new UpdateCityRequest("UpdateCityRequest", null);
            City cityBefore = cityCrudRepository.findById(1L).get();

            //when
            webTestClient.patch()
                    .uri("/cities/{id}", 1)
                    .contentType(APPLICATION_JSON)
                    .bodyValue(updateCityRequest)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .json("{\"id\":1,\"name\":\"UpdateCityRequest\",\"photo\":\"newPhoto\"}");

            //then
            City cityAfter = cityCrudRepository.findById(1L).get();
            assertNotEquals("UpdateCityRequest", cityBefore.name());
            assertNotEquals(cityBefore.name(), cityAfter.name());
            assertEquals(cityBefore.photo(), cityAfter.photo());
        }

        @Test
        void shouldThrowIfInvalidInput() {
            //given
            UpdateCityRequest updateCityRequest = new UpdateCityRequest("UpdateCityRequest", "invalid");
            City cityBefore = cityCrudRepository.findById(1L).get();

            //when
            webTestClient.patch()
                    .uri("/cities/{id}", 1)
                    .contentType(APPLICATION_JSON)
                    .bodyValue(updateCityRequest)
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody()
                    .json("{\"error\":\"Invalid photo url\"}");

            //then
            City cityAfter = cityCrudRepository.findById(1L).get();
            assertEquals(cityBefore, cityAfter);
        }

        @Test
        void shouldThrowIfNotFound() {
            //given
            UpdateCityRequest updateCityRequest = new UpdateCityRequest("UpdateCityRequest", null);

            //when
            webTestClient.patch()
                    .uri("/cities/{id}", Integer.MAX_VALUE)
                    .contentType(APPLICATION_JSON)
                    .bodyValue(updateCityRequest)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .json("{\"error\":\"Not found CITY with id '2147483647'\"}");

        }
    }
}