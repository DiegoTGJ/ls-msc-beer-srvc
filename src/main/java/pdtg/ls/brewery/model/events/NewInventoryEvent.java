package pdtg.ls.brewery.model.events;

import lombok.NoArgsConstructor;
import pdtg.ls.brewery.model.BeerDto;

/**
 * Created by Diego T. 07-08-2022
 */
@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent{
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
