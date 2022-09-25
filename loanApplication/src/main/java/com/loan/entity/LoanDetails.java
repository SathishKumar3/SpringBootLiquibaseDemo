package com.loan.entity;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.loan.util.Helper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "loan_details", schema = "bank")
@ToString
@Getter
@Setter
public class LoanDetails {
	
	@Id
	@Column(name = "loanno")
	private String loanNo;
	
	@Column(name = "loandate")
	private Timestamp loanDate;
	
	@Column(name = "loanamt")
	private Integer loanAmt = 0;
	
	@Column(name = "interestrate")
	private float interestRate;
	
	@Column(name = "fname")
	private String fName;
	
	@Column(name = "lname")
	private String lName;
	
	@Column(name = "place")
	private String place;
	
	@Column(name = "streetname")
	private String streetName;
	
	@Column(name = "mobileno")
	private String mobileNo;
	
	@Column(name = "items")
	private String items;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "netweight")
	private float netWeight;
	
	@Column(name = "amountgiven")
	private Integer amountGiven = 0;
	
	@Column(name = "interestreceived")
	private Integer interestReceived = 0;
	
	@Column(name = "actualinterest")
	private Integer actualInterest = 0;
	
	@Column(name = "returndate")
	private Timestamp returnDate;
	
	@Column(name = "loanby")
	private byte[] loanBy;
	

	@Column(name = "returnby")
	private byte[] returnBy;
	
	@Column(name = "insertedby")
	private String insertedBy;
	
	@Column(name = "updatedby")
	private String updatedBy;
	
	@Column(name = "additionalinfo")
	private String additionalInfo;
	
	@Column(name = "calcfromcurrmonth")
	private boolean calcFromCurrentMonth = false;
	
	@Column(name = "returnerinfo")  
	private String returnerInfo;
	
	@Column(name = "relation")  
	private String relation;
	
	@Transient
	private String loanDateStr;
	
	@Transient
	private String returnDateStr;
	
	@Transient
	private String loanByImg;
	
	@Transient
	private String returnByImg;
	
	@Transient
	private Integer loanInterest = 0;
	
	public LoanDetails() {
		
	}
	
	public LoanDetails(String loanNo, String relation) {
	this.loanNo = loanNo;	
	this.relation = relation;
	}
	
	public LoanDetails(String loanNo, Integer interestReceived, Integer actualInterest, String returnerinfo, byte[] returnBy) {
		this.loanNo = loanNo;	
		this.interestReceived = interestReceived;
		this.actualInterest = actualInterest;
		this.returnBy = returnBy;
		}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public Timestamp getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Timestamp loanDate) {
		this.loanDate = loanDate;
	}

	public Integer getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(Integer loanAmt) {
		this.loanAmt = loanAmt;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	
	public float getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(float netWeight) {
		this.netWeight = netWeight;
	}

	public Integer getAmountGiven() {
		return amountGiven;
	}

	public void setAmountGiven(Integer amountGiven) {
		this.amountGiven = amountGiven;
	}

	public Integer getInterestReceived() {
		return interestReceived;
	}

	public void setInterestReceived(Integer interestReceived) {
		this.interestReceived = interestReceived;
	}

	public Integer getActualInterest() {
		return actualInterest;
	}

	public void setActualInterest(Integer actualInterest) {
		this.actualInterest = actualInterest;
	}

	public Timestamp getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}

	public byte[] getLoanBy() {
		return loanBy;
	}

	public void setLoanBy(byte[] loanBy) {
		this.loanBy = loanBy;
	}

	public byte[] getReturnBy() {
		return returnBy;
	}

	public void setReturnBy(byte[] returnBy) {
		this.returnBy = returnBy;
	}

	public String getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLoanByImg() {
		if(this.loanBy!=null)
		{
			this.loanByImg = new String(this.getLoanBy(), StandardCharsets.UTF_8);
			return this.loanByImg;
		}else {
			return "";
		}
		
	}

	public void setLoanByImg(String loanByImg) {
		this.loanByImg = loanByImg;
	}

	public String getReturnByImg() {
		if(this.returnBy!=null)
		{
			this.returnByImg = new String(this.getReturnBy(), StandardCharsets.UTF_8);
			return returnByImg;
		}else {
			return "";
		}
	}

	public void setReturnByImg(String returnByImg) {
		this.returnByImg = returnByImg;
	}

	public boolean getCalcFromCurrentMonth() {
		return calcFromCurrentMonth;
	}

	public void setCalcFromCurrentMonth(boolean calcFromCurrentMonth) {
		this.calcFromCurrentMonth = calcFromCurrentMonth;
	}

	public String getLoanDateStr() {
		return Helper.formatDateToString(this.loanDate);
	}

	public void setLoanDateStr(String loanDateStr) {
		this.loanDateStr = loanDateStr;
	}

	public String getReturnDateStr() {
		return Helper.formatDateToString(this.returnDate);
	}

	public void setReturnDateStr(String returnDateStr) {
		this.returnDateStr = returnDateStr;
	}

	public String getReturnerInfo() {
		return returnerInfo;
	}

	public void setReturnerInfo(String returnerInfo) {
		this.returnerInfo = returnerInfo;
	}

	public Integer getLoanInterest() {
		if(this.amountGiven == 0) {
			return 0;
		}
		else if(this.calcFromCurrentMonth)
		{
			return 0;
		}
		else {
		return (this.loanAmt - this.amountGiven) ;
		}
	}

	public void setLoanInterest(Integer loanInterest) {
		this.loanInterest = loanInterest;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	
	
		
}
