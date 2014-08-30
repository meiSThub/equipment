package com.service;


public interface ExcelPOIService {

	/**
	 * 读取Excel文件的数据
	 * @param fileName 文件路径
	 * @return 返回List<Equipment>
	 * @throws Exception
	 */
	/*public abstract List<Equipment> POIReadExcelData(String fileName)
			throws Exception;
	*/
	/**
	 * 把Excel中的数据存储到数据库中
	 * @param fileName Excel文件的路径
	 */
	public abstract void addExcelToDB(String fileName);

}