package com.example.parking.service.impl;

import com.example.parking.api.controller.request.GarageRequest;
import com.example.parking.api.controller.response.GarageStatus;
import com.example.parking.enums.CarType;
import com.example.parking.service.GarageService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GarageServiceImpl implements GarageService {
    private static final int TRUCK_LONG = 4;
    private static final int JEEP_LONG = 2;
    private static final int CAR_LONG = 1;

    private static final List<String> EXPORT_FILES_ACCOUNT_TYPES =
            Stream.of("R1", "R2", "R3", "RC", "RD", "RI").collect(Collectors.toList());
    public static final Map<Integer, String> garageList = new HashMap<Integer, String>() {
        {
            put(0, "Empty");
            put(1, "Empty");
            put(2, "Empty");
            put(3, "Empty");
            put(4, "Empty");
            put(5, "Empty");
            put(6, "Empty");
            put(7, "Empty");
            put(8, "Empty");
            put(9, "Empty");
        }
    };

    private int checkAvailable() {
        int available = 0;
        for (Map.Entry garageNode : garageList.entrySet()) {
            if (garageNode.getValue() == "Empty") {
                available++;
            }
        }
        return available;
    }

    private boolean checkEmpty(int index, int control) {
        int temp = 0;
        for (int x = 0; x <= control; x++) {
            if (garageList.get(index).equals("Empty")) {
                temp++;
                index++;
            }
        }

        return temp > control;
    }

    private int checkEmptyForCarType(int carSlot) {
        int empty = 0;
        for (Map.Entry garageNode : garageList.entrySet()) {
            if (garageNode.getValue() == "Empty") {
                empty = (int) garageNode.getKey();
                if (checkEmpty(empty, carSlot)) {
                    return empty;
                }
            }
        }
        return empty;
    }

    private void deleteCar(int line) {
        garageList.put(line, "Empty");
    }

    @Override
    public List<GarageStatus> garageSetup(List<GarageRequest> garageRequest) {
        List<GarageStatus> list = new ArrayList<>();
        for (GarageRequest garage : garageRequest) {
            list.add(setGarageCar(garage));
        }
        return list;
    }
    /*
     * This code find row place
     */
    public int getMaxConsecutiveNumber() {
        List<Integer> arr = new ArrayList<>();
        for (Map.Entry garageNode : garageList.entrySet()) {
            if (garageNode.getValue() == "Empty") {
                arr.add((int) garageNode.getKey());
            }
        }
        HashSet<Integer> SET = new HashSet<Integer>();
        for (int i = 0; i < checkAvailable(); i++) {
            SET.add(arr.get(i));
        }
        int output = 0;
        for (int i = 0; i < checkAvailable(); i++) {
            if (SET.contains(arr.get(i))) {
                int temp = arr.get(i);
                while (SET.contains(temp))
                    temp++;
                output = Math.max(output, temp - arr.get(i));
            }
        }
        return output;
    }

    @Override
    public List<GarageStatus> leaveGarage(List<String> tickets) {
        boolean flag = false;
        int reservedNumber = 0;
        List<GarageStatus> statusList = new ArrayList<>();
        GarageStatus garageStatus = new GarageStatus();
        for (String ticket : tickets) {
            for (Map.Entry garage : garageList.entrySet()) {
                if (ticket.equals(garage.getValue())) {
                    deleteCar((int) garage.getKey());
                    garageStatus.setMessage(ticket + " " + "leaving.");
                    flag = true;
                    reservedNumber = (int) garage.getKey() + 1;
                }
            }
            if (flag) {
                deleteCar(reservedNumber);
            }
            statusList.add(garageStatus);
        }
        return statusList;
    }

    @Override
    public Map<String, List<String>> listGarageStatus() {
        Map<String, List<String>> statusList = new HashMap<>();
        List<String> uniqueKeys = garageList.values().stream().distinct().filter(x -> !x.equals("Empty"))
                .filter(x -> !x.contains("Reserved")).collect(Collectors.toList());
        for (String key : uniqueKeys) {
            List<String> numbers = new ArrayList<>();
            for (Map.Entry garageNode : garageList.entrySet()) {
                if (key.equals(garageNode.getValue())) {
                    numbers.add(garageNode.getKey().toString());
                }
            }
            statusList.put(key, numbers);
        }
        return statusList;
    }

    private void setCarOnGarage(int slot, GarageRequest garageRequest, boolean reserved) {
        if (!reserved) {
            garageList.put(slot, garageRequest.getPlateNumber() + " " + garageRequest.getColor());
        } else {
            garageList.put(slot, garageRequest.getPlateNumber() + " " + garageRequest.getColor() + " " + "Reserved");
        }
    }

    private boolean checkTickets(String ticket) {
        for (Map.Entry garageNode : garageList.entrySet()) {
            if(garageNode.getValue().equals(ticket)) {
                return true;
            }
        }
        return false;
    }

    private GarageStatus setGarageCar(GarageRequest garageRequest) {
        if(checkTickets(garageRequest.getPlateNumber() + " " + garageRequest.getColor())) {
            return new GarageStatus(garageRequest.getPlateNumber() + " " + garageRequest.getColor() + " " + "this ticket already exist.");
        }
        if (garageRequest.getCarType().toString().equals(CarType.TRUCK.toString())) {
            if (getMaxConsecutiveNumber() <= TRUCK_LONG) {
                return new GarageStatus("Garage not empty.");
            } else {
                int truckSlot = checkEmptyForCarType(TRUCK_LONG);
                for (int x = 0; x < 4; x++) {
                    setCarOnGarage(truckSlot, garageRequest, false);
                    truckSlot++;
                }
                setCarOnGarage(truckSlot, garageRequest, true);
                return new GarageStatus(garageRequest.getPlateNumber() + " " + garageRequest.getColor() + " " + "parking.");
            }
        } else if (garageRequest.getCarType().toString().equals(CarType.JEEP.toString())) {
            if (getMaxConsecutiveNumber() <= JEEP_LONG) {
                return new GarageStatus("Garage not empty.");
            } else {
                int jeepSlot = checkEmptyForCarType(JEEP_LONG);
                for (int x = 0; x < 2; x++) {
                    setCarOnGarage(jeepSlot, garageRequest, false);
                    jeepSlot++;
                }
                setCarOnGarage(jeepSlot, garageRequest, true);
                return new GarageStatus(garageRequest.getPlateNumber() + " " + garageRequest.getColor() + " " + "parking.");
            }
        } else {
            if (getMaxConsecutiveNumber() <= CAR_LONG) {
                return new GarageStatus("Garage not empty.");
            } else {
                int carSlot = checkEmptyForCarType(CAR_LONG);
                for (int x = 0; x < 1; x++) {
                    setCarOnGarage(carSlot, garageRequest, false);
                    carSlot++;
                }
                setCarOnGarage(carSlot, garageRequest, true);
                return new GarageStatus(garageRequest.getPlateNumber() + " " + garageRequest.getColor() + " " + "parking.");
            }
        }
    }
}
