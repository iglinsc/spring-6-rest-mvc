package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerCsvData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface CustomerCsvService {
    List<CustomerCsvData> convertCSV(File file) throws FileNotFoundException;
}
