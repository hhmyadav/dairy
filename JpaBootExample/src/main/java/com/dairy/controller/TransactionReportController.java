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
import org.springframework.web.bind.annotation.RequestParam;

import com.dairy.model.EntryForm;
import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.repository.LedgerRepository;
import com.dairy.service.LedgerService;
import com.dairy.service.TransactionReportService;
import com.dairy.service.UserService;

@Controller
@RequestMapping("/transactionReport")
public class TransactionReportController {
       
	
	@Autowired
	TransactionReportService transactionReportService ;
	
	@Autowired
	UserService userService ;
	
	@Autowired
	LedgerService ledgerService ;
	
	@Autowired
	LedgerRepository ledgerRepository ;
	
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
	    
	    
	    @RequestMapping(value={"milkRecords/user/{id}"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String userTransactionReport(User user ,@PathVariable Optional<Integer> id ,Model model) {
	    	
	    	if(id.isPresent())
     	   { 
     		  Integer userId = id.get();
     		if(!userService.existsById(userId)) 
     	     { 
     		  model.addAttribute("result","Cannot find userId #" + userId);
     	      return "userMilkPurchaseHistory";
     	     }
     	   }
	    	user = userService.getOne(id.get());
	    	 
	    	model.addAttribute("user",user);
	    	
  	      return "userMilkPurchaseHistory";
	    }
	    
	    
	    @RequestMapping(value={"paymentHistory/user/{id}"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String userLedgerHistory(@RequestParam(value = "paymentType", required = false) String paymentType,
	    		                        @RequestParam(value = "transactionStartDate", required = false) String transactionStartDate,
	    		                        @RequestParam(value = "transactionEndDate", required = false) String transactionEndDate,
	    		                        @RequestParam(value = "dayType", required = false) String dayType,
	    		                        @PathVariable Optional<Integer> id ,Ledger ledger ,User user ,Model model) {
	    	     
	       if(id.isPresent())
     	   { 
     		  Integer userId = id.get();
     		if(!userService.existsById(userId)) 
     	     { 
     		  model.addAttribute("result","Cannot find userId #" + userId);
     	      return "userLedgerHistory";
     	     }
     	   }
	    	
	    	user = userService.getOne(id.get());
	    	
	       if(paymentType !=null && !paymentType.isEmpty())	
	       {   
	    	  List<Ledger> ledgers = ledgerService.getLedgersByUserAndPaymentType(user,paymentType);
	          user.setLedgers(ledgers);
	       }
	    	model.addAttribute("user",user);
	    	
  	      return "userLedgerHistory";
	    }
	    
	    @RequestMapping(value={"paymentHistory"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String ledgerHistory(@RequestParam(value = "paymentType", required = false) String paymentType,
	    		                    @RequestParam(value = "transactionStartDate", required = false) String transactionStartDate,
	    		                    @RequestParam(value = "transactionEndDate", required = false) String transactionEndDate,
	    		                    @RequestParam(value = "dayType", required = false) String dayType,
	    		                    @RequestParam(value = "paymentBy", required = false) String paymentBy,
	    		                    
	    		                    Model model) {
	    	
	    	List<Ledger> ledgers = ledgerService.getLedgersByAny(dayType, paymentType, paymentBy, transactionStartDate, transactionEndDate);
		    	 
	    	
	    	double totalCredit = 0 ;
	    	double totalDebit  = 0 ;
	    	
	    	
	    	for (int i = 0; i < ledgers.size(); i++) {
	    		
	    		Ledger ledger = ledgers.get(i);
	    		
	    		if(ledger.getPaymentType().equals("CREDIT"))
	    		  totalCredit = totalCredit + ledger.getAmount();
	    		
	    		if(ledger.getPaymentType().equals("DEBIT"))
	    		  totalDebit = totalDebit + ledger.getAmount();
	    		
			}
	    	
	    	double unBalancedAmount = totalCredit - totalDebit ;
	    	
	    	      model.addAttribute("ledgers",ledgers);
		    	  model.addAttribute("numberOfTransactions",ledgers.size());
		    	  model.addAttribute("totalCredit",totalCredit);
		    	  model.addAttribute("totalDebit",totalDebit);
		    	  model.addAttribute("profit",unBalancedAmount);
		    	  model.addAttribute("dairyName","KrishnaDairy");
		    	  
	       return "ledgerHistory2";
	    
	    }
	    
	    
	    
	   
	    
      
}
