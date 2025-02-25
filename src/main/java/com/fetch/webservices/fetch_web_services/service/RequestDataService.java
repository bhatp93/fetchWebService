package com.fetch.webservices.fetch_web_services.service;

import com.fetch.webservices.fetch_web_services.requestResponseModels.Receipt;

public interface RequestDataService {

    boolean validateReceipt(Receipt receipt);
    String generateId();

    boolean isIdValid(String Id);

    Integer getPoints(String Id);
}
