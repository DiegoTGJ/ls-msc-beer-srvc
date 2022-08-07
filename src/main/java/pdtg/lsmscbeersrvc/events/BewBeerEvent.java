package pdtg.lsmscbeersrvc.events;

import pdtg.lsmscbeersrvc.web.model.BeerDto;

/**
 * Created by Diego T. 07-08-2022
 */
public class BewBeerEvent extends BeerEvent{
    public BewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
