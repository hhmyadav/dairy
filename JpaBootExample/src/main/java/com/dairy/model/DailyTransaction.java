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
public class DailyTransaction {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id ;
	
	
    private Date createdDate ;
	private Date updatedDate ;
	private char dayType ;  // evening or morning 
	private double milkQuantity ;
	private double perLiterPrice ;
	private double dayTotalAmount ;    
	private double fat ;
	private double snf ;
	private long createdUser ; 
	private long updatedUserId; 
	private String businessArea;
	
    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
	private User userId ;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
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
	public double getDayTotalAmount() {
		return dayTotalAmount;
	}
	public void setDayTotalAmount(double dayTotalAmount) {
		this.dayTotalAmount = dayTotalAmount;
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
	
	
	
	

}
