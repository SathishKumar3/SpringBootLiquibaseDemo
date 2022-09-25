package com.loan.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.loan.entity.LoanDetails;

public interface LoanRepository extends JpaRepository<LoanDetails, String>{
	
	
	@Query(value="SELECT loanNo FROM bank.loan_details ORDER BY loanDate desc limit 1", nativeQuery = true)
	String findFirstByOrderByDateDesc(); // name can be random
	
	
	@Modifying
	@Transactional
	@Query(value="update bank.loan_details set returnerinfo = :returnerInfo, "
			+ "interestreceived=:interestReceived, returndate =:returnDate , "
			+ "returnby=:returnBy , actualinterest = :actualInterest where loanno=:loanNo", nativeQuery = true)
	Integer updateLoanReturn(@Param("returnerInfo") String returnerInfo,
			@Param("interestReceived") Integer interestReceived,
			@Param("returnDate") Timestamp returnDate, 
			@Param("actualInterest") Integer actualInterest,
			@Param("returnBy") byte[] returnBy,
			@Param("loanNo") String loanNo);
	
	
	@Query(value="SELECT fname FROM bank.loan_details where lower(fname) like :fname%", nativeQuery = true)
	List<String> findTypeAheadFName(@Param("fname") String fname);
	
	@Query(value="SELECT lname FROM bank.loan_details where lower(lname) like :lname%", nativeQuery = true)
	List<String> findTypeAheadLName(@Param("lname") String lname);
	
	@Query(value="SELECT distinct place FROM bank.loan_details", nativeQuery = true)
	List<String> findTypeAheadPlace();
	
	@Query(value="SELECT distinct streetname FROM bank.loan_details", nativeQuery = true)
	List<String> findTypeAheadStreet();
	
	@Query(value="Select * from bank.loan_details where Date(loanDate)=:loanDate" ,nativeQuery = true)
	List<LoanDetails> findLoanDetailsByLoanDate(@Param("loanDate") Date loanDate);
	
	@Query(value="Select * from bank.loan_details where DATE(returnDate)=:returnDate",nativeQuery = true)
	List<LoanDetails> findLoanDetailsByReturnDate(@Param("returnDate") Date returnDate);
	
	
}
