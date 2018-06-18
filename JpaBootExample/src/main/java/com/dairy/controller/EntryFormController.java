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
	  

	
	    @RequestMapping(value={"","user","user/{id}"} , method={RequestMethod.POST,RequestMethod.GET})	    
        public String setEntryFormByUserId(@PathVariable Optional<Integer> id , User user , EntryForm entryForm,Model model) {
        	
        	   if(id.isPresent())
        	   { 
        		  Integer userId = id.get();
        		if(!userService.existsById(userId)) 
        	     { 
        		  model.addAttribute("result","Cannot find userId #" + userId);
        	      return "entryForm";
        	     }
        		user = userService.getOne(userId);
        		entryForm.setUser(user);
        	   }
        	   
        	   entryForm.setEntryDateTime(LocalDateTime.now());
        	   
        	   entryForm.setDayType("m");
        	   if(LocalDateTime.now().getHour() >= 14)
        	   entryForm.setDayType("e");
        	   
        	   entryForm.setMilkType("buffalo");
        	   return "entryForm";
	    }
        
        
        @RequestMapping(value="user/save", method={RequestMethod.POST})	    
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
        	
        	redirectAttribute.addFlashAttribute("result", "Successfully Saved "+entryForm.getUser().getName()+ " Form.");
        	return "redirect:/entryForm/user/" + entryForm.getUser().getUserId();
	    }
       
        
       /* @RequestMapping(value="byDate", method={RequestMethod.POST,RequestMethod.GET})
        public String getTransactionsByDate(
	    		  @RequestParam(value="fromDate", required=true) String fromDate,
	              @RequestParam(value="toDate", required=false) Date todate,Model model) {
	    	
    	   return "entryForm";
	    }
        
        @RequestMapping(value="byUserIdandDate", method={RequestMethod.POST,RequestMethod.GET})
        public String getTransactionsByUserIdandDate(
        		  @RequestParam(value="userId", required=true) String userId,
	              @RequestParam(value="fromDate", required=true) Date fromdate,
	              @RequestParam(value="toDate", required=false) String toDate,Model model){
	    	
    	   return "entryForm";
	    }*/
        
}
