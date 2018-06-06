package com.dairy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairy.model.Ledger;
import com.dairy.model.User;
import com.dairy.repository.LedgerRepository;

@Service
public class TransactionReportService {
	
	
	@Autowired
	LedgerRepository ledgerRepository;
	
	
	public List<Ledger> getTransationByDate(LocalDateTime startDate , LocalDateTime endDate)
	{
		return ledgerRepository.findByTransactionDateBetween(startDate, endDate) ;
	}
	
	public List<Ledger> getTransationByUserAndTransactionDateBetween(User user ,LocalDateTime startDate , LocalDateTime endDate)
	{
		return ledgerRepository.findByUserAndTransactionDateBetween(user, startDate, endDate);
	}
     
}
