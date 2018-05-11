package com.dairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dairy.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByFirstNameStartsWithIgnoreCase(String firstName);
}


