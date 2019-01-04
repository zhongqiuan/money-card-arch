package com.gome.test;

import java.util.Timer;
import java.util.TimerTask;

import com.gome.util.TimerManager;

/**
 *@author azq
 *@createtime 2019年1月2日
*/
public class TimerTest01 {

	
	
	public static void main(String[] args) {
		TimerTask tt= new TimeTask01();
		new TimerManager(tt,18,0,0);
	}
}
