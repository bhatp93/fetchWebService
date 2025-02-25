package com.fetch.webservices.fetch_web_services.Database;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class KeyValueDB {
    HashMap<String, Integer> receiptDB = new HashMap<>();

    public HashMap<String, Integer> getReceiptDB() {
        return receiptDB;
    }

    public void setReceiptDB(HashMap<String, Integer> receiptDB) {
        this.receiptDB = receiptDB;
    }
}
