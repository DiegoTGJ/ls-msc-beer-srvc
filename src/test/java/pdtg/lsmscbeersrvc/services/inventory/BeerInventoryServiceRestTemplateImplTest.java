package pdtg.lsmscbeersrvc.services.inventory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.UUID;


@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    private static final UUID beerId = UUID.fromString("d36b163a-6972-432c-b3b5-d73d73c38141");
    @Test
    void getOnHandInventory() {
        Integer qoh = beerInventoryService.getOnHandInventory(beerId);
        System.out.println(qoh);
    }
}