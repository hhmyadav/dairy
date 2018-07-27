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

import com.dairy.model.EntryForm;
import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.service.EntryFormService;
import com.dairy.service.LedgerService;
import com.dairy.service.PaymentRecieptService;
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
	
	@Autowired
	PaymentRecieptService paymentRecieptService ;
	
	
    @RequestMapping(value={"buy","buy/user","buy/user/{id}"} , method={RequestMethod.POST,RequestMethod.GET})	    
    public String getPaymentFormBuy(@PathVariable Optional<Integer> id ,
    		                     @RequestParam(value = "numberOfLastDays", required = false) Integer numberOfLastDays,
    		                     @RequestParam(value = "fromDate", required = false)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime fromDate ,
                                 @RequestParam(value = "toDate", required = false)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")  LocalDateTime toDate,                     
    		                     User user , Ledger ledger ,Model model) {
          
    	
    	   ledger.setUser(user);   
    	   
    	   if(!id.isPresent())
    		   return "paymentFormBuy";
    	   
    	   
    		  Integer userId = id.get();
    		 
    		 if(!userService.existsById(userId)) 
    	     { 
    		  model.addAttribute("result","Cannot find userId #" + userId);
    	      return "paymentFormBuy";
    	     }
    		 user = userService.getOne(userId);
    		
    		 List<EntryForm> entryForms = entryFormService.getEntryFormsBuy(model , userId.longValue(), fromDate, toDate ,numberOfLastDays);
    		 
    		 paymentRecieptService.setPaymentForm(entryForms, model);
    		 
    		 user.setEntryForms(entryForms);
    		 
    		 ledger = ledgerService.setDefaultLedgerForPaymentForm(ledger , user);
    		 
    		 model.addAttribute("ledger",ledger);
    		 
    	    return "paymentFormBuy";
    }
    
    
    
    @RequestMapping(value={"buy/user/paymentReceipt"}, method={RequestMethod.POST})	
    public String paymentReceiptBuy(@Valid Ledger ledger,
    		                     @RequestParam(value = "fromDate", required = true)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime fromDate ,
                                 @RequestParam(value = "toDate", required = true)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")  LocalDateTime toDate,
    		                     @RequestParam(value = "numberOfDays", required = false) Long numberOfDays,
    		                     @RequestParam(value = "cowMilkQuantity", required = false) Double cowMilkQuantity,
    		                     @RequestParam(value = "cowMilkAmount", required = false) Double cowMilkAmount,
    		                     @RequestParam(value = "buffaloMilkQuantity", required = false) Double buffaloMilkQuantity,
    		                     @RequestParam(value = "buffaloMilkAmount", required = false) Double buffaloMilkAmount,
    		                     @RequestParam(value = "milkTotalQuantity", required = true) Double milkTotalQuantity,
    		                     @RequestParam(value = "finalAmount", required = true) Double finalAmount,
    		                     BindingResult bindingResult ,Model model) {
    	    
    	if (bindingResult.hasErrors()) 
    	{
    		model.addAttribute("result",bindingResult.getAllErrors());
			return "paymentForm";
		}
    	
      if(!userService.existsById(ledger.getUser().getUserId())) 
   	  { model.addAttribute("result","Cannot find userId #" + ledger.getUser().getUserId());
   	     return "paymentForm";
   	  }
      
      
      if(ledger.getAmount()==null || ledger.getAmount() < 1)
      {
    	  model.addAttribute("result","Amount cannot be less than Rs. 1/-");
    	     return "paymentForm";
      }
        
        
    	ledger.setUser(userService.getOne(ledger.getUser().getUserId()));
    	  
    	ledger =  ledgerService.saveLedgerFromPaymentForm(fromDate , toDate, ledger);
    	userService.updateBalance(ledger);
       
    	
    	  paymentRecieptService.setPaymentReciept(ledger ,model, fromDate, toDate, numberOfDays, cowMilkQuantity, cowMilkAmount, buffaloMilkQuantity, buffaloMilkAmount, milkTotalQuantity, finalAmount);
    	
	      return "paymentReceipt";	
    }
    
    
    
    @RequestMapping(value={"sell","sell/user","sell/user/{id}"} , method={RequestMethod.POST,RequestMethod.GET})	    
    public String getPaymentFormSell(@PathVariable Optional<Integer> id ,
    		                     @RequestParam(value = "numberOfLastDays", required = false) Integer numberOfLastDays,
    		                     @RequestParam(value = "fromDate", required = false)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime fromDate ,
                                 @RequestParam(value = "toDate", required = false)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")  LocalDateTime toDate,                     
    		                     User user , Ledger ledger ,Model model) {
          
    	
    	   ledger.setUser(user);   
    	   
    	   if(!id.isPresent())
    		   return "paymentFormSell";
    	   
    	   
    		  Integer userId = id.get();
    		 
    		 if(!userService.existsById(userId)) 
    	     { 
    		  model.addAttribute("result","Cannot find userId #" + userId);
    	      return "paymentFormSell";
    	     }
    		 user = userService.getOne(userId);
    		
    		 List<EntryForm> entryForms = entryFormService.getEntryFormsSell(model , userId.longValue(), fromDate, toDate ,numberOfLastDays);
    		 
    		 paymentRecieptService.setPaymentForm(entryForms, model);
    		 
    		 user.setEntryForms(entryForms);
    		 
    		 ledger = ledgerService.setDefaultLedgerForPaymentForm(ledger , user);
    		 
    		 model.addAttribute("ledger",ledger);
    		 
    	    return "paymentFormSell";
    }
      
    
    @RequestMapping(value={"sell/user/paymentReceipt"}, method={RequestMethod.POST})	
    public String paymentReceiptSell(@Valid Ledger ledger,
    		                     @RequestParam(value = "fromDate", required = true)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime fromDate ,
                                 @RequestParam(value = "toDate", required = true)
                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")  LocalDateTime toDate,
    		                     @RequestParam(value = "numberOfDays", required = false) Long numberOfDays,
    		                     @RequestParam(value = "cowMilkQuantity", required = false) Double cowMilkQuantity,
    		                     @RequestParam(value = "cowMilkAmount", required = false) Double cowMilkAmount,
    		                     @RequestParam(value = "buffaloMilkQuantity", required = false) Double buffaloMilkQuantity,
    		                     @RequestParam(value = "buffaloMilkAmount", required = false) Double buffaloMilkAmount,
    		                     @RequestParam(value = "milkTotalQuantity", required = true) Double milkTotalQuantity,
    		                     @RequestParam(value = "finalAmount", required = true) Double finalAmount,
    		                     BindingResult bindingResult ,Model model) {
    	    
    	if (bindingResult.hasErrors()) 
    	{
    		model.addAttribute("result",bindingResult.getAllErrors());
			return "paymentForm";
		}
    	
      if(!userService.existsById(ledger.getUser().getUserId())) 
   	  { model.addAttribute("result","Cannot find userId #" + ledger.getUser().getUserId());
   	     return "paymentForm";
   	  }
      
      
      if(ledger.getAmount()==null || ledger.getAmount() < 1)
      {
    	  model.addAttribute("result","Amount cannot be less than Rs. 1/-");
    	     return "paymentForm";
      }
        
        
    	ledger.setUser(userService.getOne(ledger.getUser().getUserId()));
    	  
    	ledger =  ledgerService.saveLedgerFromPaymentForm(fromDate , toDate, ledger);
    	userService.updateBalance(ledger);
       
    	
    	  paymentRecieptService.setPaymentReciept(ledger ,model, fromDate, toDate, numberOfDays, cowMilkQuantity, cowMilkAmount, buffaloMilkQuantity, buffaloMilkAmount, milkTotalQuantity, finalAmount);
    	
	      return "paymentReceipt";	
    }
    
}
