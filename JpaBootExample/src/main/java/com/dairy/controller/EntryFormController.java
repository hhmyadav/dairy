package com.dairy.controller;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dairy.model.DailyTransaction;
import com.dairy.model.User;
import com.dairy.service.DailyTransactionService;
import com.dairy.service.UserService;

@Controller
@RequestMapping("transaction")
public class EntryFormController {
	
	 
	@Autowired
	DailyTransactionService dailyTransactionService ;
	     
	@Autowired
	UserService userService ;
	     
	
	
	    @RequestMapping(value={"","user","user/{id}"} , method={RequestMethod.POST,RequestMethod.GET})	    
        public String setUserTransactionByUserId(@PathVariable Optional<Integer> id ,User user,DailyTransaction dailyTransaction,Model model) {
        	
        	   if(id.isPresent())
        	   { 
        		  Integer userId = id.get();
        		if(!userService.existsById(userId)) 
        	     { 
        		  model.addAttribute("result","Cannot find userId #" + userId);
        	      return "transaction";
        	     }
        	    user = userService.getOne(userId);
        	   }
        	   dailyTransaction.setUser(user);
        	   dailyTransaction.setCreatedDateTime(LocalDateTime.now());
        	   
        	   dailyTransaction.setDayType('m');
        	   
        	   if(LocalDateTime.now().getHour() >= 14)
        	   dailyTransaction.setDayType('e');
        	   
               
        	   dailyTransaction.setMilkType("buffelow");
        	   
        	
    	   return "transaction";
	    }
        
       
        
        @RequestMapping(value="user/save", method={RequestMethod.POST})	    
        public String saveUserTransaction(User user , @Valid DailyTransaction dailyTransaction,  BindingResult bindingResult ,Model model) {
             
            if (bindingResult.hasErrors()) {
        		
        		System.out.println(bindingResult.getFieldErrorCount());
        		System.out.println(bindingResult.getAllErrors());
        		model.addAttribute("result",bindingResult.getAllErrors());
				return "transaction";
			}
            
            Long userId = dailyTransaction.getUser().getUserId();
            
           if(!userService.existsById(userId)) 
     	   { model.addAttribute("result","Cannot find userId #" + userId);
     	     return "transaction";
     	   }
     	  
        	if (dailyTransaction.getCreatedDateTime() == null) {
        		dailyTransaction.setCreatedDateTime(LocalDateTime.now());
            }
        	
        	
            
        	
        	dailyTransactionService.saveDailyTransaction(dailyTransaction);
        	
        	
        	user = userService.getOne(userId);
 	   
 	        dailyTransaction.setUser(user);
 	        dailyTransaction.setCreatedDateTime(LocalDateTime.now());
 	   
 	        dailyTransaction.setDayType('m');
 	   
 	        if(LocalDateTime.now().getHour() >= 14)
 	        dailyTransaction.setDayType('e');
 	        
        
 	        dailyTransaction.setMilkType("buffelow");
        	
        	
        	model.addAttribute("result","Successfully Saved "+user.getName()+ " Transaction.");
    	   		
        	return "transaction";
	    }
       
        
        
       /* @RequestMapping(value="byDate", method={RequestMethod.POST,RequestMethod.GET})
        public String getTransactionsByDate(
	    		  @RequestParam(value="fromDate", required=true) String fromDate,
	              @RequestParam(value="toDate", required=false) Date todate,Model model) {
	    	
    	   return "transaction";
	    }
        
        @RequestMapping(value="byUserIdandDate", method={RequestMethod.POST,RequestMethod.GET})
        public String getTransactionsByUserIdandDate(
        		  @RequestParam(value="userId", required=true) String userId,
	              @RequestParam(value="fromDate", required=true) Date fromdate,
	              @RequestParam(value="toDate", required=false) String toDate,Model model){
	    	
    	   return "transaction";
	    }*/
        
}
