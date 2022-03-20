package com.example.parking.api.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarageStatus {
    private String message;

    public GarageStatus(String message) {
        this.message = message;
    }

    public GarageStatus() {
    }
}
