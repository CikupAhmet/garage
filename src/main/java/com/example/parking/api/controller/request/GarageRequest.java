package com.example.parking.api.controller.request;


import com.example.parking.enums.CarType;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class GarageRequest {

    @NotNull
    private CarType carType;
    @NotNull
    private String color;
    @NotNull
    private String plateNumber;
}
