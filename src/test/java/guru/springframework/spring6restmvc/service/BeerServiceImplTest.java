package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerCsvData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeerServiceImplTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void ConvertCSV() throws FileNotFoundException {
        File file = new File("src/main/resources/csvdata/beers.csv");
        List<BeerCsvData> recs = beerCsvService.ConvertCSV(file);

        System.out.println(recs.size());

        assertThat(recs.size()).isGreaterThan(0);

    }
}