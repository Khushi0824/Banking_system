package com.example.demo.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Constant.ConstantValue;
import com.example.demo.Validation.CommonValidation;
import com.example.demo.model.Signup;
import com.example.demo.model.Status;
import com.example.demo.model.AccountHolder;
import com.example.demo.model.ActiveAccountHolder;
import com.example.demo.model.Address;
import com.example.demo.model.BankAddress;
import com.example.demo.model.Complete_report;
import com.example.demo.model.Log;
import com.example.demo.model.KycDetails;
import com.example.demo.repository.AccountHolderInterface;
import com.example.demo.repository.ActiveAccountHolderInterface;
import com.example.demo.repository.Complete_reportInterface;
import com.example.demo.repository.KycDetailsInterface;
import com.example.demo.repository.addressInterface;
import com.example.demo.repository.bankAddressInterface;
import com.example.demo.repository.logInterface;
import com.example.demo.repository.signupInterface;
import com.example.demo.repository.statusInterface;


@RestController
public class Api {
	
	//create signup interface object
	@Autowired
	signupInterface signupInterfaceDetails;
	//create address interface object
	@Autowired
	addressInterface addressInterfaceDetails;
	//ReadCreateAccount
	//create log interface object
	@Autowired
	logInterface logInterfaceDetails;
	@Autowired
	statusInterface statusInterfaceDetails;
	@Autowired
	bankAddressInterface bankAddressInterfaceDetails;
	@Autowired
	KycDetailsInterface KycInterfaceDetails;
	@Autowired
	AccountHolderInterface AccountHolderDetails;
	@Autowired
	ActiveAccountHolderInterface ActiveAccountHolderInterfaceDetails;
	@Autowired
	Complete_reportInterface Complete_reportInterfaceDetails;
	
	@RequestMapping("/CreateAccount/{Name}/{DOB}/{Contactno}/{Email}/{Userid}")
	String CreateAccounts(@PathVariable String Name, @PathVariable String DOB, @PathVariable String Contactno, @PathVariable String Email, @PathVariable String Userid)
	{
		//Validation
		
		if(new CommonValidation().CheckValidAlphabate(Name) &&
			new CommonValidation().CheckValidDOB(DOB)  &&
			new CommonValidation().ChceckValidContactno(Contactno) &&
			new CommonValidation().CheckValidEmail(Email) &&
			new CommonValidation().CheckValidUserid(Userid) &&
			new CommonValidation().CheckDuplicateUserid(signupInterfaceDetails.findAll(),Userid))
		{
			//insert the data in database
			/*
			 * 1. Inside the model, we created Signup class responsible for creating the table in database
			 * 2. we created interface to bind the table
			 * 3. using interface object we can insert the data
			 */
			 
			Signup signupDetails=new Signup();
			signupDetails.setName(Name);
			signupDetails.setDOB(DOB);
			signupDetails.setContactno(Contactno);
			signupDetails.setEmail(Email);
			signupDetails.setUserid(Userid);
			//generate password for the user
			Integer password=new Random().nextInt(10000,99999);    //Random is a function
			signupDetails.setPassword(password.toString());
			signupInterfaceDetails.save(signupDetails);
			
			return Userid+" "+password.toString();
			
			
		}
		return "Invalid";
		
		
	}
	
	
	@RequestMapping("/Login/{Userid}/{Password}")
	String Login(@PathVariable String Userid, @PathVariable String Password) {
		//variableValidation
		if(new CommonValidation().CheckValidUserid(Userid))
		{
			//database Validation
			
			List<Signup> alldata=signupInterfaceDetails.findAll();
			for(Signup eachdata:alldata) 
				{     
				//'alldata' stores all the data that's being inserted into the table and will be fetching it one by one
				if(Userid.equals(eachdata.getUserid()) &&Password.equals(eachdata.getPassword()) ) 
				{
					return "Success";
				}
				
			}
			
			
		}
		return "failed";
			
	}
	
	@RequestMapping("SaveAdress/{Hno}/{Street}/{City}/{State}/{Country}/{Userid}")
	String SaveAddress(@PathVariable String Hno, @PathVariable String Street, @PathVariable String City, @PathVariable String State, @PathVariable String Country,  @PathVariable String Userid )
	{
		//Validation
		if( new CommonValidation().CheckValidHno(Hno) &&
				new CommonValidation().CheckValidAlphabate(Street)&&
				new CommonValidation().CheckValidAlphabate(City)&&
				new CommonValidation().CheckValidAlphabate(State)&&
				new CommonValidation().CheckValidAlphabate(Country) &&
				! new CommonValidation().CheckDuplicateUserid(signupInterfaceDetails.findAll(),Userid)    //! is a symbol used for opposite functioning or opposite action
				)
		{
			Address addressDetails= new Address();
			addressDetails.setHno(Hno);
			addressDetails.setStreet(Street);
			addressDetails.setCity(City);
			addressDetails.setState(State);
			addressDetails.setCountry(Country);
			addressDetails.setUserid(Userid);
			addressInterfaceDetails.save(addressDetails);
			return "Success";
		

		}
		return "Failed";
	}
	
	@RequestMapping("/ValidUserid/{Userid}")
	boolean ValidUserid(@PathVariable String Userid)
	{
		if(! new CommonValidation().CheckDuplicateUserid(signupInterfaceDetails.findAll(),Userid) )
		{
			return true;
		}
		return false;
	}
	
	@RequestMapping("/ValidEmail/{Userid}/{Email}")
	boolean ValidEmail(@PathVariable String Userid, @PathVariable String Email)
	{
		List<Signup> alldata=signupInterfaceDetails.findAll();
		for(Signup eachdata : alldata) {
			if(Userid.equals(eachdata.getUserid()) && Email.equals(eachdata.getEmail())){
			   return true;
			}
		}
		return false;
	}
	
	@RequestMapping("/UpdatePassword/{Userid}/{NewPassword}/{ConfirmPassword}")
	String UpdatePassword(@PathVariable String Userid, @PathVariable String NewPassword, @PathVariable String ConfirmPassword)
	{
		if(! new CommonValidation().CheckDuplicateUserid(signupInterfaceDetails.findAll(), Userid) &&
				new CommonValidation().CheckPassword(NewPassword, ConfirmPassword))
		{
			//-1. Fetch the system id for the given userid
			 for(Signup eachdata: signupInterfaceDetails.findAll())
			 {
				 if(eachdata.getUserid().equals(Userid))
				 {
					 Integer sid=eachdata.getId();
					// step-2. Create backup of data
					 Signup new_object= new Signup();
					 new_object.setName(eachdata.getName());
					 new_object.setDOB(eachdata.getDOB());
					 new_object.setContactno(eachdata.getContactno());
					 new_object.setEmail(eachdata.getEmail());
					 new_object.setUserid(eachdata.getUserid());
					 new_object.setPassword(NewPassword);
					 
					//Step-3 Delete the entire the data
					 signupInterfaceDetails.deleteById(sid);
			       //Step-4 insert the backup data
					 signupInterfaceDetails.save(new_object);
					 
					 return "Password Updated"; 
				 }
			 }	
		}
		return "Invalid";
	}
	
	@RequestMapping("/GenerateCaptcha")
	String GenerateCaptcha()
	{
		Integer captcha= new Random().nextInt(10000,99999);
		return captcha.toString();
		
		
	}
	
	@RequestMapping("/Log/{Userid}/{Operation}/{Result}")
	String Log(@PathVariable String Userid, @PathVariable String Operation, @PathVariable String Result)
	{
		//Validation
		if(! new CommonValidation().CheckDuplicateUserid(signupInterfaceDetails.findAll(), Userid) &&
				new CommonValidation().CheckValidAlphabet(Operation) &&
				new CommonValidation().CheckValidAlphabet(Result))
		{
			Log logDetails= new Log();
			logDetails.setUserid(Userid);
			logDetails.setOperation(Operation);
			logDetails.setResult(Result);
			
			Date todayDate= new Date();
			
			//fetch the time
			SimpleDateFormat dateformat=new SimpleDateFormat("HH:mm");
			String d1=dateformat.format(todayDate);
			logDetails.setTime(d1);
			//fetch the date
			SimpleDateFormat dateformat1=new SimpleDateFormat("YYYY-MM-dd");
			String d2=dateformat1.format(todayDate);
			logDetails.setDate(d2);			
			logInterfaceDetails.save(logDetails);
			return "Returned";
			
		}
		return "Failure";
	}
	
	@RequestMapping("/FetchlogDetails/{Userid}")
	ArrayList<com.example.demo.model.Log> FetchlogDetails(@PathVariable String Userid, Signup eachdata)
	{
		
		//validation
		ArrayList<Log> listdatta= new ArrayList<>();
		List<Log> alldetails  = logInterfaceDetails.findAll();
		if(! new CommonValidation().CheckDuplicateUserid(signupInterfaceDetails.findAll(), Userid))
		{
			for(Log eachdata1: alldetails)
			 {
				 if(eachdata1.getUserid().equals(Userid))
				 {
					
					 listdatta.add(eachdata1);
				 }
	
		     }
		}
		return listdatta;    
	}
	
	@RequestMapping("/PersonalDetails/{Name}/{DOB}/{ContactNo}/{Email}/{MaritalStatus}/{Occupation}")
	Status PersonalDetails(@PathVariable String Name, @PathVariable String DOB, @PathVariable String ContactNo, @PathVariable String Email, 
			@PathVariable String MaritalStatus, @PathVariable String Occupation)
	{
		//validation
		if(new CommonValidation().CheckValidAlphabate(Name) &&
				new CommonValidation().CheckValidDOB(DOB)  &&
				new CommonValidation().ChceckValidContactno(ContactNo) &&
				new CommonValidation().CheckValidEmail(Email) &&
				new CommonValidation().CheckValidMaritalStatus(MaritalStatus) &&
				new CommonValidation().CheckValidOccupation(Occupation) )
		{
			Status personalDetails=new Status();
			personalDetails.setName(Name);
			personalDetails.setDOB(DOB);
			personalDetails.setContactno(ContactNo);
			personalDetails.setEmail(Email);
			personalDetails.setMaritalStatus(MaritalStatus);
			personalDetails.setOccupation(Occupation);
			
			Integer cid=new Random().nextInt(10000,99999);    //Random is a function
			personalDetails.setCID(cid.toString());
		
			statusInterfaceDetails.save(personalDetails);
			return personalDetails;
			
		}
		return null;	
	}
	
	@RequestMapping("AddressDetails/{Hno}/{Street}/{City}/{State}/{Country}/{cid}")
	String AddressDetails(@PathVariable String Hno, @PathVariable String Street, @PathVariable String City, @PathVariable String State, @PathVariable String Country,  @PathVariable String cid )
	{
		       //Validation
				if( new CommonValidation().CheckValidHno(Hno) &&
						new CommonValidation().CheckValidAlphabate(Street)&&
						new CommonValidation().CheckValidAlphabate(City)&&
						new CommonValidation().CheckValidAlphabate(State)&&
						new CommonValidation().CheckValidAlphabate(Country) &&
						new CommonValidation().CheckDuplicatecid(statusInterfaceDetails.findAll(),cid)    //! is a symbol used for opposite functioning or opposite action
						)
				{
					BankAddress bankAddressDetails= new BankAddress();
					bankAddressDetails.setHno(Hno);
					bankAddressDetails.setStreet(Street);
					bankAddressDetails.setCity(City);
					bankAddressDetails.setState(State);
					bankAddressDetails.setCountry(Country);
					bankAddressDetails.setCid(cid);
					bankAddressInterfaceDetails.save(bankAddressDetails);
					return "Success";
				}
				return "Failed";
		
	}
	
	@RequestMapping("KycDetails/{cid}/{DOB}/{Address}/{FullName}")
	AccountHolder KycDetails(@PathVariable String cid, @PathVariable String DOB, @PathVariable String Address, @PathVariable String FullName)
	{
		if(new CommonValidation().CheckDuplicatecid(statusInterfaceDetails.findAll(),cid ) &&
		        new CommonValidation().CheckValidfullName(FullName,statusInterfaceDetails.findAll(),cid) &&
				new CommonValidation().CheckValidDateOfBirth(DOB,statusInterfaceDetails.findAll(),cid) &&
				new CommonValidation().ChechDuplicateAddress(bankAddressInterfaceDetails.findAll(),Address,cid) &&
				new CommonValidation().CheckDuplicatecidInKyc(KycInterfaceDetails.findAll(),cid)
				)
		{
			KycDetails kycDetails =new KycDetails();
			kycDetails.setCID(cid);
			kycDetails.setFullName(FullName);
			kycDetails.setFullAddress(Address);
			kycDetails.setAccount_Created("Yes");
			KycInterfaceDetails.save(kycDetails);
			//Generate Account number and create accountholderDetails table 
			
			AccountHolder accountHolder=new AccountHolder();
			Integer A1= new Random().nextInt(1000,9999);
			Integer A2= new Random().nextInt(1000,9999);
			Integer A3= new Random().nextInt(1000,9999);
			String acc=A1.toString()+A2.toString()+A3.toString();
			
			Date todaysDate= new Date();
			SimpleDateFormat dateformat1=new SimpleDateFormat("YYYY-MM-dd");
			String d2=dateformat1.format(todaysDate);
			
			Integer P1= new Random().nextInt(1000,9999);
			
			
			accountHolder.setCID(cid);
			accountHolder.setAccountNo(acc);
			accountHolder.setAccountCreatedOn(d2);
			accountHolder.setAccountPin(P1.toString());
			accountHolder.setAccountStatus("Inactive");
			AccountHolderDetails.save(accountHolder);
			
			return accountHolder;
			
		}
		return null;
		
				
	}
	
	@RequestMapping("/ActivateAccountNo")
	String ActivateAccountNo()
	{
		 SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	        // Create a calendar object with today date. Calendar is in java.util package.
	        Calendar calendar = Calendar.getInstance();
	        // Move calendar to yesterday
	        calendar.add(Calendar.DATE, -0);
	        // Get current date of calendar which point to the yesterday now
	        Date yesterday = calendar.getTime();
	        String oneweekbeforedate=dateFormat.format(yesterday).toString();
	        for(AccountHolder eachdata:AccountHolderDetails.findAll()) {
	        	if(eachdata.getAccountCreatedOn().equals(oneweekbeforedate) && eachdata.getAccountStatus().equals("Inactive")) {
	        		//update operation
	        		int cid=eachdata.getId();
					// step-2. Create backup of data
					 AccountHolder new_object= new AccountHolder();
					 new_object.setCID(eachdata.getCID());
					 new_object.setAccountNo(eachdata.getAccountNo());
					 new_object.setAccountCreatedOn(eachdata.getAccountCreatedOn());
					 new_object.setAccountPin(eachdata.getAccountPin());
					 new_object.setAccountStatus("Active"); 
					//Step-3 Delete the entire the data
					 AccountHolderDetails.deleteById(cid);
			       //Step-4 insert the backup data
					 AccountHolderDetails.save(new_object);
					 ActiveAccountHolder acc_details= new ActiveAccountHolder();
					 acc_details.setAccountNo(new_object.getAccountNo());
					 acc_details.setBalance("0");
					 acc_details.setBlock("No");
					 ActiveAccountHolderInterfaceDetails.save(acc_details);
					 
	        	}
	        }
	        return "operation complted";      
	}
	
	@RequestMapping("DepositAmount/{AccountNo}/{Balance}")
	String DepositAmount(@PathVariable String AccountNo, @PathVariable String Balance)
	{
		if(new CommonValidation().CheckValidAccountNo(ActiveAccountHolderInterfaceDetails.findAll(),AccountNo)&& 
				new CommonValidation().CheckValidBalance(ActiveAccountHolderInterfaceDetails.findAll(),Balance))
		{
			
			for(ActiveAccountHolder eachdata: ActiveAccountHolderInterfaceDetails.findAll())
			 {
				 if(eachdata.getAccountNo().equals(AccountNo))
				 {
					 ActiveAccountHolder new_obj=new ActiveAccountHolder();
					 int sid=eachdata.getId();
					 new_obj.setAccountNo(eachdata.getAccountNo());
					 new_obj.setBalance(eachdata.getBalance());
					 new_obj.setBlock(eachdata.getBlock());
					 Integer newBalance=(Integer.parseInt(Balance)+Integer.parseInt(new_obj.getBalance()));
					 new_obj.setBalance(newBalance.toString());	 
					 ActiveAccountHolderInterfaceDetails.deleteById(sid);
				       //Step-4 insert the backup data
					 ActiveAccountHolderInterfaceDetails.save(new_obj);
					 return "Deposited";
				 }
			
			 }
		return "Try again";		
				
		}
		return Balance;
	}
	
	@RequestMapping("ReportAccountBlock/{AccountNo}/{Block_reason}")
	String ReportAccountBlock(@PathVariable String AccountNo, @PathVariable String Block_reason)
	{
		ConstantValue obj=new ConstantValue();
		obj.SetAccount_status(obj.Account_statusList);
		
		if(new CommonValidation().CheckValidAccountNo(ActiveAccountHolderInterfaceDetails.findAll(),AccountNo)&&
				new CommonValidation().CheckValidBlock_reason(Block_reason,obj.Account_statusList))
		{
			for(ActiveAccountHolder eachdata: ActiveAccountHolderInterfaceDetails.findAll())
			{
				if(eachdata.getAccountNo().equals(AccountNo) && eachdata.getBlock().equalsIgnoreCase("No"))
				{
					ActiveAccountHolder obje=new ActiveAccountHolder();
					obje.setAccountNo(eachdata.getAccountNo());
					obje.setBalance(eachdata.getBalance());
					obje.setBlock("Yes");
					
					ActiveAccountHolderInterfaceDetails.deleteById(eachdata.getId());
					
					ActiveAccountHolderInterfaceDetails.save(obje);
					
					Complete_report details=new Complete_report();
					details.setAccountNo(eachdata.getAccountNo());
					details.setBlocking_date(new Date().toString());
					details.setBlock_reason(Block_reason);
					
					Complete_reportInterfaceDetails.save(details);
					return "Card Block Successfully";
					
				}
			}
		}
		return "Card Blocking Failed";
	}
	
}

