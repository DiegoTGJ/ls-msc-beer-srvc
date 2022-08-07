package pdtg.lsmscbeersrvc.events;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pdtg.lsmscbeersrvc.web.model.BeerDto;

import java.io.Serializable;

/**
 * Created by Diego T. 07-08-2022
 */

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 307788760459972843L;
    private final BeerDto beerDto;
}
