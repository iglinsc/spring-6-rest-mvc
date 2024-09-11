package guru.springframework.spring6restmvc.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring6restmvc.model.CustomerCsvData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class CustomerCsvServiceImpl implements CustomerCsvService {

    @Override
    public List<CustomerCsvData> convertCSV(File file) throws FileNotFoundException {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            return new CsvToBeanBuilder<CustomerCsvData>(reader)
                    .withType(CustomerCsvData.class)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new FileNotFoundException("Error reading CSV file: " + e.getMessage());
        }
    }
}
