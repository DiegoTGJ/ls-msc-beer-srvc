package pdtg.lsmscbeersrvc.services.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pdtg.ls.brewery.model.BeerOrderDto;
import pdtg.lsmscbeersrvc.repositories.BeerRepository;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Diego T. 17-08-2022
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public boolean validateOrder(BeerOrderDto beerOrderDto){
        AtomicInteger beersNotFound = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(orderline -> {
            if(!beerRepository.findByUpc(orderline.getUpc()).isPresent()){
                beersNotFound.incrementAndGet();
            }
        });
        return beersNotFound.get() == 0;
    }
}
