package az.prompt.zoounn;

import az.prompt.zoounn.animals.Animal;
import az.prompt.zoounn.exceptions.BaseException;
import az.prompt.zoounn.model.Sensor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Zoo {
    void addCell(Cell cell) throws BaseException;
    void addCells(Collection<Cell> cells);
    List<List<String>> hearAnimals();
    void addAnimal(Animal animal, int cellNumber) throws BaseException;
    List<String> uniqueAnimalType();
    long countHerbivores();
    long countPredators();
    Set<Cell> getCells();
    List<Sensor> getSensors();
    void addSensor(Sensor sensor);
    void updateSensor(int id, Sensor sensor);
    void deleteSensor(int sensorId);
}
