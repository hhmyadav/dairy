package com.dairy.controller;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
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

import com.dairy.model.EntryForm;
import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.service.EntryFormService;
import com.dairy.service.LedgerService;
import com.dairy.service.UserService;

@Controller
@RequestMapping("paymentForm")
public class PaymentFormController {
	 
	
	@Autowired
	UserService userService ;
	
	@Autowired 
	LedgerService ledgerService ;
	
	@Autowired
	EntryFormService entryFormService ; 
	
	DateTimeFormatter ddMMyyyyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	DecimalFormat df = new DecimalFormat("#.##");  
	
    @RequestMapping(value={"","user","user/{id}"} , method={RequestMethod.POST,RequestMethod.GET})	    
    public String setPaymentForm(@PathVariable Optional<Integer> id ,
    		                     @RequestParam(value = "fromLastPaid", required = false) Boolean fromLastPaid,
    		                     @RequestParam(value = "fromDate", required = false)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime fromDate ,
                                 @RequestParam(value = "toDate", required = false)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")  LocalDateTime toDate,                     
    		                     User user , Ledger ledger ,Model model) {
          
    	  
    	   ledger.setUser(user);   
    	   
    	   if(!id.isPresent())
    		   return "paymentForm";
    	   
    	   
    		  Integer userId = id.get();
    		 
    		 if(!userService.existsById(userId)) 
    	     { 
    		  model.addAttribute("result","Cannot find userId #" + userId);
    	      return "paymentForm";
    	     }
    		 user = userService.getOne(userId);
    		 
    		 if(fromDate==null)
       		  {
    			 fromDate = LocalDateTime.now().minusDays(11);
    		  }
    		 
    		 List<EntryForm> entryForms = entryFormService.getEntryForms(model , userId.longValue(), fromDate, toDate ,fromLastPaid);
    		 
    		 
    		 user.setEntryForms(entryForms);
    		 
    		 ledger = ledgerService.setDefaultLedgerForPaymentForm(ledger , user);
    		 
    		
    		 toDate = LocalDateTime.now();
    		 long numberOfDays = ChronoUnit.DAYS.between(fromDate, toDate);
    		 
    		 model.addAttribute("numberOfDays",numberOfDays);
    		 
    		 
    		 
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
      if(ledger.getAmount() == 1)
      {
    	  model.addAttribute("result","Amount cannot be less than Rs. 1/-");
    	     return "paymentForm";
      }
    	
        ledger =  ledgerService.saveLedgerFromPaymentForm(ledger);
    	userService.updateBalance(ledger);
       
    	ledger.setUser(userService.getOne(ledger.getUser().getUserId()));
    	
    	
    	double cowMilkQuantity = 0 ;
    	double cowMilkAmount = 0 ;
    	double buffaloMilkQuantity = 0 ;
    	double buffaloMilkAmount = 0 ;
    	double milkTotalQuantity = 0 ;
    	double finalAmount = 0 ;
    	
    	List<EntryForm> entryForm = ledger.getUser().getEntryForms();
         
		   Iterator<EntryForm> entryForms = entryForm.iterator();
		  
		  while (entryForms.hasNext()) {
		
			EntryForm entryform = entryForms.next();
			
			if(entryform.getMilkType().equals("cow"))
			{
				cowMilkQuantity = cowMilkQuantity + entryform.getMilkQuantity();
				cowMilkAmount = cowMilkAmount + entryform.getTotalAmount();
			}
			
			if(entryform.getMilkType().equals("buffelow"))
			{
				buffaloMilkQuantity = buffaloMilkQuantity + entryform.getMilkQuantity();
				buffaloMilkAmount = buffaloMilkAmount + entryform.getTotalAmount();
			}
				
			milkTotalQuantity = milkTotalQuantity + entryform.getMilkQuantity();
			finalAmount = finalAmount + entryform.getTotalAmount();
			
		  }
    	
    	
    	model.addAttribute("buffaloMilkQuantity",buffaloMilkQuantity);
    	model.addAttribute("buffaloMilkAmount",buffaloMilkAmount);
    	model.addAttribute("cowMilkQuantity",cowMilkQuantity);
    	model.addAttribute("cowMilkAmount",cowMilkAmount);
    	model.addAttribute("receiptNumber",ledger.getId());
    	
    	
	      return "paymentReceipt";
    }

}
