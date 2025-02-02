package it.pliot.equipment.controller;

public class EquipmentNotFoundException extends RuntimeException{

    EquipmentNotFoundException(String id) {
        super("Could not find equipment " + id);
    }
}
