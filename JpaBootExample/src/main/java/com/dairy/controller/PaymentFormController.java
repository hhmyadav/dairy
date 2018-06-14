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

import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.service.LedgerService;
import com.dairy.service.UserService;

@Controller
@RequestMapping("paymentForm")
public class PaymentFormController {
	 
	
	@Autowired
	UserService userService ;
	
	@Autowired 
	LedgerService ledgerService ;
	
	
    @RequestMapping(value={"","user","user/{id}"} , method={RequestMethod.POST,RequestMethod.GET})	    
    public String setPaymentForm(@PathVariable Optional<Integer> id , User user ,Model model) 
    {
    	
    	   if(id.isPresent())
    	   { 
    		  Integer userId = id.get();
    		if(!userService.existsById(userId)) 
    	     { 
    		  model.addAttribute("result","Cannot find userId #" + userId);
    	      return "paymentForm";
    	     }
    		user = userService.getOne(userId);
    	   }
    	   
    	   
    	   model.addAttribute("user", user);
    	   return "paymentForm";
    }
    
    @RequestMapping(value={"paymentReceipt/user/{id}"}, method={RequestMethod.POST,RequestMethod.GET})	
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
 	      return "paymentReceipt";
 	     }
 	   }
    	
    	user = userService.getOne(id.get());
    	
       if(paymentType !=null && !paymentType.isEmpty())	
       {   
    	  List<Ledger> ledgers = ledgerService.getLedgersByUserAndPaymentType(user,paymentType);
          user.setLedgers(ledgers);
       }
    	model.addAttribute("user",user);
    	
	      return "paymentReceipt";
    }

}
