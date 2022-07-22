package pdtg.lsmscbeersrvc.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pdtg.lsmscbeersrvc.domain.Beer;

import java.util.UUID;

/**
 * Created by Diego T. 22-07-2022
 */
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {


}
