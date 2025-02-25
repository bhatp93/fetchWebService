package com.fetch.webservices.fetch_web_services.repository;

import com.fetch.webservices.fetch_web_services.requestResponseModels.Receipt;

public interface KeyValueDataManager {
    boolean addData(String Id, int points);
    int getData(String id);
    boolean isUniqueId(String receiptId);

}
