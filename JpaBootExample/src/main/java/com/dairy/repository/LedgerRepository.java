package com.dairy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dairy.model.Ledger;
import com.dairy.model.User;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long>{
	
	
    public List<Ledger> findByPaymentBy(String paymentBy);
	
    public List<Ledger> findByUser(User user);
	
	public List<Ledger> findByTransactionDateBetween(LocalDateTime startdateTime , LocalDateTime enddate );
  
	public List<Ledger> findByUserAndTransactionDateBetween(User user,LocalDateTime startdateTime , LocalDateTime enddate );
	 
	
	
	
	
	
    

}
