package com.gome.util;

import java.math.BigDecimal;

/**
 *@author azq
 *@createtime 2019年1月2日
*/
public class ArithUtil {

	 private ArithUtil(){}  
     
	 /**
	  * 加
	  * @param d1
	  * @param d2
	  * @return
	  */
	    public static double add(double d1,double d2){  
	        BigDecimal b1=new BigDecimal(Double.toString(d1));  
	        BigDecimal b2=new BigDecimal(Double.toString(d2));  
	        return b1.add(b2).doubleValue();  
	          
	    }  
	     /**
	      * 减
	      * @param d1
	      * @param d2
	      * @return
	      */
	    public static double sub(double d1,double d2){  
	        BigDecimal b1=new BigDecimal(Double.toString(d1));  
	        BigDecimal b2=new BigDecimal(Double.toString(d2));  
	        return b1.subtract(b2).doubleValue();  
	          
	    }  
	      /**
	       * 乘
	       * @param d1
	       * @param d2
	       * @return
	       */
	    public static double mul(double d1,double d2){  
	        BigDecimal b1=new BigDecimal(Double.toString(d1));  
	        BigDecimal b2=new BigDecimal(Double.toString(d2));  
	        return b1.multiply(b2).doubleValue();  
	          
	    }  
	      
	  
	      /**
	       * 除
	       * @param d1
	       * @param d2
	       * @param scale
	       * @return
	       */
	    public static double div(double d1,double d2,int scale){  
	        if(scale<0){  
	            throw new IllegalArgumentException("The scale must be a positive integer or zero");  
	        }  
	        BigDecimal b1=new BigDecimal(Double.toString(d1));  
	        BigDecimal b2=new BigDecimal(Double.toString(d2));  
	        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();  
	          
	    }  
}
