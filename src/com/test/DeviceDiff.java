package com.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DeviceDiff {

//	  	SET @time = '2017-09-08';
//		SET @mac = '864270036535056';
//		SELECT * FROM detail_acc_order_000 o LEFT JOIN sc_basic.basic_data d
//		ON o.basicDataId = d.id WHERE deviceMac = @mac AND 
//		o.addTime between @time AND CONCAT(@time,' 23:59:59') ;
//
//		SELECT * FROM stat_device_ec_000 o LEFT JOIN sc_basic.basic_data d
//		ON o.basicDataId = d.id WHERE deviceMac = @mac AND
//		o.statDate = @time;
//
//		SELECT * FROM stat_device_pay_000 o LEFT JOIN sc_basic.basic_data d
//		ON o.basicDataId = d.id WHERE deviceMac = @mac AND
//		o.statDate = @time;	
		
//	8号的差异
//	862446035710062- allpay:274 sc:276
//	862810033350041- allpay:42 sc:52
//	864270036535056- allpay:60 sc:70
//	376-398
	
	public static void main(String[] args) {
		int allpaySum = 0, scSum = 0;;
		Map<String, String> allpay = new HashMap<>();
		Map<String, String> sc = new HashMap<>();
		setAllpay(allpay);
		setSc(sc);
		for (Map.Entry<String, String> entry : allpay.entrySet()) {
			// System.out.println("key= " + entry.getKey() + " and value= " +
			// entry.getValue());
			if (!sc.get(entry.getKey()).equals(entry.getValue())) {
				System.out.println(entry.getKey() + "- allpay:" + entry.getValue() + " sc:" + sc.get(entry.getKey()));
				allpaySum += Integer.parseInt(entry.getValue());
				scSum += Integer.parseInt(sc.get(entry.getKey()));
			}
		}
		System.out.println(allpaySum + "-" +scSum);
	}

	public static void setAllpay(Map<String, String> map) {
		jxl.Workbook readwb = null;
		try {
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook
			InputStream instream = new FileInputStream("C:/Users/Administrator/Desktop/allpay.xls");
			readwb = Workbook.getWorkbook(instream);
			// Sheet的下标是从0开始
			// 获取第一张Sheet表
			Sheet readsheet = readwb.getSheet(0);
			// 获取Sheet表中所包含的总列数
			int rsColumns = readsheet.getColumns();
			// 获取Sheet表中所包含的总行数
			int rsRows = readsheet.getRows();
			// 获取指定单元格的对象引用
			for (int j = 1; j < rsRows; j++) {
				Cell mac = readsheet.getCell(8, j);
				Cell amount = readsheet.getCell(13, j);
				if (map.containsKey(mac.getContents())) {
					int amount1 = Integer.parseInt(map.get(mac.getContents()));
					int amount2 = Integer.parseInt(amount.getContents());
					map.put(mac.getContents(), (amount1 + amount2) + "");
				} else {
					map.put(mac.getContents(), amount.getContents());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readwb.close();
		}
	}

	public static void setSc(Map<String, String> map) {
		jxl.Workbook readwb = null;
		try {
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook
			InputStream instream = new FileInputStream("C:/Users/Administrator/Desktop/sc.xls");
			readwb = Workbook.getWorkbook(instream);
			// Sheet的下标是从0开始
			// 获取第一张Sheet表
			Sheet readsheet = readwb.getSheet(0);
			// 获取Sheet表中所包含的总列数
			int rsColumns = readsheet.getColumns();
			// 获取Sheet表中所包含的总行数
			int rsRows = readsheet.getRows();
			// 获取指定单元格的对象引用
			for (int j = 1; j < rsRows; j++) {
				Cell mac = readsheet.getCell(7, j);
				Cell amount = readsheet.getCell(12, j);
				if (map.containsKey(mac.getContents())) {
					int amount1 = Integer.parseInt(map.get(mac.getContents()));
					int amount2 = Integer.parseInt(amount.getContents());
					map.put(mac.getContents(), (amount1 + amount2) + "");
				} else {
					map.put(mac.getContents(), amount.getContents());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readwb.close();
		}
	}

}
