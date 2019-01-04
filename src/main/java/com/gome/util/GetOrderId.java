package com.gome.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author azq
 * @createtime 2019年1月2日
 */
public class GetOrderId {

	private static final String orderId = "GMPAY";
	private static int number = 1;
	private static final Map<String, Integer> orderNumber = new HashMap<>();

	public static String getOrderId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());
		return orderId + date + autoNumber(date);

	}
	//自动补齐位数
	private static String autoNumber(String date){
		int ss= orderNum(date);
		return String.format("%04d", ss); 
		
	}
	//生成自增序列
	private static int orderNum(String date) {
		if (orderNumber.containsKey(date)) {
			number= number+1;
			return number;
			
		}
		orderNumber.put(date, 1);
		number=1;
		return number;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
		System.out.println(	getOrderId());
		}
	}

}
