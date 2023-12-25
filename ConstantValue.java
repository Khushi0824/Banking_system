package com.example.demo.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConstantValue {
	public int ContactLength=10;
	public int ddmin=0;
	public int ddmax=32;
	public int mmmin=0;
	public int mmmax=13;
	public int yymin=1990;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
    LocalDateTime now = LocalDateTime.now();
	public int yymax=Integer.parseInt(now.format(dtf).toString());
	
	
	
	
	public ArrayList<String> emailList= new ArrayList<>();
	public void setEmail (ArrayList<String> emailList)
	{
		emailList.add(".+@gmail.com");
	}

	public ArrayList<String> StatusList= new ArrayList<>();
	public void setStatus (ArrayList<String> StatusList)
	{
		StatusList.add("Single");
		StatusList.add("Married");
		StatusList.add("Divorced");
		StatusList.add("Widowed");

	}
	public ArrayList<String>Account_statusList=new ArrayList();
	public void SetAccount_status(ArrayList<String>Account_statusList)
	{
		Account_statusList.add("Card_Loss");
		Account_statusList.add("Card_stolen");
		Account_statusList.add("Unauthorized_usage");
		Account_statusList.add("Updating_details");
	}
}
