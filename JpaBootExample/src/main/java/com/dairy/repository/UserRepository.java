package com.dairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dairy.model.Ledger;
import com.dairy.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByNameStartsWithIgnoreCase(String name);
	
}


