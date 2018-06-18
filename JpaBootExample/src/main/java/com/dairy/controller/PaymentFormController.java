package com.dairy.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String setPaymentForm(@PathVariable Optional<Integer> id , User user , Ledger ledger  ,Model model) 
    {
    	   ledger.setUser(user);   
    	   
    	   if(id.isPresent())
    	   { 
    		  Integer userId = id.get();
    		if(!userService.existsById(userId)) 
    	     { 
    		  model.addAttribute("result","Cannot find userId #" + userId);
    	      return "paymentForm";
    	     }
    		 user = userService.getOne(userId);
    		 ledger = ledgerService.setDefaultLedgerForPaymentForm(ledger , user);
    	   }
    	   
         	 
         	
           ledger.setTransactionDate(LocalDateTime.now());
      	   ledger.setDayType("m");
      	   if(LocalDateTime.now().getHour() >= 14)
      	   ledger.setDayType("e");
      	  
    	   return "paymentForm";
    }
    
    @RequestMapping(value={"/user/paymentReceipt"}, method={RequestMethod.POST})	
    public String paymentReceipt(@Valid Ledger ledger, BindingResult bindingResult ,Model model) {
    	    
    	if (bindingResult.hasErrors()) {
    		model.addAttribute("result",bindingResult.getAllErrors());
			return "paymentForm";
		}
    	
      if(!userService.existsById(ledger.getUser().getUserId())) 
   	   { model.addAttribute("result","Cannot find userId #" + ledger.getUser().getUserId());
   	     return "paymentForm";
   	   }
    	
        ledger =  ledgerService.saveLedgerFromPaymentForm(ledger);
    	userService.updateBalance(ledger);
        
    	ledger.setUser(userService.getOne(ledger.getUser().getUserId()));
    	
    	
    	
    	ledger.setUser(ledger.getUser().setEntryForms(entryForms));
    	
    	
    	double cowMilkQuantity = 23 ;
    	double buffaloMilkQuantity = 12 ;
    	
    	
    	model.addAttribute("buffaloMilkQuantity",buffaloMilkQuantity);
    	model.addAttribute("cowMilkQuantity",cowMilkQuantity);
    	model.addAttribute("receiptNumber",43242341);
    	
    	
	      return "paymentReceipt";
    }

}
