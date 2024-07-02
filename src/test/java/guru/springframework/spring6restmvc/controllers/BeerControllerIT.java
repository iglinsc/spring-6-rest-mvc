package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.bootstrap.BeerStyle;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    private BeerController beerController;
    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private BeerMapper beerMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testGetBeerByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer")
                        .param("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").isNotEmpty());
    }





    @Test
    void deleteByIdNotFound(){
        assertThrows(NotFoundException.class, () -> beerController.deleteById(UUID.randomUUID()));


    }

    @Test
    void testByIdNotFound(){
     assertThrows(NotFoundException.class, ()->{
         beerController.updateById(UUID.randomUUID() , BeerDTO.builder().build());

     });
    }


    @Test
    void deleteById(){
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity<BeerDTO> response = beerController.deleteById(beer.getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        beerRepository.delete(beer);



        assertThat(beerRepository.existsById(beer.getId())).isFalse();



    }

    @Test
    void deleteByIdController() {
        // Assuming beerRepository returns a Beer entity which we convert to BeerDTO
        BeerDTO beer = beerRepository.findAll().stream()
                .map(beerMapper::beerToBeerDto) // Convert Beer to BeerDTO
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No beers found in repository")); // Error handling can be adjusted

        ResponseEntity<Void> responseEntity = beerController.deleteById(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(beerRepository.existsById(beer.getId())).isFalse();
    }

    @Transactional
    @Rollback
    @Test
    void updateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerController.getBeerById(beer.getId());
        beerDTO.setVersion(null);  // This is okay if version is not needed for the update
        final String BeerName = "updateExistingBeer";
        beerDTO.setBeerName(BeerName);

        // Use the original ID of the beer entity to update
        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();  // Use the original ID to fetch the updated beer
        assertThat(updatedBeer.getBeerName()).isEqualTo(BeerName);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }



    @Test
    void testSaveNewBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer")
                .beerStyle(BeerStyle.PILSNER)
                .upc("123456789012")

                .version(2)
                .price(BigDecimal.valueOf(3.99))
                .QuantityOnDemand(1)

                .build();




        ResponseEntity responseEntity = beerController.HandlePost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNull(); // Expecting no body for a 201 response
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedId = UUID.fromString(locationUUID[locationUUID.length - 1]); // Get the last part of the path
        Optional<Beer> savedBeerOptional = beerRepository.findById(savedId);
        assertThat(savedBeerOptional).isPresent();
        Beer savedBeer = savedBeerOptional.get();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getBeerName()).isEqualTo("New Beer");
    }


    @Test
    void testById(){
      Beer beer=  beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getBeerById(beer.getId());

        assertThat(beerDTO.getId()).isEqualTo(beer.getId());

    }


    @Test
    void testListBeers(){
        List<Beer> beers = beerRepository.findAll();

        assertThat(beers.size()).isGreaterThan(2000);
    }
    @Rollback
     @Transactional
    @Test
   void  testEmptyList(){
        beerRepository.deleteAll();

        assertThat(beerRepository.count()).isEqualTo(0);


   }
}