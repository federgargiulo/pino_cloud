package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.io.SensorIO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Sensor;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.repository.SensorRepository;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.business.api.EquipmentService;
import it.pliot.equipment.service.business.util.ConvertUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class EquipmentServiceImpl implements EquipmentService {

}
