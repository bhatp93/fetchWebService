package com.fetch.webservices.fetch_web_services.repository.impl;

import com.fetch.webservices.fetch_web_services.Database.KeyValueDB;
import com.fetch.webservices.fetch_web_services.repository.KeyValueDataManager;
import com.fetch.webservices.fetch_web_services.requestResponseModels.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class KeyValueDataManagerimpl implements KeyValueDataManager {

    @Autowired
    KeyValueDB keyValueDB;
    @Override
    public boolean addData(String id, int points) {
        try{
            keyValueDB.getReceiptDB().put(id, points);
        }
        catch(Exception e){
            return false;
        }

        return  true;
    }

    @Override
    public int getData(String id) {
        Integer points = keyValueDB.getReceiptDB().get(id);
        return Objects.requireNonNullElse(points, -1);
    }

    @Override
    public boolean isUniqueId(String receiptID) {
        return keyValueDB.getReceiptDB().containsKey(receiptID);
    }


}
