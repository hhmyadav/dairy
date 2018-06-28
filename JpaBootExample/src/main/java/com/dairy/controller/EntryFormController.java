package com.dairy.controller;


import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dairy.model.EntryForm;
import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.service.EntryFormService;
import com.dairy.service.UserService;

@Controller
@RequestMapping("entryForm")
public class EntryFormController {
	
	 
	@Autowired
	EntryFormService entryFormService ;
	     
	@Autowired
	UserService userService ;
	   

	
	    @RequestMapping(value={"user/{type}","user/{type}/{id}"} , method={RequestMethod.GET})	    
        public String setEntryFormByUserId(@PathVariable String type,@PathVariable Optional<Integer> id , User user , EntryForm entryForm,Model model) {
        	
        	   if(id.isPresent())
        	   { 
        		  Integer userId = id.get();
        		if(!userService.existsById(userId)) 
        	     { 
        		  model.addAttribute("result","Cannot find userId #" + userId);
        		  if(type.toLowerCase().equals("buy"))
               	   return "buyEntryForm";
        		  return "sellEntryForm";
        	     }
        		user = userService.getOne(userId);
        		entryForm.setUser(user);
        	   }
        	   
        	   entryForm.setEntryDateTime(LocalDateTime.now());
        	   
        	   entryForm.setDayType("MORNING");
        	   if(LocalDateTime.now().getHour() >= 14)
        	   entryForm.setDayType("EVENING");
        	   
        	   entryForm.setMilkType("Buffalo");
        	   
        	   if(type.toLowerCase().equals("sell"))
        	   {   entryForm.setType("SELL");
        	       entryForm.setPerLiterPrice(40.0); 
        		   return "sellEntryForm";
        	   }
        	   entryForm.setType("BUY");
        	   return "test2";
	    }
        
        
        @RequestMapping(value="user/{type}/save", method={RequestMethod.POST})	    
        public String saveEntryForm(Ledger ledger , @Valid EntryForm entryForm,RedirectAttributes redirectAttribute , BindingResult bindingResult ,Model model) {
             
        	
            if (bindingResult.hasErrors()) {
        		
        		System.out.println(bindingResult.getFieldErrorCount());
        		System.out.println(bindingResult.getAllErrors());
        		model.addAttribute("result",bindingResult.getAllErrors());
				return "entryForm";
			}
            
           if(!userService.existsById(entryForm.getUser().getUserId())) 
     	   { model.addAttribute("result","Cannot find userId #" + entryForm.getUser().getUserId());
     	     return "entryForm";
     	   }
     	  
            
        	entryForm = entryFormService.saveEntryForm(ledger,entryForm);
        	
        	redirectAttribute.addFlashAttribute("result", "Successfully Saved "+entryForm.getUser().getName()+ " Entry");
        	return "redirect:/entryForm/user/{type}/" + entryForm.getUser().getUserId();
	    }
       
      
        
}
