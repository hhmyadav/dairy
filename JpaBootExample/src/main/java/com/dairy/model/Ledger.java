package com.dairy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ledger {
	
	
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id ; 
	private Date createdDate ;
	private Date updatedDate ;
	private char daytype; 
	private double amount ;
	private String paymentSummary ;
	private long createdUser ; 
	private long updatedUserId; 
	private String businessArea;
	
	@ManyToOne
    @JoinColumn(name = "UserId")
	private User user ;
	
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public char getDaytype() {
		return daytype;
	}
	public void setDaytype(char daytype) {
		this.daytype = daytype;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPaymentSummary() {
		return paymentSummary;
	}
	public void setPaymentSummary(String paymentSummary) {
		this.paymentSummary = paymentSummary;
	}
	public long getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(long createdUser) {
		this.createdUser = createdUser;
	}
	public long getUpdatedUserId() {
		return updatedUserId;
	}
	public void setUpdatedUserId(long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}
	public String getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
