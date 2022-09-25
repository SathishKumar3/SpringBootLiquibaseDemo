package com.loan.model;

import java.util.Base64;
import java.util.List;

public class LoanReturn {
	
	
	private String returnerInfo;
	private String returnByStr;
	private List<ReturnDetail> returnDetails;
	private byte[] returnBy;
	
	
	public List<ReturnDetail> getReturnDetails() {
		return returnDetails;
	}
	public void setReturnDetails(List<ReturnDetail> returnDetails) {
		this.returnDetails = returnDetails;
	}
	public String getReturnerInfo() {
		return returnerInfo;
	}
	public void setReturnerInfo(String returnerInfo) {
		this.returnerInfo = returnerInfo;
	}
	public String getReturnByStr() {
		return returnByStr;
	}
	public void setReturnByStr(String returnByStr) {
		this.returnByStr = returnByStr;
	}
	public byte[] getReturnBy() {
		return this.returnByStr.getBytes();
	}
	public void setReturnBy(byte[] returnBy) {
		this.returnBy = returnBy;
	}
	
	
	

}
