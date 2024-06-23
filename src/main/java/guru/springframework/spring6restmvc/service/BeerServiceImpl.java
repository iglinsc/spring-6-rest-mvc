package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, BeerDTO> beerMap;
public BeerServiceImpl() {
    this.beerMap = new HashMap<>();

    BeerDTO beer1 = BeerDTO.builder()
            .id(UUID.randomUUID())
            .version(2)
            .beerName("lol Cat")
            .version(1)
            .upc("123")
            .price(new BigDecimal("1.99"))
            .QuantityOnDemand(12)
            .createdDate(LocalDate.now())
            .updatedDate(LocalDate.now())
            .build();

    BeerDTO beer2 = BeerDTO.builder()
            .id(UUID.randomUUID())
            .version(2).upc("123")
            .beerName("Doog Beer")
             .version(2)
            .price(new BigDecimal("69.69"))
            .QuantityOnDemand(169)
            .createdDate(LocalDate.now())
            .updatedDate(LocalDate.now())
            .build();


    BeerDTO beer3 = BeerDTO.builder()
            .id(UUID.randomUUID())
            .version(2).upc("123")
            .beerName("Heineken")

            .price(new BigDecimal("3.99"))
            .QuantityOnDemand(22)
            .createdDate(LocalDate.now())
            .updatedDate(LocalDate.now())
            .build();

    beerMap.put(beer1.getId(), beer1);
    beerMap.put(beer2.getId(), beer2);
    beerMap.put(beer3.getId(), beer3);

}

@Override
public List<BeerDTO> listBeers() {
 return new ArrayList<>(beerMap.values());
}

    @Override
    public Optional<BeerDTO> getBeerById(UUID Id) {
      log.debug("getBeerById");

        return Optional.of(beerMap.get(Id)) ;
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(2)
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .price(beer.getPrice())
                .QuantityOnDemand(beer.getQuantityOnDemand())
                .build();

        beerMap.put(beer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateById(UUID id, BeerDTO beer) {
      BeerDTO existing =    beerMap.get(id);
      existing.setBeerName(beer.getBeerName());
      existing.setBeerStyle(beer.getBeerStyle());
      existing.setPrice(beer.getPrice());
      existing.setQuantityOnDemand(beer.getQuantityOnDemand());
      existing.setUpdatedDate(LocalDate.now());
      existing.setUpdatedDate(LocalDate.now());
      existing.setUpc(beer.getUpc());
      beerMap.put(id, existing);
      return Optional.of(existing);
    }

    @Override
    public boolean deleteById(UUID id) {
        beerMap.remove(id);
        return true;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existing = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())){
            existing.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null) {
            existing.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            existing.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnDemand() != null){
            existing.setQuantityOnDemand(beer.getQuantityOnDemand());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }

        return Optional.of(existing);
    }

}


