package guru.springframework.spring6restmvc.mappers;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    @Mapping(target = "beerStyle", ignore = true)
    BeerDTO beerToBeerDto(Beer beer);


}
