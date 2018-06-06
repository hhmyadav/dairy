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
	
	
	/*@Query("from ledger where transaction_date between %:fromDate% and %:toDate%")
	public List<Ledger> getLedgerBetweenDate(@Param("fromDate") String fromDate,@Param("toDate") String toDate);
    */

	public List<Ledger> findByUser(User user);
	
	public List<Ledger> findByTransactionDateBetween(LocalDateTime startdateTime , LocalDateTime enddate );

	
	
    

}
