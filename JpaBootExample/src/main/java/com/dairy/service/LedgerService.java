package com.dairy.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairy.model.EntryForm;
import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.repository.LedgerRepository;

@Service
public class LedgerService {
	
	

	 private static final String CREDIT = "CREDIT";
     private static final String DEBIT  = "DEBIT";
     private static final String DAYTYPE_NA = "NA";
     private static final String DAYTYPE_EVENING= "EVENING";
     private static final String DAYTYPE_MORNING = "MORNING";
     private static final String PAYMENT_BY_MILK_BUY = "MILK_BUY";  
     private static final String PAYMENT_BY_MILK_SELL = "MILK_SELL"; 
     private static final String PAYMENT_BY_CASH = "CASH"; 
     private static final String PAYMENT_BY_OLD_BALANCE = "OLD_BALANCE"; 
     private static final String PAYMENT_SUMMARY_MILK_SELL =  "Sold Milk To Buyer : " ;
	 private static final String PAYMENT_SUMMARY_MILK_BUY =  "Purchased Milk From Seller : " ;
	 private static final String PAYMENT_SUMMARY_USER_ADDED =  "New User Added With Old Balance : " ;
	 private static final String PAYMENT_SUMMARY_PAYMENT_FORM = "Paying Amount To User : " ;
	
	
	 
	@Autowired
	LedgerRepository ledgerRepository ; 
	
	
	public Ledger getLedgerFromEntryForm(EntryForm entryForm , Ledger ledger)
	{    
		
		ledger.setTransactionDate(entryForm.getEntryDateTime());
		ledger.setDayType(entryForm.getDayType().toUpperCase());
		ledger.setUser(entryForm.getUser());
		
		ledger.setAmount(entryForm.getTotalAmount());
		
		if(entryForm.getType().toUpperCase().equals("BUY"))
		{   
			ledger.setPaymentType(CREDIT);
			if(ledger.getPaymentSummary() == null || ledger.getPaymentSummary().equals(""))
			ledger.setPaymentSummary(PAYMENT_SUMMARY_MILK_BUY +entryForm.getUser().getName());
			ledger.setPaymentBy(PAYMENT_BY_MILK_BUY);
		}
		else if(entryForm.getType().toUpperCase().equals("SELL"))
		{
			ledger.setPaymentType(DEBIT);
			if(ledger.getPaymentSummary() == null || ledger.getPaymentSummary().equals(""))
			ledger.setPaymentSummary(PAYMENT_SUMMARY_MILK_SELL +entryForm.getUser().getName());
			ledger.setPaymentBy(PAYMENT_BY_MILK_SELL);
		}
		
		ledger.setEntryForm(entryForm);
		
		return ledger ;
	}
	
	public Ledger saveLedgerFromPaymentForm(Ledger ledger)
	{    
	 
	      ledger.setPaymentType(DEBIT);
	    
	     
	      if(ledger.getTransactionDate() == null)
	       ledger.setTransactionDate((LocalDateTime.now()));
	      
	      ledger.setDayType(DAYTYPE_MORNING);
		 	if(ledger.getTransactionDate().getHour() >= 14)
		  ledger.setDayType(DAYTYPE_EVENING);
	      
	      if(ledger.getPaymentBy() == null || ledger.getPaymentBy().equals(""))
	      ledger.setPaymentBy(PAYMENT_BY_CASH);
	      
	      ledger.setPaymentBy(ledger.getPaymentBy().toUpperCase());
	      
	      if(ledger.getPaymentSummary() == null || ledger.getPaymentSummary().equals(""))
	  		ledger.setPaymentSummary(PAYMENT_SUMMARY_PAYMENT_FORM +ledger.getUser().getName());
	   
	      
		ledgerRepository.save(ledger);
		return ledger ;
	}
    
	public Ledger setDefaultLedgerForPaymentForm(Ledger ledger , User user)
	{   
		
		ledger.setUser(user);
		ledger.setPaymentType(DEBIT);
		
		ledger.setTransactionDate(LocalDateTime.now());
		
		ledger.setDayType(DAYTYPE_MORNING);
	 	if(LocalDateTime.now().getHour() >= 14)
	 	 ledger.setDayType(DAYTYPE_EVENING);
		
	 	double currentTotalAmount = (double) 0 ;
	 	List<EntryForm> entryForms = user.getEntryForms();
        
        Iterator<EntryForm> entryForm = entryForms.iterator();
		  
		  while (entryForm.hasNext()) {
		
			EntryForm entryform = entryForm.next();
			
			currentTotalAmount = currentTotalAmount + entryform.getTotalAmount();
		  } 
		ledger.setAmount(format2Decimal(currentTotalAmount));
	  
 	    ledger.setPaymentBy(PAYMENT_BY_CASH);
		ledger.setPaymentSummary(PAYMENT_SUMMARY_PAYMENT_FORM + user.getName());
		
		return ledger ;
	}
	
	public User setLedgerForNewUser(User user)
	{   
	
		user.getLedgers().get(0).setPaymentType(CREDIT);
		user.getLedgers().get(0).setDayType(DAYTYPE_NA);
		user.getLedgers().get(0).setTransactionDate(LocalDateTime.now());
		user.getLedgers().get(0).setAmount(format2Decimal(user.getAmountBalance()));
		user.getLedgers().get(0).setPaymentBy(PAYMENT_BY_OLD_BALANCE);
		user.getLedgers().get(0).setUser(user);
		user.getLedgers().get(0).setPaymentSummary(PAYMENT_SUMMARY_USER_ADDED + user.getName());
		
		return user ;
	}
	
	public List<Ledger> getAllLedger()
	{
		return ledgerRepository.findAll();
	}
	
	
	public List<Ledger>getLedgersByAny(Long userId , String dayType ,String paymentType,String paymentBy, LocalDateTime transactionStartDate , LocalDateTime transactionEndDate)
	{     
		if( (userId != null && userId > 0) && isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate == null)
	    {   
			return  ledgerRepository.findByUserUserId(userId);
	    }
		else if((userId != null && userId > 0) && isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate != null)
		{
			return  ledgerRepository.findByTransactionDateBetweenAndUserUserId(transactionStartDate ,transactionEndDate,userId );
		}
		else if((userId != null && userId > 0) && isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate == null)
		{
			return  ledgerRepository.findByTransactionDateBetweenAndUserUserId(transactionStartDate ,LocalDateTime.now(),userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate == null)
		{
			return  ledgerRepository.findByPaymentTypeAndUserUserId(paymentType , userId);
		}
		else if((userId != null && userId > 0) && isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate == null)
		{
			return  ledgerRepository.findByDayTypeAndUserUserId(dayType , userId );
		}
		else if((userId != null && userId > 0) && isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate == null)
		{
			return  ledgerRepository.findByPaymentByAndUserUserId(paymentBy , userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate == null)
		{
			return  ledgerRepository.findByPaymentTypeAndDayTypeAndUserUserId(paymentType , dayType , userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate == null)
		{
			return  ledgerRepository.findByPaymentTypeAndDayTypeAndPaymentByAndUserUserId(paymentType , dayType , paymentBy , userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate == null)
		{
			return  ledgerRepository.findByPaymentTypeAndDayTypeAndPaymentByAndTransactionDateBetweenAndUserUserId(paymentType , dayType , paymentBy ,transactionStartDate ,LocalDateTime.now(),userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate != null)
		{
			return  ledgerRepository.findByPaymentTypeAndDayTypeAndPaymentByAndTransactionDateBetweenAndUserUserId(paymentType , dayType , paymentBy ,transactionStartDate ,transactionEndDate,userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate == null)
		{
			return  ledgerRepository.findByPaymentTypeAndDayTypeAndTransactionDateBetweenAndUserUserId(paymentType , dayType , transactionStartDate ,LocalDateTime.now(),userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate != null)
		{
			return  ledgerRepository.findByPaymentTypeAndDayTypeAndTransactionDateBetweenAndUserUserId(paymentType , dayType , transactionStartDate ,transactionEndDate,userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate != null)
		{
			return  ledgerRepository.findByPaymentTypeAndTransactionDateBetweenAndUserUserId(paymentType , transactionStartDate ,transactionEndDate,userId );
		}
		else if((userId != null && userId > 0) && !isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate == null)
		{
			return  ledgerRepository.findByPaymentTypeAndTransactionDateBetweenAndUserUserId(paymentType , transactionStartDate ,LocalDateTime.now(),userId );
		}
		else if((userId != null && userId > 0) && isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate == null)
		{
			return  ledgerRepository.findByDayTypeAndTransactionDateBetweenAndUserUserId(dayType , transactionStartDate ,LocalDateTime.now(),userId );
		}
		else if((userId != null && userId > 0) && isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate != null)
		{
			return  ledgerRepository.findByDayTypeAndTransactionDateBetweenAndUserUserId(dayType , transactionStartDate ,transactionEndDate,userId );
		}
		
		else if(!isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate== null)
	    {   
			return  ledgerRepository.findByPaymentType(paymentType);
	    }
		else if(!isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate== null)
	    {   
			return  ledgerRepository.findByPaymentTypeAndTransactionDateBetween(paymentType, transactionStartDate, LocalDateTime.now());
	    }
		else if(!isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate!= null)
	    {   
			return  ledgerRepository.findByPaymentTypeAndTransactionDateBetween(paymentType, transactionStartDate, transactionEndDate);
	    }
	    else if(isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate== null)	
	    {   
			return  ledgerRepository.findByDayType(dayType);
	    }
	    else if(isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate== null)	
	    {   
			return  ledgerRepository.findByPaymentBy(paymentBy);
	    }
	    else if(isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate== null)	
	    {   
			return  ledgerRepository. findByTransactionDateBetween(transactionStartDate, LocalDateTime.now());
	    }
	    else if(isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate != null)	
	    {   
			return  ledgerRepository.findByTransactionDateBetween(transactionStartDate, transactionEndDate);
	    }
	    else if(!isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate == null)	
	    {   
			return  ledgerRepository.findByPaymentTypeAndDayType(paymentType, dayType);
	    }
	    else if(isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && transactionStartDate == null && transactionEndDate== null)	
	    {   
			return  ledgerRepository.findByPaymentByAndDayType(paymentBy, dayType);
	    } 
		else if(!isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && transactionStartDate != null && transactionEndDate!= null)	
	    {   
			 return ledgerRepository.findByDayTypeAndPaymentTypeAndPaymentByAndTransactionDateBetween(dayType , paymentType ,paymentBy, transactionStartDate, transactionEndDate);
		}
		
		return  ledgerRepository.findAll();
		
		
		
	}
	
	public Ledger getLedgerByLastTransactionDate(Long userId , String paymentType)
	{         
		Ledger ledger = ledgerRepository.findFirstByUserUserIdAndPaymentTypeOrderByTransactionDateDesc(userId ,paymentType);
		
		return ledger ;
	}
	
	
	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.trim().isEmpty())
            return false;
        return true;
    }
	
    public Double format2Decimal(double value)
    {    
        DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");  
    	return Double.valueOf(twoDecimalFormat.format(value));
    }
	
	
	

}
