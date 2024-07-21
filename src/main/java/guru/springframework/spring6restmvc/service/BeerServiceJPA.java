package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerStyle;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Primary
@Service
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private static final int DEFAULT_PAGE_SIZE = 25;
    private static final int DEFAULT_PAGE = 0;

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventory, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = findBeersByName(beerName, pageRequest);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = findBeersByStyle(beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = findBeersByNameAndStyle(beerName, beerStyle, pageRequest);
        } else {
            beerPage = findAllBeers(pageRequest);
        }

        if (!showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);
    }

    private Page<Beer> findBeersByName(String beerName, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    private Page<Beer> findBeersByStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
    }

    private Page<Beer> findBeersByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageable);
    }

    private Page<Beer> findAllBeers(Pageable pageable) {
        return beerRepository.findAll(pageable);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = (pageNumber != null && pageNumber > 0) ? pageNumber - 1 : DEFAULT_PAGE;
        int queryPageSize = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
        Sort sort = Sort.by(Sort.Direction.DESC, "beerName");

        return PageRequest.of(queryPageNumber, queryPageSize , sort);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beer));
        return beerMapper.beerToBeerDto(savedBeer);
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
            if (StringUtils.hasText(beer.getBeerName())) {
                foundBeer.setBeerName(beer.getBeerName());
            }

            if (StringUtils.hasText(beer.getUpc())) {
                foundBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null) {
                foundBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnDemand() != null) {
                foundBeer.setQuantityOnHand(beer.getQuantityOnDemand());
            }
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
}
