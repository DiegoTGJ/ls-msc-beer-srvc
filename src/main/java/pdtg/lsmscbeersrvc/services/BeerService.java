package pdtg.lsmscbeersrvc.services;

import org.springframework.data.domain.PageRequest;
import pdtg.ls.brewery.model.BeerDto;
import pdtg.ls.brewery.model.BeerPagedList;
import pdtg.ls.brewery.model.BeerStyleEnum;

import java.util.UUID;

/**
 * Created by Diego T. 26-07-2022
 */
public interface BeerService {
    BeerDto getById(UUID beerId,boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    void updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, boolean showInventoryOnHand);

    BeerDto getByUpc(String upc, boolean showInventoryOnHand);
}
