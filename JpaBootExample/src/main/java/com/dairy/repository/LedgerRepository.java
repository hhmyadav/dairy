package com.dairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dairy.model.Ledger;


public interface LedgerRepository extends JpaRepository<Ledger, Long>{

}
