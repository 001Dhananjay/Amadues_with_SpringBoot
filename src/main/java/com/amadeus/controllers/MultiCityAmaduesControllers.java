package com.amadeus.controllers;

import com.amadeus.service.MultiCityAmaduesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
//@RestController
//@RequestMapping("/v2/")
public class MultiCityAmaduesControllers {


    @Autowired
    private MultiCityAmaduesService amadeusService;

    @PostMapping("/structured-search")
    @Operation(summary = "find multi city flight offer search ")
    public ResponseEntity<?> multiCityFlightOfferSearch(@RequestBody Map<String, Object> flightRequest) {
        try {     //multiCityFlightOfferSearch

            String result = amadeusService.searchFlightOffers(flightRequest);

            // Convert raw JSON string to a JSON object (Map)
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(result, Map.class);
            System.out.println(jsonMap.size());

            return ResponseEntity.ok(jsonMap);  // Returned as proper application/json


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


}







/*


{
    "originDestinations": [
        {
            "id": "1",
            "originLocationCode": "DEL",
            "destinationLocationCode": "BOM",
            "departureDateTimeRange": {
                "date": "2025-07-03"
            }
        },
        {
            "id": "2",
            "originLocationCode": "BOM",
            "destinationLocationCode": "BKK",
            "departureDateTimeRange": {
                "date": "2025-07-05"
            }
        },
        {
            "id": "3",
            "originLocationCode": "BKK",
            "destinationLocationCode": "DEL",
            "departureDateTimeRange": {
                "date": "2025-07-08"
            }
        }
    ],
    "travelers": [
        {
            "id": "1",
            "travelerType": "ADULT",
            "fareOptions": [
                "STANDARD"
            ]
        }
    ],
    "sources": [
        "GDS"
    ],
    "searchCriteria": {
        "maxFlightOffers": 1
    }
}



 */