package org.city.list.controller;

import org.city.list.model.request.UpdateCityRequest;
import org.city.list.model.view.CityView;
import org.city.list.service.CityModificationService;
import org.city.list.service.CityViewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        path = "/cities",
        produces = APPLICATION_JSON_VALUE
)
@CrossOrigin(origins = "http://localhost:3000")
public class CityController {

    private final CityViewService cityViewService;
    private final CityModificationService cityModificationService;

    public CityController(CityViewService cityViewService,
                          CityModificationService cityModificationService) {
        this.cityViewService = cityViewService;
        this.cityModificationService = cityModificationService;
    }

    @GetMapping()
    public Page<CityView> getCities(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return cityViewService.getCities(PageRequest.of(page, size), name);
    }

    @PatchMapping(
            path = "/{id}",
            consumes = APPLICATION_JSON_VALUE
    )
    public CityView updateCity(
            @PathVariable Long id,
            @RequestBody UpdateCityRequest request
    ) {
        requireNonNull(request);
        return cityModificationService.update(id, request.name(), request.photo());
    }

}
