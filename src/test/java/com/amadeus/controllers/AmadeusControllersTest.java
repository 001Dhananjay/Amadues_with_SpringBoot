package com.amadeus.controllers;

import com.amadeus.Amadeus;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.service.AmadeusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(AmadeusControllers.class)
public class AmadeusControllersTest {
/*


    @MockBean
    private AmadeusService amadeusService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFlightOfferSearchReturns200() throws Exception {
        FlightOfferSearch dummyOffer = mock(FlightOfferSearch.class);
        FlightOfferSearch[] offers = new FlightOfferSearch[]{dummyOffer};

        when(amadeusService.flightOfferSearches(anyMap())).thenReturn(offers);

        mockMvc.perform(MockMvcRequestBuilders.get("/v2/search")
                        .param("originLocationCode", "SYD")
                        .param("destinationLocationCode", "BKK")
                        .param("departureDate", "2025-07-01")
                        .param("adults", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("["))); //  basic JSON check
    }
*/






    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmadeusService amadeusService;

    @Test
    public void testFlightOfferSearch() throws Exception {
        // 1. Load JSON file from resources
        String jsonResponse = new String(
                Files.readAllBytes(Paths.get("src/test/resources/flightOffers.json"))
        );

        // 2. Parse JSON into FlightOfferSearch[] (using Jackson)
        ObjectMapper mapper = new ObjectMapper();
        FlightOfferSearch[] mockFlights = mapper.readValue(jsonResponse, FlightOfferSearch[].class);

        // 3. Mock the service to return parsed data
        when(amadeusService.flightOfferSearches(anyMap()))
                .thenReturn(mockFlights);

        // 4. Test the endpoint (compare with the raw JSON)
        mockMvc.perform(get("/v2/search")
                        .param("originLocationCode", "DEL")
                        .param("destinationLocationCode", "DXB")
                        .param("departureDate", "2025-07-01")
                        .param("adults", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse)); // Directly compare JSON
    }




}
