package guru.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.bootstrap.BeerStyle;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.service.BeerService;
import guru.springframework.spring6restmvc.service.BeerServiceImpl;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    void testListBeersSorting() throws Exception {
        // Arrange
        BeerDTO beer1 = BeerDTO.builder().id(UUID.randomUUID()).beerName("BeerA").build();
        BeerDTO beer2 = BeerDTO.builder().id(UUID.randomUUID()).beerName("BeerB").build();
        BeerDTO beer3 = BeerDTO.builder().id(UUID.randomUUID()).beerName("BeerC").build();

        // Sorted order as per the test
        List<BeerDTO> beers = List.of(beer1, beer2, beer3);  // Expected sorted order
        Page<BeerDTO> page = new PageImpl<>(beers, PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "beerName")), beers.size());

        given(beerService.listBeers(any(), any(), anyBoolean(), anyInt(), anyInt())).willReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/v1/beer?pageNumber=0&pageSize=3&sort=beerName,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].beerName", is("BeerA")))  // Adjusted for expected order
                .andExpect(jsonPath("$.content[1].beerName", is("BeerB")))
                .andExpect(jsonPath("$.content[2].beerName", is("BeerC")));
    }



    @Test
    void testListBeersPagination() throws Exception {
        // Arrange
        BeerDTO beer1 = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer1").build();
        BeerDTO beer2 = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer2").build();
        BeerDTO beer3 = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer3").build();
        List<BeerDTO> beers = List.of(beer1, beer2, beer3);
        Page<BeerDTO> page = new PageImpl<>(beers, PageRequest.of(0, 3), beers.size());

        given(beerService.listBeers(null, null, false, 0, 3)).willReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/v1/beer?pageNumber=0&pageSize=3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].beerName", is("Beer1")))
                .andExpect(jsonPath("$.content[1].beerName", is("Beer2")))
                .andExpect(jsonPath("$.content[2].beerName", is("Beer3")));
    }


    @Test
    void updateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null,null,false,null,null).getContent().get(0);

        mockMvc.perform(put("/api/v1/beer/" +  beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(beer)))

        ;

        verify(beerService).updateById(any(UUID.class), any(BeerDTO.class));



    }

    @Test
    void testCreateNewBeer() throws Exception {
        // Create a BeerDTO to test
        BeerDTO beer = BeerDTO.builder()
                .beerName("Random Beer Name")
                .id(UUID.randomUUID())
                .beerStyle(BeerStyle.ALE)
                .upc("123456789012")
                .QuantityOnDemand(50)
                .price(BigDecimal.valueOf(9.99))
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        // Mock the behavior of the beerService to return the created beerDTO upon saving
        given(beerService.saveBeer(any(BeerDTO.class))).willReturn(beer);

        // Perform the POST request and validate the response
        mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())  // Expect HTTP status 201 Created
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(beer.getId().toString()))
                .andExpect(jsonPath("$.beerName").value(beer.getBeerName()))
                .andExpect(jsonPath("$.beerStyle").value(beer.getBeerStyle().toString()))
                .andExpect(jsonPath("$.upc").value(beer.getUpc()))
                .andExpect(jsonPath("$.quantityOnDemand").value(beer.getQuantityOnDemand()))
                .andExpect(jsonPath("$.price").value(beer.getPrice().doubleValue()))
                .andExpect(jsonPath("$.createdDate").value(beer.getCreatedDate().toString()))
                .andExpect(jsonPath("$.updatedDate").value(beer.getUpdatedDate().toString()));

        // Verify the service method was called
        verify(beerService).saveBeer(any(BeerDTO.class));
    }



    @Test
    void testCreateBeerNullBeerName() throws Exception {
        // Create a BeerDTO with a null beerName
        BeerDTO beerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerStyle(BeerStyle.ALE)
                .upc("123456789012")
                .QuantityOnDemand(50)
                .price(BigDecimal.valueOf(9.99))
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        // Mock the behavior of the beerService
        // This is optional as we're primarily testing for validation
        given(beerService.saveBeer(any(BeerDTO.class))).willReturn(beerDTO);

        // Perform the POST request with BeerDTO having a null beerName
        mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest());  // Expect HTTP status 400 Bad Request
    }







    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers(any(),any(),anyBoolean(),anyInt(),anyInt())).willReturn(beerServiceImpl.listBeers(any(),any(),anyBoolean(),anyInt(),anyInt()));

        mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));

        System.out.println(mapper.writeValueAsString(beerServiceImpl.listBeers(any(),any(),anyBoolean(),anyInt(),anyInt())));

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
    void testPaging() throws Exception {
        // Create sample BeerDTO objects
        BeerDTO beer1 = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer 1").build();
        BeerDTO beer2 = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer 2").build();
        BeerDTO beer3 = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer 3").build();

        // Create a Page object with sample data
        Page<BeerDTO> beerPage = new PageImpl<>(Arrays.asList(beer1, beer2), PageRequest.of(0, 2), 3);

        // Mock the service method
        given(beerService.listBeers(any(), any(), eq(false), anyInt(), anyInt())).willReturn(beerPage);

        // Perform the request and validate the pagination
        mockMvc.perform(get("/api/v1/beer?pageNumber=0&pageSize=2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(2))) // Page size is 2
                .andExpect(jsonPath("$.totalPages", is(2))) // Total pages are 2
                .andExpect(jsonPath("$.totalElements", is(3))) // Total elements are 3
                .andExpect(jsonPath("$.number", is(0))) // Current page is 0
                .andExpect(jsonPath("$.size", is(2))); // Page size is 2
    }

/*    @Test
    void testSorting() throws Exception {
        // Create sample BeerDTO objects with different beer names
        BeerDTO beerA = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer A").build();
        BeerDTO beerB = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer B").build();

        // Create a Page object with the sample data sorted by beer name
        Page<BeerDTO> beerPage = new PageImpl<>(Arrays.asList(beerA, beerB), PageRequest.of(0, 10, Sort.by("beerName").ascending()), 2);

        // Mock the service method
        given(beerService.listBeers(any(), any(), eq(false), anyInt(), anyInt())).willReturn(beerPage);

        // Perform the request and verify the sorting
        mockMvc.perform(get("/api/v1/beer?sort=beerName,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].beerName", is("Beer A"))) // First item should be "Beer A" due to sorting
                .andExpect(jsonPath("$.content[1].beerName", is("Beer B"))); // Second item should be "Beer B"
    }*/






    @Test
    void getBeerById() throws Exception {
        // Given
        BeerDTO testBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Test Beer")
                .beerStyle(BeerStyle.ALE)
                .upc("123456789012")
                .QuantityOnDemand(50)
                .price(BigDecimal.valueOf(9.99))
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        // When & Then
        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }
}
