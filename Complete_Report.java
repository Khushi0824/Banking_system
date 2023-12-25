package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Complete_report")

public class Complete_report {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String AccountNo;
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public String getBlocking_date() {
		return Blocking_date;
	}
	public void setBlocking_date(String blocking_date) {
		Blocking_date = blocking_date;
	}
	public String getBlock_reason() {
		return Block_reason;
	}
	public void setBlock_reason(String block_reason) {
		Block_reason = block_reason;
	}
	
	private String Blocking_date;
	private String Block_reason;
	

}
