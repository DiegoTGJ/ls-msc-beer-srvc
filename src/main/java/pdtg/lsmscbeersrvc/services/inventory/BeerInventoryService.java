package pdtg.lsmscbeersrvc.services.inventory;

import java.util.UUID;

/**
 * Created by Diego T. 28-07-2022
 */
public interface BeerInventoryService {
    Integer getOnHandInventory(UUID beerId);
}
