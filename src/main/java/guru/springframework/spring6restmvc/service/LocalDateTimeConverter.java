package guru.springframework.spring6restmvc.service;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    protected LocalDateTime convert(String value) {
        return LocalDateTime.parse(value, formatter);
    }
}
