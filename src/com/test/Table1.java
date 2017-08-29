package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Table1 {

	public static int length = 26;
	public static String[] mac = new String[length];
	public static String[] start = new String[length];
	public static String[] end = new String[length];
	
	public static void start(){
		try {
			String encoding = "GBK";
			File file = new File("C:/Users/Administrator/Desktop/table.txt");
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
		
		sql4();
	}
	
	public static void sql4() {
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
	
	public static void sql3() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (\n");
		for (int i = 0; i < length; i++) {
			sql.append("(SELECT\n" + "\td.deviceMac mac, SUM(a.num) num\n" + 
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

	public static void sql2() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (\n");
		for (int i = 0; i < length; i++) {
			sql.append("(SELECT\n" + "\td.deviceMac mac, SUM(a.num) num\n" + 
					"FROM\n" + "\tallPay.ec_consume_detail a\n" + 
					"LEFT JOIN allPay.ec_detail b ON a.ec_detail_id = b.id\n" + 
					"LEFT JOIN allpay_management.device_info d on a.device_id = d.id\n" + 
					"WHERE\n" + "\tb.address_id = 4729\n" + "AND a.consume_time BETWEEN '" + start[i] + "'\n" + "AND '" + end[i] + "'\n" + 
					"AND b.create_time < '" + start[i] + 
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
}
