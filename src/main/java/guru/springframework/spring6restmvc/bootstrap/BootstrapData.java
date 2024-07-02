package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerStyle;
import guru.springframework.spring6restmvc.model.BeerCsvData;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.service.BeerCsvService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
   private  final BeerCsvService BeerCsvService;
    @Autowired
    public BootstrapData(BeerRepository beerRepository, guru.springframework.spring6restmvc.service.BeerCsvService beerCsvService) {
        this.beerRepository = beerRepository;
        BeerCsvService = beerCsvService;
    }
    @Transactional
    @Override
    public void run(String... args) throws Exception {

        loadBeerData();
        loadCsvData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File file = new File("src/main/resources/csvdata/beers.csv");
            List<BeerCsvData> recs = BeerCsvService.ConvertCSV(file);

            recs.forEach(beerCSVRecord -> {


                // Assuming BeerCsvData has methods getBeer(), getRow(), and getCount()
                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(BeerStyle.PILSNER)
                        .price(BigDecimal.TEN)
                                .createdDate(LocalDateTime.now())
                                .updateDate(LocalDateTime.now())
                        .upc(Integer.toString(beerCSVRecord.getRow())) // Convert int to String
                        .quantityOnHand(Integer.valueOf(beerCSVRecord.getCountY())) // Assuming getCount() retrieves count
                        .build());
            });
        }


    }

    private void loadBeerData() {
        Beer beer1 = Beer.builder()
                .beerName("IPA")
                .beerStyle(BeerStyle.LAGER)
                .upc("123456789012")
                .quantityOnHand(100)
                .version(2)
                .price(BigDecimal.valueOf(3.99))
                .createdDate(LocalDateTime.now().minusMonths(6))
                .updateDate(LocalDateTime.now().minusDays(1))
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Stout")
                .beerStyle(BeerStyle.PORTER)
                .upc("987654321098")
                .quantityOnHand(50).version(3)
                .price(BigDecimal.valueOf(4.50))
                .createdDate(LocalDateTime.now().minusMonths(3))
                .updateDate(LocalDateTime.now().minusHours(12))
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Pilsner")
                .beerStyle(BeerStyle.LAGER).version(4)
                .upc("246801357924")
                .quantityOnHand(75)
                .price(BigDecimal.valueOf(2.99))
                .createdDate(LocalDateTime.now().minusMonths(1))
                .updateDate(LocalDateTime.now().minusMinutes(30))
                .build();

        beerRepository.save(beer1);
        beerRepository.save(beer2);
        beerRepository.save(beer3);
    }
}
