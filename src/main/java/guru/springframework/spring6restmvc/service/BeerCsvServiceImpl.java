package guru.springframework.spring6restmvc.service;

import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring6restmvc.model.BeerCsvData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
@Service
public class BeerCsvServiceImpl implements BeerCsvService {

    @Override
    public List<BeerCsvData> ConvertCSV(File csvFile) {
        try (FileReader fileReader = new FileReader(csvFile)) {
            return new CsvToBeanBuilder<BeerCsvData>(fileReader)
                    .withType(BeerCsvData.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException(e); // or handle exception as needed
        }
    }
}
