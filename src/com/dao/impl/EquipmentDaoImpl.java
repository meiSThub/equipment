package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtils;

import com.bean.Equipment;

/**
 * @author Administrator
 *
 */
public class EquipmentDaoImpl {
	
	private Connection conn=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	public List<Equipment> getEquips(){
		String sql="select * from equip";
		List<Equipment> equips=new ArrayList<Equipment>();
		try {
			conn=JDBCUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					Equipment e=new Equipment();
					e.setAddress(rs.getString("address"));
					e.setCheckMan(rs.getString("check_man"));
					e.setDate(rs.getDate("date"));
					e.setId(rs.getInt("id"));
					e.setName(rs.getString("name"));
					e.setNumber(rs.getInt("number"));
					e.setPart(rs.getString("part"));
					e.setQualityGrade(rs.getInt("quality_grade"));
					e.setTechnicalStatus(rs.getString("tech_status"));
					e.setType(rs.getString("type"));
					e.setUnit(rs.getString("unit"));
					equips.add(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equips;
	}
}
