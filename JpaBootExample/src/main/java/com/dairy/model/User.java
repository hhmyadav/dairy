package com.dairy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    private String name;
    private String email ;
    private String address ;
    private long mobileNumber;
    private char userType ;


    
    @OneToMany(mappedBy = "user")
    private List<DailyTransaction> dailyTransaction;
   
    
    @OneToMany(mappedBy = "user")
    private List<Ledger> ledger;
   
    
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


    public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public char getUserType() {
		return userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
	}

	public List<DailyTransaction> getDailyTransaction() {
		return dailyTransaction;
	}

	public void setDailyTransaction(List<DailyTransaction> dailyTransaction) {
		this.dailyTransaction = dailyTransaction;
	}

	public List<Ledger> getLedger() {
		return ledger;
	}

	public void setLedger(List<Ledger> ledger) {
		this.ledger = ledger;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}