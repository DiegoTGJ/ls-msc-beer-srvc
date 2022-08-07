package pdtg.lsmscbeersrvc.events;

import pdtg.lsmscbeersrvc.web.model.BeerDto;

/**
 * Created by Diego T. 07-08-2022
 */
public class NewInventoryEvent extends BeerEvent{
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
