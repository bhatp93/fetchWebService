package com.fetch.webservices.fetch_web_services.service.impl;

import com.fetch.webservices.fetch_web_services.repository.KeyValueDataManager;
import com.fetch.webservices.fetch_web_services.requestResponseModels.Receipt;
import com.fetch.webservices.fetch_web_services.service.PointsCalculationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PointsCalculationServiceImpl implements PointsCalculationsService {

    @Autowired
    KeyValueDataManager keyValueDataManager;
    @Override
    public boolean calculatePoints(Receipt receipt, String id){
        int totalPoints = 0;
        String retailerName = receipt.getRetailer();
        for(int i=0; i<retailerName.length();i++){
            char c = retailerName.charAt(i);
            if(Character.isAlphabetic(c) || Character.isDigit(c))
                totalPoints++;
        }

        double receiptTotal = Double.parseDouble(receipt.getTotal());
        if(receiptTotal % 1 == 0)
            totalPoints = totalPoints + 50;

        if(receiptTotal % 0.25 == 0)
            totalPoints = totalPoints + 25;
        int numberOfItems = receipt.getItems().length;
        totalPoints = totalPoints + (numberOfItems/2 * 5);

        for(int i=0; i<receipt.getItems().length;i++){
            String shortDescription = receipt.getItems()[i].getShortDescription();
            shortDescription = shortDescription.trim();
            if(shortDescription.length() % 3 ==0){
                double price = Double.parseDouble(receipt.getItems()[i].getPrice());
                double points = Math.ceil(price * 0.2);
                totalPoints = totalPoints + (int)points;
            }
        }
        LocalDate purchaseDate = receipt.getPurchaseDate();
        int day = purchaseDate.getDayOfMonth();
        if(day % 2 != 0)
            totalPoints = totalPoints + 6;
        int purchaseTime = receipt.getPurchaseTime().getHour();
        if(purchaseTime>=14 && purchaseTime <=16 )
            totalPoints = totalPoints  + 10;
        return keyValueDataManager.addData(id, totalPoints);
    }
}
