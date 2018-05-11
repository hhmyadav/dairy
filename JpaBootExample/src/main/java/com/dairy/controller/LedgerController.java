package com.dairy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LedgerController {
	
	 
        
	  @PostMapping("/getLedger")
	  public String getTracsaction(@RequestParam(value="tracsactionId", required=true) String tracsactionId,Model model){
		 
		  return "index";
	  }  
	  
	  @PostMapping("/getFullLedger")
	  public String getAllgetTracsactions(Model model){
			 
		  return "index";
	  }
	  
}
