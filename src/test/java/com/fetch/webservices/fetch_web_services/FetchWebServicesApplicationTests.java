package com.fetch.webservices.fetch_web_services;

import com.fetch.webservices.fetch_web_services.repository.KeyValueDataManager;
import com.fetch.webservices.fetch_web_services.requestResponseModels.Item;
import com.fetch.webservices.fetch_web_services.requestResponseModels.Receipt;
import com.fetch.webservices.fetch_web_services.service.PointsCalculationsService;


import com.fetch.webservices.fetch_web_services.service.impl.PointsCalculationServiceImpl;
import com.fetch.webservices.fetch_web_services.service.impl.RequestDataServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class FetchWebServicesApplicationTests {

	@Mock
	KeyValueDataManager keyValueDataManager;

	@InjectMocks
	PointsCalculationServiceImpl pointsCalculationsService;

	@InjectMocks
	RequestDataServiceImpl requestDataService;

	Receipt testReceipt;
	Item[] itemList;

	@BeforeEach
	void setUp(){
		testReceipt = Mockito.mock(Receipt.class);
		when(testReceipt.getRetailer()).thenReturn("retailerName");
		when(testReceipt.getTotal()).thenReturn(String.valueOf(9.00));
		Item item1 = new Item()	;
		item1.setShortDescription("Gatorade");
		item1.setPrice("2.25");
		Item item2 = new Item()	;
		item2.setShortDescription("Pepsi");
		item2.setPrice("2.25");
		itemList = new Item[]{item1, item2};
		when(testReceipt.getItems()).thenReturn(itemList);
		when(testReceipt.getPurchaseDate()).thenReturn(LocalDate.parse("2022-03-20"));
		when(testReceipt.getPurchaseTime()).thenReturn(LocalTime.parse("14:33"));
	}


	@Test
	void contextLoads() {
	}

	@Test
	public void GivenStubbedReceipt_whenCalculateMethodCalled_ReturnTrueForCorrectPoints(){
		when(keyValueDataManager.addData("randomString123", 102)).thenReturn(true);

		boolean returnValue = pointsCalculationsService.calculatePoints(testReceipt, "randomString123");
		Assertions.assertTrue(returnValue);
	}
	@Test
	public void GivenStubbedReceipt_whenCalculateMethodCalled_ReturnFalseForIncorrectPoints(){
		when(keyValueDataManager.addData("randomString123", 103)).thenReturn(false);

		boolean returnValue = pointsCalculationsService.calculatePoints(testReceipt, "randomString123");
		Assertions.assertFalse(returnValue);
	}

	@Test
	public void GivenStubbedReceipt_whenValidateReceiptCalled_returnsTrueForValidReceipt(){
		boolean returnValue = requestDataService.validateReceipt(testReceipt);
		Assertions.assertTrue(returnValue);
	}
	@Test
	public void GivenStubbedReceipt_whenValidateReceiptCalled_returnsFalseForNullRetailer(){
		when(testReceipt.getRetailer()).thenReturn(null);
		boolean returnValue = requestDataService.validateReceipt(testReceipt);
		Assertions.assertFalse(returnValue);
	}

	@Test
	public void GivenStubbedReceipt_whenValidateReceiptCalled_returnsFalseForNullPurchaseDate(){
		when(testReceipt.getPurchaseDate()).thenReturn(null);
		boolean returnValue = requestDataService.validateReceipt(testReceipt);
		Assertions.assertFalse(returnValue);
	}
	@Test
	public void GivenStubbedReceipt_whenValidateReceiptCalled_returnsFalseForInvalidPrice(){
		Item item3 = new Item()	;
		item3.setShortDescription("Pepsi");
		item3.setPrice("2.d5");
		itemList = Arrays.copyOf(itemList, itemList.length + 1);
		itemList[itemList.length - 1] = item3;
		when(testReceipt.getItems()).thenReturn(itemList);
		boolean returnValue = requestDataService.validateReceipt(testReceipt);
		Assertions.assertFalse(returnValue);
	}

	@Test
	public void GivenNothing_whenGenerateIdCalled_returnsFalseForNullPurchaseDate(){
		when(keyValueDataManager.isUniqueId(anyString())).thenReturn(false);
		String returnValue = requestDataService.generateId();
		Assertions.assertNotNull(returnValue);
	}
}
