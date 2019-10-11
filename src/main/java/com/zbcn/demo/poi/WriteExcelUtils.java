package com.zbcn.demo.poi;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * 写excel的工具方法
 *
 * @author
 * @create 2018-06-21 20:20
 **/
public class WriteExcelUtils {
    private static final String EXCEL_XLS = "xls";

    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 从指定excel中写入数据
     * @return
     * @throws IOException
     */
    public static InputStream writeExcel(Map<String,List<Map<String,Object>>> dataMap, File xlsxFile){
        //写入到指定位置
        InputStream in = null;
        String tempPath =  "ajpc" + File.separator + "downloads" + File.separator
                + "pcgz"+ File.separator;
        File destfile = getDestFile(tempPath);
        try {
            Workbook workBook = getWorkBook(xlsxFile);
            writeData2Sheet(dataMap, workBook);
            OutputStream out =  new FileOutputStream(destfile.getPath());
            workBook.write(out);
            out.close();
            in = new BufferedInputStream(new FileInputStream(destfile));
//			in.close();
        } catch (IOException e) {
            throw new RuntimeException("写excel失败", e);
        }finally{
            FileUtils.deleteQuietly(destfile);
        }
        return in;
    }

    /**
     * 设置工作簿的单元格样式
     * @param workBook
     */
    private static CellStyle setWorkBookStyle(Workbook workBook) {
        CellStyle style = workBook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setWrapText(true);//设置自动换行
        return style;
    }

    /**
     * 获取destFile
     * @param tempPath
     * @return
     */
    private static File getDestFile(String tempPath) {
        String filename =  "ajpcgz.xls";
        String sourceName = tempPath + filename;
        File folderFile = new File(tempPath);
        File destfile = new File(sourceName);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        return destfile;
    }

    /**
     * 向工作薄中写入数据
     * @param dataMap
     * @param workBook
     */
    private static void writeData2Sheet(Map<String,List<Map<String,Object>>> dataMap, Workbook workBook) {
        CellStyle cellStyle = setWorkBookStyle(workBook);
        Iterator<Map.Entry<String, List<Map<String, Object>>>> it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<Map<String,Object>>> entry =  it.next();
            String sheetName = entry.getKey();
            List<Map<String, Object>> dataList = entry.getValue();
            Sheet sheet = workBook.getSheet(sheetName);
            writeDate2Sheet(sheet,dataList,cellStyle);
        }
    }

    /**
     * 写入数据到sheet
     * @param sheet
     * @param dataList
     */
    private static void writeDate2Sheet(Sheet sheet,
                                        List<Map<String, Object>> dataList, CellStyle cellStyle) {
        Integer columnNumCount = null;
        if(CollectionUtils.isNotEmpty(dataList)){

            for (int j = 0; j < dataList.size(); j++) {
                Row row = sheet.createRow(j + 1);
                Map<String, Object> map = dataList.get(j);
                columnNumCount = map.size();
                for (int i = 0; i < columnNumCount; i++) {
                    Cell cell = row.createCell(i);
                    Object obj = map.get("row_"+i);
                    if(obj == null){
                        obj = StringUtils.EMPTY;
                    }
                    if(obj instanceof String){
                        cell.setCellValue((String)obj);
                    } else if(obj instanceof Boolean){
                        cell.setCellValue((boolean)obj);
                    }else if(obj instanceof Date){
                        cell.setCellValue((Date)obj);
                    }else if(obj instanceof Double){
                        cell.setCellValue((Double)obj);
                    }else {
                        throw new IllegalArgumentException("参数格式不支持");
                    }
                    cell.setCellStyle(cellStyle);
                }
            }
        }

    }

    /**
     * 获取workBook
     * @param xlsxFile
     * @return
     * @throws IOException
     */
    private static Workbook getWorkBook(File xlsxFile) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(xlsxFile);
        if(xlsxFile.getName().endsWith(EXCEL_XLS)){     //Excel;2003
            wb = new HSSFWorkbook(in);
        }else if(xlsxFile.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    public static void main(String[] args) throws IOException {

        Map<String, Object> dataMap=new HashMap<>();
        dataMap.put("BankName", "BankName");
        dataMap.put("Addr", "Addr");
        dataMap.put("Phone", "Phone");
        List<Map<String, Object>> list=new ArrayList<>();
        list.add(dataMap);
        File file = new File("D:/writeExcel.xlsx");
        Map<String,List<Map<String,Object>>> dataMap1 = new HashMap<>();
        writeExcel(dataMap1, file);

    }

}
