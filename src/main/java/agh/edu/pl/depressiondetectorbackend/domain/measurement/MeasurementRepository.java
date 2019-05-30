package agh.edu.pl.depressiondetectorbackend.domain.measurement;

import agh.edu.pl.depressiondetectorbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findAllByUser(User user);
}
