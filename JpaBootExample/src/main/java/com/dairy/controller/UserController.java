package com.dairy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	
	    @PostMapping("/getAllUsers")
	    public String getAllUsers(Model model) {
	    	
  	     return "index";
	    }
	    
	    @PostMapping("/getUser")
	    public String getUser(@RequestParam(value="userId", required=false) String userId,
	    		              @RequestParam(value="firstName", required=false) String firstName,
	    		              @RequestParam(value="mobileNumber", required=false) String mobileNumber,Model model){
	    	
	    	if(userId != null || firstName != null  || mobileNumber != null)
	    	 return "index" ;
	    	
	    	
	    	
  	     return "index";
	    }
	    
	    
	    
}
