package citasmedicas.utils;

import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    @Named("stringToLocalDateTime")
    public LocalDateTime stringToLocalDateTime(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(fecha, formatter);
    }

    @Named("localDateTimeToString")
    public String localDateTimeToString(LocalDateTime fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return fecha.format(formatter);
    }

    @Named("stringToLocalDate")
    public LocalDate stringToLocalDate(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }

    @Named("localDateToString")
    public String localDateToString(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fecha.format(formatter);
    }
}
