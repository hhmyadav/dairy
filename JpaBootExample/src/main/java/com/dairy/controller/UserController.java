package com.dairy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dairy.model.User;
import com.dairy.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService ;
	
	   
	    @PostMapping("/getUser")
	    public String getUser(@RequestParam(value="userId", required=false) String userId,
	    		              @RequestParam(value="firstName", required=false) String firstName,
	    		              @RequestParam(value="mobileNumber", required=false) String mobileNumber,Model model){
	    	
	    	if(userId != null || firstName != null  || mobileNumber != null)
	    	 return "index" ;
	    	
	    	
  	     return "index";
	    }
	    
	    @PostMapping("/addUser")
	    public String addUser(@Valid User user,  BindingResult bindingResult ,Model model) {
	    	
			if (bindingResult.hasErrors()) {
				return "manageUsers";
			}
	    	  userService.addUser(user);
		      model.addAttribute("userId",user.getUserId());
  	     return "forward:/manageUsers";
	    }
	    
	    
	    
	    
	    
}
