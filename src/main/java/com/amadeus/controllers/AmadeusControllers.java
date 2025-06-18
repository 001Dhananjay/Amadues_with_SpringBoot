package com.amadeus.controllers;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.service.AmadeusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v2/")
public class AmadeusControllers {

    @Autowired
    private AmadeusService amadeusService;

/*
    @GetMapping("search")
    public ResponseEntity<String> flightOfferSearch() throws ResponseException, JsonProcessingException {
       FlightOfferSearch[] offerSearches= amadeusService.flightOfferSearches();
        for(FlightOfferSearch flightOfferSearch:offerSearches){
            System.out.println(flightOfferSearch);
        }


        Gson gson = new Gson();
        String jsonOutput = gson.toJson(offerSearches);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonOutput);
       // return new ResponseEntity<>("Flight search executed successfully", HttpStatus.OK);
    }
*/



    @GetMapping("/search")
    public ResponseEntity<String> flightOfferSearch(@RequestParam Map<String, String> queryParams)
            throws ResponseException {

        FlightOfferSearch[] offers = amadeusService.flightOfferSearches(queryParams);

        // Convert response to JSON using Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(offers);

        System.out.println(offers.length);
        return ResponseEntity.ok(jsonOutput);
    }



}
