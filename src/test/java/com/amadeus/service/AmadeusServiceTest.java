package com.amadeus.service;

import com.amadeus.*;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.shopping.FlightOffersSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class AmadeusServiceTest {

    private AmadeusService amadeusService;
    private Amadeus mockAmadeus;
    private Shopping mockShopping;
    private FlightOffersSearch mockFlightOffersSearch;

    //
    private RestTemplate mockRestTemplate;
    private ObjectMapper mockObjectMapper;
    @BeforeEach
    void setUp() throws Exception {
        amadeusService = new AmadeusService();

        // Mock Amadeus and its internal structure
        mockAmadeus = mock(Amadeus.class);
        mockShopping = mock(Shopping.class);
        mockFlightOffersSearch = mock(FlightOffersSearch.class);
        //
        mockRestTemplate = mock(RestTemplate.class);
        mockObjectMapper = mock(ObjectMapper.class);

        // When get() is called on flightOffersSearch mock, return dummy data
        when(mockFlightOffersSearch.get(any())).thenReturn(new FlightOfferSearch[1]);

        // Manually assign the nested mocks
        // Here, instead of using when(...), just assign the mocks
        mockShopping.flightOffersSearch = mockFlightOffersSearch;
        mockAmadeus.shopping = mockShopping;

        // Inject mocked Amadeus into service
        Field field = AmadeusService.class.getDeclaredField("amadeus");
        field.setAccessible(true);
        field.set(amadeusService, mockAmadeus);

        //me

        Field restTemplateField = AmadeusService.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(amadeusService, mockRestTemplate);

        Field objectMapperField = AmadeusService.class.getDeclaredField("objectMapper");
        objectMapperField.setAccessible(true);
        objectMapperField.set(amadeusService, mockObjectMapper);

        // Inject clientId and secret for getAccessToken
        Field clientIdField = AmadeusService.class.getDeclaredField("clientId");
        clientIdField.setAccessible(true);
        clientIdField.set(amadeusService, "dummy-client-id");

        Field clientSecretField = AmadeusService.class.getDeclaredField("clientSecret");
        clientSecretField.setAccessible(true);
        clientSecretField.set(amadeusService, "dummy-client-secret");
    }


    @Test
    void testFlightOfferSearches_returnsFlightOffers() throws ResponseException {
        // Create mock request parameters
        Map<String, String> params = new HashMap<>();
        params.put("originLocationCode", "DEL");
        params.put("destinationLocationCode", "BOM");
        params.put("departureDate", "2025-06-12");
        params.put("adults","1");
        // Call method
        FlightOfferSearch[] result = amadeusService.flightOfferSearches(params);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.length);

        // Verify interaction
        verify(mockFlightOffersSearch, times(1)).get(any(Params.class));
    }

















/*

    @Test
    void testMultiCitySearchFlightOffers() throws Exception {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("originLocationCode", "DEL");

        // Step 1: Mock getAccessToken REST call
        Map<String, Object> tokenResponseBody = new HashMap<>();
        tokenResponseBody.put("access_token", "mock-token");

        ResponseEntity<Map> tokenResponse = new ResponseEntity<>(tokenResponseBody, HttpStatus.OK);
        when(mockRestTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Map.class))).thenReturn(tokenResponse);

        // Step 2: Mock ObjectMapper JSON conversion
        when(mockObjectMapper.writeValueAsString(any())).thenReturn("{\"originLocationCode\":\"DEL\"}");

        // Step 3: Mock final POST to flight offers
        ResponseEntity<String> finalResponse = new ResponseEntity<>("flight-offer-response", HttpStatus.OK);
        when(mockRestTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class))).thenReturn(finalResponse);

        // Call the method
        String result = amadeusService.searchMultiCityFlightOffers(requestMap);
        // Assert
        assertEquals("flight-offer-response", result);
    }
*/







}
