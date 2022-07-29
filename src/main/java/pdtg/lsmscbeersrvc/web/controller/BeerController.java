package pdtg.lsmscbeersrvc.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pdtg.lsmscbeersrvc.services.BeerService;
import pdtg.lsmscbeersrvc.web.model.BeerDto;
import pdtg.lsmscbeersrvc.web.model.BeerPagedList;
import pdtg.lsmscbeersrvc.web.model.BeerStyleEnum;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by Diego T. 21-07-2022
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false,defaultValue = "false") boolean showInventoryOnHand
                                                   ){
        if (pageNumber == null || pageNumber<0) pageNumber = DEFAULT_PAGE_NUMBER;
        if (pageSize == null || pageSize < 1) pageSize = DEFAULT_PAGE_SIZE;

        BeerPagedList beerList = beerService.listBeers(beerName,beerStyle, PageRequest.of(pageNumber,pageSize), showInventoryOnHand);
        return new ResponseEntity<>(beerList,HttpStatus.OK);
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false,defaultValue = "false") boolean showInventoryOnHand){
            BeerDto beerDto = beerService.getById(beerId,showInventoryOnHand);
        return new ResponseEntity<>(beerDto, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BeerDto> saveNewBeer(@Valid @RequestBody BeerDto beerDto){
        BeerDto savedBeer = beerService.saveNewBeer(beerDto);
        return new ResponseEntity<>(savedBeer,HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@PathVariable UUID beerId,@Valid  @RequestBody BeerDto beerDto){
        beerService.updateBeer(beerId,beerDto);
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeerById(@PathVariable UUID beerId){

    }
}
