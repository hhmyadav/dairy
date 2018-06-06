package com.dairy.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dairy.model.Ledger;
import com.dairy.service.TransactionReportService;

@Controller
@RequestMapping("/transactionReport")
public class TransactionReportController {
       
	
	@Autowired
	TransactionReportService transactionReportService ;
	
	    @RequestMapping(value={""}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String getAllTransactions(Model model) {
	    	
  	      return "transactionReport";
	    }
	    
	    
	    @RequestMapping(value={"/fromDate"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String transactionBetweenDates(Model model) {
	    	
	    	LocalDateTime startDate =  LocalDateTime.of(2018, 05, 28, 15, 56); 
	    	LocalDateTime endDate =  LocalDateTime.of(2018, 05, 28, 15, 56); 
	    	
	    	List<Ledger> legders = transactionReportService.getTransationByDate(startDate, endDate);
	    	
	    	model.addAttribute("ledgers",legders);
	    	
  	      return "transactionReport";
	    }
	    
	    @RequestMapping(value={"/fromDateToNow"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String transactionFromDateToNow(Model model) {
	    	
	    	LocalDateTime startDate =  LocalDateTime.of(2018, 05, 28, 15, 56); 
	    	
	    	List<Ledger> legders = transactionReportService.getTransationByDate(startDate, LocalDateTime.now());
	    	
	    	model.addAttribute("ledgers",legders);
	    	
  	      return "transactionReport";
	    }
	    
	  
	    
      
}
