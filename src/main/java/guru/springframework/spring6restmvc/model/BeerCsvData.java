package guru.springframework.spring6restmvc.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerCsvData {

    @CsvBindByName(column = "")
    private int row;

    @CsvBindByName(column = "count.x")
    private int countX;

    @CsvBindByName(column = "abv")
    private String abv;

    @CsvBindByName(column = "ibu")
    private String ibu;

    @CsvBindByName(column = "id")
    private int id;

    @CsvBindByName(column = "beer")
    private String beer;

    @CsvBindByName(column = "style")
    private String style;

    @CsvBindByName(column = "brewery_id")
    private int breweryId;

    @CsvBindByName(column = "ounces")
    private float ounces;

    @CsvBindByName(column = "style2")
    private String style2;

    @CsvBindByName(column = "count.y")
    private String countY;

    @CsvBindByName(column = "brewery")
    private String brewery;

    @CsvBindByName(column = "city")
    private String city;

    @CsvBindByName(column = "state")
    private String state;

    @CsvBindByName(column = "label")
    private String label;
}
