package pdtg.lsmscbeersrvc.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pdtg.lsmscbeersrvc.config.JmsConfig;
import pdtg.lsmscbeersrvc.domain.Beer;
import pdtg.lsmscbeersrvc.events.BrewBeerEvent;
import pdtg.lsmscbeersrvc.repositories.BeerRepository;
import pdtg.lsmscbeersrvc.services.inventory.BeerInventoryService;
import pdtg.lsmscbeersrvc.web.mappers.BeerMapper;

import java.util.List;

/**
 * Created by Diego T. 07-08-2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BrewingServiceImpl implements BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnHandInventory(beer.getId());
            log.debug("Min On Hand for beer with ID:"+beer.getId()+" is: "+beer.getMinOnHand());
            log.debug("Quantity on Hand is: "+beer.getMinOnHand());
            if(beer.getMinOnHand() > invQOH){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
