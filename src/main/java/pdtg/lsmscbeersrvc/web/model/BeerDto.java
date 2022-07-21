package pdtg.lsmscbeersrvc.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created by Diego T. 21-07-2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {

    private UUID id;
    private Integer version;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;

    private String beerName;
    private BeerStyleEnum beerStyle;

    private Long upc;
    private BigDecimal price;

    private Integer quantityOnHand;

}
