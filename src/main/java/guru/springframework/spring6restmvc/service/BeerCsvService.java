package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerCsvData;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCsvData> ConvertCSV(File csvFile);
}
