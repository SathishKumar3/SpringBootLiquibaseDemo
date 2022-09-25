package com.loan.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loan.entity.LoanDetails;
import com.loan.model.DailySheetModel;
import com.loan.model.LoanReturn;
import com.loan.model.ReturnSummary;
import com.loan.model.SearchRequest;
import com.loan.model.TypeAheadModel;
import com.loan.service.LoanService;
import com.loan.util.Helper;

@Controller
public class HomeController {
	
	@Autowired
	private LoanService loanSerice;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("dailySheetDate", new Date());
		model.addAttribute("dailySheetModel", loanSerice.fetchDailySheet(Helper.formatDateToString(new Timestamp(new Date().getTime()))));
		return "dailySheet";
	}
	
	//@ResponseBody
	@GetMapping("/dailySheetByDate")
	public String dailySheet(@RequestParam("dailyDate") String date, @RequestParam("navigation") String navigation, Model model) {
		
		Date dateObj = Helper.formatStringToDate(date);
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(dateObj);
		
		if(navigation!=null && navigation.equals("back")) {
			
			cal.add(Calendar.DATE, -1);
		}
		else if(navigation!=null && navigation.equals("forward")) {
	        cal.add(Calendar.DATE, 1);
		}
		
		
		model.addAttribute("dailySheetDate", cal.getTime());
		model.addAttribute("dailySheetModel", loanSerice.fetchDailySheet(Helper.formatDateToString(new Timestamp(cal.getTime().getTime()))));
		return "dailySheet";
	}
	
	@ResponseBody
	@GetMapping("/typeAhead")
	public TypeAheadModel typeAhead(HttpServletRequest request) {
		TypeAheadModel typeAheadModel = null;
		if(request.getSession().getAttribute("typeAheadModel")==null) {
			request.getSession().setAttribute("typeAheadModel", loanSerice.fetchTypeAheadInfo());
		}
		typeAheadModel = (TypeAheadModel) request.getSession().getAttribute("typeAheadModel");
		
		return typeAheadModel;
	}
	
	
	@ResponseBody
	@GetMapping("/typeAheadName/{name}")
	public List<String> typeAhead(@PathVariable String name) {
		return loanSerice.fetchTypeAheadName(name);
	}
	
	
	@GetMapping("/search")
	public String search(Model model) {
		model.addAttribute("searchRequest", new SearchRequest());
		return "search";
	}
	
	@GetMapping("/newLoan")
	public String newLoan(Model model) {
		String loanNo = loanSerice.fetchNewLoanNo();
		model.addAttribute("loanDetails", new LoanDetails(loanNo, "s/o"));
		model.addAttribute("standardDate", new Date());
		return "newloan";
	}
	
	@PostMapping("/newLoanSubmit")
	public String newLoanSubmit(@ModelAttribute LoanDetails loanDetails, Model model) {
		
		loanDetails.setLoanDate(new Timestamp(new Date().getTime()));
		loanSerice.newLoan(loanDetails);
		model.addAttribute("loanDetails", loanDetails);
		model.addAttribute("detection", loanDetails.getLoanAmt() - loanDetails.getAmountGiven());
		return "loanSummary";
	}
	
	@GetMapping("/editLoan")
	public String editLoan(@ModelAttribute LoanDetails loanDetails,Model model) {
		
		if(loanDetails.getLoanNo()!=null && !loanDetails.getLoanNo().equalsIgnoreCase("")) {
			loanDetails =loanSerice.fetchLoanDetails(loanDetails.getLoanNo());
			model.addAttribute("loanDetails", loanDetails);
			model.addAttribute("loanDateFmt", new Date(loanDetails.getLoanDate().getTime())); 
		}else {
			model.addAttribute("loanDetails", new LoanDetails());
		}
		return "editLoan";
	}
	
	
	
	@PostMapping("/editLoanSubmit")
	public String editLoanSubmit(@ModelAttribute LoanDetails loanDetails, Model model) {
		loanSerice.editLoan(loanDetails);
		model.addAttribute("loanDetails", loanDetails);
		model.addAttribute("editSuccess", true);
		return "editLoan";
		
	}
	
	@GetMapping("/viewLoan")
	public String viewLoan(Model model) {
		model.addAttribute("loanDetails", new LoanDetails());
		return "viewloan";
	}
	
	
	@GetMapping("/viewLoan/{loanNo}")
	public String viewLoanFragment(@PathVariable String loanNo,Model model) {	
		LoanDetails loanDetails = null;
		if(loanNo!=null && !loanNo.equalsIgnoreCase("")) {
			loanDetails =loanSerice.fetchLoanDetails(loanNo);
			model.addAttribute("loanDetails", loanDetails);
		}
		
		return "viewloanResults";
	}
	
	
	@GetMapping("/returnLoan")
	public String returnLoan(Model model) {
		model.addAttribute("loanDetails", new LoanDetails());
		return "returnloan";
	}
	
	@PostMapping("/calculateInt")
	public String calculateInt(@RequestBody List<String> loanNoList, Model model) {
		List<ReturnSummary> returnSummaryList = loanSerice.calculateInt(loanNoList);
		model.addAttribute("returnSummaryList", returnSummaryList);
		return "returnLoanSummary";
	}
	
	@ResponseBody
	@PostMapping("/returnSubmit")
	public boolean returnSubmit(@RequestBody LoanReturn loanReturn) {
		loanSerice.returnSubmit(loanReturn);
		return true;
	}
	
	
	@PostMapping("/searchLoan")
	public String searchLoan(@ModelAttribute SearchRequest searchRequest,Model model) {
		
		System.out.println("Page No:"+searchRequest.getPageNo());
		
		
		Page<LoanDetails> searchResults = loanSerice.searchLoan(searchRequest);
		model.addAttribute("searchResults", searchResults);
		
		   model.addAttribute("currentPage", searchRequest.getPageNo());
		    model.addAttribute("totalPages", searchResults.getTotalPages());
		    model.addAttribute("totalItems", searchResults.getTotalElements());

		return "search";
	}
	
	
	
	
	
	
	
	
	
}
