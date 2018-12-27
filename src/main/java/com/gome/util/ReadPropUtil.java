package com.gome.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.swing.Spring;

import org.apache.log4j.Logger;

/**
 * @author azq
 * @createtime 2018年12月18日
 */

public class ReadPropUtil {

	private static Logger log = Logger.getLogger(ReadPropUtil.class);
	public static HashMap<String,String> readProperties() {
		// 测试和生产公用
		String path = System.getProperty("user.dir").replace("money_card-arch", "");

		//初始化一个map装参数
		final HashMap<String,String> propMap= new HashMap<String, String>();
		log.info("文件路径为="+path + "/params.properties");
		// 使用InPutStream流读取properties文件
		Properties properties = null;
		try {//读入配置文件信息
			properties = new Properties();
			InputStream br = new BufferedInputStream(new FileInputStream(path + "/params.properties"));
			properties.load(br);
			Iterator<String> it = properties.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value=properties.getProperty(key);
				log.info("key="+key+",value="+value);
				propMap.put(key, value);
			}
		} catch (IOException e) {
			System.out.println("读取文件失败，没找到文件");
			e.printStackTrace();
		}
		// 获取key对应的value值

		return propMap;
	}

	

}
