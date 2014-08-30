package com.service.impl;

import java.util.List;

import com.bean.Equipment;
import com.dao.impl.EquipmentDaoImpl;
import com.service.EquipService;

public class EquipServiceImpl implements EquipService{

	public List<Equipment> getEquips(){
		return new EquipmentDaoImpl().getEquips();
	}


}
