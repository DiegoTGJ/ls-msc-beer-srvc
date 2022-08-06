package pdtg.lsmscbeersrvc.services.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pdtg.lsmscbeersrvc.services.inventory.model.BeerInventoryDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Diego T. 28-07-2022
 */
@Slf4j
@ConfigurationProperties(prefix = "pdtg.brewery", ignoreUnknownFields = false)
@Component
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {
    private final RestTemplate restTemplate;

    private String beerInventoryServiceHost;

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost){
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    public  BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Integer getOnHandInventory(UUID beerId) {

            log.debug("Calling Inventory Service");

            String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
            ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate
                    .exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<BeerInventoryDto>>() {
                            }, beerId);

            //sum from inventory list

            return Objects.requireNonNull(responseEntity.getBody())
                    .stream()
                    .mapToInt(BeerInventoryDto::getQuantityOnHand)
                    .sum();
    }
}
