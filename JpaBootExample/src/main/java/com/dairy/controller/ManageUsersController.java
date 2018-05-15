package com.dairy.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dairy.model.User;
import com.dairy.service.UserService;

@Controller
public class ManageUsersController {
    
	@Autowired
	UserService userService ;
	     
	
	    @RequestMapping(value="/manageUsers", method={RequestMethod.POST,RequestMethod.GET})
	    public String manageUsersPost(User user ,Model model) {
	    	
	    model.addAttribute("users",userService.getAllUsers());
	    	
  	     return "manageUsers";
	    }
	   
}
