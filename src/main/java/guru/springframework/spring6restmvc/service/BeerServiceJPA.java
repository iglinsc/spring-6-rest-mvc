package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor


public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    @Override
    public List<BeerDTO> listBeers(String beerName) {
        List<Beer> beerList;
        if(StringUtils.hasText(beerName)){
            beerList = beerListByName(beerName);


        }  else {
            beerList = beerRepository.findAll();
        }



        return beerList.stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList());
    }

    private List<Beer> beerListByName(String beerName) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%"+beerName+"%");
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID Id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(Id).orElse(null)));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateById(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>(Optional.empty());

        beerRepository.findById(id).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            Beer updatedBeer = beerRepository.save(foundBeer);
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(updatedBeer)));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }


    @Override
    public boolean deleteById(UUID id) {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beer.getBeerName())){
                foundBeer.setBeerName(beer.getBeerName());
            }

            if (StringUtils.hasText(beer.getUpc())){
                foundBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                foundBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnDemand() != null){
                foundBeer.setQuantityOnHand(beer.getQuantityOnDemand());
            }
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

}
