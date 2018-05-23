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
	private Date transactionDate ;
	private char daytype; 
	private double amountDebit ;
	private double amountCredit ;
	private String paymentSummary ;

	
	
	@ManyToOne
    @JoinColumn(name = "UserId")
	private User user ;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public char getDaytype() {
		return daytype;
	}
	public void setDaytype(char daytype) {
		this.daytype = daytype;
	}
	
	public double getAmountDebit() {
		return amountDebit;
	}
	public void setAmountDebit(double amountDebit) {
		this.amountDebit = amountDebit;
	}
	public double getAmountCredit() {
		return amountCredit;
	}
	public void setAmountCredit(double amountCredit) {
		this.amountCredit = amountCredit;
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
	
	
	

}
