package com.dairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dairy.model.Ledger;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long>{
	
	@Query("from ledger where paymentBy like %:keyword%")
	public List<Ledger> search(@Param("keyword") String keyword);

}
