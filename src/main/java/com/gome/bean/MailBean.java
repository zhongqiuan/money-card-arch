package com.gome.bean;

import java.util.Arrays;

/**
 *@author azq
 *@createtime 2019年1月2日
*/
public class MailBean {

	private String [] toPeople;
	private String[] ccPeople;
	private String subJect ;
	private String contentText;
	public String[] getToPeople() {
		return toPeople;
	}
	public void setToPeople(String[] toPeople) {
		this.toPeople = toPeople;
	}
	public String[] getCcPeople() {
		return ccPeople;
	}
	public void setCcPeople(String[] ccPeople) {
		this.ccPeople = ccPeople;
	}
	public String getSubJect() {
		return subJect;
	}
	public void setSubJect(String subJect) {
		this.subJect = subJect;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	@Override
	public String toString() {
		return "MailBean [toPeople=" + Arrays.toString(toPeople) + ", ccPeople=" + Arrays.toString(ccPeople)
				+ ", subJect=" + subJect + ", contentText=" + contentText + "]";
	}
	
	
}
