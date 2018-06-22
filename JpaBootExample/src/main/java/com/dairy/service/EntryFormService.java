package com.dairy.service;

import java.time.LocalDateTime;
import java.util.List;

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
    
    public List<EntryForm> getEntryForms(Long userId , LocalDateTime fromDate , LocalDateTime toDate , Boolean fromLastPaid)
    {   
    	if(fromLastPaid != null)
    	{
    	  Ledger ledger = ledgerService.getLedgerByLastTransactionDate(userId ,"DEBIT");
    	   
    	   if(ledger!=null)
    	   fromDate = ledger.getTransactionDate();
    	}
    	
    	if(fromDate!=null && toDate==null)
    	return entryFormRepository.findByUserUserIdAndEntryDateTimeAfter(userId, fromDate);
    	
    	if(fromDate!=null && toDate!=null)
    	return entryFormRepository.findByUserUserIdAndEntryDateTimeBetween(userId, fromDate ,toDate);
    	
    	return entryFormRepository.findByUserUserId(userId);
    	
    }

}
