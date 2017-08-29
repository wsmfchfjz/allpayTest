package com.test;

public class Xls {
//	try {
//	// 打开文件
//	WritableWorkbook book3 = Workbook.createWorkbook(new File("C:/Users/Administrator/Desktop/table.xls"));
//	// 生成名为“第一页”的工作表，参数0表示这是第一页
//	WritableSheet sheet3 = book3.createSheet("第一页", 0);
//	
//	
//	String encoding = "GBK";
//	File file = new File("C:/Users/Administrator/Desktop/table.txt");
//	if (file.isFile() && file.exists()) { // 判断文件是否存在
//		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
//		BufferedReader bufferedReader = new BufferedReader(read);
//		String lineTxt = null;
//		int lie = 0;
//		while ((lineTxt = bufferedReader.readLine()) != null) {
//			String[] list = lineTxt.split("\\|");
//			for (int i = 0; i < list.length; i++) {
//				Label label = new Label(i, lie, list[i]);
//				sheet3.addCell(label);
//			}
//			lie++;
//		}
//		read.close();
//	} else {
//		System.out.println("找不到指定的文件");
//	}
//	
//	// 写入数据并关闭文件
//	book3.write();
//	book3.close();
//	
//} catch (Exception e) {
//	System.out.println("读取文件内容出错");
//	e.printStackTrace();
//}
}
