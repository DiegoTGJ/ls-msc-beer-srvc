package pdtg.lsmscbeersrvc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import pdtg.lsmscbeersrvc.domain.Beer;
import pdtg.ls.brewery.model.BeerStyleEnum;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Diego T. 22-07-2022
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {


    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);

    Page<Beer> findAllByBeerName(String beerName, PageRequest pageRequest);

    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, PageRequest pageRequest);

    Optional<Beer> findByUpc(String upc);

}
