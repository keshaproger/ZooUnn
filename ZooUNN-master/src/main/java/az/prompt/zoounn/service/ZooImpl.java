package az.prompt.zoounn.service;

import az.prompt.zoounn.Cell;
import az.prompt.zoounn.model.Sensor;
import az.prompt.zoounn.Zoo;
import az.prompt.zoounn.animals.Animal;
import az.prompt.zoounn.exceptions.BaseException;
import az.prompt.zoounn.repository.SensorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZooImpl implements Zoo {
    private Set<Cell> cells = new HashSet<>();
    private final SensorJpaRepository sensorJpaRepository;
    @Override
    public void addCell(Cell cell) throws BaseException {
        int cellNumber = cell.getNumber();
        Optional<Cell> cellWithSameNumber = cells.stream()
                .filter(c -> c.getNumber() == cellNumber)
                .findFirst();
        if (cellWithSameNumber.isPresent())
            throw new BaseException(String.format("Cell with %d already exists", cellNumber));
        cells.add(cell);
    }

    @Override
    public void addCells(Collection<Cell> cells) {
        this.cells = new HashSet<>(cells);
    }

    @Override
    public List<List<String>> hearAnimals() {
        return cells.stream()
                .map(Cell::hearAnimals)
                .collect(Collectors.toList());
    }

    @Override
    public long countHerbivores() {
        return cells.stream()
                .map(Cell::getAnimals)
                .flatMap(List::stream)
                .filter(animal -> !animal.isPredator())
                .count();
    }

    @Override
    public long countPredators() {
        return cells.stream()
                .map(Cell::getAnimals)
                .flatMap(List::stream)
                .filter(Animal::isPredator)
                .count();
    }

    @Override
    public List<String> uniqueAnimalType() {
        return cells.stream()
                .map(Cell::getAnimals)
                .flatMap(List::stream)
                .map(Animal::getAnimalType)
                .distinct()
                .collect(Collectors.toList());
    }
    @Autowired
    public ZooImpl(SensorJpaRepository sensorJpaRepository) {
        this.sensorJpaRepository = sensorJpaRepository;
    }

    @Override
    public void addAnimal(Animal animal, int cellNumber) throws BaseException {
        Cell cell = cells.stream()
                .filter(c -> c.getNumber() == cellNumber)
                .findFirst()
                .orElseThrow(() -> new BaseException(String.format("Cell with number %d not found", cellNumber)));

        cell.addAnimal(animal);
    }

    @Override
    public Set<Cell> getCells() {
        return cells;
    }

    @Override
    public List<Sensor> getSensors() {
        return sensorJpaRepository.findAll();
    }

    @Override
    public void addSensor(Sensor sensor) {
        sensorJpaRepository.save(sensor);
    }

    @Override
    public void updateSensor(int id, Sensor sensor) {
        Optional<Sensor> existingSensor = sensorJpaRepository.findById(id);
        if (existingSensor.isPresent()) {
            Sensor updatedSensor = existingSensor.get();
            updatedSensor.setSensorName(sensor.getSensorName());
            updatedSensor.setSensorType(sensor.getSensorType());
            updatedSensor.setUnit(sensor.getUnit());
            updatedSensor.setValue(sensor.getValue());
            updatedSensor.setCellId(sensor.getCellId());
            sensorJpaRepository.save(updatedSensor);
        } else {
            throw new RuntimeException("Sensor with ID " + id + " not found");
        }
    }
    @Override
    public void deleteSensor(int sensorId) {
        Optional<Sensor> sensorOptional = sensorJpaRepository.findById(sensorId);
        if (sensorOptional.isPresent()) {
            Sensor sensor = sensorOptional.get();
            sensorJpaRepository.delete(sensor);
        } else {
            throw new RuntimeException("Sensor with ID " + sensorId + " not found");
        }
    }

}
