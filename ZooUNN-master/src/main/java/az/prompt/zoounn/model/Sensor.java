package az.prompt.zoounn.model;

import az.prompt.zoounn.dto.enums.SensorType;
import az.prompt.zoounn.dto.enums.Units;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sensorName;
    private SensorType sensorType;
    private Units unit;
    private double value;
    private int cellId;

}
