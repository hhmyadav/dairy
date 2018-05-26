package com.dairy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/transactionReport")
public class TransactionReportController {
          
	
	
	    @RequestMapping(value={""}, method={RequestMethod.POST,RequestMethod.GET})	
	    public String getAllTransactions(Model model) {
	    	
  	      return "transactionReport";
	    }
      
}
