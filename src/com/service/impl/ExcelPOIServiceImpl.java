package com.service.impl;

import com.poi.ExcelPOI;
import com.service.ExcelPOIService;

public class ExcelPOIServiceImpl implements ExcelPOIService {

	

	@Override
	public void addExcelToDB(String fileName) {
		System.out.println("aaaaaaaaaaaaaaaaa");
		ExcelPOI.addExcelToDB(fileName);
	}
}
