package com.fetch.webservices.fetch_web_services.controllers;


import com.fetch.webservices.fetch_web_services.Database.KeyValueDB;
import com.fetch.webservices.fetch_web_services.requestResponseModels.*;
import com.fetch.webservices.fetch_web_services.service.PointsCalculationsService;
import com.fetch.webservices.fetch_web_services.service.RequestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReceiptController {

    @Autowired
    RequestDataService requestDataService;

    @Autowired
    PointsCalculationsService pointsCalculationsService;


    @PostMapping(path="/receipts/process")
    public ResponseEntity<PostResponseObject> postReceipt(@RequestBody Receipt receipt){
        PostResponseObject responseObject = new PostResponseObject();
        if(!requestDataService.validateReceipt(receipt)){
            responseObject.setMessage("Incorrect Request Payload");
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }

        String id = requestDataService.generateId();

        boolean isPointCalculated = pointsCalculationsService.calculatePoints(receipt, id);
        if(!isPointCalculated){
            responseObject.setMessage("Error in points calculation");
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        responseObject.setId(id);
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @GetMapping(path = "/receipts/{id}/points")
    public ResponseEntity<GetResponseObject> getReceiptPoints(@PathVariable String id){
        GetResponseObject responseObject = new GetResponseObject();
        if(!requestDataService.isIdValid(id)){
            responseObject.setMessage("description: no receipt found for that id");
            return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
        }
        Integer points = requestDataService.getPoints(id);
        if(points == -1){
            responseObject.setMessage("description: Server Error");
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        responseObject.setPoints(points);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }


}
