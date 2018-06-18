package com.dairy.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Ledger {
	
	
	
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id ; 
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime transactionDate ;
	private String dayType; 
	private Double amount ;
	private String paymentType ;
	private String paymentBy ;
	private String paymentSummary ;
   
	
	@ManyToOne
	@JoinColumn(name = "userId",nullable=false)
	private User user ;
	
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPaymentSummary() {
		return paymentSummary;
	}
	public void setPaymentSummary(String paymentSummary) {
		this.paymentSummary = paymentSummary;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPaymentBy() {
		return paymentBy;
	}
	public void setPaymentBy(String paymentBy) {
		this.paymentBy = paymentBy;
	}
	
	
	
	

}
