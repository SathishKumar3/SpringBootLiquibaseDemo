package com.loan.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.loan.dao.LoanRepository;
import com.loan.entity.LoanDetails;
import com.loan.model.DailySheetModel;
import com.loan.model.LoanReturn;
import com.loan.model.ReturnDetail;
import com.loan.model.ReturnSummary;
import com.loan.model.SearchRequest;
import com.loan.model.TypeAheadModel;
import com.loan.util.Helper;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Service
@Slf4j
public class LoanService {

	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired 
	private EntityManager entityManager;
	
	
	private String loanPrefix = "A";
	private String nextloanPrefix = "B";
	
	
	public void newLoan(LoanDetails loanDetails) {
		loanRepository.saveAndFlush(loanDetails);
		
	}
	
	public void editLoan(LoanDetails loanDetails) {
		
		LoanDetails oldLoanDetails = fetchLoanDetails(loanDetails.getLoanNo());
		
		loanDetails.setLoanBy(oldLoanDetails.getLoanBy());
		loanDetails.setLoanDate(oldLoanDetails.getLoanDate());
		loanRepository.saveAndFlush(loanDetails);
	}
	
	//TODO Apache string util
	@SuppressWarnings("deprecation")
	public String fetchNewLoanNo() {
		
		
		String previousLoanNo = loanRepository.findFirstByOrderByDateDesc();
	
		
		String newLoanNo = "";
		
		if(StringUtils.isEmpty(previousLoanNo)) {
			newLoanNo = loanPrefix + 1;
			
		}
		else {
		
		int previousLoanNoInt = Integer.parseInt(previousLoanNo.substring(1));
		

		if(previousLoanNoInt == 10000) {
			loanPrefix = nextloanPrefix;
			newLoanNo = loanPrefix + 1;
		}
		else {
		
	    int loanNo = previousLoanNoInt + 1;
		
	    
	     newLoanNo = loanPrefix + loanNo;
		}
		}
		
		return newLoanNo;
	}
	
	
	public void returnLoan() {
		
	}
	
	public LoanDetails fetchLoanDetails(String loanNo) {
		
		Optional<LoanDetails> loanDetails = loanRepository.findById(loanNo.toUpperCase());
		
		if(loanDetails.isPresent()) {
			return loanDetails.get();
		}
		return null;
	}
	
		public List<ReturnSummary> calculateInt(List<String> loanNoList) {
		
		List<LoanDetails> loanDetailsList =  loanRepository.findAllById(loanNoList);
		Integer totalSum = 0;
		List<ReturnSummary> returnSummaryList = new ArrayList();
		
			for (LoanDetails loanDetails : loanDetailsList) {
				ReturnSummary returnSummary = new ReturnSummary();
				if(loanDetails.getReturnDate()==null) {
					Period diff = Period.between(loanDetails.getLoanDate().toLocalDateTime().toLocalDate().withDayOfMonth(1),
				            LocalDate.now().withDayOfMonth(1));
					
					Integer oneMonthInterest = (int) (Math.ceil(loanDetails.getLoanAmt() * loanDetails.getInterestRate())/100);
					
					int totalMonths = diff.getMonths() + (diff.getYears() * 12) ;
					if(loanDetails.getLoanDate().toLocalDateTime().getDayOfMonth() < LocalDate.now().getDayOfMonth()) {
						totalMonths++;
						
						returnSummary.setComments((totalMonths-1) + " Months and " + (LocalDate.now().getDayOfMonth() - 
								loanDetails.getLoanDate().toLocalDateTime().getDayOfMonth()) + " Days.");
					}
					else {
						returnSummary.setComments(totalMonths + " Months and " + (LocalDate.now().getDayOfMonth() - 
																loanDetails.getLoanDate().toLocalDateTime().getDayOfMonth()) + " Days.");
					}
					
					Integer daysBetween = (int)ChronoUnit.DAYS.between(loanDetails.getLoanDate().toLocalDateTime().toLocalDate(), LocalDate.now());
					Integer oneDayInterest = (int) (Math.round(loanDetails.getLoanAmt() * 0.12)/365);
					returnSummary.setActualInterest(oneDayInterest * daysBetween);
					Integer totalInterest = 0;
					
					
					totalInterest = loanDetails.getCalcFromCurrentMonth() ? (oneMonthInterest * totalMonths) : oneMonthInterest * (totalMonths - 1);
					
					if(totalInterest < 0) {
						totalInterest = 0;
					}
					
					returnSummary.setLoanAmt(loanDetails.getLoanAmt());
					returnSummary.setLoanNo(loanDetails.getLoanNo());
					returnSummary.setInterest(totalInterest);
					returnSummary.setSum(loanDetails.getLoanAmt() + totalInterest);
					totalSum = totalSum + returnSummary.getSum();
					returnSummary.setTotalSum(totalSum);
					returnSummary.setLoanDetails(loanDetails);
					returnSummaryList.add(returnSummary);
				}
				else {
					returnSummary.setLoanNo(loanDetails.getLoanNo());
					returnSummary.setComments("Already returned");
					returnSummary.setLoanDetails(loanDetails);
					returnSummaryList.add(returnSummary);
				}
			}
		
		
		return returnSummaryList;
	}
		
	
		public void returnSubmit(LoanReturn loanReturn) {
			
			try {
			
			loanReturn.getReturnDetails().stream().forEach(it->{
				
			loanRepository.updateLoanReturn(loanReturn.getReturnerInfo(), 
						it.getInterestAmt(), new Timestamp(new Date().getTime()), 
						it.getActualIntAmt(),loanReturn.getReturnBy(),it.getLoanNo());
				
			}
			);
			}
			catch(Exception e) {
				e.printStackTrace();

			}
		}
		
		
		public Page<LoanDetails> searchLoan(SearchRequest searchRequest) {
			
			
			Page<LoanDetails> searchResult = null;
			
			try {
				
				Pageable pageable; 
			
				CriteriaBuilder builder =  entityManager.getCriteriaBuilder();
		        CriteriaQuery<LoanDetails> criteria = builder.createQuery(LoanDetails.class);
		        Root<LoanDetails> root = criteria.from(LoanDetails.class);
		        List<Predicate> predicates = new ArrayList<Predicate>();

		        if(!StringUtils.isEmpty(searchRequest.getFname())){
		        	 predicates.add(builder.like(builder.lower(root.get("fName")),  searchRequest.getFname().toLowerCase() + "%"));

		        }
		        
		        if(!StringUtils.isEmpty(searchRequest.getLname())){
		        	 predicates.add(builder.like(builder.lower(root.get("lName")), searchRequest.getLname().toLowerCase() + "%"));
		        }
		        
		        if(!StringUtils.isEmpty(searchRequest.getReturnOption()) && (searchRequest.getReturnOption().equals("1"))){
		        	predicates.add(builder.isNull(root.get("returnDate")));
		        }
		        
		        if(!StringUtils.isEmpty(searchRequest.getReturnOption()) && (searchRequest.getReturnOption().equals("2"))){
		        	predicates.add(builder.isNotNull(root.get("returnDate")));
		        }
		        
		        
		        if(!StringUtils.isEmpty(searchRequest.getType()) && searchRequest.getType().equals("2")){
		        	predicates.add(builder.greaterThanOrEqualTo(root.get("interestRate"),5));
		        }
		        
		        if(!StringUtils.isEmpty(searchRequest.getType()) && searchRequest.getType().equals("1")){
		        	predicates.add(builder.lessThanOrEqualTo(root.get("interestRate"),4));
		        }
		        
		        
		        if(!StringUtils.isEmpty(searchRequest.getPlace())){
		        	predicates.add(builder.like(builder.lower(root.get("place")),  searchRequest.getPlace().toLowerCase() + "%"));
		        }
		        
		        if(!StringUtils.isEmpty(searchRequest.getMobileNo())){
		        	predicates.add(builder.equal(root.get("mobileNo"), searchRequest.getMobileNo()));
		        }
		        
		        
		        if(!StringUtils.isEmpty(searchRequest.getStartDate()) && !StringUtils.isEmpty(searchRequest.getEndDate())  ){
		        	predicates.add(builder.between(root.get("loanDate"), Helper.formatStringToDate(searchRequest.getStartDate()), Helper.formatStringToDate(searchRequest.getEndDate())));
		        }
		       
		        criteria.where(builder.and(predicates.toArray( new Predicate[predicates.size()])));

		        criteria.orderBy(builder.asc(root.get("loanDate")));
		      

		        // This query fetches the Books as per the Page Limit
		        List<LoanDetails> result = entityManager.createQuery(criteria)
		        		.setFirstResult((searchRequest.getPageNo()-1) * searchRequest.getPageSize()).setMaxResults(searchRequest.getPageSize()).getResultList();
		        
		        
		        // Create Count Query
		        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		        Root<LoanDetails> loanCount = countQuery.from(LoanDetails.class);
		        countQuery.select(builder.count(loanCount)).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		        // Fetches the count of all Books as per given criteria
		        Long count = entityManager.createQuery(countQuery).getSingleResult();

		        
		        searchResult = new PageImpl<>(result, PageRequest.of((searchRequest.getPageNo()-1), searchRequest.getPageSize()), count);
		       
				
			
			}
			catch(Exception e) {
				e.printStackTrace();

			}
			return searchResult;
		}
		
	
		
		public TypeAheadModel fetchTypeAheadInfo() {
			
			TypeAheadModel aheadModel = new TypeAheadModel();
			aheadModel.setPlace(loanRepository.findTypeAheadPlace());
			aheadModel.setStreet(loanRepository.findTypeAheadStreet());
			
			
			return aheadModel;
		}
		
		
		public List<String> fetchTypeAheadName(String name) {
			
			List<String> fnameList = loanRepository.findTypeAheadFName(name);
			
			fnameList.addAll(loanRepository.findTypeAheadLName(name));
			
			List<String> nameList = fnameList.stream().distinct().collect(Collectors.toList());
			
			return nameList;
		}
		
		public DailySheetModel fetchDailySheet(String dateStr) {
			
			DailySheetModel dailySheetModel = new DailySheetModel();
			
			if(!StringUtils.isEmpty(dateStr)) {
				
				java.sql.Date date = Helper.formatStringToSqlDate(dateStr);
				
				List<LoanDetails> loanDetails = loanRepository.findLoanDetailsByLoanDate(date);
				List<LoanDetails> returnDetils = loanRepository.findLoanDetailsByReturnDate(date);
				
				
				Integer totalLoanAmt = loanDetails.stream().mapToInt(it->it.getLoanAmt()).sum();
				Integer totalLoanInterestAmt = loanDetails.stream().mapToInt(it->  it.getLoanInterest()).sum();
				
				Integer totalReturnAmt = returnDetils.stream().mapToInt(it->it.getLoanAmt()).sum();
				Integer totalReturnInterestAmt = returnDetils.stream().mapToInt(it->it.getInterestReceived()).sum();
				
				dailySheetModel.setLoanCount(loanDetails.size());
				dailySheetModel.setLoanList(loanDetails);
				dailySheetModel.setTotalLoanAmt(totalLoanAmt);
				dailySheetModel.setTotalLoanInterest(totalLoanInterestAmt);
				
				dailySheetModel.setReturnCount(returnDetils.size());
				dailySheetModel.setReturnList(returnDetils);
				dailySheetModel.setTotalReturnAmt(totalReturnAmt);
				dailySheetModel.setTotalReturnInterest(totalReturnInterestAmt);
				
			}
			
			return dailySheetModel;
			
		}
		
		
		
		
		
			
}









