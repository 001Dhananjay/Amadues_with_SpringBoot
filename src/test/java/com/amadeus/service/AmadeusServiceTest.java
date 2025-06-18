package com.amadeus.service;

import com.amadeus.*;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.shopping.FlightOffersSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class AmadeusServiceTest {

    private AmadeusService amadeusService;
    private Amadeus mockAmadeus;
    private Shopping mockShopping;
    private FlightOffersSearch mockFlightOffersSearch;
    @BeforeEach
    void setUp() throws Exception {
        amadeusService = new AmadeusService();

        // Mock Amadeus and its internal structure
        mockAmadeus = mock(Amadeus.class);
        mockShopping = mock(Shopping.class);
        mockFlightOffersSearch = mock(FlightOffersSearch.class);

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
}
