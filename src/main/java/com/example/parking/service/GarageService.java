package com.example.parking.service;

import com.example.parking.api.controller.request.GarageRequest;
import com.example.parking.api.controller.response.GarageStatus;

import java.util.List;
import java.util.Map;

public interface GarageService {
    Map<String , List<String>> listGarageStatus();

    List<GarageStatus> leaveGarage(List<String> tickets);

    List<GarageStatus> garageSetup(List<GarageRequest> garageRequest);

}
