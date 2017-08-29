package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static int length = 26;
	public static String[] mac = new String[length];
	public static String[] start = new String[length];
	public static String[] end = new String[length];
	public static Map<String, String> map = new HashMap<>();

	
	public static void main(String[] args) {//o6d5swdZ4C72WNwtROHqYCD3QKnM
		try {
			String encoding = "GBK";
			File file = new File("C:/Users/Administrator/Desktop/table.txt");
//			File file = new File("/Users/jeff/Desktop/table.txt");
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int index = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (index < length) {
						mac[index] = lineTxt;
					} else if (index < length * 2) {
						end[index - length] = lineTxt;
					} else {
						start[index - length * 2] = lineTxt;
					}
					index++;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		
		sql5();

	}
	
	public static void sql5() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (\n");
		for (int i = 0; i < length; i++) {
			sql.append("(SELECT\n" + "\tdeviceMac mac, SUM(amount) / 100 amount, SUM(num) + SUM(give_num) num\n" + 
					"FROM\n" + "\tallPay.ec_order a\n" + 
					"LEFT JOIN allpay_management.device_info d on a.device_id = d.id\n" + 
					"WHERE\n" + "a.create_time BETWEEN '" + start[i] + "'\n" + "AND '" + end[i] + "'\n" + 
					"AND d.deviceMac = '" + mac[i] + "')\n");
			if (i != length - 1) {
				sql.append("UNION\n");
			}
		}
		sql.append(") r");
		System.out.println(sql.toString());
	}
	
	public static void sql4() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (\n");
		for (int i = 0; i < length; i++) {
			sql.append("(SELECT\n" + "\tifnull(d.deviceMac,'"+mac[i]+"') mac, ifnull(SUM(a.num),0) num\n" + 
					"FROM\n" + "\tallPay.ec_consume_detail a\n" + 
					"LEFT JOIN allPay.ec_detail b ON a.ec_detail_id = b.id\n" + 
					"LEFT JOIN allpay_management.device_info d on a.device_id = d.id\n" + 
					"WHERE\n" + "\tb.address_id != 4729\n" + "AND a.consume_time BETWEEN '" + start[i] + "'\n" + "AND '" + end[i] + "'\n" + 
					"AND d.deviceMac = '" + mac[i] + "')\n");
			if (i != length - 1) {
				sql.append("UNION\n");
			}
		}
		sql.append(") r");
		System.out.println(sql.toString());
	}

	public static void sql3() {
		initMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (\n");
		for (int i = 0; i < length; i++) {
			sql.append("(SELECT\n" + "\tifnull(d.deviceMac,'"+mac[i]+"') mac, ifnull(SUM(a.num),0) num\n" + 
					"FROM\n" + "\tallPay.ec_consume_detail a\n" + 
					"LEFT JOIN allPay.ec_detail b ON a.ec_detail_id = b.id\n" + 
					"LEFT JOIN allpay_management.device_info d on a.device_id = d.id\n" + 
					"WHERE\n" + "\tb.address_id = 4729\n" + "AND a.consume_time BETWEEN '" + start[i] + "'\n" + "AND '" + end[i] + "'\n" + 
					"AND b.create_time < '" + map.get(mac[i]) + 
					"'\n" + "AND d.deviceMac = '" + mac[i] + "')\n");
			if (i != length - 1) {
				sql.append("UNION\n");
			}
		}
		sql.append(") r");
		System.out.println(sql.toString());
	}
	
	public static void sql2() {
		initMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (\n");
		for (int i = 0; i < length; i++) {
			sql.append("(SELECT\n" + "\tifnull(d.deviceMac,'"+mac[i]+"') mac, ifnull(SUM(a.num),0) num\n" + 
					"FROM\n" + "\tallPay.ec_consume_detail a\n" + 
					"LEFT JOIN allPay.ec_detail b ON a.ec_detail_id = b.id\n" + 
					"LEFT JOIN allpay_management.device_info d on a.device_id = d.id\n" + 
					"WHERE\n" + "\tb.address_id = 4729\n" + "AND a.consume_time BETWEEN '" + start[i] + "'\n" + "AND '" + end[i] + "'\n" + 
					"AND b.create_time BETWEEN '" + map.get(mac[i]) + "'\n" + "AND '" + start[i] + 
					"'\n" + "AND d.deviceMac = '" + mac[i] + "')\n");
			if (i != length - 1) {
				sql.append("UNION\n");
			}
		}
		sql.append(") r");
		System.out.println(sql.toString());
	}
	
	public static void sql1() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (\n");
		for (int i = 0; i < length; i++) {
			sql.append("(SELECT\n" + "\td.deviceMac mac, SUM(a.num) num\n" + 
					"FROM\n" + "\tallPay.ec_consume_detail a\n" + 
					"LEFT JOIN allPay.ec_detail b ON a.ec_detail_id = b.id\n" + 
					"LEFT JOIN allpay_management.device_info d on a.device_id = d.id\n" + 
					"WHERE\n" + "\tb.address_id = 4729\n" + "AND a.consume_time BETWEEN '" + start[i] + "'\n" + "AND '" + end[i] + "'\n" + 
					"AND b.create_time BETWEEN '" + start[i] + "'\n" + "AND '" + end[i]
					+ "'\n" + "AND d.deviceMac = '" + mac[i] + "')\n");
			if (i != length - 1) {
				sql.append("UNION\n");
			}
		}
		sql.append(") r");
		System.out.println(sql.toString());
	}
	
	public static void initMap(){
		map.put("864270035858194","2017-08-23 12:11:14");
		map.put("864270035673981","2017-08-23 12:10:59");
		map.put("864270035701550","2017-08-23 12:09:50");
		map.put("864270035435597","2017-08-23 12:14:02");
		map.put("864270035701386","2017-08-23 11:30:25");
		map.put("864270035673361","2017-08-23 11:30:51");
		map.put("864270035623531","2017-08-23 11:31:12");
		map.put("864270036516205","2017-08-23 11:31:44");
		map.put("864270035858228","2017-08-23 11:37:19");
		map.put("864270035702228","2017-08-23 11:32:21");
		map.put("864270035446107","2017-08-23 11:51:02");
		map.put("864270035446198","2017-08-23 11:51:30");
		map.put("864270035462096","2017-08-23 11:51:48");
		map.put("864797032994010","2017-08-23 12:14:20");
		map.put("864270035462088","2017-08-23 12:15:05");
		map.put("864270035450281","2017-08-23 11:22:02");
		map.put("864270035674021","2017-08-23 11:21:37");
		map.put("864270035450265","2017-08-23 11:20:23");
		map.put("864270035446172","2017-08-23 11:19:59");
		map.put("864270036499014","2017-08-23 11:21:08");
		map.put("864270035450216","2017-08-23 11:20:47");
		map.put("864270035701188","2017-08-23 11:19:24");
		map.put("864270036523029","2017-08-23 11:18:41");
		map.put("864270036492027","2017-08-23 12:03:10");
		map.put("864270035699531","2017-08-23 12:03:38");
		map.put("864270035450323","2017-08-23 12:03:59");
	}
	
}
