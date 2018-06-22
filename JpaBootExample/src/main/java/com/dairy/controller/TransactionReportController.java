package com.dairy.controller;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	
	DecimalFormat df = new DecimalFormat("#.##");  
	
	
	    @RequestMapping(value={""}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String getAllTransactions(Model model) {
	    	
  	      return "ledgerHistory2";
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
	    
	    
	    @RequestMapping(value={"paymentHistory"}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String ledgerHistory(@RequestParam(value = "userId", required = false) Long userId,
	    		                    @RequestParam(value = "paymentType", required = false) String paymentType,
	    		                    @RequestParam(value = "transactionStartDate", required = false)
	    		                    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime transactionStartDate ,
	    		                    @RequestParam(value = "transactionEndDate", required = false)
	                                @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")  LocalDateTime transactionEndDate,
	    		                    @RequestParam(value = "dayType", required = false) String dayType,
	    		                    @RequestParam(value = "paymentBy", required = false) String paymentBy,
	    		                    
	    		                    Model model) {
	    	
	    	if(userId != null && userId > 0)
	    	{
	    		if(!userService.existsById(userId)) 
	     	     { 
	     		  model.addAttribute("result","Cannot find userId #" + userId);
	     	      return "ledgerHistory2";
	     	     }
	    	}
	    	
	    	
	    	List<Ledger> ledgers = ledgerService.getLedgersByAny(userId,dayType, paymentType, paymentBy, transactionStartDate ,transactionEndDate);
		    	 
	    	
	    	Double totalCredit = (double) 0 ;
	    	Double totalDebit  = (double) 0 ;
	    	
	    	
	    	for (int i = 0; i < ledgers.size(); i++) {
	    		
	    		Ledger ledger = ledgers.get(i);
	    		
	    		if(ledger.getPaymentType().equals("CREDIT"))
	    		  totalCredit = totalCredit + ledger.getAmount();
	    		
	    		if(ledger.getPaymentType().equals("DEBIT"))
	    		  totalDebit = totalDebit + ledger.getAmount();
	    		
			}
	    	
	    	double remainingAmount = totalCredit - totalDebit ;
	    	
	    	      model.addAttribute("ledgers",ledgers);
		    	  model.addAttribute("numberOfTransactions",ledgers.size());
		    	  model.addAttribute("totalCredit",df.format(totalCredit));
		    	  model.addAttribute("totalDebit",df.format(totalDebit));
		    	  model.addAttribute("remainingAmount",df.format(remainingAmount));
		    	  model.addAttribute("dairyName","KrishnaDairy");
		    	  
		    	  
	       return "ledgerHistory2";
	    
	    }
	    
	    
	    
	   
	    
      
}
