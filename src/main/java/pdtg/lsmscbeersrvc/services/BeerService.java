package pdtg.lsmscbeersrvc.services;

import org.springframework.data.domain.PageRequest;
import pdtg.lsmscbeersrvc.web.model.BeerDto;
import pdtg.lsmscbeersrvc.web.model.BeerPagedList;
import pdtg.lsmscbeersrvc.web.model.BeerStyleEnum;

import java.util.UUID;

/**
 * Created by Diego T. 26-07-2022
 */
public interface BeerService {
    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    void updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);
}
