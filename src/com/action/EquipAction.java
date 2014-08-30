package com.action;

import java.util.List;

import com.bean.Equipment;
import com.opensymphony.xwork2.ActionSupport;
import com.service.impl.EquipServiceImpl;
import com.service.impl.ExcelPOIServiceImpl;



public class EquipAction extends ActionSupport{
	
	private List<Equipment> equips=null;
	private String fileName=null;//Excel文件路径名

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Equipment> getEquips() {
		return equips;
	}

	public void setEquips(List<Equipment> equips) {
		this.equips = equips;
	}
	/**
	 * 取出数据库中的数据
	 * @return
	 */
	public String list(){
		equips=new EquipServiceImpl().getEquips();
		return "success";
	}
	/**
	 * 把Excel文件中的数据存储到数据库中
	 * @return
	 */
	public String add(){
		String status=null;
		System.out.println("路径是："+fileName);
		if(fileName!=null||!"".equals(fileName)){
			new ExcelPOIServiceImpl().addExcelToDB(fileName);
			status="success";
			System.out.println("11111111111111111");
		}else{
			status="error";
		}
		System.out.println("222222222222");
		return status;
	}

}
