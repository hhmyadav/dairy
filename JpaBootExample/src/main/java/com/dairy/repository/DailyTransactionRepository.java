package com.dairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dairy.model.DailyTransaction;

public interface DailyTransactionRepository extends JpaRepository<DailyTransaction, Long> {

}
