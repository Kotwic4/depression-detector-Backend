package agh.edu.pl.depressiondetectorbackend.domain.measurement;

import agh.edu.pl.depressiondetectorbackend.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/measurement")
public class MeasurementController {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementController(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @PostMapping
    @Transactional
    public void sendMeasurement(@Valid @RequestBody MeasurementData measurementData,
                                Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Measurement measurement = new Measurement();
        measurement.setDate(measurementData.date);
        measurement.setValue(measurementData.value);
        measurement.setUser(user);
        measurementRepository.save(measurement);
    }

    @GetMapping
    @Transactional
    public List<MeasurementData> getMeasurements(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return measurementRepository.findAllByUser(user).stream().map(Measurement::toData).collect(Collectors.toList());
    }
}
