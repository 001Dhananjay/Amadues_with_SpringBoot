package com.amadeus.controllers;

import com.amadeus.Amadeus;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.service.AmadeusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//import static org.springframework.http.RequestEntity.post;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

    @Autowired
    private ObjectMapper objectMapper;



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



    @Test
    public void testPriceFlightOfferSearch() throws Exception {
        // Prepare mock input
        Map<String, Object> request = new HashMap<>();
        request.put("data", "sample-flight-offer");  // Simplified request

        // Expected JSON response from service
        String mockJsonResponse = "{\"price\":\"12000\",\"currency\":\"INR\"}";

        // Mock service call
        when(amadeusService.priceOfferFlightSearches(request)).thenReturn(mockJsonResponse);

        // Perform POST request
        mockMvc.perform(post("/v2/price-search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mockJsonResponse));

    }




        @Test
        void testSearchMultiCity_success() throws Exception {
            // Prepare request payload
            Map<String, Object> request = Map.of(
                    "currencyCode", "INR",
                    "originDestinations", List.of(
                            Map.of(
                                    "originLocationCode", "DEL",
                                    "destinationLocationCode", "DXB",
                                    "date", "2025-08-01"
                            )
                    ),
                    "travelers", List.of(
                            Map.of("travelerType", "ADULT")
                    )
            );

            // Prepare mocked response
            String mockResponse = "{\"result\": \"Mocked Flight Offer\"}";

            // Mock service call
            when(amadeusService.searchMultiCityFlightOffers(request)).thenReturn(mockResponse);

            // Call endpoint
            mockMvc.perform(post("/v2/search-multicity")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mockResponse));
        }
    }




