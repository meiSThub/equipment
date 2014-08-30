package com.service;

import java.util.List;

import com.bean.Equipment;

public interface EquipService {

	/**
	 * 获取全部数据
	 * @return
	 */
	public abstract List<Equipment> getEquips();
	/**
	 * 添加一条数据
	 */
	//public abstract void addEquips();

}