package com.example.parking.api.controller;

import com.example.parking.api.controller.request.GarageRequest;
import com.example.parking.api.controller.response.GarageStatus;
import com.example.parking.service.GarageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/garage")
public class GarageApiController {
    private final GarageService garageService;

    public GarageApiController(GarageService garageService) {
        this.garageService = garageService;
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<GarageStatus> leave(@RequestBody List<String> list) {
        return garageService.leaveGarage(list);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<GarageStatus> park(@RequestBody List<GarageRequest> garageRequest) {
        return garageService.garageSetup(garageRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Map<String , List<String>> status() {
        return garageService.listGarageStatus();
    }
}
