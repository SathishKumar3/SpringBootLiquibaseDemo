package com.loan.model;

import java.util.List;

import com.loan.entity.LoanDetails;

public class DailySheetModel {
	
	private List<LoanDetails> returnList;
	private List<LoanDetails> loanList;
	private int returnCount;
	private int totalReturnAmt;
	private int totalReturnInterest;
	private int loanCount;
	private int totalLoanAmt;
	private int totalLoanInterest;
	
	
	public List<LoanDetails> getReturnList() {
		return returnList;
	}
	public void setReturnList(List<LoanDetails> returnList) {
		this.returnList = returnList;
	}
	public List<LoanDetails> getLoanList() {
		return loanList;
	}
	public void setLoanList(List<LoanDetails> loanList) {
		this.loanList = loanList;
	}
	public int getReturnCount() {
		return returnCount;
	}
	public void setReturnCount(int returnCount) {
		this.returnCount = returnCount;
	}
	public int getTotalReturnAmt() {
		return totalReturnAmt;
	}
	public void setTotalReturnAmt(int totalReturnAmt) {
		this.totalReturnAmt = totalReturnAmt;
	}
	public int getTotalReturnInterest() {
		return totalReturnInterest;
	}
	public void setTotalReturnInterest(int totalReturnInterest) {
		this.totalReturnInterest = totalReturnInterest;
	}
	public int getLoanCount() {
		return loanCount;
	}
	public void setLoanCount(int loanCount) {
		this.loanCount = loanCount;
	}
	public int getTotalLoanAmt() {
		return totalLoanAmt;
	}
	public void setTotalLoanAmt(int totalLoanAmt) {
		this.totalLoanAmt = totalLoanAmt;
	}
	public int getTotalLoanInterest() {
		return totalLoanInterest;
	}
	public void setTotalLoanInterest(int totalLoanInterest) {
		this.totalLoanInterest = totalLoanInterest;
	}
	
	
	
}
