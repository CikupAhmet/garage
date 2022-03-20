package com.example.parking;

import com.example.parking.api.controller.request.GarageRequest;
import com.example.parking.api.controller.response.GarageStatus;
import com.example.parking.enums.CarType;
import com.example.parking.service.GarageService;
import com.example.parking.service.impl.GarageServiceImpl;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class GarageServiceTest {
    /**
     * Test should be start together
     * Because service use static list
     *
     *
     */

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Autowired
    private GarageService garageService;

    @Test
    public void deleteCar() {
        // Arrange
        garageService = new GarageServiceImpl();
        List<String> tickets = new ArrayList<>();
        tickets.add("35-XXX-4567 BLUE");

        // Act
        List<GarageStatus> response = garageService.leaveGarage(tickets);

        // Assert
        Assertions.assertEquals(response.get(0).getMessage(),
                "35-XXX-4567 BLUE leaving.");

    }

    @Test
    public void added() {
        // Arrange
        garageService = new GarageServiceImpl();

        List<GarageRequest> garageRequests = new ArrayList<>();
        garageRequests.add(prepareGarageRequest(CarType.JEEP,
                "RED", "35-AAH-1234"));
        garageRequests.add(prepareGarageRequest(CarType.TRUCK,
                "BLUE", "35-XXX-4567"));
        garageRequests.add(prepareGarageRequest(CarType.CAR,
                "BLACK", "35-AS-6283"));

        // Act
        List<GarageStatus> response = garageService.garageSetup(garageRequests);

        // Assert
        Assertions.assertEquals(response.get(0).getMessage(),
                "35-AAH-1234 RED parking.");
        Assertions.assertEquals(response.get(1).getMessage(),
                "35-XXX-4567 BLUE parking.");
        Assertions.assertEquals(response.get(2).getMessage(),
                "35-AS-6283 BLACK parking.");
    }

    @Test
    public void checkStatus() {
        // Arrange
        garageService = new GarageServiceImpl();

        // Act
        Map<String , List<String>> response = garageService.listGarageStatus();

        // Assert
        Assertions.assertEquals(response.size(), 3);

    }

    @Test
    public void deleteCarAfterStatus() {
        // Arrange
        garageService = new GarageServiceImpl();

        // Act
        Map<String , List<String>> response = garageService.listGarageStatus();

        // Assert
        Assertions.assertEquals(response.size(), 2);

    }

    @Test
    public void added2() {
        // Arrange
        garageService = new GarageServiceImpl();

        List<GarageRequest> garageRequests = new ArrayList<>();
        garageRequests.add(prepareGarageRequest(CarType.JEEP,
                "PURPLE", "35-YZZ-1234"));

        // Act
        List<GarageStatus> response = garageService.garageSetup(garageRequests);

        // Assert
        Assertions.assertEquals(response.get(0).getMessage(),
                "Garage not empty.");
    }

    @Test
    public void added3() {
        // Arrange
        garageService = new GarageServiceImpl();

        List<GarageRequest> garageRequests = new ArrayList<>();
        garageRequests.add(prepareGarageRequest(CarType.CAR,
                "BLACK", "35-AS-6283"));

        // Act
        List<GarageStatus> response = garageService.garageSetup(garageRequests);

        // Assert
        Assertions.assertEquals(response.get(0).getMessage(),
                "35-AS-6283 BLACK this ticket already exist.");
    }

    private
    GarageRequest prepareGarageRequest(CarType carType, String color, String plateNumber) {
        GarageRequest garageRequest = new GarageRequest();
        garageRequest.setCarType(carType);
        garageRequest.setColor(color);
        garageRequest.setPlateNumber(plateNumber);
        return garageRequest;
    }
}
