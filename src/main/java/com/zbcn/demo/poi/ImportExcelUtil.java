package com.zbcn.demo.poi;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel数据导入类
 *
 * @author
 * @create 2018-06-14 17:14
 **/
public class ImportExcelUtil {
    //2003版本的excel
    private final static String EXCEL_2003= "xls";

    private final static String EXCEL_2007= "xlsx";

    /**
     * 获取IO流中的数据
     * @param in
     * @param fileName
     * @return
     */
    public Map<String,List<List<Object>>> getBankListByExcel(InputStream in, String fileName){
        Map<String,List<List<Object>>> sheetMap = new HashMap<>();
        //创建工作薄
        try {
            Workbook work =getWorkBook(in,fileName);
            if (null == work) {
                throw new RuntimeException("创建Excel工作薄为空！");
            }
            int sheets = work.getNumberOfSheets();
            for(int i = 0; i<sheets; i++) {
                List<List<Object>> list = new ArrayList<>();
                Sheet sheet = work.getSheetAt(i);
                if(sheet == null){
                    continue;
                }
                analyseSheet(list, sheet);
                String sheetName = sheet.getSheetName();
                sheetMap.put(sheetName,list);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetMap;
    }

    /**
     * 分析工作薄
     * @param list
     * @param sheet
     */
    private void analyseSheet(List<List<Object>> list, Sheet sheet) {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        for (int j = firstRowNum; j<lastRowNum+1;j++){
            analyseRow(list, sheet, j);
        }
    }

    /**
     * 分析列
     * @param list
     * @param sheet
     * @param j
     */
    private void analyseRow(List<List<Object>> list, Sheet sheet, int j) {
        Row row;
        row = sheet.getRow(j);
        if (row == null || row.getFirstCellNum() == j) {
            return;
        }
        // 遍历所有的列
        List<Object> li = new ArrayList<>();
        for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
            Cell cell = row.getCell(y);
            li.add(this.getCellValue(cell));
        }
        list.add(li);
    }

    /**
     * 获取不同版本excel的文件
     * @param in
     * @param fileName
     * @return
     * @throws IOException
     */
    private Workbook getWorkBook(InputStream in, String fileName) throws IOException {
        Workbook wb = null;
        String fileType = FilenameUtils.getExtension(fileName);
        if(StringUtils.equals(fileType,EXCEL_2003)){
            wb = new HSSFWorkbook(in);
        }else if(StringUtils.equals(fileType,EXCEL_2007)){
            wb = new XSSFWorkbook(in);
        }else{
            throw new RuntimeException("解析的文件格式错误，文件名为："+fileName);
        }

        return wb;
    }

    /**
     * 获取列数据
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell){
        Object value = null;
        // 格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        // 日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        // 格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) {
        ImportExcelUtil importExcelUtil = new ImportExcelUtil();
        File file = new File("F:\\spgl\\案件评查\\v2.6\\评查规则导入设计\\评查规则.模板.xls");
        try {
            InputStream in = new FileInputStream(file);
            Map<String, List<List<Object>>> bankListByExcel = importExcelUtil.getBankListByExcel(in, file.getName());
            System.out.println(bankListByExcel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
