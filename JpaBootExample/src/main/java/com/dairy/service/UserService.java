package com.dairy.service;

import java.util.List;

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
	 public void addUser(User user)
	 {  
		if(user.getAmountBalance()==null) user.setAmountBalance(0.0);  
		user = ledgerService.setLedgerForNewUser(user);
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
    		 user.setAmountBalance(amountbalance);
    		 userRepository.save(user);
    	 }
    	 else if(ledger.getPaymentType().equals("DEBIT"))
    	 {
    		 
    		 double amountbalance = user.getAmountBalance() - ledger.getAmount();
    		 user.setAmountBalance(amountbalance);
    		 userRepository.save(user);
    	 }
    	 
     }
    
}
