package com.dairy.controller;

import java.util.Date;

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
import com.dairy.service.DailyTransactionService;
import com.dairy.service.UserService;

@Controller
@RequestMapping("/transaction")
public class DailyTransactionController {
	
	 
	@Autowired
	DailyTransactionService dailyTransactionService ;
	     
	@Autowired
	UserService userService ;
	
	
        @RequestMapping(value="/all", method={RequestMethod.POST,RequestMethod.GET})	
	    public String getAllTransactions(Model model) {
	    	
    	   return "transaction";
	    }
        
        @RequestMapping(value="/user/{userId}", method={RequestMethod.POST,RequestMethod.GET})	    
        public String getTransactionsByUserId(@PathVariable("userId") int userId,Model model) {
        	
        	model.addAttribute("user", userService.getOne(userId));
        	
    	   return "transaction";
	    }
        
        @RequestMapping(value="/user/save", method={RequestMethod.POST,RequestMethod.GET})	    
        public String saveTransactionsByUserId(@Valid DailyTransaction dailyTransaction,  BindingResult bindingResult ,Model model) {
             
        	if (bindingResult.hasErrors()) {
				return "transaction";
			}
        	
        	dailyTransactionService.saveDailyTransaction(dailyTransaction);
        	
    	   return "transaction";
	    }
       
        @RequestMapping(value="/byDate", method={RequestMethod.POST,RequestMethod.GET})
        public String getTransactionsByDate(
	    		  @RequestParam(value="fromDate", required=true) String fromDate,
	              @RequestParam(value="toDate", required=false) Date todate,Model model) {
	    	
    	   return "transaction";
	    }
        
        @RequestMapping(value="/byUserIdandDate", method={RequestMethod.POST,RequestMethod.GET})
        public String getTransactionsByUserIdandDate(
        		  @RequestParam(value="userId", required=true) String userId,
	              @RequestParam(value="fromDate", required=true) Date fromdate,
	              @RequestParam(value="toDate", required=false) String toDate,Model model){
	    	
    	   return "transaction";
	    }
        
}
