package com.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.JDBCUtils;

import com.bean.Equipment;


public class ExcelPOI  {
	
	/**
	 * 把数据存储到Excel文件当中
	 * @throws IOException
	 */
	private static void POICreateExcel() throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("equipment");//0行
		
		for(int i=0;i<4000;i++){
			HSSFRow row = sheet.createRow((short) i);// 创建一行
			for(int j=0;j<1;j++){
				row.createCell((short) j).setCellValue(i);
				row.createCell((short) j+1).setCellValue("equip"+i);
				row.createCell((short) j+2).setCellValue("aa-bb-cc"+i);
				row.createCell((short) j+3).setCellValue("个");
				row.createCell((short) j+4).setCellValue(i+1);
				row.createCell((short) j+5).setCellValue(new Date());
				
				row.createCell((short) j+6).setCellValue(j+1);
				row.createCell((short) j+7).setCellValue("良");
				row.createCell((short) j+8).setCellValue("武汉青山区第"+i+"号");
				row.createCell((short) j+9).setCellValue("person"+i);
				row.createCell((short) j+10).setCellValue("后勤部");
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream("C:/Users/Administrator/Desktop/web2.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	

	/**
	 * 读取Excel文件的数据
	 * @param fileName 文件的路径
	 * @return
	 * @throws IOException
	 */
	public static List<Equipment> POIReadExcel(String fileName) throws IOException{
		
		List<Equipment> equips=new ArrayList<Equipment>();//存储Equipment对象
		
		try{//读取07版本的Excel文件中的数据
			equips=POIReadExcel07(fileName);
		}catch(Exception e){
			//读取03版本的Excel文件中的数据
			equips=POIReadExcel03(fileName);
		}
		return equips;
	}
	/**
	 * 把Excel文件中的数据存储到数据库中
	 * @param fileName 文件路径名
	 */
	public static void addExcelToDB(String fileName){
		String sql="insert into equip(name,type,unit,number,date,quality_grade,tech_status,address,check_man,part)values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=null;
		Connection conn=null;
		try {
			conn=JDBCUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			//根据Excel文件的路径，找到文件并读出文件中的数据
			List<Equipment> equips=POIReadExcel(fileName);
			
			for(Equipment e:equips){
				pstmt.setString(1, e.getName());
				pstmt.setString(2, e.getType());
				pstmt.setString(3, e.getUnit());
				pstmt.setInt(4,e.getNumber());
				pstmt.setDate(5,(java.sql.Date) new Date(12));
				pstmt.setInt(6, e.getQualityGrade());
				pstmt.setString(7, e.getTechnicalStatus());
				pstmt.setString(8, e.getAddress());
				pstmt.setString(9, e.getCheckMan());
				pstmt.setString(10, e.getPart());
				pstmt.executeUpdate();//把Excel文件的数据存储到数据库中
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 读取Excel文件，生成Workbook对象
	 * @param filename  文件的路径
	 * @return
	 * @throws IOException
	 */
	private static Workbook readFile(String filename) throws IOException {
		//POISFileSystem fs=new POIFSFileSystem(is);
		Workbook wb=null;
		try{
			wb=new XSSFWorkbook(new FileInputStream(filename));
		}catch(Exception e){
			wb=new HSSFWorkbook(new FileInputStream(filename));
		}
		return wb;
	}
	
	/**
	 * 读取03版的Excel文件,并把数据存储到数据库中
	 * @param fileName	
	 * @throws IOException
	 */
	public static List<Equipment> POIReadExcel03(String fileName) throws IOException {
		
		List<Equipment> equips=new ArrayList<Equipment>();//存储Equipment对象
		
		HSSFWorkbook hwb= (HSSFWorkbook) ExcelPOI.readFile(fileName);
		for (int k = 0; k < hwb.getNumberOfSheets(); k++) {
			//读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
			HSSFSheet sheet = hwb.getSheetAt(k);
			
			int rows = sheet.getPhysicalNumberOfRows();//获取行数
			
			//循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
			//如果总共有3条记录，那获取到的最后记录号就为2，因为是从0开始的
			//int rows=sheet.getLastRowNum();
			for (int r = 0; r < rows; r++) {//行循环
				HSSFRow row = sheet.getRow(r);//创建一个行对象
				if (row == null) {
					continue;
				}
				Equipment equip=new Equipment();
//				equip.setId(Integer.parseInt(ExcelPOI.getCellValue(row, 0)));
				equip.setName(ExcelPOI.getCellValue(row, 1));
				equip.setType(ExcelPOI.getCellValue(row, 2));
				equip.setUnit(ExcelPOI.getCellValue(row, 3));
				equip.setNumber((int)Double.parseDouble(ExcelPOI.getCellValue(row, 4).trim()));
				equip.setDate(new Date());
				
				equip.setQualityGrade((int)Double.parseDouble(ExcelPOI.getCellValue(row, 4).trim()));
				equip.setTechnicalStatus(ExcelPOI.getCellValue(row, 7));
				equip.setAddress(ExcelPOI.getCellValue(row, 8));
				equip.setCheckMan(ExcelPOI.getCellValue(row, 9));
				equip.setPart(ExcelPOI.getCellValue(row, 10));
				equips.add(equip);
			}
		}
		return equips;
	}
	
	/**
	 * 获取每行指定列的值
	 * @param row HSSFRow对象
	 * @param num 要取数据的字段编号，与数据库中的字段编号对应
	 * @return  返回取得的数据
	 */
	public static String getCellValue(HSSFRow row,int num){
		
		int cells = row.getPhysicalNumberOfCells();//获取列数
		String value=null;
//		for (int c = 0; c < cells; c++) {//列循环
			HSSFCell cell = row.getCell(num);//创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
		
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA:
					value =cell.getCellFormula();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					value =""+cell.getNumericCellValue()+"";
					break;
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				default:
				}
			
//		}
		return value;
	}
	
	/**
	 * 获取每行指定列的值
	 * @param row XSSFRow对象
	 * @param num 要取数据的字段编号，与数据库中的字段编号对应
	 * @return  返回取得的数据
	 */
	public static String getCellValue(XSSFRow row,int num){
		
		int cells = row.getPhysicalNumberOfCells();//获取列数
		String value=null;
		XSSFCell cell = row.getCell(num);//创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
		
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_FORMULA:
			value =cell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			value =""+cell.getNumericCellValue()+"";
			break;
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		default:
		}
		return value;
	}
	/**
	 * 读取07版的Excel文件
	 * @param fileName 需要读取的文件的路径名
	 * @throws IOException 异常
	 */
	public static List<Equipment> POIReadExcel07(String fileName) throws IOException {
		
		List<Equipment> equips=new ArrayList<Equipment>();//存储Equipment对象
		
		XSSFWorkbook xwb=(XSSFWorkbook) ExcelPOI.readFile(fileName);//把一张xls的数据表读到wb里
		System.out.println("Data dump:\n"+xwb.getNumberOfSheets());
		for (int k = 0; k < xwb.getNumberOfSheets(); k++) {
			//读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
			XSSFSheet sheet = xwb.getSheetAt(k);
			
			int rows = sheet.getPhysicalNumberOfRows();//获取行数
			
			//循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
			//如果总共有3条记录，那获取到的最后记录号就为2，因为是从0开始的
			//int rows=sheet.getLastRowNum();
			for (int r = 0; r < rows; r++) {//行循环
				
				XSSFRow row = sheet.getRow(r);//创建一个行对象
				if (row == null) {
					continue;
				}
				Equipment equip=new Equipment();
//				equip.setId(Integer.parseInt(ExcelPOI.getCellValue(row, 0)));
				equip.setName(ExcelPOI.getCellValue(row, 1));
				equip.setType(ExcelPOI.getCellValue(row, 2));
				equip.setUnit(ExcelPOI.getCellValue(row, 3));
				equip.setNumber((int)Double.parseDouble(ExcelPOI.getCellValue(row, 4).trim()));
				equip.setDate(new Date());
				
				equip.setQualityGrade((int)Double.parseDouble(ExcelPOI.getCellValue(row, 4).trim()));
				equip.setTechnicalStatus(ExcelPOI.getCellValue(row, 7));
				equip.setAddress(ExcelPOI.getCellValue(row, 8));
				equip.setCheckMan(ExcelPOI.getCellValue(row, 9));
				equip.setPart(ExcelPOI.getCellValue(row, 10));
				equips.add(equip);
			}
		}
		return equips;
	}
}