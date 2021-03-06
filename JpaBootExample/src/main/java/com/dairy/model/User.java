package com.dairy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO )
     private Long userId;
    @Column(nullable = false)
    private String name;
    private String email ;
    private String address ;
    @Column(unique = true)
    private Long mobileNumber;
    @Column(nullable = true)
    private String userType ;
    private Double amountBalance ;

    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<EntryForm> entryForms = new ArrayList<EntryForm>();
   
    
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<Ledger> ledgers = new ArrayList<Ledger>();
   
    
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    
    public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	

	public List<EntryForm> getEntryForms() {
		return entryForms;
	}

	public void setEntryForms(List<EntryForm> entryForms) {
		this.entryForms = entryForms;
	}

	public List<Ledger> getLedgers() {
		return ledgers;
	}

	public void setLedgers(List<Ledger> ledgers) {
		this.ledgers = ledgers;
	}

	public Double getAmountBalance() {
		return amountBalance;
	}

	public void setAmountBalance(Double amountBalance) {
		this.amountBalance = amountBalance;
	}

	

	


	
	

}