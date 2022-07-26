package pdtg.lsmscbeersrvc.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pdtg.lsmscbeersrvc.domain.Beer;
import pdtg.lsmscbeersrvc.repositories.BeerRepository;
import pdtg.lsmscbeersrvc.web.controller.errors.exceptions.NotFoundException;
import pdtg.lsmscbeersrvc.web.mappers.BeerMapper;
import pdtg.ls.brewery.model.BeerDto;
import pdtg.ls.brewery.model.BeerPagedList;
import pdtg.ls.brewery.model.BeerStyleEnum;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Diego T. 26-07-2022
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getById(UUID beerId,boolean showInventoryOnHand) {
        System.out.println("i was called");
        return !showInventoryOnHand ? beerMapper.beerToBeerDto(
                beerRepository.findById(beerId)
                .orElseThrow(NotFoundException::new))
                : beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId)
                .orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(
                beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        beerRepository.save(beer);
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,boolean showInventoryOnHand) {
        System.out.println("i was called");
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if(!StringUtils.hasText(beerName) && !StringUtils.isEmpty(beerStyle)){
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName,beerStyle,pageRequest);
        }else if(StringUtils.hasText(beerName) && StringUtils.isEmpty(beerStyle)){
            // search beer service name
            beerPage = beerRepository.findAllByBeerName(beerName,pageRequest);
        } else if (!StringUtils.hasText(beerName) && !StringUtils.isEmpty(beerStyle)) {
            // search beer service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        }else{
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList((beerPage
                .getContent()
                .stream()
                .map(showInventoryOnHand ? beerMapper::beerToBeerDtoWithInventory : beerMapper::beerToBeerDto)
                .collect(Collectors.toList())),
        PageRequest
                .of(beerPage.getPageable().getPageNumber(),
                        beerPage.getPageable().getPageSize()),
        beerPage.getTotalElements());

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerUpcCache", key = "#upc", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getByUpc(String upc, boolean showInventoryOnHand) {
        return !showInventoryOnHand ? beerMapper.beerToBeerDto(
                beerRepository.findByUpc(upc)
                        .orElseThrow(NotFoundException::new))
                : beerMapper.beerToBeerDtoWithInventory(beerRepository.findByUpc(upc)
                .orElseThrow(NotFoundException::new));
    }
}
