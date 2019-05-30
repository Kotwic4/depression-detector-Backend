package agh.edu.pl.depressiondetectorbackend.domain.measurement;

import agh.edu.pl.depressiondetectorbackend.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;

@Entity
@Data
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @NotNull
    public Date date;

    @Lob
    private HashMap<String, Object> value;

    public MeasurementData toData(){
        MeasurementData measurementData = new MeasurementData();
        measurementData.date = date;
        measurementData.value = value;
        return measurementData;
    }
}
