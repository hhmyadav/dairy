package com.dairy.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String setPaymentForm(@PathVariable Optional<Integer> id ,User user , Ledger ledger  ,Model model) 
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
    		 ledger.setUser(user);
    	   }
    	   
    	   
    	   ledger.setTransactionDate(LocalDateTime.now());
    	   ledger.setDayType("m");
    	   if(LocalDateTime.now().getHour() >= 14)
    	   ledger.setDayType("e");
    	  
    	  
    	   return "paymentForm";
    }
    
    @RequestMapping(value={"/user/paymentReceipt"}, method={RequestMethod.POST})	
    public String paymentReceipt(@RequestParam(value = "paymentBy", required = false) String paymentBy,
    		                     @RequestParam(value = "paymentSummary", required = false) String paymentSummary,
    		                     @RequestParam(value = "todaydate", required = false)
    		                     @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime transactionDate ,
    		                     @Valid User user,Ledger ledger , BindingResult bindingResult ,Model model) {
    	     
    	if (bindingResult.hasErrors()) {
    		
    		System.out.println(bindingResult.getFieldErrorCount());
    		System.out.println(bindingResult.getAllErrors());
    		model.addAttribute("result",bindingResult.getAllErrors());
			return "paymentForm";
		}
    	
    	 if(!userService.existsById(ledger.getUser().getUserId())) 
   	   { model.addAttribute("result","Cannot find userId #" + ledger.getUser().getUserId());
   	     return "paymentForm";
   	   }
    	
    System.out.println(user.getName());
    	/*  if(user.getUserId() > 0)
 	   { 
 		if(!userService.existsById(user.getUserId())) 
 	     { 
 		  model.addAttribute("result","Cannot find userId #" + user.getUserId());
 	      return "paymentForm";
 	     }
 	   }*/
    	
    	/*
       if(paymentType !=null && !paymentType.isEmpty())	
       {   
    	  List<Ledger> ledgers = ledgerService.getLedgersByUserAndPaymentType(user,paymentType);
          user.setLedgers(ledgers);
       }
    	model.addAttribute("user",user);*/
    	
	      return "paymentReceipt";
    }

}
