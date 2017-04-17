package com.zjcds.common.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;


/**
 * 基于apache poi包的工具方法
 * @author niezhegang
 */
public class POIUtils {

	private static FastDateFormat formater = FastDateFormat.getInstance("yyyy-MM-dd");
	/**
	 * 根据传入的输入流创建workbook
	 * @param is
	 * @return
	 * 修改说明：
	 */
	public static Workbook createWorkBook(InputStream is) {
		return createWorkBook(is,null);
	}

	/**
	 * 创建workbook
	 * @param is
	 * @param password
	 * @return
	 * @throws Exception
     */
	public static Workbook createWorkBook(InputStream is,String password) {
		Workbook workbook = null;
		try{
			workbook = WorkbookFactory.create(is,password);
		}
		catch(Exception e){
			throw new RuntimeException("解析Excel文件格式失败，传入的excel格式不支持。",e);
		}
		return workbook;
	}

	
	/**
	 * 抽取excel文件数据至List<String[]>数据结构
	 * 失败抛出异常
	 * @param excelFile
	 * @param sheetName
	 * @return
	 * @throws Exception
	 * 创建日期：2014年5月31日
	 * 修改说明：
	 */
	public static List<String[]> extractData(File excelFile,String sheetName) {
		return extractData(excelFile,sheetName,-1);
	}

	/**
	 * 抽取excel文件数据至List<String[]>数据结构
	 * @param is
	 * @param sheetName
     * @return
     */
	public static List<String[]> extractData(InputStream is,String sheetName) {
		return extractData(is,sheetName,-1);
	}
	
	/**
	 * 抽取excel文件数据至List<String[]>数据结构
	 * 失败抛出异常
	 * @param excelFile
	 * @param sheetName
	 * @param extractToColumnNum  0基础的数值计算法，设置为负数则按每行的实际列数抽取，否则按设置值抽取，这样做的目的是可以对齐列
	 * @return
	 * @throws Exception
	 * 修改说明：
	 */
	public static List<String[]> extractData(File excelFile,String sheetName,int extractToColumnNum) {
		if(excelFile == null || !excelFile.exists())
			throw new RuntimeException("文件不存在或被移除！");
		InputStream is = null;
		try{
			is = new FileInputStream(excelFile);
			return extractData(is,sheetName,extractToColumnNum);
		}
		catch(FileNotFoundException e){
			throw new RuntimeException("文件不存在",e);
		}
		finally{
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * 抽取excel文件数据至List<String[]>数据结构
	 * @param is
	 * @param sheetName
	 * @param extractToColumnNum
     * @return
     */
	public static List<String[]> extractData(InputStream is,String sheetName,int extractToColumnNum) {
		try{
			if(is == null)
				throw new IllegalArgumentException("输入流不能为空！");
			Workbook workbook = createWorkBook(is);
			Sheet sheet = workbook.getSheet(sheetName);
			if(sheet == null)
				throw new RuntimeException("文件中未找到名为"+sheetName+"的sheet。");
			return extractData(sheet,extractToColumnNum);
		}
		catch(Exception e){
			throw new RuntimeException("抽取数据失败",e);
		}
	}
	
	/**
	 * 抽取excel文件数据至List<String[]>数据结构
	 * 失败抛出异常
	 * @param excelFile
	 * @param sheetNo
	 * @param extractToColumnNum  0基础的数值计算法，设置为负数则按每行的实际列数抽取，否则按设置值抽取，这样做的目的是可以对齐列
	 * @return
	 * @throws Exception
	 * 创建日期：2014年5月31日
	 * 修改说明：
	 */
	public static List<String[]> extractData(File excelFile,int sheetNo,int extractToColumnNum) {
		if(excelFile == null || !excelFile.exists())
			throw new RuntimeException("文件不存在或被移除！");
		InputStream is = null;
		try{
			is = new FileInputStream(excelFile);
			return extractData(is,sheetNo,extractToColumnNum);
		}
		catch(FileNotFoundException e){
			throw new RuntimeException("文件不存在!",e);
		}
		finally{
			IOUtils.closeQuietly(is);
		}
	}

	public static List<String[]> extractData(InputStream is,int sheetNo,int extractToColumnNum) {
		try{
			if(is == null)
				throw new IllegalArgumentException("输入流不能为空！");
			Workbook workbook = createWorkBook(is);
			Sheet sheet = workbook.getSheetAt(sheetNo);
			if(sheet == null)
				throw new RuntimeException("文件中未找到索引为"+sheetNo+"的sheet。");
			return extractData(sheet,extractToColumnNum);
		}
		catch (Exception e) {
			throw new RuntimeException("抽取数据失败",e);
		}
	}
	
	/**
	 * 从sheet中抽取数据至List<String[]>数据结构中
	 * @param sheet
	 * @return
	 * 创建日期：2014年5月31日
	 * 修改说明：
	 */
	private static List<String[]> extractData(Sheet sheet,int extractToColumnNum){
		List<String[]> ret = new ArrayList<String[]>();
		Row row;
		Cell cell;
		String[] rowData;
		int maxColumnNum = 0;
		for(int i = 0; i <= sheet.getLastRowNum();i++){
			row = sheet.getRow(i);
			if(row == null || row.getLastCellNum() < 0)
				continue;
			if(extractToColumnNum < 0) {
				maxColumnNum = row.getLastCellNum() - 1;
			}
			else
				maxColumnNum = extractToColumnNum;
			rowData = new String[maxColumnNum + 1];
			for(int j = 0;j <= maxColumnNum;j++){
				cell = row.getCell(j);
				if(cell != null)
					rowData[j] = getCellValueToString(cell);
			}
			ret.add(rowData);
		}
		return ret;
	}
	
	/**
	 * 辅助方法，以String格式获取cell值
	 * @param cell
	 * @return
	 * 创建日期：2014年5月31日
	 * 修改说明：
	 */
	private static String getCellValueToString(Cell cell){
		String ret = null;
		int type = cell.getCellType();
		if(type == Cell.CELL_TYPE_STRING || 
				type == Cell.CELL_TYPE_BLANK || 
					type == Cell.CELL_TYPE_BOOLEAN ||
						type == Cell.CELL_TYPE_ERROR){
			ret = cell.getStringCellValue();
		}
		else if(type == Cell.CELL_TYPE_NUMERIC || type == Cell.CELL_TYPE_FORMULA){
			if(DateUtil.isCellDateFormatted(cell)){
				ret = formater.format(cell.getDateCellValue());
			}
			else{
				BigDecimal dTemp = new BigDecimal(cell.getNumericCellValue());
				BigInteger iTemp = dTemp.toBigInteger();
				if(dTemp.compareTo(new BigDecimal(iTemp)) == 0)
					ret = iTemp.toString();
				else
					ret = Double.toString(cell.getNumericCellValue());
			}
		}
//		else if(type == Cell.CELL_TYPE_FORMULA){
//			FormulaEvaluator evaluator = cell.getSheet().getWorkbook()
//					.getCreationHelper().createFormulaEvaluator();
//			evaluator.evaluateFormulaCell(cell);
//			CellValue cellValue = evaluator.evaluate(cell);
//			ret = Double.toString(cellValue.getNumberValue());
//		}
		else
			throw new RuntimeException("不支持得cell类型:类型值为"+type);
		return StringUtils.trim(ret);
	}

}
