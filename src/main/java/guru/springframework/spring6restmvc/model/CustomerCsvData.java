package guru.springframework.spring6restmvc.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import guru.springframework.spring6restmvc.service.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCsvData {

    @CsvBindByName(column = "id")
    private UUID id;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "version")
    private Integer version;

    @CsvBindByName(column = "created_date")
    @CsvCustomBindByName(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdDate;

    @CsvBindByName(column = "update_date")
    @CsvCustomBindByName(converter = LocalDateTimeConverter.class)
    private LocalDateTime updateDate;
}
