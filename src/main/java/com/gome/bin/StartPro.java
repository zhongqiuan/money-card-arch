package com.gome.bin;

import java.sql.SQLException;


import com.gome.service.MoneyTimeTask;
import com.gome.util.TimerManager;

/**
 *@author azq
 *@createtime 2018年12月18日
*/
public class StartPro {

	
	public static void main(String[] args) throws SQLException {
		new TimerManager(new MoneyTimeTask(), 2,0,0);

		
	}
}
