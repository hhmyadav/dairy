package com.dairy.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.service.UserService;


@Controller
@RequestMapping("/userOperation")
public class UserController {

	@Autowired
	UserService userService ;
	
	
	
	    @RequestMapping(value="/users", method={RequestMethod.POST,RequestMethod.GET})
	    public String manageUsersPost(User user ,Model model) {
	     model.addAttribute("users",userService.getAllUsers());
	     return "users";
	    }
	   
	   
	    @PostMapping("/getUser")
	    public String getUser(@RequestParam(value="userId", required=false) String userId,
	    		              @RequestParam(value="firstName", required=false) String firstName,
	    		              @RequestParam(value="mobileNumber", required=false) String mobileNumber,Model model){
	    	
	    	if(userId != null || firstName != null  || mobileNumber != null)
	    	 return "index" ;
	    	
	    	
  	     return "index";
	    }
	    
	   
	    @RequestMapping(value="/addUser", method={RequestMethod.POST,RequestMethod.GET})	
	    public String addUser(Ledger ledger , @Valid User user ,  BindingResult bindingResult ,Model model) {
	    	
			if (bindingResult.hasErrors()) {
				return "manageUsers";
			}  
			 
			 user.getLedgers().add(ledger);	
			 userService.addUser(user);
		     
  	     return "forward:/userOperation/users";
	    }
	    
	    @RequestMapping(value="/editUser/{userId}", method={RequestMethod.POST,RequestMethod.GET})	
	    public String editUser(@PathVariable("userId") Long userId,@Valid User user,  BindingResult bindingResult ,Model model) {
	    	
	    	
			if (bindingResult.hasErrors()) {
				return "manageUsers";
			}
	    	  userService.addUser(user);
		     
  	     return "forward:/userOperation/users";
	    }
	 
	    @RequestMapping(value="/deleteUser/{userId}", method={RequestMethod.POST,RequestMethod.GET})	
	    public String deleteUser(@PathVariable("userId") Long userId,@Valid User user,  BindingResult bindingResult ,Model model) {
	    	
			if (bindingResult.hasErrors()) {
				return "manageUsers";
			}
	    	  userService.deleteUser(userId);
		     
  	     return "forward:/userOperation/users";
	    }
	    
	   
	   
	    
	    
	    
	    
	    
	    
}
