package com.dairy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairy.model.DailyTransaction;
import com.dairy.repository.DailyTransactionRepository;

@Service
public class DailyTransactionService {
	
	@Autowired
	DailyTransactionRepository dailyTransactionRepository ;
	
	
	
	public void saveDailyTransaction(DailyTransaction dailyTransaction)
	{
		dailyTransactionRepository.save(dailyTransaction);
	}

}
