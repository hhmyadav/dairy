package com.dairy.service;

import java.time.LocalDateTime;
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
     private static final String PAYMENT_BY_MILK = "Milk";  
     private static final String PAYMENT_BY_CASH = "Cash"; 
     private static final String PAYMENT_BY_OLD_BALANCE = "OldBalance"; 
	 private static final String PAYMENT_SUMMARY_MILK_PURCHASED =  "Purchased Milk From Buyer : " ;
	 private static final String PAYMENT_SUMMARY_USER_ADDED =  "New User Added Adding Old Balance : " ;
	 private static final String PAYMENT_SUMMARY_PAYMENT_FORM = "Paying Amount To User : " ;
	
	@Autowired
	LedgerRepository ledgerRepository ; 
	
	
	public void saveLedgerFromEntryForm(EntryForm entryForm , Ledger ledger)
	{    
		
		ledger.setAmount(entryForm.getTotalAmount());
		ledger.setPaymentType(CREDIT);
		
		ledger.setDayType(entryForm.getDayType());
		
		ledger.setTransactionDate(entryForm.getCreatedDateTime());
		ledger.setPaymentBy(PAYMENT_BY_MILK);
		ledger.setUser(entryForm.getUser());
		ledger.setPaymentSummary(PAYMENT_SUMMARY_MILK_PURCHASED +entryForm.getUser().getName());
		
		ledgerRepository.save(ledger);
	}
	
	public void saveLedgerPaymentForm(Ledger ledger)
	{    
		
	    ledger.setPaymentType(DEBIT);
	    
	      if(ledger.getTransactionDate() == null) 
	      ledger.setTransactionDate((LocalDateTime.now()));
	  
	      if(ledger.getPaymentBy() == null || ledger.getPaymentBy().equals("") )
	      ledger.setPaymentBy(PAYMENT_BY_CASH);
	    
	   
		ledger.setPaymentSummary(PAYMENT_SUMMARY_PAYMENT_FORM + ledger.getUser().getName());
		
		ledgerRepository.save(ledger);
	}

	public User setLedgerForNewUser(User user)
	{   
	
		user.getLedgers().get(0).setPaymentType(CREDIT);
		user.getLedgers().get(0).setDayType(DAYTYPE_NA);
		user.getLedgers().get(0).setTransactionDate(LocalDateTime.now());
		user.getLedgers().get(0).setAmount(user.getAmountBalance());
		user.getLedgers().get(0).setPaymentBy(PAYMENT_BY_OLD_BALANCE);
		user.getLedgers().get(0).setUser(user);
		user.getLedgers().get(0).setPaymentSummary(PAYMENT_SUMMARY_USER_ADDED + user.getName());
		
		return user ;
	}
	
	public List<Ledger> getLedgersByUserAndPaymentType(User user , String paymentType)
	{
		return ledgerRepository.findByUserAndPaymentType(user, paymentType);
	}
	
	public List<Ledger> getAllLedger()
	{
		return ledgerRepository.findAll();
	}
	
	public List<Ledger>getLedgersByAny(String dayType ,String paymentType,String paymentBy, String transactionStartDate , String transactionEndDate)
	{     
		
		   
	    if(!isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && isNullOrEmpty(transactionStartDate) && isNullOrEmpty(transactionEndDate))
	    {   
			return  ledgerRepository.findByPaymentType(paymentType);
	    }
	    else if(isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && isNullOrEmpty(transactionStartDate) && isNullOrEmpty(transactionEndDate))	
	    {   
			return  ledgerRepository.findByDayType(dayType);
	    }
	    else if(isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && isNullOrEmpty(transactionStartDate) && isNullOrEmpty(transactionEndDate))	
	    {   
			return  ledgerRepository.findByPaymentBy(paymentBy);
	    }
	    else if(isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && !isNullOrEmpty(transactionStartDate) && isNullOrEmpty(transactionEndDate))	
	    {   
			return  ledgerRepository. findByTransactionDateBetween(LocalDateTime.parse(transactionStartDate), LocalDateTime.now());
	    }
	    else if(isNullOrEmpty(paymentType) && isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && !isNullOrEmpty(transactionStartDate) && !isNullOrEmpty(transactionEndDate))	
	    {   
			return  ledgerRepository.findByTransactionDateBetween(LocalDateTime.parse(transactionStartDate), LocalDateTime.parse(transactionEndDate));
	    }
	    else if(!isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && isNullOrEmpty(paymentBy) && isNullOrEmpty(transactionStartDate) && isNullOrEmpty(transactionEndDate))	
	    {   
			return  ledgerRepository.findByPaymentTypeAndDayType(paymentType, dayType);
	    }
	    else if(isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && isNullOrEmpty(transactionStartDate) && isNullOrEmpty(transactionEndDate))	
	    {   
			return  ledgerRepository.findByPaymentByAndDayType(paymentBy, dayType);
	    }
		else if(!isNullOrEmpty(paymentType) && !isNullOrEmpty(dayType) && !isNullOrEmpty(paymentBy) && !isNullOrEmpty(transactionStartDate) && !isNullOrEmpty(transactionEndDate))	
	    {   
			 return ledgerRepository.findByDayTypeAndPaymentTypeAndPaymentByAndTransactionDateBetween(dayType , paymentType ,paymentBy, LocalDateTime.parse(transactionStartDate), LocalDateTime.parse(transactionEndDate));
		}
		
		return  ledgerRepository.findAll();
		
		
		
	}
	
	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.trim().isEmpty())
            return false;
        return true;
    }
	

}
