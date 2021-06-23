# Use cases
## Use case for Add Patient
### Primary actor:
 Patient
### Preconditions
The doctor must be logged in
### Success guarantee
A patient is correctly saved into the system
### Main Success Scenario
1. A patient is accepted into the doctor's office
2. The doctor starts collecting user information
3. The doctor enters ahv number, name, surname, birth date and assigns the patient to his care
### Extensions
If a patient with the same ahv number is already present in the system, print error message

## Use case For Login
### Primary Actor 
Doctor
### Scope
System management Access
### Precondition
A valid doctor account saved in the database 
### Success guarentees
Credentials provided match credentials of a doctor in the database 
### Main Success Scenario
1. The doctor inserts email and passwords in the login
2. The system checks the inserted credentials against doctors in the database
3. The system logs the user into the system
### Extensions
1. The authentication request sent is empty. Throw an error message for empty E_mail and/or empty password.
2. The credentials don't match any doctor in the database. Throw an error for credentials wrong. 

## Use case for Start Observation
### Primary Actor
Patient
### Scope
Observation managment
### Precondition
an existing free Patient, a valid observation period 
### Sucess guarentees
Patient don't have an observation period , start date and end date are valid dates
### Main Sucess Scenario
1. the doctor choses a Patient
2. the doctor enter a start Date and end Date
3. System checks the validy of dates
4. System checks the availablity of the patient
### Extentions
1.1 a patient is under observation, throw an error message 
2.1 not valid dates , throw an error message
