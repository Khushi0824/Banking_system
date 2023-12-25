# -*- coding: utf-8 -*-
"""
Created on Sat Jul 15 15:12:17 2023

@author: khush
"""

from tkinter import *  # (*= from 0 to everything)
import requests
from tkinter import messagebox
import urllib.request
import ast



def fetchlog():
    ui=luserid_entry.get()
    api="http://localhost:8080/FetchlogDetails/"+ui
    #Call the api to get list
    
    #type-1
    '''response1=requests.get(api)
    response1=response1.text
    print(response1)
    print(type(response1))
    print("************************")'''
    
    
    response=urllib.request.urlopen(api)
    response=response.read()
    '''print(response)
    print(type(response))
    print("************************")'''
    
    
    dict_str = response.decode("UTF-8")
    data = list(ast.literal_eval(dict_str))
    count=0
    #fetch eachData from list
    for eachData in data:
       # print(eachData)
        date1=eachData['date']
        operation=eachData['operation']
        result=eachData['result']
       # print(date,operation,result)
        #fetch the current date in python
        from datetime import date
        today=date.today()
        date2=today.strftime("%Y-%m-%d")
        print(date1, date2)
       # print(date1)
        if(date1==date2 and operation=="Login" and result=="LoginFailed"):
            count=count+1
    print(count)
    if(count<=2):
        VALIDATE()
    else:
        messagebox.showerror('Error', 'Invalid attempt exceed')
    
        
        
    
    
    
#create captchaLog function
def captchaLog(ui,op,res):
    api="http://localhost:8080/Log/"+ui+"/"+op+"/"+res
    print(api)
    response=requests.get(api)
    response=response.text
    print(response)

def VALIDATE():
    ucaptcha=captcha_entry.get()
    if(ucaptcha==system_captcha):
        LOGIN()
        
    else:
        captchaLog(luserid_entry.get(), "Login", "CaptchaInvalid")
        messagebox.showerror("Error",'CaptchaMismatch')
        
    
def LOGIN():
    ui=luserid_entry.get()
    pw=lpassword_entry.get()
    if(ui=="" or pw==""):
        messagebox.showwarning("Warning","Insufficient data")
    else:   
        api="http://localhost:8080/Login/"+ui+"/"+pw
        response=requests.get(api)
        data =response.text
        
        if(data=="Success"):
            captchaLog(ui, "Login", "Success")
            messagebox.showinfo('Success','LoginSuccessful')
        else:
            messagebox.showerror('Error', 'Invalid input')
            captchaLog(ui, "Login", "LoginFailed")

def gotoverifyframe():
    login_frame.place_forget()
    verify_frame.place(x=100,y=200, height=500, width=1200)
        
    
def gotoemailverifypage():
    ui=fuserid_entry.get()
    if(ui==""):
        messagebox.showwarning("Warning","Insufficient data")
    else:
        api="http://localhost:8080/ValidUserid/"+ui
        response=requests.get(api)
        data =response.text
        
        if(data=="true"):
            messagebox.showinfo('Valid','Valid Userid')
            verify_frame.place_forget()
            captchaLog(ui, "UserIdInserted", "Valid")
            emailverify_frame.place(x=100,y=200, height=500, width=1200)
            
        else:
            messagebox.showerror('Error', 'Invalid input')
            captchaLog(ui, "UserIdInserted", "Failed")
            
        
def gotopasswordframe():
    ui=fuserid_entry.get()
    e=femail_entry.get()
    if(e==""):
        messagebox.showwarning("Warning","Insufficient data")
    else:
        api="http://localhost:8080/ValidEmail/"+ui+"/"+e
        response=requests.get(api)
        data =response.text
        
        if(data=="true"):
            messagebox.showinfo('Valid','Valid Email')
            emailverify_frame.forget()
            captchaLog(ui, "EmailIdInserted", "Valid")
            password_frame.place(x=100,y=200, height=500, width=1200)
        else:
             messagebox.showerror('Error', 'Invalid input')
             captchaLog(ui, "EmailIdInserted", "Invalid")

             
    
def gotoreset():
    ui=fuserid_entry.get()
    pw=password_entry.get()
    cp=cpassword_entry.get()
    if(ui=="" or pw=="" or cp==""):
        messagebox.showwarning("Warning","Insufficient data")
    elif(pw!=cp):
        
        messagebox.showwarning("Warning","Password doesn't match")
    else:
        api="http://localhost:8080/UpdatePassword/"+ui+"/"+pw+"/"+cp
        print(api)
        response=requests.get(api)
        data =response.text
        print(data)
        if(data=="Password Updated"):
            messagebox.showinfo('Updated','Password Updated')
            password_frame.place_forget()
            verify_frame.place_forget()
            emailverify_frame.place_forget()
            captchaLog(ui, "NewPasswordInserted", "PasswordUpdated")
            login_frame.place(x=300,y=200, height=350, width=950)
            
        else:
             messagebox.showerror('Error', 'Invalid input')
             captchaLog(ui, "NewPasswordInserted", "Invalid")

             
        
        
    
    
def gotosignuppage():
    login_frame.place_forget()
    signup_frame.place(x=100,y=200, height=500, width=1200)
   
def gotoaddresspage():
   
    n=name_entry.get()
    dob=dob_spinbox1.get()+"-"+dob_spinbox2.get()+"-"+dob_spinbox3.get()
    cn=contact_entry.get()
    e=email_entry.get()
    ui=userid_entry.get()
    if(n=="" or dob=="" or cn=="" or e=="" or ui==""):
        messagebox.showwarning("Warning","Insufficient data")
    else:
        api="http://localhost:8080/CreateAccount/"+n+"/"+dob+"/"+cn+"/"+e+"/"+ui
        response=requests.get(api)
        data =response.text
        
        a=data.split(" ")
        b=a[0]
        global password
        password= a[1]
        
        if(b==ui):
            messagebox.showinfo("success","Valid")
            captchaLog(ui, "PersonalDataInserted", "Success")
            signup_frame.place_forget()
            address_frame.place(x=100,y=200, height=500, width=1200)
        else:
           messagebox.showerror("Error","Invalid")
           captchaLog(ui, "PersonalDataInserted", "Failed")

           
    
def gotologinpage():
    no=Hno_entry.get()
    s=street_entry.get()
    c=city_entry.get()
    st=state_entry.get()
    co=country_entry.get()
    pc=pincode_entry.get()
    ui=userid_entry.get()
   
    if(no=="" or s=="" or c=="" or st=="" or co=="" or pc=="" or ui=="" ):
        messagebox.showwarning("Warning","Insufficient data")
    else:
        api="http://localhost:8080/SaveAdress/"+no+"/"+s+"/"+c+"/"+st+"/"+co+"/"+ui
        
        response=requests.get(api)
        data =response.text
        
        if(data=="Success"):
            messagebox.showinfo("success","Success")
            address_frame.place_forget()
            captchaLog(ui, "AddressDataInserted", "Success")
            login_frame.place(x=300,y=200, height=350, width=950)
            #display the password 
            password_window=Tk()
            password_window.geometry('500x500')
            label=Label(password_window, text="Your password is:"+password)
            label.place(x=0, y=0)
            password_window.mainloop()
            
        else:
            messagebox.showerror("Error", "Failure")
            captchaLog(ui, "AddressDataInserted", "Failed")

              
   
    
    
    
w=Tk()
w.state('zoomed')

#**************************Login frsame*****************************

login_frame=Frame(w,bg='purple2')
login_frame.place(x=300,y=200, height=350, width=950)
user_label=Label(login_frame, text="ENTER DETAILS")
user_label.place(x=400,y=5)
user_label=Label(login_frame,text="USERID:")
user_label.place(x=10,y=40)
luserid_entry=Entry(login_frame)
luserid_entry.place(x=100,y=40)
password_label=Label(login_frame,text="PASSWORD :")
password_label.place(x=10,y=70)
lpassword_entry=Entry(login_frame, show="*")
lpassword_entry.place(x=100,y=70)
captcha_label=Label(login_frame, text="1234")
captcha_label.place(x=100, y=95)
#call the API to generate the captcha
def generateCaptcha():
    api="http://localhost:8080/GenerateCaptcha"
    response=requests.get(api)
    global system_captcha
    system_captcha=response.text
    print(system_captcha) 
    #Set the response in label 
    captcha_label.configure(text=system_captcha)               
generateCaptcha()

captcha_entry=Entry(login_frame)
captcha_entry.place(x=100,y=120)

refresh_btn=Button(login_frame, text="Refresh", command=generateCaptcha)
refresh_btn.place(x=150,y=95)
login_btn=Button(login_frame,text="FORGET PASSWORD?", command=gotoverifyframe)
login_btn.place(x=100,y=200)
login_btn=Button(login_frame,text="LOGIN", command=fetchlog)
login_btn.place(x=400,y=200)
newuser_btn=Button(login_frame,text="NEW USER ?", command=gotosignuppage)
newuser_btn.place(x=700,y=200)

#**************************Login end*****************************

#**************************signup frsame*****************************
signup_frame=Frame(w,bg='purple2')

user_label=Label(signup_frame, text="SIGNUP")
user_label.place(x=400,y=5)
#signup_frame.place(x=100,y=200, height=500, width=1200)
name_Label=Label(signup_frame,text="NAME:")
name_Label.place(x=10,y=35)
name_entry=Entry(signup_frame)
name_entry.place(x=100,y=40)
dob_label=Label(signup_frame,text="DOB : ")
dob_label.place(x=10,y=55)
dob_spinbox1=Spinbox(signup_frame, from_=1,to_=31)
dob_spinbox2=Spinbox(signup_frame,from_=1, to_=12 )
dob_spinbox3=Spinbox(signup_frame,from_=1990, to_=2023)
dob_spinbox1.place(x=100,y=55)
dob_spinbox2.place(x=250,y=55)
dob_spinbox3.place(x=400,y=55)
contact_label=Label(signup_frame,text="CONTACT_NO :")
contact_label.place(x=10,y=70)
contact_entry=Entry(signup_frame)
contact_entry.place(x=100,y=70)
email_label=Label(signup_frame,text="EMAIL :")
email_label.place(x=10, y=85)
email_entry=Entry(signup_frame)
email_entry.place(x=100,y=85)
userid_label=Label(signup_frame,text="USERID :")
userid_label.place(x=10,y=100)
userid_entry=Entry(signup_frame)
userid_entry.place(x=100,y=100)

login_btn=Button(signup_frame,text="NEXT", command=gotoaddresspage)
login_btn.place(x=400,y=200)

#*********************SIGNUP END******************************************8

address_frame=Frame(w,bg='purple2')
user_label=Label(address_frame, text="ADDRESS DETAILS")
user_label.place(x=600,y=5)

#address_frame.place(x=100,y=200, height=500, width=1200)
Hno_Label=Label(address_frame,text="HNO :")
Hno_Label.place(x=10,y=40)
Hno_entry=Entry(address_frame)
Hno_entry.place(x=100,y=40)

street_label=Label(address_frame,text="STREET :")
street_label.place(x=10,y=55)
street_entry=Entry(address_frame)
street_entry.place(x=100,y=55)

city_label=Label(address_frame,text="CITY :")
city_label.place(x=10,y=70)
city_entry=Entry(address_frame)
city_entry.place(x=100,y=70)

state_label=Label(address_frame,text="STATE :")
state_label.place(x=10,y=85)
state_entry=Entry(address_frame)
state_entry.place(x=100,y=85)

country_label=Label(address_frame,text="COUNTRY:")
country_label.place(x=10,y=100)
country_entry=Entry(address_frame)
country_entry.place(x=100,y=100)
pincode_label=Label(address_frame,text="PINCODE:")
pincode_label.place(x=10,y=115)
pincode_entry=Entry(address_frame)
pincode_entry.place(x=100,y=115)
login_btn=Button(address_frame,text="NEXT", command=gotologinpage)
login_btn.place(x=600,y=200)


#Create userid verify frame
verify_frame=Frame(w,bg='purple2')
verify_label=Label(verify_frame, text="VERIFY DETAILS")
verify_label.place(x=400,y=5)
#verify_frame.place(x=100,y=200, height=500, width=1200)
verify_label=Label(verify_frame,text="USERID:")
verify_label.place(x=10,y=40)
fuserid_entry=Entry(verify_frame)
fuserid_entry.place(x=100,y=40)
next_btn=Button(verify_frame,text="NEXT", command=gotoemailverifypage)
next_btn.place(x=400,y=200)


#create email verify frame
emailverify_frame=Frame(w,bg='purple2')
email_label=Label(emailverify_frame, text="VERIFY EMAIL")
email_label.place(x=400,y=5)
#emailverify_frame.place(x=100,y=200, height=500, width=1200)
email_label=Label(emailverify_frame,text="EMAIL:")
email_label.place(x=10,y=40)
femail_entry=Entry(emailverify_frame)
femail_entry.place(x=100,y=40)
next_btn=Button(emailverify_frame,text="NEXT", command=gotopasswordframe)
next_btn.place(x=400,y=200)


#create set passwrod frame
password_frame=Frame(w,bg='purple2')
password_label=Label(password_frame, text="VERIFY PASSWORD")
password_label.place(x=400,y=5)
#password_frame.place(x=100,y=200, height=500, width=1200)
password_label=Label(password_frame,text="ENTER NEW PASSWORD:")
password_label.place(x=10,y=40)
password_entry=Entry(password_frame, show="*")
password_entry.place(x=200,y=40)
password_label=Label(password_frame,text="CONFIRM PASSWORD:")
password_label.place(x=10,y=70)
cpassword_entry=Entry(password_frame, show="*")
cpassword_entry.place(x=200,y=70)
reset_btn=Button(password_frame,text="RESET PASSWORD", command=gotoreset)
reset_btn.place(x=400,y=200)


w.mainloop()
