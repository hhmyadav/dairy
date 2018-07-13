package com.dairy.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository ;
	
	@Autowired 
	LedgerService ledgerService ;
	
	 public List<User> getAllUsers()
	 {   return userRepository.findAll(); 
	 }
	 
	 @Transactional
	 public void addUser(Ledger ledger ,User user)
	 {  
		if(user.getAmountBalance()==null) user.setAmountBalance(0.0);  
		user = ledgerService.getLedgerForNewUser(ledger,user);
		userRepository.save(user);
	 }
	 
	 @Transactional
	 public void editUser(Ledger ledger , User user)
	 {  
		 
		if(user.getAmountBalance()==null) 
			user.setAmountBalance(0.0);
		else
		    user.setAmountBalance(format2Decimal(user.getAmountBalance()));
		
		Optional<User> oldUser = userRepository.findById(user.getUserId());
		
		double changeBalance = user.getAmountBalance();
		double oldBalance = oldUser.get().getAmountBalance();
		
		if(changeBalance != oldBalance)
		  user = ledgerService.getLedgerForEditedUser(ledger,user,oldBalance);
		
		 userRepository.save(user);
	
	 }
	 
	 public boolean existsById(long userId)
	 {
		return userRepository.existsById(userId);
	 }
	 
	 public User getOne(long userId)
	 {
		 return userRepository.getOne(userId);
	 }
     
	 public void deleteUser(Long userId)
	 {
		 userRepository.deleteById(userId);
	 }
	 
     public void updateBalance(Ledger ledger)
     {  
    	 User user = getOne(ledger.getUser().getUserId());
    	 
    	 if(ledger.getPaymentType().equals("CREDIT"))
    	 {   
    		 double amountbalance = user.getAmountBalance() + ledger.getAmount() ;
    		 user.setAmountBalance(format2Decimal(amountbalance));
    		 userRepository.save(user);
    	 }
    	 else if(ledger.getPaymentType().equals("DEBIT"))
    	 {
    		 double amountbalance = user.getAmountBalance() - ledger.getAmount();
    		 user.setAmountBalance(format2Decimal(amountbalance));
    		 userRepository.save(user);
    	 }
    	 
     }
     public void reverseBalance(Ledger ledger)
     {  
    	 User user = getOne(ledger.getUser().getUserId());
    	 
    	 if(ledger.getPaymentType().equals("DEBIT"))
    	 {   
    		 double amountbalance = user.getAmountBalance() + ledger.getAmount() ;
    		 user.setAmountBalance(amountbalance);
    		 userRepository.save(user);
    	 }
    	 else if(ledger.getPaymentType().equals("CREDIT"))
    	 {  
    		 double amountbalance = user.getAmountBalance() - ledger.getAmount();
    		 user.setAmountBalance(amountbalance);
    		 userRepository.save(user);
    	 }
     }
     
     public Double format2Decimal(double value)
     {    
         DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");  
     	return Double.valueOf(twoDecimalFormat.format(value));
     }
    
}
