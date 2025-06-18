package com.amadeus.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AmadeusService {

  //  Amadeus amadeus=Amadeus.builder(System.getenv()).build();


    @Value("${amadeus.client-id}")
    private String clientId;

    @Value("${amadeus.client-secret}")
    private String clientSecret;
    private Amadeus amadeus;//=Amadeus.builder(clientId, clientSecret).build();

    @PostConstruct
    public void init(){
        amadeus=Amadeus.builder(clientId, clientSecret).build();
    }


/*
        public FlightOfferSearch[] flightOfferSearches() throws ResponseException{
            FlightOfferSearch[] offerSearches=amadeus.shopping.flightOffersSearch.get(Params.with("originLocationCode","SYD")
                    .and("destinationLocationCode","NYC")
                    .and("departureDate","2025-06-12").and("maxPrice",140000)
                    .and("adults",2).and("currencyCode","INR"));
            System.out.println(offerSearches.length);
            return offerSearches;
        }
*/


    public FlightOfferSearch[] flightOfferSearches(Map<String, String> paramsMap) throws ResponseException {
        Params params = null;

        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            if (params == null) {
                params = Params.with(entry.getKey(), entry.getValue());
            } else {
                params.and(entry.getKey(), entry.getValue());
            }
        }

        return amadeus.shopping.flightOffersSearch.get(params);
    }





}
