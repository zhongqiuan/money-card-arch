package com.gome.bean;

import java.sql.Date;

/**
 * 每日佣金计算逻辑
 * 
 *@author azq
 *@createtime 2019年1月2日
*/
public class DayCalcuMoney {

	
	private String daySreialNmber;
	private Date archTime;
	private String chnlId ;
	private String productId;
	private int archNumber;
	private double agentFee;
	private int policyId;
	private int ruleFee;
	private double totalFee;
	private double paidFee;
	private double unPaidFee;
	private String checkState;
	private Date checkTime;
	public String getDaySreialNmber() {
		return daySreialNmber;
	}
	public void setDaySreialNmber(String daySreialNmber) {
		this.daySreialNmber = daySreialNmber;
	}
	public Date getArchTime() {
		return archTime;
	}
	public void setArchTime(Date archTime) {
		this.archTime = archTime;
	}
	public String getChnlId() {
		return chnlId;
	}
	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getArchNumber() {
		return archNumber;
	}
	public void setArchNumber(int archNumber) {
		this.archNumber = archNumber;
	}
	public double getAgentFee() {
		return agentFee;
	}
	public void setAgentFee(double agentFee) {
		this.agentFee = agentFee;
	}
	public int getPolicyId() {
		return policyId;
	}
	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
	public int getRuleFee() {
		return ruleFee;
	}
	public void setRuleFee(int ruleFee) {
		this.ruleFee = ruleFee;
	}
	public double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}
	public double getPaidFee() {
		return paidFee;
	}
	public void setPaidFee(double paidFee) {
		this.paidFee = paidFee;
	}
	public double getUnPaidFee() {
		return unPaidFee;
	}
	public void setUnPaidFee(double unPaidFee) {
		this.unPaidFee = unPaidFee;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	@Override
	public String toString() {
		return "DayCalcuMoney [daySreialNmber=" + daySreialNmber + ", archTime=" + archTime + ", chnlId=" + chnlId
				+ ", productId=" + productId + ", archNumber=" + archNumber + ", agentFee=" + agentFee + ", policyId="
				+ policyId + ", ruleFee=" + ruleFee + ", totalFee=" + totalFee + ", paidFee=" + paidFee + ", unPaidFee="
				+ unPaidFee + ", checkState=" + checkState + ", checkTime=" + checkTime + ", getDaySreialNmber()="
				+ getDaySreialNmber() + ", getArchTime()=" + getArchTime() + ", getChnlId()=" + getChnlId()
				+ ", getProductId()=" + getProductId() + ", getArchNumber()=" + getArchNumber() + ", getAgentFee()="
				+ getAgentFee() + ", getPolicyId()=" + getPolicyId() + ", getRuleFee()=" + getRuleFee()
				+ ", getTotalFee()=" + getTotalFee() + ", getPaidFee()=" + getPaidFee() + ", getUnPaidFee()="
				+ getUnPaidFee() + ", getCheckState()=" + getCheckState() + ", getCheckTime()=" + getCheckTime()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
}
