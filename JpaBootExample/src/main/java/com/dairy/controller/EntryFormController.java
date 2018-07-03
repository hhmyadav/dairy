package com.dairy.controller;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        public String entryFormController(@RequestParam("pageSize") Optional<Integer> pageSize,
                                          @RequestParam("page") Optional<Integer> page,
                                          @PathVariable String type,
                                          @PathVariable Optional<Integer> id ,
                                          EntryForm entryForm, 
                                          User user ,
                                          BindingResult bindingResult, 
                                          Model model) {
        	                               
                if(!type.toUpperCase().equals("BUY") && !type.toUpperCase().equals("SELL"))  
	    	     return "dashBoard";
               	 
                entryFormService.setEntryFormsPaginationAndSorting(model,pageSize,page,type);
             	  
	           if(id.isPresent())
        	   { 
            	  Integer userId = id.get();
        		if(!userService.existsById(userId)) 
        	     { 
        		  model.addAttribute("result","Cannot find userId #" + userId);
        		  if(type.toUpperCase().equals("BUY"))
               	   return "buyMilk";
        		  return "sellMilk";
        	     }
        		user = userService.getOne(userId);
        		entryForm.setUser(user);
        	   }
        	   
               entryForm = entryFormService.setDefaultEntryFormValues(entryForm,type);
	         
               if(entryForm.getType().equals("SELL"))
        	   return "sellMilk";
        	   else 
        	   return "buyMilk";
    	    
	    }
        
        
        @RequestMapping(value="user/{type}/save", method={RequestMethod.POST})	    
        public String saveEntryForm(@PathVariable String type ,
        		                    @Valid EntryForm entryForm,
        		                    Ledger ledger , 
        		                    RedirectAttributes redirectAttribute , 
        		                    BindingResult bindingResult ,Model model) {
             
        	
            if (bindingResult.hasErrors()) 
            {
        		model.addAttribute("result",bindingResult.getAllErrors());
				if(type.toUpperCase().equals("BUY"))
        		return "buyMilk";
				return "sellMilk";
			}
            
           if(!userService.existsById(entryForm.getUser().getUserId())) 
     	   { model.addAttribute("result","Cannot find userId #" + entryForm.getUser().getUserId());
     	     if(type.toUpperCase().equals("BUY"))
     	     return "buyMilk";
     	     return "sellMilk";
     	   }
     	   
        	entryForm = entryFormService.saveEntryForm(ledger,entryForm);
        	
        	redirectAttribute.addFlashAttribute("result", "Successfully Saved "+entryForm.getUser().getName()+ " Entry");
        	return "redirect:/entryForm/user/{type}/" + entryForm.getUser().getUserId();
	    }
        
        @RequestMapping(value="user/{type}/deleteEntry", method={RequestMethod.DELETE})	    
        public String deleteEntryForm(@RequestParam("id") String id,
        		                      @PathVariable String type,
                                      RedirectAttributes redirectAttribute) {
        	
        	entryFormService.updateBalanceAndDeleteEntry(id,type);
        	
        	redirectAttribute.addFlashAttribute("result", "Entry Deleted Successfully ! ");
        	return "redirect:/entryForm/user/"+type;
        }
       
      
        
}
