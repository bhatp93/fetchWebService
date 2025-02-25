package com.fetch.webservices.fetch_web_services.service.impl;

import com.fetch.webservices.fetch_web_services.repository.KeyValueDataManager;
import com.fetch.webservices.fetch_web_services.requestResponseModels.Receipt;
import com.fetch.webservices.fetch_web_services.service.RequestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RequestDataServiceImpl implements RequestDataService {
    @Autowired
    KeyValueDataManager keyValueDataManager;

    @Override
    public boolean validateReceipt(Receipt receipt) {
        if(receipt.getRetailer() == null || receipt.getRetailer().isEmpty())
            return false;
        if(receipt.getPurchaseDate() == null || receipt.getPurchaseTime() == null)
            return false;
        if(receipt.getItems() == null)
            return false;
        for(int i=0; i < receipt.getItems().length;i++){
            String price = receipt.getItems()[i].getPrice();
            if(price.isEmpty())
                return false;
            for(int j=0; j<price.length();j++){
                if(!Character.isDigit(price.charAt(j)) && price.charAt(j) != '.' )
                    return false;
            }
            if(receipt.getItems()[i].getShortDescription() == null || receipt.getItems()[i].getShortDescription().isEmpty())
                return false;
        }

        String total = receipt.getTotal();
        if(total == null || total.isEmpty())
            return false;
        for(int i=0; i<total.length();i++){
            if(!Character.isDigit(total.charAt(i)) && total.charAt(i) != '.' )
                return false;
        }

        return true;
    }

    public String generateId(){
        char[] characters ="abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

        Random r = new Random();
        String newId = "";
        do{
            StringBuilder generatedId = new StringBuilder();
            for(int i =1; i <= 36; i++) {
                if(i%9 ==0 && i!=36)
                    generatedId.append("-");
                else{
                    int index = (int) (r.nextInt(characters.length));
                    generatedId.append(characters[index]);
                }
            }
            newId = generatedId.toString();
        }while(keyValueDataManager.isUniqueId(newId));
        return newId;
    }

    @Override
    public boolean isIdValid(String id) {
        return keyValueDataManager.isUniqueId(id);
    }

    @Override
    public Integer getPoints(String id) {
        return  keyValueDataManager.getData(id);
    }
}
