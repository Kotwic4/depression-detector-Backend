package agh.edu.pl.depressiondetectorbackend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.HashMap;

public class MeasurementData {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date date;
    public HashMap<String, Object> value;
}
