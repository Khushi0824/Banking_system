package com.example.demo.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.example.demo.Constant.ConstantValue;
import com.example.demo.model.ActiveAccountHolder;
import com.example.demo.model.BankAddress;
import com.example.demo.model.KycDetails;
import com.example.demo.model.Log;
import com.example.demo.model.Signup;
import com.example.demo.model.Status;



public class CommonValidation {
	
	ConstantValue obj_Constant= new ConstantValue();
	
	public boolean CheckValidName(String Name)
	{
		if(Pattern.matches("[a-zA-Z]+",Name))
		{
			return true;
		}
		return false;
		
	}
	
	public boolean CheckValidDOB(String DOB)
	{
		int dd= Integer.parseInt(DOB.substring(0,2));
		int mm= Integer.parseInt(DOB.substring(3,5));
		int yy= Integer.parseInt(DOB.substring(6));
		
		if((dd>new ConstantValue().ddmin && dd<new ConstantValue().ddmax) &&
			(mm>new ConstantValue().mmmin && mm<new ConstantValue().mmmax) &&
			(yy >new ConstantValue().yymin && yy<new ConstantValue().yymax))
		{
			return true;
		}
		return false;
	}
	
	public boolean ChceckValidContactno(String Contactno)
	{ 
		CommonValidation C_N_Obj =new CommonValidation();  
		if(Pattern.matches("[0-9]+", Contactno) == true )
		{
			return true;
		}
		return false;		
		
	}
	
	public boolean CheckValidEmail(String Email)
	{
		obj_Constant.setEmail(obj_Constant.emailList);
		for( String e : obj_Constant.emailList)
		{
			if(Email.matches(e))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean CheckValidUserid(String Userid)
	{
		if(Userid.length()<=10)
		{
			return true;
		}
		return false;
			
				
	}
	
	public boolean CheckValidaUserid(String Userid)
	{
		if(Userid.length()<=10)
		{
			return true;
		}
		return false;
	}
	
	public boolean CheckValidContactno(String Contactno)
	{
		CommonValidation Contact_no=new CommonValidation();
		if(Pattern.matches("[0-9]+", Contactno)== true)
		{
			return true;
		}
		return false;
	}

	public boolean CheckDuplicateUserid(List<Signup> alldata, String userid) {
		// TODO Auto-generated method stub
		for(Signup eachdata:alldata)
		{
			if(eachdata.getUserid().equals(userid))
			{
				return false;
			}
		}
		return true;
	}

	public boolean CheckValidHno(String hno) {
		// TODO Auto-generated method stub
		if(Pattern.matches("[0-9]+", hno)== true)
		{
			return true;
		}
		return false;
	}



	public boolean CheckValidAlphabate(String value) {
		// TODO Auto-generated method stub
		if(Pattern.matches("[a-zA-Z]+", value)==true)
		{
			return true;
		}
	return false;
	}

	public boolean CheckPassword(String newPassword, String confirmPassword) {
		
		// TODO Auto-generated method stub
		if(newPassword.equals(confirmPassword))
		{
			return true;
		}
		return false;
	}

	public boolean CheckValidAlphabet(String Statement) {
		if(Pattern.matches("[a-zA-Z]+", Statement)==true)
		{
			return true;
		}
	return false;
	}

		
	

	public boolean CheckValidMaritalStatus(String maritalStatus)
	{
		obj_Constant.setStatus(obj_Constant.StatusList);
		for(String s: obj_Constant.StatusList)
		{
			if(maritalStatus.matches(s))
			{
				return true;
			}
		}
		return false;
	}
		

	public boolean CheckValidOccupation(String occupation) {
		// TODO Auto-generated method stub
		if(Pattern.matches("[a-zA-Z]+",occupation))
		{
			return true;
		}
		return false;
	}

	public boolean CheckDuplicatecid(List<Status> alldata, String cid) {
		for(Status eachdata:alldata)
		{
			if(eachdata.getCID().equals(cid))
			{
				return true;
			}
		}
		return false;
	}


	public boolean CheckValidfullName(String fullName, List<Status> findAll, String cid) 
	{
		for(Status eachdata:findAll)
		{
			if(eachdata.getName().equals(fullName))
			{
				return true;
			}
		}
		return false;
	}

	public boolean CheckValidDateOfBirth(String dob, List<Status> findAll, String cid) 
	{
		for(Status eachdata:findAll)
		{
			if(eachdata.getDOB().equals(dob))
			{
				return true;
			}
		}
		return false;
	}

	public boolean ChechDuplicateAddress(List<BankAddress> findAll, String address, String cid) 
	{
		for(BankAddress eachdata:findAll)
		{
		     String hno=eachdata.getHno();
		     String street=eachdata.getStreet();
		     String city=eachdata.getCity();
		     String state=eachdata.getState();
		     String country=eachdata.getCountry();
		     String fullAddress=hno+" "+street+" "+city+" "+state+" "+country;
		     if(fullAddress.equals(address))
		     {
		    	 return true;
		     }
		}
		return false;	
	}

	public boolean CheckDuplicatecidInKyc(List<KycDetails> alldata, String cid) {
		for(KycDetails eachdata:alldata)
		{
			if(eachdata.getCID().equals(cid))
			{
				return false;
			}
		}
		return true;
	}

	public boolean CheckValidBalance(List<ActiveAccountHolder> alldata, String balance) {
		
		//balance validation
		Integer a= Integer.parseInt(balance);
		if(a%100==0 && a>500 && a<50000) {
			return true;
		}
		
		return false;
	}

	public boolean CheckValidAccountNo(List<ActiveAccountHolder> alldata, String accountNo) {
		for(ActiveAccountHolder eachdata:alldata) {
			if(eachdata.getAccountNo().equals(accountNo) && eachdata.getBlock().equalsIgnoreCase("No")) {
				return true;
			}
		}
		return false;
	}

	public boolean CheckValidBlock_reason(String block_reason, ArrayList<String> account_statusList) {
		for(String eachdata: account_statusList)
		{
			if(eachdata.equals(block_reason))
			{
				return true;
			}
		}
		return false;
	}



		
	

}
