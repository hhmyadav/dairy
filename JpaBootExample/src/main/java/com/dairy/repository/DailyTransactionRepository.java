package com.dairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dairy.model.DailyTransaction;

@Repository
public interface DailyTransactionRepository extends JpaRepository<DailyTransaction, Long> {

}
