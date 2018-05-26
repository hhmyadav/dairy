package com.dairy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairy.model.User;
import com.dairy.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository ;
	
	
	 public List<User> getAllUsers()
	 {  
		 return userRepository.findAll();
	 }
	 
	 public void addUser(User user)
	 {
		 userRepository.save(user);
	 }
	 
	 public boolean existsById(long userId)
	 {
		return userRepository.existsById(userId);
	 }
	 
	 public User getOne(long userId)
	 {
		 return userRepository.getOne(userId);
	 }
     
	 public void deleteUser(Long userId)
	 {
		 userRepository.deleteById(userId);
	 }
}
