package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerStyle;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerCsvData;
import guru.springframework.spring6restmvc.model.CustomerCsvData;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import guru.springframework.spring6restmvc.service.BeerCsvService;
import guru.springframework.spring6restmvc.service.CustomerCsvService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final BeerCsvService beerCsvService;
    private final CustomerRepository customerRepository;
    private final CustomerCsvService customerCsvService;

    @Autowired
    public BootstrapData(
            BeerRepository beerRepository,
            BeerCsvService beerCsvService,
            CustomerRepository customerRepository,
            CustomerCsvService customerCsvService) {
        this.beerRepository = beerRepository;
        this.beerCsvService = beerCsvService;
        this.customerRepository = customerRepository;
        this.customerCsvService = customerCsvService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadBeerCsvData();
        loadCustomerCsvData();
    }

    private void loadBeerCsvData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File file = new File("src/main/resources/csvdata/beers.csv");
            List<BeerCsvData> recs = beerCsvService.ConvertCSV(file);

            recs.forEach(beerCSVRecord -> {
                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(BeerStyle.PILSNER) // Adjust as needed
                        .price(BigDecimal.TEN) // Adjust as needed
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .upc(Integer.toString(beerCSVRecord.getRow())) // Convert int to String
                        .quantityOnHand(Integer.valueOf(beerCSVRecord.getCountY())) // Assuming getCount() retrieves count
                        .build());
            });
        }
    }

    private void loadCustomerCsvData() throws FileNotFoundException {
        if (customerRepository.count() < 10) { // Adjust the condition as needed
            File file = new File("src/main/resources/csvdata/customer.csv");
            List<CustomerCsvData> recs = customerCsvService.convertCSV(file);

            recs.forEach(customerCSVRecord -> {
                customerRepository.save(Customer.builder()
                        .id(UUID.randomUUID()) // Generate a new UUID or use the one from the CSV if needed
                        .name(customerCSVRecord.getName())
                        .email(customerCSVRecord.getEmail())
                        .version(customerCSVRecord.getVersion())
                        .createdDate(customerCSVRecord.getCreatedDate())
                        .updateDate(customerCSVRecord.getUpdateDate())
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
                .quantityOnHand(50)
                .version(3)
                .price(BigDecimal.valueOf(4.50))
                .createdDate(LocalDateTime.now().minusMonths(3))
                .updateDate(LocalDateTime.now().minusHours(12))
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Pilsner")
                .beerStyle(BeerStyle.LAGER)
                .version(4)
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
