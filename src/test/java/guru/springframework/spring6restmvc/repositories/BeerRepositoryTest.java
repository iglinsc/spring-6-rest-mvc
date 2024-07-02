package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class BeerRepositoryTest {

    @Autowired
    private BeerRepository beerRepository;


    @Test
    void testFindBeerByName(){
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(list.size()).isGreaterThan(300);


    }

    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder().beerName("My Beer").build());



        assertThat(savedBeer.getBeerName()).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}