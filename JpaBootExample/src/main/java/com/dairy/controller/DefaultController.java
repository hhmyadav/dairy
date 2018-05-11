package com.dairy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController {
	
	
	@Value("${dairyName}")
	private String dairyName;
	
	@Value("${ownerName}")
	private String ownerName;
	
	
	    @ModelAttribute
	    public void addAttributes(Model model) {
	      	model.addAttribute("dairyName",dairyName);
			model.addAttribute("ownerName",ownerName);
	    }
    
    @GetMapping({ "/","/index","/result"})
    public String indexGet(Model model) {
          return "index";
    }

    @PostMapping({"/","/index",})
    public String homePost(Model model) {
    	return "index";
    }
    
   
}
