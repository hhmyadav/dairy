package com.dairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dairy.model.Ledger;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long>{

}
