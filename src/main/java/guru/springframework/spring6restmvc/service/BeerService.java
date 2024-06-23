package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> listBeers();

   Optional<BeerDTO>  getBeerById(UUID Id);


    BeerDTO saveBeer(BeerDTO beer);

    Optional<BeerDTO> updateById(UUID id, BeerDTO beer);

    boolean deleteById(UUID id);
    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

}
