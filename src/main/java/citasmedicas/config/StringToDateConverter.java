package citasmedicas.config;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {
    private final SimpleDateFormat dateFormat;

    public StringToDateConverter(String dateFormatPattern) {
        this.dateFormat = new SimpleDateFormat(dateFormatPattern);
    }

    @Override
    public Date convert(MappingContext<String, Date> context) {
        try {
            return dateFormat.parse(context.getSource());
        } catch (ParseException e) {
            return null;
        }
    }
}
