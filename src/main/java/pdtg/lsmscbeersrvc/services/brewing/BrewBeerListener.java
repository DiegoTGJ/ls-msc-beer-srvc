package pdtg.lsmscbeersrvc.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pdtg.lsmscbeersrvc.config.JmsConfig;
import pdtg.lsmscbeersrvc.domain.Beer;
import pdtg.ls.common.events.BrewBeerEvent;
import pdtg.ls.common.events.NewInventoryEvent;
import pdtg.lsmscbeersrvc.repositories.BeerRepository;
import pdtg.lsmscbeersrvc.web.model.BeerDto;

import javax.transaction.Transactional;

/**
 * Created by Diego T. 07-08-2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){
        BeerDto beerDto = event.getBeerDto();
        Beer beer = beerRepository.getReferenceById(beerDto.getId());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer for beer id:"+beer.getId()+" QOH: "+beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE,newInventoryEvent);
    }
}
