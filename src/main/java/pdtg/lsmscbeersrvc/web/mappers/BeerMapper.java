package pdtg.lsmscbeersrvc.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import pdtg.lsmscbeersrvc.domain.Beer;
import pdtg.lsmscbeersrvc.web.model.BeerDto;

/**
 * Created by Diego T. 22-07-2022
 */
@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);

    BeerDto beerToBeerDtoWithInventory(Beer beer);
}
