package com.dairy.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="entryForm")
public class EntryForm {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id ;
	
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime entryDateTime;
	
	@Column(nullable = false)
	private String dayType ; 
	
	@Column(nullable = false)
	private Double milkQuantity ;
	
	@Column(nullable = false)
	private String milkType ;
	
	@Column(nullable = false)
	private Double perLiterPrice ;
	
	@Column(nullable = false)
	private Double totalAmount ;    
	
	private Double fat ;
	
	private Double snf ;
	
    
	@ManyToOne
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
	
	
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public Double getMilkQuantity() {
		return milkQuantity;
	}
	public void setMilkQuantity(Double milkQuantity) {
		this.milkQuantity = milkQuantity;
	}
	public Double getPerLiterPrice() {
		return perLiterPrice;
	}
	public void setPerLiterPrice(Double perLiterPrice) {
		this.perLiterPrice = perLiterPrice;
	}
	
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getFat() {
		return fat;
	}
	public void setFat(Double fat) {
		this.fat = fat;
	}
	public Double getSnf() {
		return snf;
	}
	public void setSnf(Double snf) {
		this.snf = snf;
	}
	public String getMilkType() {
		return milkType;
	}
	public void setMilkType(String milkType) {
		this.milkType = milkType;
	}

	public LocalDateTime getEntryDateTime() {
		return entryDateTime;
	}

	public void setEntryDateTime(LocalDateTime entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	



}
