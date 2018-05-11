package com.dairy.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DailyTransactionController {
	
	
        @PostMapping("/allTracsactions")
	    public String getAllTransactions(Model model) {
	    	
    	   return "index";
	    }
        
        @PostMapping("/transactionsOfUser")
	    public String getTransactionsByDateUserId(@RequestParam(value="userId", required=true) String userId,Model model) {
	    	
    	   return "index";
	    }
        
        @PostMapping("/transactionsByDate")
	    public String getTransactionsByDate(
	    		  @RequestParam(value="fromDate", required=true) String fromDate,
	              @RequestParam(value="toDate", required=false) Date todate,Model model) {
	    	
    	   return "index";
	    }
        
        
        @PostMapping("/transactionsByUserIdandDate") public String getTransactionsByUserIdandDate(
        		  @RequestParam(value="userId", required=true) String userId,
	              @RequestParam(value="fromDate", required=true) Date fromdate,
	              @RequestParam(value="toDate", required=false) String toDate,Model model){
	    	
    	   return "index";
	    }
        
}
