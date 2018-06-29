package com.dairy.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.dairy.model.EntryForm;
import com.dairy.model.Ledger;
import com.dairy.repository.EntryFormRepository;

@Service
public class EntryFormService {
	
	@Autowired
	EntryFormRepository entryFormRepository ;
	
	@Autowired 
	LedgerService ledgerService ;
	
	@Autowired
	UserService userService ;
	
	DateTimeFormatter ddMMyyyyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
    @Transactional
	public EntryForm saveEntryForm(Ledger ledger , EntryForm entryForm)
	{    
		if (entryForm.getEntryDateTime() == null) 
    	 entryForm.setEntryDateTime(LocalDateTime.now());
        
		entryFormRepository.save(entryForm);
		ledgerService.saveLedgerFromEntryForm(entryForm, ledger);
		userService.updateBalance(ledger);
		
		return entryForm ;
		
	}
    
    public List<EntryForm> getEntryForms(String type)
    {
    	return entryFormRepository.findByType(type);
    }
    
    public List<EntryForm> getEntryForms(Model model ,Long userId , LocalDateTime fromDate , LocalDateTime toDate , Boolean fromLastPaid)
    {   
 	   model.addAttribute("fromDate", fromDate.format(ddMMyyyyFormatter));
        
 	    
 	   if(fromLastPaid != null)
       {
    	  Ledger ledger = ledgerService.getLedgerByLastTransactionDate(userId ,"DEBIT");
    	   
    	   if(ledger==null)
    	   {   
    		   model.addAttribute("result", "Never Paid");
    		   return entryFormRepository.findByUserUserId(userId);
    	   }
    	   else  
    	   {   
    		   fromDate = ledger.getTransactionDate();
    		   model.addAttribute("fromDate", fromDate.format(ddMMyyyyFormatter));
    	   }
    	   
    	}
 	    
 	    if(fromDate!=null && toDate==null)
    	{   toDate = LocalDateTime.now();
    	    model.addAttribute("toDate",toDate.format(ddMMyyyyFormatter));
		    return entryFormRepository.findByUserUserIdAndEntryDateTimeAfter(userId, fromDate);
    	}
    	else if(fromDate!=null && toDate!=null)
    	return entryFormRepository.findByUserUserIdAndEntryDateTimeBetween(userId, fromDate ,toDate);
    	
    	return entryFormRepository.findByUserUserId(userId);
    	
    }

}
