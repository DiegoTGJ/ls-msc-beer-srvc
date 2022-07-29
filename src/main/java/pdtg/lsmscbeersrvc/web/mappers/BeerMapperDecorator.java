package pdtg.lsmscbeersrvc.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import pdtg.lsmscbeersrvc.domain.Beer;
import pdtg.lsmscbeersrvc.services.inventory.BeerInventoryService;
import pdtg.lsmscbeersrvc.web.model.BeerDto;

/**
 * Created by Diego T. 28-07-2022
 */
public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper beerMapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService){
        this.beerInventoryService = beerInventoryService;
    }
    @Autowired
    public void setBeerMapper(BeerMapper beerMapper){
        this.beerMapper = beerMapper;
    }
    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return beerMapper.beerDtoToBeer(beerDto);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto dto = beerMapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }
}
