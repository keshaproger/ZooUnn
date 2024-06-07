package az.prompt.zoounn.repository;

import az.prompt.zoounn.model.CellJpa;
import az.prompt.zoounn.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorJpaRepository extends JpaRepository<Sensor, Integer> {

}
