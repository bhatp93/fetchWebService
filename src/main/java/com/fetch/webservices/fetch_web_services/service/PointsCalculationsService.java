package com.fetch.webservices.fetch_web_services.service;

import com.fetch.webservices.fetch_web_services.requestResponseModels.Receipt;

public interface PointsCalculationsService {

    boolean calculatePoints(Receipt receipt, String Id);
}
