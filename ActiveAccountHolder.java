package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ActiveAccountHolder")


public class ActiveAccountHolder {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String AccountNo;
	private String Balance;
	private String Block;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
	}
	public String getBlock() {
		return Block;
	}
	public void setBlock(String block) {
		Block = block;
	}

}
