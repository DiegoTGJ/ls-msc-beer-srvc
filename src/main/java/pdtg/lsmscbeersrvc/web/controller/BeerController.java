package pdtg.lsmscbeersrvc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdtg.lsmscbeersrvc.web.model.BeerDto;

import java.util.UUID;

/**
 * Created by Diego T. 21-07-2022
 */
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId){

        return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewBeer(@RequestBody BeerDto beerDto){
        //todo impl
    }

    @PutMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@PathVariable UUID beerId,@RequestBody BeerDto beerDto){
        //todo impl
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeerById(@PathVariable UUID beerId){
        //todo impl
    }
}
