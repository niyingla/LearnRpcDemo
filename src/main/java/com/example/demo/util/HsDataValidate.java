package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: continutiy-parent
 * @description:
 * @author: xiaoye
 * @create: 2019-07-01 14:38
 **/
@Slf4j
public class HsDataValidate {

   // private static List<String> filedList;


    public static void validate(String filePathFrom, String filePathTo, String sheetNameFrom, String sheetNameTo, Integer startLineFrom, Integer startLineTo, List<String> filedList) throws Exception {
        Workbook sheets = WorkbookFactory.create(new FileInputStream(filePathFrom));
        Workbook wookBookTo =WorkbookFactory.create(new FileInputStream(filePathTo));
        Sheet sheetFrom = sheets.getSheet(sheetNameFrom);
        Sheet sheetTo = wookBookTo.getSheetAt(0); //从表默认第一个sheet
        //-----------------------主excel-------------------------
        //字段列  在第几行
        //获取每段标题行
        Row sheetRowFrom = sheetFrom.getRow(startLineFrom);
        //主excel需要检查的列名级在几行
        Map<String, Integer> filedMap = getValidateFiledMap(filedList, sheetRowFrom);
        Map<String, Map<String, String>> fieldValueFrom = getFieldValue(sheetFrom, filedList, startLineFrom, filedMap);

        //-----------------------从excel-------------------------
        Row sheetRowTo = sheetTo.getRow(startLineTo);
        //从excel需要检查的列名级在几行
        Map<String, Integer> filedMapTo = getValidateFiledMap(filedList, sheetRowTo);
        Map<String, Map<String, String>> fieldValueTo = getFieldValue(sheetTo, filedList, startLineTo, filedMapTo);

        //todo 比较数据
        compareData(fieldValueFrom, fieldValueTo,filedList);
    }

    /**
     * 比价数据
     * @param fieldValueFrom
     * @param fieldValueTo
     */
    private static void compareData(Map<String, Map<String, String>> fieldValueFrom, Map<String, Map<String, String>> fieldValueTo,List<String> filedList) {
        //获取两个对应对象
        Integer errorCount=0;
        for (Map.Entry<String, Map<String, String>> stringMapEntry : fieldValueFrom.entrySet()) {
            String key = stringMapEntry.getKey();
            Map<String, String> mapFrom = stringMapEntry.getValue();
            Map<String, String> mapTo = fieldValueTo.get(key.trim());
            if(mapTo == null){
                log.error("数据空：{}",mapFrom);
                errorCount++;
                continue;
            }
            //两个map比较
            compareMap(mapFrom, mapTo, key,filedList);
            fieldValueTo.remove(key);
        }
        log.info("----------------------------------------------------------------------");
        log.info("恒生剩余未匹配数量是【{}】：数据：[{}]",fieldValueTo.size(),fieldValueTo);
        log.info("比较完成！！！，本次共校验 [{}] 条数据,恒生表中空数据条数：[{}]",fieldValueFrom.size(),errorCount);

    }

    /**
     * 比较
     * @param mapFrom
     * @param mapTo
     */
    private static void compareMap(Map<String, String> mapFrom, Map<String, String> mapTo,String key,List<String> filedList) {
        if(!StringUtils.equals(mapFrom.get("size"),mapTo.get("size"))){
            log.error("\n错误key:{} 主：{} 从：{} value:[|{}|  |{}|]",key,mapFrom.get("size"),mapTo.get("size"),mapFrom,mapTo);
        }
        for (int i = 0; i < filedList.size(); i++) {
            String fieldKey = filedList.get(i);
            String valueFrom = mapFrom.get(fieldKey);
            String valueTo = mapTo.get(fieldKey);
            if(!StringUtils.equals(valueFrom, valueTo)){
                log.error("\n错误key:{} 字段：{} value:[|{}|  |{}|]",key,fieldKey,valueFrom,valueTo);
            }
        }

    }

    /**
     *
     * @param sheetFrom
     * @param filedList 需要校验字段
     * @param startLine 字段开始行
     * @param filedMap 字段和行数对应的map
     * @return
     */
    private static Map<String, Map<String, String>> getFieldValue(Sheet sheetFrom, List<String> filedList, int startLine, Map<String, Integer> filedMap) throws Exception{
        Map<String, Map<String, String>> map = new HashMap<>();
        int rows = sheetFrom.getPhysicalNumberOfRows();

        //循环所有数据行
        for (int i = startLine + 1; i < rows; i++) {
            Row row = sheetFrom.getRow(i);
            Map<String, String> valueMap = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            //循环数据列
            for (int j = 0; j < filedList.size(); j++) {
                String filedValue = filedList.get(j);
                Integer col = filedMap.get(filedValue);
                if (col == null) {
                    log.error(filedValue+"字段不存在");
                }
                Cell cell = row.getCell(col);
                //如果是数字类型 进行格式化
                String rawValue ="";
                if(cell==null){
                    log.error("空错误{} ",cell);
                    continue;
                }
                if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {

                    double numericCellValue = cell.getNumericCellValue();
                    rawValue = new DecimalFormat("0.00").format(numericCellValue);
                    valueMap.put(filedValue, rawValue.trim().replaceAll("\\.00", ""));
                } else {
                    cell.setCellType(CellType.STRING);
                    rawValue = cell.getStringCellValue();
                    //0开头替换  这行代码可以选择性打开
//                    if (rawValue != null && rawValue.trim().length() > 1) {
//                        rawValue = rawValue.replaceAll("^(0+)", "");
//                    }
                    valueMap.put(filedValue, rawValue.trim().replaceAll("\\.00", "").replaceAll("   ", "").replaceAll(" ",""));

                }
                sb.append(rawValue.trim().replaceAll("\\.00","").replaceAll("   ", "").replaceAll(" ",""));
            }
            String tmp =   DigestUtils.md5DigestAsHex(sb.toString().trim().getBytes());
            if(map.containsKey(tmp)){
                valueMap.put("size", (valueMap.get("size") == null) ? "2" : (
                        Integer.parseInt(valueMap.get("size")
                        ) + 1) + "");
            }
            map.put(tmp, valueMap);

        }
        return map;
    }

    /**
     * 获取
     * @param filedList 需要校验的字段数据
     * @param sheetRow excel字段列
     * @return
     */
    private static Map<String, Integer> getValidateFiledMap(List<String> filedList, Row sheetRow) {
        //获取列数
        int physicalNumberOfCells = sheetRow.getPhysicalNumberOfCells();
        //key 字段名 value 在第几列
        Map<String, Integer> filedMap = new HashMap<>(30);
        //虚幻所有字段
        for (int i = 0; i < physicalNumberOfCells; i++) {
            Cell cell = sheetRow.getCell(i);

            cell.setCellType(CellType.STRING);

            String rawValue = cell.getStringCellValue();

            if (StringUtils.isBlank(rawValue)) {
                continue;
            }
            //字段存在需要校验的list中
            if (filedList.indexOf(rawValue.trim()) >= 0) {
                filedMap.put(rawValue, i);
            }
        }
        return filedMap;
    }
}
