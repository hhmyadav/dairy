package com.dairy.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dairy.model.EntryForm;
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
    public String setPaymentForm(@PathVariable Optional<Integer> id ,Ledger ledger , User user ,Model model) {
    	
    	   if(id.isPresent())
    	   { 
    		  Integer userId = id.get();
    		if(!userService.existsById(userId)) 
    	     { 
    		  model.addAttribute("result","Cannot find userId #" + userId);
    	      return "paymentForm";
    	     }
    		user = userService.getOne(userId);
    		ledger.setUser(user);
    	   }
    	   
    	   ledger.setDayType("m");
    	   if(LocalDateTime.now().getHour() >= 14)
    	   ledger.setDayType("e");
    	   
    	   ledger.setPaymentBy("Cash");
    	   return "entryForm";
    }

}
