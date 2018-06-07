package com.dairy.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.service.TransactionReportService;
import com.dairy.service.UserService;

@Controller
@RequestMapping("/transactionReport")
public class TransactionReportController {
       
	
	@Autowired
	TransactionReportService transactionReportService ;
	
	@Autowired
	UserService userService ;
	
	    @RequestMapping(value={""}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String getAllTransactions(Model model) {
	    	
  	      return "userTransactionReport";
	    }
	    
	    
	    @RequestMapping(value={"/fromDate"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String transactionBetweenDates(Model model) {
	    	
	    	LocalDateTime startDate =  LocalDateTime.of(2018, 05, 28, 15, 56); 
	    	LocalDateTime endDate =  LocalDateTime.of(2018, 05, 28, 15, 56); 
	    	
	    	List<Ledger> legders = transactionReportService.getTransationByDate(startDate, endDate);
	    	
	    	model.addAttribute("ledgers",legders);
	    	
  	      return "userTransactionReport";
	    }
	    
	    @RequestMapping(value={"/fromDateToNow"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String transactionFromDateToNow(Model model) {
	    	
	    	LocalDateTime startDate =  LocalDateTime.of(2018, 05, 28, 15, 56); 
	    	
	    	List<Ledger> legders = transactionReportService.getTransationByDate(startDate, LocalDateTime.now());
	    	
	    	model.addAttribute("ledgers",legders);
	    	
  	      return "userTransactionReport";
	    }
	    
	    
	    @RequestMapping(value={"/user/{id}"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String userTransactionReport(User user ,@PathVariable Optional<Integer> id ,Model model) {
	    	
	    	if(id.isPresent())
     	   { 
     		  Integer userId = id.get();
     		if(!userService.existsById(userId)) 
     	     { 
     		  model.addAttribute("result","Cannot find userId #" + userId);
     	      return "userTransactionReport";
     	     }
     	   }
	    	
	    	LocalDateTime startDate =  LocalDateTime.of(2018, 05, 28, 15, 56); 
	    	
	    	/*List<Ledger> reports = transactionReportService.getTransationByDate(startDate, LocalDateTime.now());
	    	*/
	    	
	        user = userService.getOne(id.get());
	    	
	    	model.addAttribute("user",user);
	    	
  	      return "userTransactionReport";
	    }
	  
	    
      
}
