package pdtg.lsmscbeersrvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pdtg.lsmscbeersrvc.domain.Beer;
import pdtg.lsmscbeersrvc.repositories.BeerRepository;
import pdtg.lsmscbeersrvc.web.controller.errors.NotFoundException;
import pdtg.lsmscbeersrvc.web.mappers.BeerMapper;
import pdtg.lsmscbeersrvc.web.model.BeerDto;

import java.util.UUID;

/**
 * Created by Diego T. 26-07-2022
 */
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public BeerDto getById(UUID beerId) {
        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId)
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
}
