package com.dairy.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.dairy.model.EntryForm;
import com.dairy.model.Ledger;
import com.dairy.model.PagerModel;
import com.dairy.repository.EntryFormRepository;

@Service
@Transactional
public class EntryFormService {
	
	
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 10;
    private static final int[] PAGE_SIZES = {5,10,20,30,40,50,100};
	
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
        
		entryForm.setType(entryForm.getType().toUpperCase());
		
		ledger = ledgerService.getLedgerFromEntryForm(entryForm, ledger);
		
		entryForm.setLedger(ledger);
		entryFormRepository.save(entryForm);
		userService.updateBalance(ledger);
		
		return entryForm ;
		
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
    
    public Page<EntryForm>  setEntryFormsPaginationAndSorting(Model model ,Optional<Integer> pageSize,Optional<Integer> page,String type)
    {  
    	
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
       
        Page<EntryForm> entryForms = entryFormRepository.findByTypeOrderByEntryDateTimeDesc(type.toUpperCase() ,PageRequest.of(evalPage,evalPageSize));
        
        PagerModel pager = new PagerModel(entryForms.getTotalPages(),entryForms.getNumber(),BUTTONS_TO_SHOW);

	        model.addAttribute("entryForms",entryForms);
            model.addAttribute("selectedPageSize", evalPageSize);
            model.addAttribute("pageSizes", PAGE_SIZES);
            model.addAttribute("pager", pager);
       
     return entryForms ;
    }
    
    public void deleteEntryFormById(String id)
    {    
    	entryFormRepository.deleteById(Long.valueOf(id));
    }
    
    
    public EntryForm setDefaultEntryFormValues(EntryForm entryForm,String type)
    {     
    	
    	 entryForm.setType(type.toUpperCase());
    	 entryForm.setEntryDateTime(LocalDateTime.now());
  	     
    	 entryForm.setDayType("MORNING");
  	     if(LocalDateTime.now().getHour() >= 14)
  	     entryForm.setDayType("EVENING");
  	   
  	     entryForm.setMilkType("BUFFALO");
  	     
  	     if(type.toUpperCase().equals("SELL"))
	       entryForm.setPerLiterPrice(40.0); 
  	     
  	     
    	return entryForm ;
    }
    
    public void deleteEntryAndUpdateBalance( String id)
    {  
    	Ledger ledger = ledgerService.getLedgerByEntryFormId(Long.valueOf(id));
    	userService.reverseBalance(ledger);  
        deleteEntryFormById(id);
    }
    

}
