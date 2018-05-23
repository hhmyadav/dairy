package com.dairy.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="dailyTransaction")
public class DailyTransaction {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id ;
	
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime createdDateTime;
	
	
	private char dayType ; 
	private double milkQuantity ;
	private String milkType ;
	private double perLiterPrice ;
	private double totalAmount ;    
	private double fat ;
	private double snf ;
	
    
	@ManyToOne()
    @JoinColumn(name = "userId",nullable=false)
	private User user ;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public char getDayType() {
		return dayType;
	}
	public void setDayType(char dayType) {
		this.dayType = dayType;
	}
	public double getMilkQuantity() {
		return milkQuantity;
	}
	public void setMilkQuantity(double milkQuantity) {
		this.milkQuantity = milkQuantity;
	}
	public double getPerLiterPrice() {
		return perLiterPrice;
	}
	public void setPerLiterPrice(double perLiterPrice) {
		this.perLiterPrice = perLiterPrice;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getFat() {
		return fat;
	}
	public void setFat(double fat) {
		this.fat = fat;
	}
	public double getSnf() {
		return snf;
	}
	public void setSnf(double snf) {
		this.snf = snf;
	}
	public String getMilkType() {
		return milkType;
	}
	public void setMilkType(String milkType) {
		this.milkType = milkType;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}



}
