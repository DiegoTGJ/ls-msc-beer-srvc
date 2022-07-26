package pdtg.lsmscbeersrvc.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import pdtg.lsmscbeersrvc.bootstrap.BeerLoader;
import pdtg.lsmscbeersrvc.services.BeerService;
import pdtg.ls.brewery.model.BeerDto;
import pdtg.ls.brewery.model.BeerStyleEnum;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https",uriHost = "dev.springframework.pdtg",uriPort = 80)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "/api/v1/beer/";

    @MockBean
    BeerService beerService;

    BeerDto validBeerDto;
    UUID randomUUID;

    @BeforeEach
    void setUp() {
        validBeerDto = BeerDto.builder()
                .beerName("Some beer")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(BeerLoader.BEER_1_UPC)
                .price(new BigDecimal("12.2"))
                .build();
        randomUUID = UUID.randomUUID();
    }

    @Test
    void getBeerById() throws Exception {
        BeerDto validResponseDto = validBeerDto;
        validResponseDto.setVersion(1);
        validResponseDto.setCreatedDate(OffsetDateTime.now());
        validResponseDto.setLastModifiedDate(OffsetDateTime.now());
        validResponseDto.setId(UUID.randomUUID());
        given(beerService.getById(any(UUID.class),any(boolean.class))).willReturn(validResponseDto);
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        mockMvc.perform(get(API_URL + "{beerId}", randomUUID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer/GET", pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get")),
                        responseFields(
                                fields.withPath("id").description("Id of beer"),
                                fields.withPath("version").description("Version number"),
                                fields.withPath("createdDate").description("Date Created"),
                                fields.withPath("lastModifiedDate").description("Date Updated"),
                                fields.withPath("beerName").description("Beer Name"),
                                fields.withPath("beerStyle").description("Beer Style"),
                                fields.withPath("upc").description("UPC of Beer"),
                                fields.withPath("price").description("Price of the beer")
                        )
                ));
    }

    @Test
    void saveNewBeer() throws Exception {
        String beerDtoString = objectMapper.writeValueAsString(validBeerDto);
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoString))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer/POST",
                        requestFields(
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("Beer UPC").attributes(),
                                fields.withPath("price").description("The Beer Price")
                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        validBeerDto.setBeerName("Another name");
        String beerDtoString = objectMapper.writeValueAsString(validBeerDto);
        mockMvc.perform(put(API_URL + randomUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoString))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBeerById() throws Exception {
        mockMvc.perform(delete(API_URL + randomUUID))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldValidateIdIsNull() throws Exception {
        validBeerDto.setId(UUID.randomUUID());
        String beerDtoString = objectMapper.writeValueAsString(validBeerDto);
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateVersionIsNull() throws Exception {
        validBeerDto.setVersion(3);
        String beerDtoString = objectMapper.writeValueAsString(validBeerDto);
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateCreatedDateIsNull() throws Exception {
        validBeerDto.setCreatedDate(OffsetDateTime.now());
        String beerDtoString = objectMapper.writeValueAsString(validBeerDto);
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateModifiedDateIsNull() throws Exception {
        validBeerDto.setLastModifiedDate(OffsetDateTime.now());
        String beerDtoString = objectMapper.writeValueAsString(validBeerDto);
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoString))
                .andExpect(status().isBadRequest());
    }

    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;


        private ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path){
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path),". ")));
        }
    }
}