package pdtg.lsmscbeersrvc.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pdtg.lsmscbeersrvc.web.model.BeerDto;
import pdtg.lsmscbeersrvc.web.model.BeerStyleEnum;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "/api/v1/beer/";

    BeerDto validBeerDto;
    UUID randomUUID;
    @BeforeEach
    void setUp() {
        validBeerDto = BeerDto.builder()
                .beerName("Some beer")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(1231232L)
                .price(new BigDecimal("12.2"))
                .build();
        randomUUID = UUID.randomUUID();
    }

    @Test
    void getBeerById() throws Exception {

        mockMvc.perform(get(API_URL+ randomUUID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        String beerDtoString = objectMapper.writeValueAsString(validBeerDto);
        mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoString))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        validBeerDto.setBeerName("Another name");
        String beerDtoString = objectMapper.writeValueAsString(validBeerDto);
        mockMvc.perform(put(API_URL+randomUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoString))
                        .andExpect(status().isNoContent());
    }

    @Test
    void deleteBeerById() throws Exception {
        mockMvc.perform(delete(API_URL+randomUUID))
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
}