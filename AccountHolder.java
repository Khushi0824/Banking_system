package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="AccountHolder")

public class AccountHolder {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String CID;
	private String AccountNo;
	private String AccountCreatedOn;
	private String AccountPin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String AccountStatus;
	public String getCID() {
		return CID;
	}
	public void setCID(String cID) {
		CID = cID;
	}
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public String getAccountCreatedOn() {
		return AccountCreatedOn;
	}
	public void setAccountCreatedOn(String accountCreatedOn) {
		AccountCreatedOn = accountCreatedOn;
	}
	public String getAccountPin() {
		return AccountPin;
	}
	public void setAccountPin(String accountPin) {
		AccountPin = accountPin;
	}
	public String getAccountStatus() {
		return AccountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		AccountStatus = accountStatus;
	}
	

}
