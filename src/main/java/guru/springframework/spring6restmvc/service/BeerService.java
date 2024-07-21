package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.entities.BeerStyle;
import guru.springframework.spring6restmvc.model.BeerDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventory, Integer pageNumber, Integer pageSize);

   Optional<BeerDTO>  getBeerById(UUID Id);


    BeerDTO saveBeer(BeerDTO beer);

    Optional<BeerDTO> updateById(UUID id, BeerDTO beer);

    boolean deleteById(UUID id);
    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

}
