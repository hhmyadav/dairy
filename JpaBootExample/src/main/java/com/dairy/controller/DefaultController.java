package com.dairy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
   
   @RequestMapping(value={"/","/index","/result"}, method={RequestMethod.POST,RequestMethod.GET})
   public String indexGet(Model model) {
          return "homePage";
    }

    
}
