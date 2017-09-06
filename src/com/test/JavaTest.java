package com.test;

import java.util.HashMap;
import java.util.Map;

public class JavaTest {

	public static void main(String[] args) {
		Map<String, String[]> params = new HashMap<String, String[]>();
		String[] str = {"111"};
		params.put("transactionId8", str);
		
		String transactionId8 = "";
		if (params.get("transactionId8") != null){
			transactionId8 = " and a.transactionId8 = '" + params.get("transactionId8")[0] + "' ";
		}
		
		params.remove("transactionId8");
		
		System.out.println(transactionId8);
		
	}

}
