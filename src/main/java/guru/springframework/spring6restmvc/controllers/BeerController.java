package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("api/v1/beer")

public class BeerController {

  private final BeerService beerService;

  @PatchMapping("/api/v1/beer/")
  public ResponseEntity updateBeerPatchById(@PathVariable("beerId")UUID beerId, @RequestBody BeerDTO beer){

    beerService.patchBeerById(beerId, beer);

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteById(@PathVariable("id") UUID id) {
    if ( !beerService.deleteById(id)) {

      throw new NotFoundException("Beer with id " + id + " not found");
    }

    return new  ResponseEntity(HttpStatus.NO_CONTENT);
  }


@PostMapping("/api/v1/beer/")
public ResponseEntity HandlePost(@RequestBody BeerDTO beer) {
    BeerDTO savedBeer = beerService.saveBeer(beer);
            HttpHeaders headers = new HttpHeaders();
     headers.add("Location", savedBeer.getId().toString());
     return new ResponseEntity(headers, HttpStatus.CREATED);
}

  @PutMapping("{id}")
  public ResponseEntity updateById(@PathVariable("id") UUID id, @RequestBody BeerDTO beer) {
    if (beerService.updateById(id , beer).isEmpty() ) {
      throw new NotFoundException("Beer with id " + id + " not found");
    }

    return new  ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @PostMapping
  public ResponseEntity createBeer(@Validated @RequestBody BeerDTO beer) throws URISyntaxException {
    BeerDTO savedBeer = beerService.saveBeer(beer);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(new URI("/api/v1/beer/" + savedBeer.getId().toString()));
    return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<BeerDTO> listBeers() {
    return beerService.listBeers();
  }

  @RequestMapping(value = "{beerId}" , method = RequestMethod.GET)
  public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
    log.debug("Getting beer by beerId {}", beerId);


    return beerService.getBeerById(beerId).orElseThrow(()-> new RuntimeException("Beer not found"));
  }
}
