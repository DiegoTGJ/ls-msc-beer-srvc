package pdtg.lsmscbeersrvc.services;

import pdtg.lsmscbeersrvc.web.model.BeerDto;

import java.util.UUID;

/**
 * Created by Diego T. 26-07-2022
 */
public interface BeerService {
    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    void updateBeer(UUID beerId, BeerDto beerDto);
}
