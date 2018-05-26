package com.dairy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairy.model.DailyTransaction;
import com.dairy.model.User;
import com.dairy.repository.DailyTransactionRepository;
import com.dairy.repository.UserRepository;

@Service
public class DailyTransactionService {
	
	@Autowired
	DailyTransactionRepository dailyTransactionRepository ;
	
	@Autowired
	UserRepository userRepository ;
	
	
	public void saveDailyTransaction(DailyTransaction dailyTransaction)
	{    
		
		dailyTransactionRepository.save(dailyTransaction);
		
	}

}
