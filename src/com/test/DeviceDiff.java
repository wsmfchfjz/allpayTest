package com.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

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
	
	public static void main(String[] args) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		System.out.println(System.currentTimeMillis());
//		//17 55 add
//		int allpaySum = 0, scSum = 0;;
//		Map<String, String> allpay = new HashMap<>();
//		Map<String, String> sc = new HashMap<>();
//		setAllpay(allpay);
//		setSc(sc);
//		for (Map.Entry<String, String> entry : allpay.entrySet()) {
//			// System.out.println("key= " + entry.getKey() + " and value= " +
//			// entry.getValue());
//			if (!sc.get(entry.getKey()).equals(entry.getValue())) {
//				System.out.println(entry.getKey() + "- allpay:" + entry.getValue() + " sc:" + sc.get(entry.getKey()));
//				allpaySum += Integer.parseInt(entry.getValue());
//				scSum += Integer.parseInt(sc.get(entry.getKey()));
//			}
//		}
//		System.out.println(allpaySum + "-" +scSum);
//		Test1 t = new Test1();
//		Method m = t.getClass().getDeclaredMethod("setS", String.class);
//		m.invoke(t, "aa");
//		System.out.println(t.getS());
		getOnline();
//		getOffline();
	}
	
	public static void getOffline(){
//		String[] devices = {"800487000001","800488000001","800489000001","800490000001","800491000001","800492000001","800493000001","800494000001",};
		String[] devices = {"800495000002"};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfNum = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int day = 1;//周末标识
		while (!"2017-11-30".equals(sdf.format(c.getTime()))){
			for (int i = 0; i < devices.length; i++) {
				int[] amount = getOfflineAmount(day);
				String sql = "INSERT INTO sc_offline.stat_device_offline_001(id,statDate,basicDataId,amount,delta,proprietorId) VALUES('";
				sql += sdfNum.format(c.getTime()) + devices[i] + "','"+sdf.format(c.getTime())+"'," + devices[i] +","+amount[0]+","+amount[1]+",24273);";
				System.out.println(sql);
			}
			c.add(Calendar.DAY_OF_YEAR, -1);
			day++;
		}
	}
	
	public static int[] getOfflineAmount(int day){
		int[] amount = new int[2];
		if(day % 7 == 0 || day % 7 == 6){
			int r = (int)(Math.random()*100);
			amount[0] = 100000 + r * 100;
			amount[1] = amount[0] / 100;
		} else {
			int r = (int)(Math.random()*30);
			amount[0] = 50000 + r * 100;
			amount[1] = amount[0] / 100;
		}
		return amount;
	}
	
	public static void getOnline(){
//		String[] devices = {"800487000001","800488000001","800489000001","800490000001","800491000001","800492000001","800493000001","800494000001",};
		String[] devices = {"800495000002"};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfNum = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int day = 1;//周末标识
		while (!"2017-11-30".equals(sdf.format(c.getTime()))){
			for (int i = 0; i < devices.length; i++) {
				int[] amount = getOnlineAmount(day);
				String sql = "INSERT INTO sc_online.stat_device_pay_001(id,statDate,basicDataId,wechatAmount,wechatOrderNum,proprietorId) VALUES('";
				sql += sdfNum.format(c.getTime()) + devices[i] + "','"+sdf.format(c.getTime())+"'," + devices[i] +","+amount[0]+","+amount[1]+",24273);";
				System.out.println(sql);
			}
			c.add(Calendar.DAY_OF_YEAR, -1);
			day++;
		}
	}
	
	public static int[] getOnlineAmount(int day){
		int[] amount = new int[2];
		if(day % 7 == 0 || day % 7 == 6){
			int r = (int)(Math.random()*30);
			amount[0] = 100000 + r * 100;
			amount[1] = 100 + r;
		} else {
			int r = (int)(Math.random()*30);
			amount[0] = 50000 + r * 100;
			amount[1] = 50 + r;
		}
		return amount;
	}
	
    public static Date dealOrderReturnTimeByBank(Date payTime, Date orderReturnTime){
        Calendar c = Calendar.getInstance();
        c.setTime(orderReturnTime);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour == 23){
            String today = new SimpleDateFormat("yyyy-MM-dd").format(orderReturnTime);
            c.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            try {
                Date todayEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(today + " 22:59:59");
                Date yesterDayStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yesterday + " 23:00:00");
                if (payTime.getTime() >= yesterDayStart.getTime() && payTime.getTime() <= todayEnd.getTime()) {
                    return payTime;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return orderReturnTime;
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
class Test1{
	String s ;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
}
