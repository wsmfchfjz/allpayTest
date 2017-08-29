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

public class Order {

	
	public static void main(String[] args) {//o6d5swdZ4C72WNwtROHqYCD3QKnM
		List<String> sc = new ArrayList<>();
		List<String> allpay = new ArrayList<>();
		setSc(sc);
		setAllpay(allpay);
		for (String string : allpay) {
			if (!sc.contains(string)){
				System.out.println(string);
			}
		}
	}
	
	public static void setSc(List<String> sc){
		try {
			String encoding = "GBK";
			File file = new File("C:/Users/Administrator/Desktop/scOrder.txt");
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int index = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sc.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
	
	public static void setAllpay(List<String> allpay){
		try {
			String encoding = "GBK";
			File file = new File("C:/Users/Administrator/Desktop/allpayOrder.txt");
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int index = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					allpay.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
	
	
	
}
