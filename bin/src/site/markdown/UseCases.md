# Use cases
## Use case for Add Patient
### Primary actor: Patient
### Preconditions
The doctor must be logged in
### Success guarantee
A patient is correctly saved into the system
### Main Success Scenario
1. A patient is accepted into the doctor's office
2. The doctor starts collecting user information
3. The doctor enters ahv number, name, surname, birth date and assigns the patient to his care
4. The system returns a confirmation message when the patient has been correctly saved into the system
### Extensions
If a patient with the same ahv number is already present in the system, print error message

## Use case For Login
### Primary Actor : Doctor
### Stakeholders and Interests: 
1. A Doctor wants to be the only person to have access to his patients and account
2. The Product Owner want the access to be fluid and private so all patient data are easily accessible and protected
3. The Product Owner want to ensure that without the right logins,no one could access any patient data
4. A doctor want to have a user-freindly login plateform that allows reseting the password in case he forgot it

### Scope: System management Access
### Precondition : Having an Account and the right password and E_mail

##Minimal Guarentees: a log file with all the access attempts time/date/ip
##Success Guarentees: Doctor have access to the platform 
##Trigger : Doctor want to access platform and manage his patients
##Main Sucess Scenario:
####1.Doctor/requestor: enter an access request request with an E_mail and password
####2.System/approver:Check if the E_mail is correct , then check if the password is correct for the E_mail Entred
####3.System/approver: Welcome message with notifications and patient updates 
####4.Doctor/requestor: Can save his login as cookies for his session if he is using his own computer
## Alternative and Extentions:
####1.1 The request sended is empty throws an error message(prompt) for empty E_mail and/or empty Password
####1.2 The request sended doesn't match any E_mail throws an error message(prompt) for unexisting Email
####1.3 The request sended provides a correct E_mail but a wrong password throws an error message (prompt) for Wrong password
####1.4 The Doctor ask for reset password , a mail sended with a a link to reset
##Frequency of use : while still logged in and session didn't expire
##Secondary Actor: observing health device data being automatically updated