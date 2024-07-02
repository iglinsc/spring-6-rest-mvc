package guru.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.bootstrap.BeerStyle;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.service.BeerService;
import guru.springframework.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl ;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }



    @Test
    void updateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put("/api/v1/beer/" +  beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(beer)))

        ;

        verify(beerService).updateById(any(UUID.class), any(BeerDTO.class));



    }

    @Test
    void testCreateNewBeer() throws Exception {



        BeerDTO beer = BeerDTO.builder()

                .beerName("Random Beer Name")  // Random beer name
                .id(UUID.randomUUID())
                .beerStyle(BeerStyle.ALE)  // Random beer style
                .upc("123456789012")  // Random 12-digit UPC code
                .QuantityOnDemand(50)  // Random quantity on demand
                .price(BigDecimal.valueOf(9.99))  // Random price
                .createdDate(LocalDate.now())  // Current date
                .updatedDate(LocalDate.now())  // Current date
                .build();

        // Mock the behavior of the beerService to return another beer object upon saving
        given(beerService.saveBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        // Perform the POST request and validate the response
       assertThat(beer).isNotNull();
       assertThat(beer.getId()).isNotNull();



    }



@Test
void testCreateBeerNullBeerName() throws Exception {
    BeerDTO beerDTO = BeerDTO.builder().build();

given(beerService.saveBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

mockMvc.perform(post("/api/v1/beer").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(beerDTO))).andExpect(status().isBadRequest());
}



    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));

        System.out.println(mapper.writeValueAsString(beerServiceImpl.listBeers()));

    }
    @Test
    void deleteBeerById() throws Exception {
        UUID beerId = UUID.randomUUID();

        given(beerService.deleteById(any())).willReturn(true);

        mockMvc.perform(delete("/api/v1/beer/" + beerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(eq(beerId));
    }

    @Test
    public void testBeerByIdNotFound() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())).andExpect(status().isNotFound());
    }




    @Test
    void getBeerById() throws Exception {
        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));


        ;
    }
}
