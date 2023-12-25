package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Signup")

public class Signup {
	//create system column
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	//generate user defined columns
	private String Name;
	private String DOB;
	private String Contactno;
	private String Email;
	private String Userid;
	private String Password;
	
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getContactno() {
		return Contactno;
	}
	public void setContactno(String contactno) {
		Contactno = contactno;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getUserid() {
		return Userid;
	}
	public void setUserid(String userid) {
		Userid = userid;
	}
	
	

}
