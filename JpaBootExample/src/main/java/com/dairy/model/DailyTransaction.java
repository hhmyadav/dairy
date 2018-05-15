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
	private String milkType ;
	private double perLiterPrice ;
	private double dayTotalAmount ;    
	private double fat ;
	private double snf ;
	
    
	@ManyToOne
    @JoinColumn(name = "userId")
	private User user ;
	
	 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getMilkType() {
		return milkType;
	}
	public void setMilkType(String milkType) {
		this.milkType = milkType;
	}
	
	
	
	
	

}
