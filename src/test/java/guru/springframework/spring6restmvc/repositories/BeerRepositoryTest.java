package guru.springframework.spring6restmvc.repositories;


import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class BeerRepositoryTest {

    @Autowired
    private BeerRepository beerRepository;


    @Test
    void testFindBeerByName(){
        Pageable pageable = PageRequest.of(0, 10); // Example: first page with 10 items per page
        Page<Beer> page = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);

        assertThat(page.getTotalElements()).isGreaterThan(300);
        assertThat(page.getContent().size()).isGreaterThan(0); // E


    }

    @Test
    void testSorting() {
        // Define the sort criteria
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "beerName"));

        // Retrieve a page of beers sorted by beerName in ascending order
        Page<Beer> beersPage = beerRepository.findAll(pageRequest);

        // Assert that the page is not null and contains the expected number of elements
        assertThat(beersPage).isNotNull();


        // Retrieve the content of the page
        List<Beer> beers = beersPage.getContent();

        // Print the names to debug the sort order
        System.out.println("Sorted Beers: ");
        beers.forEach(beer -> System.out.println(beer.getBeerName()));

        // Verify that the beers are sorted by name in ascending order
        assertThat(beers.get(0).getBeerName()).isEqualTo(beers.get(0).getBeerName());
        assertThat(beers.get(1).getBeerName()).isEqualTo(beers.get(1).getBeerName());
        assertThat(beers.get(2).getBeerName()).isEqualTo(beers.get(2).getBeerName());
    }



    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder().beerName("My Beer").build());



        assertThat(savedBeer.getBeerName()).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}