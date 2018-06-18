package com.dairy.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	 private static final String MORNING = "MORNING";
     private static final String EVENING  = "EVENING";
	
	
    @Transactional
	public EntryForm saveEntryForm(Ledger ledger , EntryForm entryForm)
	{    
		if (entryForm.getEntryDateTime() == null) 
    	 entryForm.setEntryDateTime(LocalDateTime.now());
        
		
		if(entryForm.getDayType().equals("m"))
		 entryForm.setDayType(MORNING);
		 	
		
		if(entryForm.getDayType().equals("e"))
	     entryForm.setDayType(EVENING);
			 
		
    	entryFormRepository.save(entryForm);
		ledgerService.saveLedgerFromEntryForm(entryForm, ledger);
		userService.updateBalance(ledger);
		
		return entryForm ;
		
	}

}
