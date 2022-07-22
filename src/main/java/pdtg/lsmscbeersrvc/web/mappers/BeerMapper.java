package pdtg.lsmscbeersrvc.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pdtg.lsmscbeersrvc.domain.Beer;
import pdtg.lsmscbeersrvc.web.model.BeerDto;

/**
 * Created by Diego T. 22-07-2022
 */
@Mapper(uses = DateMapper.class)
public interface BeerMapper {
    @Mapping(target = "quantityOnHand", ignore = true)
    BeerDto beerToBeerDto(Beer beer);
    @Mapping(target = "minOnHand", ignore = true)
    @Mapping(target = "quantityToBrew", ignore = true)
    Beer beerDtoToBeer(BeerDto beerDto);
}
