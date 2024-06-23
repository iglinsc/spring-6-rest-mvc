package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Autowired
    public BootstrapData(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
    }

    private void loadBeerData() {
        Beer beer1 = Beer.builder()
                .beerName("IPA")
                .beerStyle(BeerStyle.LAGER)
                .upc("123456789012")
                .quantityOnHand(100)
                .price(BigDecimal.valueOf(3.99))
                .createdDate(LocalDateTime.now().minusMonths(6))
                .updateDate(LocalDateTime.now().minusDays(1))
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Stout")
                .beerStyle(BeerStyle.PORTER)
                .upc("987654321098")
                .quantityOnHand(50)
                .price(BigDecimal.valueOf(4.50))
                .createdDate(LocalDateTime.now().minusMonths(3))
                .updateDate(LocalDateTime.now().minusHours(12))
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Pilsner")
                .beerStyle(BeerStyle.LAGER)
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
