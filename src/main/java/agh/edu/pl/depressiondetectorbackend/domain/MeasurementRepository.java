package agh.edu.pl.depressiondetectorbackend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findAllByUser(User user);
}
