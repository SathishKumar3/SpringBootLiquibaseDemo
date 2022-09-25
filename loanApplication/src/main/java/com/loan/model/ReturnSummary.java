package com.loan.model;

import com.loan.entity.LoanDetails;

public class ReturnSummary {
	
	private String loanNo;
	private Integer loanAmt;
	private Integer interest;
	private Integer sum;
	private String comments;
	private Integer totalSum;
	private LoanDetails loanDetails;
	private Integer actualInterest;
	
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public Integer getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(Integer loanAmt) {
		this.loanAmt = loanAmt;
	}
	public Integer getInterest() {
		return interest;
	}
	public void setInterest(Integer interest) {
		this.interest = interest;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(Integer totalSum) {
		this.totalSum = totalSum;
	}
	public LoanDetails getLoanDetails() {
		return loanDetails;
	}
	public void setLoanDetails(LoanDetails loanDetails) {
		this.loanDetails = loanDetails;
	}
	public Integer getActualInterest() {
		return actualInterest;
	}
	public void setActualInterest(Integer actualInterest) {
		this.actualInterest = actualInterest;
	}
	
	
	

}
